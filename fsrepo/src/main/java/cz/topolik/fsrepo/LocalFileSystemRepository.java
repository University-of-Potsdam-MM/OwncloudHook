/**
 * Copyright (c) 2012 Tomáš Polešovský
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package cz.topolik.fsrepo;

import static cz.topolik.fsrepo.Constants.ADD_GROUP_PERMISSIONS;
import static cz.topolik.fsrepo.Constants.ADD_GUEST_PERMISSIONS;
import static cz.topolik.fsrepo.Constants.ROOT_FOLDER;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.BaseRepositoryImpl;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.RepositoryEntryUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

import cz.topolik.fsrepo.mapper.FileSystemRepositoryEnvironment;
import cz.topolik.fsrepo.mapper.FileSystemRepositoryIndexer;
import cz.topolik.fsrepo.mapper.FileSystemRepositoryMapper;
import cz.topolik.fsrepo.model.FileSystemFileEntry;
import cz.topolik.fsrepo.model.FileSystemFileVersion;
import cz.topolik.fsrepo.model.FileSystemFolder;
import de.unipotsdam.elis.chemistry.opencmis.inmemory.storedobj.api.Fileable;
import de.unipotsdam.elis.owncloud.OwncloudService;
import de.unipotsdam.elis.webdav.WebdavDocumentImpl;
import de.unipotsdam.elis.webdav.WebdavFolderImpl;
import de.unipotsdam.elis.webdav.WebdavIdDecoderAndEncoder;
import de.unipotsdam.elis.webdav.WebdavObjectStore;

/**
 * @author Tomas Polesovsky
 */
// TODO: PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE - check ACCESS + VIEW
// on the parent folder
public class LocalFileSystemRepository extends BaseRepositoryImpl {

	private static Log _log = LogFactoryUtil
			.getLog(LocalFileSystemRepository.class);
	private static boolean notInitialized;
	private FileSystemRepositoryEnvironment environment;
	private LocalFileSystemLocalRepository localRepository;
	private ExpandoColumn expandoColumn;

	private long lastShareUpdate = System.currentTimeMillis() - 30001;

	public LocalFileSystemRepository() {
		setRepositoryId(RepositoryStartupAction.repoId);
		localRepository = new LocalFileSystemLocalRepository(this);
	}

	public static synchronized WebdavObjectStore getWebdavRepository() {
		long userId = PrincipalThreadLocal.getUserId();
		String username = null;
		String password = null;
		if (userId == 0l) {
			username = "test";
			password = "test";
		} else {
			try {
				username = UserLocalServiceUtil.getUser(userId).getLogin();
				password = PrincipalThreadLocal.getPassword();
				LocalFileSystemRepository.notInitialized = false;
			} catch (PortalException e) {
				LocalFileSystemRepository.notInitialized = true;
			} catch (SystemException e) {
				LocalFileSystemRepository.notInitialized = true;
			}
		}

		return new WebdavObjectStore("A1", username, password);
	}

	@Override
	public LocalRepository getLocalRepository() {
		return localRepository;
	}

	@Override
	public void initRepository() throws PortalException, SystemException {
		try {
			if (_log.isInfoEnabled()) {
				_log.info("Initializing FileSystemRepository for: "
						+ getRootFolder());
			}

			environment = new FileSystemRepositoryEnvironment();
			environment.setRepository(this);
			environment.setMapper(new FileSystemRepositoryMapper(environment));
			environment
					.setIndexer(new FileSystemRepositoryIndexer(environment));

			expandoColumn = ExpandoColumnLocalServiceUtil
					.getDefaultTableColumn(getCompanyId(),
							RepositoryEntry.class.getName(),
							Constants.ABSOLUTE_PATH);
			if (expandoColumn == null) {
				ExpandoTable table = ExpandoTableLocalServiceUtil
						.fetchDefaultTable(getCompanyId(),
								RepositoryEntry.class.getName());
				if (table == null) {
					table = ExpandoTableLocalServiceUtil.addDefaultTable(
							getCompanyId(), RepositoryEntry.class.getName());
				}
				expandoColumn = ExpandoColumnLocalServiceUtil.addColumn(
						table.getTableId(), Constants.ABSOLUTE_PATH,
						ExpandoColumnConstants.STRING);
			}

			//
			//
			// boolean indexOnStartup =
			// GetterUtil.getBoolean(PropsUtil.get(Constants.FSREPO_INDEX_ON_STARTUP),
			// false);
			// if (indexOnStartup) {
			// if (_log.isInfoEnabled()) {
			// _log.info("Forced reindexing of " + getRootFolder());
			// }
			// environment.getIndexer().reIndex(true);
			// }
		} catch (PortalException e) {
			_log.error(e);
			throw e;
		} catch (SystemException e) {
			_log.error(e);
			throw e;
		}

	}

	public FileSystemRepositoryEnvironment getEnvironment() {
		return environment;
	}

	@Override
	public List<Object> getFoldersAndFileEntries(long folderId, int start,
			int end, OrderByComparator obc) throws SystemException {
		start = start == QueryUtil.ALL_POS ? 0 : start;
		end = end == QueryUtil.ALL_POS ? Integer.MAX_VALUE : end;

		List<Object> result = new ArrayList<Object>();
		try {
			LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId,
					ActionKeys.VIEW);
			WebdavFolderImpl systemFolder = (WebdavFolderImpl) folderIdToFile(folderId);
			if (systemFolder.canRead()) {
				for (Fileable file : loadFilesFromDisk(systemFolder, 0)) {
					// if (file.canRead()) {
					if (file instanceof WebdavFolderImpl) {
						Folder f = fileToFolder(file);
						if (f != null) {
							result.add(f);
						}
					} else {
						FileEntry f = fileToFileEntry(file);
						if (f != null) {
							result.add(f);
						}
					}
					// }
					if (obc == null && result.size() > end) {
						return result.subList(start < 0 ? 0 : start,
								end > result.size() ? result.size() : end);
					}
				}
			}

		} catch (PortalException ex) {
			_log.error(ex);
			throw new SystemException(ex);
		}

		if (obc != null) {
			Collections.sort(result, obc);
		}
		result = result.subList(start < 0 ? 0 : start,
				end > result.size() ? result.size() : end);
		return result;
	}

	@Override
	public List<Object> getFoldersAndFileEntries(long folderId,
			String[] mimeTypes, int start, int end, OrderByComparator obc)
			throws PortalException, SystemException {
		return getFoldersAndFileEntries(folderId, start, end, obc);
	}

	@Override
	public int getFoldersAndFileEntriesCount(long folderId)
			throws SystemException {
		try {
			Fileable dir = folderIdToFile(folderId);
			if (dir == null) {
				return 0;
			}
			return loadFilesFromDisk(dir, 0).size();
		} catch (PortalException e) {
			throw new SystemException(e);
		}
	}

	@Override
	public int getFoldersAndFileEntriesCount(long folderId, String[] mimeTypes)
			throws PortalException, SystemException {
		return getFoldersAndFileEntriesCount(folderId);
	}

	public String[] getSupportedConfigurations() {
		return new String[] { "FILESYSTEM" };
	}

	public String[][] getSupportedParameters() {
		return new String[][] { { ROOT_FOLDER, ADD_GUEST_PERMISSIONS,
				ADD_GROUP_PERMISSIONS } };
	}

	public FileEntry addFileEntry(long folderId, String sourceFileName,
			String mimeType, String title, String description,
			String changeLog, InputStream is, long size,
			ServiceContext serviceContext) throws PortalException,
			SystemException {
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId,
				ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId,
				ActionKeys.ADD_DOCUMENT);
		WebdavFolderImpl directory = (WebdavFolderImpl) folderIdToFile(folderId);
		if (directory.exists(getWebdavRepository()) && directory.canWrite()) {
			// Fileable file = new File(directory, sourceFileName);
			WebdavDocumentImpl documentImpl = new WebdavDocumentImpl(
					directory.getId() + sourceFileName);
			try {
				return fileToFileEntry(createFileWithInputStream(mimeType,
						title, is, size, documentImpl));
				// StreamUtil.transfer(is, new FileOutputStream(file), true);
				// return fileToFileEntry(file);
			} catch (Exception ex) {
				_log.error(ex);
				throw new SystemException(ex);
			}
		} else {
			throw new SystemException("Directory " + directory
					+ " cannot be read!");
		}
	}

	public Folder addFolder(long parentFolderId, String title,
			String description, ServiceContext serviceContext)
			throws PortalException, SystemException {
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(),
				parentFolderId, ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(),
				parentFolderId, ActionKeys.ADD_SUBFOLDER);
		WebdavFolderImpl subDir = (WebdavFolderImpl) folderIdToFile(parentFolderId);
		if (subDir.exists(getWebdavRepository()) && subDir.canWrite()) {
			// folder.mkdir();
			WebdavFolderImpl folder = getWebdavRepository().createFolder(title,
					subDir.getId());
			return fileToFolder(folder);
		} else {
			throw new SystemException("Parent directory " + subDir
					+ " cannot be read!");
		}
	}

	/*
	 * 6.1 vs. 6.2 breaking API change, return type changed from void to
	 * FileVersion :((
	 */
	public FileVersion cancelCheckOut(long fileEntryId) throws PortalException,
			SystemException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void checkInFileEntry(long fileEntryId, boolean major,
			String changeLog, ServiceContext serviceContext)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void checkInFileEntry(long fileEntryId, String lockUuid)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void checkInFileEntry(long fileEntryId, String lockUuid,
			ServiceContext serviceContext) throws PortalException,
			SystemException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	// 6.1 CE
	public FileEntry checkOutFileEntry(long fileEntryId)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	// 6.1 EE

	public FileEntry checkOutFileEntry(long fileEntryId,
			ServiceContext serviceContext) throws PortalException,
			SystemException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	// 6.1 CE
	public FileEntry checkOutFileEntry(long fileEntryId, String owner,
			long expirationTime) throws PortalException, SystemException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	// 6.1 EE

	public FileEntry checkOutFileEntry(long fileEntryId, String owner,
			long expirationTime, ServiceContext serviceContext)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public FileEntry copyFileEntry(long groupId, long fileEntryId,
			long destFolderId, ServiceContext serviceContext)
			throws PortalException, SystemException {
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(),
				fileEntryId, ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), destFolderId,
				ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), destFolderId,
				ActionKeys.ADD_DOCUMENT);
		WebdavDocumentImpl srcFile = (WebdavDocumentImpl) fileEntryIdToFile(fileEntryId);
		WebdavFolderImpl destDir = (WebdavFolderImpl) folderIdToFile(destFolderId);
		if (!srcFile.exists()) {
			throw new SystemException("Source file " + srcFile
					+ " cannot be read!");
		}
		if (!destDir.exists(getWebdavRepository()) || !destDir.canWrite()) {
			throw new SystemException(
					"Cannot write into destination directory " + destDir);
		}

		// Fileable dstFile = new File(destDir, srcFile.getName());
		String newFileId;
		try {
			newFileId = destDir.getId() + srcFile.getName();
			getWebdavRepository().copy(srcFile.getId(), newFileId);
			// StreamUtil.transfer(new FileInputStream(srcFile),
			// new FileOutputStream(dstFile), true);
			// return fileToFileEntry(dstFile);
		} catch (Exception ex) {
			_log.error(ex);
			throw new SystemException(ex);
		}
		return fileToFileEntry(new WebdavDocumentImpl(newFileId));
	}

	public void deleteFileEntry(long fileEntryId) throws PortalException,
			SystemException {
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(),
				fileEntryId, ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(),
				fileEntryId, ActionKeys.DELETE);
		WebdavDocumentImpl file = (WebdavDocumentImpl) fileEntryIdToFile(fileEntryId);
		if (!file.exists() || !file.canWrite()) {
			throw new SystemException(
					"File doesn't exist or cannot be modified " + file);
		}

		file.delete();
		RepositoryEntryUtil.remove(fileEntryId);
	}

	public void deleteFolder(long folderId) throws PortalException,
			SystemException {
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId,
				ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId,
				ActionKeys.DELETE);
		WebdavFolderImpl folder = (WebdavFolderImpl) folderIdToFile(folderId);
		if (!folder.exists(getWebdavRepository()) || !folder.canWrite()) {
			throw new SystemException(
					"Folder doesn't exist or cannot be modified " + folder);
		}

		folder.delete(getWebdavRepository());
		RepositoryEntryUtil.remove(folderId);
	}

	public List<FileEntry> getFileEntries(long folderId, int start, int end,
			OrderByComparator obc) throws SystemException {
		start = start == QueryUtil.ALL_POS ? 0 : start;
		end = end == QueryUtil.ALL_POS ? Integer.MAX_VALUE : end;
		try {
			LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId,
					ActionKeys.VIEW);
		} catch (PrincipalException ex) {
			throw new SystemException(ex);
		}
		List<FileEntry> result = new ArrayList<FileEntry>();
		try {
			WebdavFolderImpl systemFolder = (WebdavFolderImpl) folderIdToFile(folderId);
			if (systemFolder.canRead()) {
				for (Fileable file : loadFilesFromDisk(systemFolder, 2)) {
					if (!(file instanceof WebdavFolderImpl)) {
						FileEntry f = fileToFileEntry(file);
						if (f != null) {
							result.add(f);
						}
					}
					if (obc == null && result.size() > end) {
						return result.subList(start < 0 ? 0 : start,
								end > result.size() ? result.size() : end);
					}
				}
			}

		} catch (PortalException ex) {
			_log.error(ex);
			throw new SystemException(ex);
		}

		if (obc != null) {
			Collections.sort(result, obc);
		}
		result = result.subList(start < 0 ? 0 : start,
				end > result.size() ? result.size() : end);
		return result;

	}

	public List<FileEntry> getFileEntries(long folderId, long fileEntryTypeId,
			int start, int end, OrderByComparator obc) throws SystemException {
		return new ArrayList<FileEntry>();
	}

	public List<FileEntry> getFileEntries(long folderId, String[] mimeTypes,
			int start, int end, OrderByComparator obc) throws PortalException,
			SystemException {
		return getFileEntries(folderId, start, end, obc);
	}

	public int getFileEntriesCount(long folderId) throws SystemException {
		try {
			Fileable dir = folderIdToFile(folderId);
			if (dir == null) {
				return 0;
			}
			return loadFilesFromDisk(dir, 2).size();
		} catch (PortalException e) {
			throw new SystemException(e);
		}
	}

	public int getFileEntriesCount(long folderId, long fileEntryTypeId)
			throws SystemException {
		return getFileEntriesCount(folderId);
	}

	public int getFileEntriesCount(long folderId, String[] mimeTypes)
			throws PortalException, SystemException {
		return getFileEntriesCount(folderId);
	}

	public FileEntry getFileEntry(long fileEntryId) throws PortalException,
			SystemException {
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(),
				fileEntryId, ActionKeys.VIEW);
		return fileToFileEntry(fileEntryIdToFile(fileEntryId));
	}

	public FileEntry getFileEntry(long folderId, String title)
			throws PortalException, SystemException {
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId,
				ActionKeys.VIEW);
		FileEntry entry = fileToFileEntry(new WebdavDocumentImpl(
				folderIdToFile(folderId).getId() + title));
		if (entry == null) {
			throw new PrincipalException();
		}
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(),
				entry.getFileEntryId(), ActionKeys.VIEW);
		return entry;
	}

	public FileEntry getFileEntryByUuid(String uuid) throws PortalException,
			SystemException {
		try {
			RepositoryEntry repositoryEntry = RepositoryEntryUtil.findByUUID_G(
					uuid, getGroupId());

			LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(),
					repositoryEntry.getRepositoryEntryId(), ActionKeys.VIEW);

			return getFileEntry(repositoryEntry.getRepositoryEntryId());
		} catch (NoSuchRepositoryEntryException nsree) {
			throw new NoSuchFileEntryException(nsree);
		} catch (SystemException se) {
			throw se;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}

	}

	public FileVersion getFileVersion(long fileVersionId)
			throws PortalException, SystemException {
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(),
				fileVersionId, ActionKeys.VIEW);
		return fileToFileVersion(fileVersionIdToFile(fileVersionId));
	}

	public Folder getFolder(long folderId) throws PortalException,
			SystemException {
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId,
				ActionKeys.VIEW);
		return fileToFolder(folderIdToFile(folderId));
	}

	public Folder getFolder(long parentFolderId, String title)
			throws PortalException, SystemException {
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(),
				parentFolderId, ActionKeys.VIEW);
		Folder f = fileToFolder(new WebdavFolderImpl(folderIdToFile(
				parentFolderId).getId()
				+ title));
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(),
				f.getFolderId(), ActionKeys.VIEW);
		return f;
	}

	public List<Folder> getFolders(long parentFolderId,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc) throws PortalException, SystemException {
		start = start == QueryUtil.ALL_POS ? 0 : start;
		end = end == QueryUtil.ALL_POS ? Integer.MAX_VALUE : end;
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(),
				parentFolderId, ActionKeys.VIEW);
		String fileSystemDirectory = folderIdToFile(parentFolderId).getId();
		WebdavFolderImpl dir = new WebdavFolderImpl(fileSystemDirectory);
		if (dir.canRead()) {
			List<Folder> result = new ArrayList<Folder>();
			for (Fileable subDir : loadFilesFromDisk(dir, 1)) {
				Folder f = fileToFolder(subDir);
				if (f != null) {
					result.add(f);
				}
			}
			if (obc != null) {
				Collections.sort(result, obc);
			}
			result = result.subList(start < 0 ? 0 : start,
					end > result.size() ? result.size() : end);
			return result;

		}
		return new ArrayList<Folder>();
	}

	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
			throws PortalException, SystemException {
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(),
				parentFolderId, ActionKeys.VIEW);
		try {
			Fileable dir = folderIdToFile(parentFolderId);
			return loadFilesFromDisk(dir, 1).size();
		} catch (PortalException e) {
			throw new SystemException(e);
		}
	}

	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
			throws SystemException {
		int result = 0;
		for (Long folderId : folderIds) {
			if (LocalFileSystemPermissionsUtil.containsFolder(getGroupId(),
					folderId, ActionKeys.VIEW)) {
				result += getFileEntriesCount(folderId);
			}
		}
		return result;
	}

	public List<Folder> getMountFolders(long parentFolderId, int start,
			int end, OrderByComparator obc) throws SystemException {
		return new ArrayList<Folder>();
	}

	public int getMountFoldersCount(long parentFolderId) throws SystemException {
		return 0;
	}

	public void getSubfolderIds(List<Long> folderIds, long folderId)
			throws SystemException {
		// TODO: where is it used?
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<Long> getSubfolderIds(long folderId, boolean recurse)
			throws SystemException {
		try {
			LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId,
					ActionKeys.VIEW);
			List<Long> result = new ArrayList();
			List<Folder> folders = getFolders(folderId, false, 0,
					Integer.MAX_VALUE, null);
			for (Folder folder : folders) {
				if (LocalFileSystemPermissionsUtil.containsFolder(getGroupId(),
						folder.getFolderId(), ActionKeys.VIEW)) {
					result.add(folder.getFolderId());
					if (recurse) {
						result.addAll(getSubfolderIds(folder.getFolderId(),
								recurse));
					}
				}
			}
			return result;
		} catch (PortalException ex) {
			throw new SystemException(ex);
		}
	}

	public Lock lockFolder(long folderId) throws PortalException,
			SystemException {
		throw new UnsupportedOperationException();
	}

	public Lock lockFolder(long folderId, String owner, boolean inheritable,
			long expirationTime) throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	public FileEntry moveFileEntry(long fileEntryId, long newFolderId,
			ServiceContext serviceContext) throws PortalException,
			SystemException {
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(),
				fileEntryId, ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(),
				fileEntryId, ActionKeys.UPDATE);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), newFolderId,
				ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), newFolderId,
				ActionKeys.ADD_DOCUMENT);
		WebdavDocumentImpl fileToMove = (WebdavDocumentImpl) fileEntryIdToFile(fileEntryId);
		WebdavFolderImpl parentFolder = (WebdavFolderImpl) folderIdToFile(newFolderId);
		WebdavDocumentImpl dstFile = new WebdavDocumentImpl(
				parentFolder.getId() + fileToMove.getName());

		if (!fileToMove.exists()) {
			throw new SystemException("Source file doesn't exist: "
					+ fileToMove);
		}
		if (!parentFolder.exists(getWebdavRepository())) {
			throw new SystemException(
					"Destination parent folder doesn't exist: " + parentFolder);
		}
		if (dstFile.exists()) {
			throw new SystemException("Destination file does exist: " + dstFile);
		}
		if (fileToMove.canWrite() && parentFolder.canWrite()) {
			if (!fileToMove.renameTo(dstFile)) {
				throw new SystemException(
						"Moving was not successful (don't know why) [from, to]: ["
								+ fileToMove + ", " + dstFile + "]");
			}

			RepositoryEntry repositoryEntry = RepositoryEntryUtil
					.fetchByPrimaryKey(fileEntryId);
			RepositoryEntryUtil.update(repositoryEntry);
			try {
				saveFileToExpando(repositoryEntry, dstFile);
			} catch (FileNotFoundException ex) {
				throw new SystemException(ex.getMessage(), ex);
			}

			return fileToFileEntry(dstFile);
		} else {
			throw new SystemException(
					"Doesn't have rights to move the file [src, toParentDir]: ["
							+ fileToMove + ", " + parentFolder + "]");
		}
	}

	public Folder moveFolder(long folderId, long newParentFolderId,
			ServiceContext serviceContext) throws PortalException,
			SystemException {
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId,
				ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId,
				ActionKeys.UPDATE);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(),
				newParentFolderId, ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(),
				newParentFolderId, ActionKeys.ADD_SUBFOLDER);
		WebdavFolderImpl folderToMove = (WebdavFolderImpl) folderIdToFile(folderId);
		WebdavFolderImpl parentFolder = (WebdavFolderImpl) folderIdToFile(newParentFolderId);
		WebdavFolderImpl dstFolder = new WebdavFolderImpl(parentFolder.getId()
				+ folderToMove.getName());

		if (!getWebdavRepository().exists(folderToMove)) {
			throw new SystemException("Source folder doesn't exist: "
					+ folderToMove);
		}
		if (!getWebdavRepository().exists(parentFolder)) {
			throw new SystemException(
					"Destination parent folder doesn't exist: " + parentFolder);
		}
		if (!getWebdavRepository().exists(parentFolder)) {
			throw new SystemException("Destination folder does exist: "
					+ dstFolder);
		}
		if (folderToMove.canWrite() && parentFolder.canWrite()) {
			getWebdavRepository().rename(folderToMove.getId(),
					dstFolder.getId());

			RepositoryEntry repositoryEntry = RepositoryEntryUtil
					.fetchByPrimaryKey(folderId);
			RepositoryEntryUtil.update(repositoryEntry);
			try {
				saveFileToExpando(repositoryEntry, dstFolder);
			} catch (FileNotFoundException ex) {
				throw new SystemException(ex.getMessage(), ex);
			}

			return fileToFolder(dstFolder);
		} else {
			throw new SystemException(
					"Doesn't have rights to move the directory [srcDir, toParentDir]: ["
							+ folderToMove + ", " + parentFolder + "]");
		}
	}

	public Lock refreshFileEntryLock(String lockUuid, long companyId,
			long expirationTime) throws PortalException, SystemException {
		throw new UnsupportedOperationException("Not supported yet.");

	}

	public Lock refreshFolderLock(String lockUuid, long companyId,
			long expirationTime) throws PortalException, SystemException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Lock refreshFileEntryLock(String lockUuid, long expirationTime)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	public void revertFileEntry(long fileEntryId, String version,
			ServiceContext serviceContext) throws PortalException,
			SystemException {
		throw new UnsupportedOperationException();
	}

	public Hits search(long creatorUserId, int status, int start, int end)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Hits search(long creatorUserId, long folderId, String[] mimeTypes,
			int status, int start, int end) throws PortalException,
			SystemException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Hits search(SearchContext searchContext, Query query)
			throws SearchException {
		// TODO: implement indexing and add specific FILE_SYSTEM key into the
		// query
		System.out.println("Searched: " + query);
		return SearchEngineUtil.search(searchContext, query);
	}

	public void unlockFolder(long folderId, String lockUuid)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	public FileEntry updateFileEntry(long fileEntryId, String sourceFileName,
			String mimeType, String title, String description,
			String changeLog, boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext) throws PortalException,
			SystemException {
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(),
				fileEntryId, ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(),
				fileEntryId, ActionKeys.UPDATE);
		WebdavDocumentImpl file = (WebdavDocumentImpl) fileEntryIdToFile(fileEntryId);
		WebdavDocumentImpl dstFile = new WebdavDocumentImpl(
				WebdavIdDecoderAndEncoder.decodedIdToParentEncoded(file.getId())
						+ title);
		boolean toRename = false;
		if (!file.canWrite()) {
			throw new SystemException("Cannot modify file: " + file);
		}
		if (Validator.isNotNull(title) && !title.equals(file.getName())) {
			if (getWebdavRepository().exists(dstFile)) {
				throw new SystemException("Destination file already exists: "
						+ dstFile);
			}
			toRename = true;
		}
		if (size > 0) {

			// StreamUtil.transfer(is, new FileOutputStream(file));
			createFileWithInputStream(mimeType, title, is, size, dstFile);

		}
		if (toRename) {
			getWebdavRepository().rename(file.getId(), dstFile.getId());

			RepositoryEntry repositoryEntry = RepositoryEntryUtil
					.fetchByPrimaryKey(fileEntryId);
			RepositoryEntryUtil.update(repositoryEntry);
			try {
				saveFileToExpando(repositoryEntry, dstFile);
			} catch (FileNotFoundException ex) {
				throw new SystemException(ex.getMessage(), ex);
			}
		}
		return fileToFileEntry(dstFile);
	}

	private WebdavDocumentImpl createFileWithInputStream(String mimeType,
			String title, InputStream is, long size, WebdavDocumentImpl dstFile) {
		ContentStreamImpl contentStreamImpl = new ContentStreamImpl(title,
				new BigInteger(size + ""), mimeType, is);
		getWebdavRepository().createFile(title,
				dstFile.getParentDocument().getId(), contentStreamImpl);
		return dstFile;
	}

	public Folder updateFolder(long folderId, String title, String description,
			ServiceContext serviceContext) throws PortalException,
			SystemException {
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId,
				ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId,
				ActionKeys.UPDATE);
		if (title.contains(File.separator)) {
			throw new SystemException("Invalid character " + File.separator
					+ " in the title! [title]: [" + title + "]");
		}
		WebdavFolderImpl folder = (WebdavFolderImpl) folderIdToFile(folderId);
		if (!getWebdavRepository().exists(folder) || !folder.canWrite()) {
			throw new SystemException(
					"Folder doesn't exist or cannot be changed: " + folder);
		}
		Fileable newFolder = new WebdavFolderImpl(folder.getParentId() + title);
		LocalFileSystemRepository.getWebdavRepository().rename(folder.getId(),
				newFolder.getId());
		return fileToFolder(newFolder);
	}

	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	public boolean verifyInheritableLock(long folderId, String lockUuid)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	/* *****************
	 */

	protected RepositoryEntry findEntryFromExpando(Fileable file)
			throws SystemException {
		String className = RepositoryEntry.class.getName();
		long companyId = getCompanyId();
		ExpandoColumn col = ExpandoColumnLocalServiceUtil
				.getDefaultTableColumn(companyId, className,
						Constants.ABSOLUTE_PATH);

		DynamicQuery query = DynamicQueryFactoryUtil.forClass(
				ExpandoValue.class, PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("columnId", col.getColumnId()));

		try {
			query.add(RestrictionsFactoryUtil.eq("data",
					getCombinedExpandoValue(file)));
		} catch (FileNotFoundException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}

		List<ExpandoValue> result = (List<ExpandoValue>) ExpandoValueLocalServiceUtil
				.dynamicQuery(query);
		if (result.size() == 0) {
			return null;
		}
		long entryId = result.get(0).getClassPK();
		try {
			return RepositoryEntryUtil.findByPrimaryKey(entryId);
		} catch (NoSuchRepositoryEntryException ex) {
			_log.error(ex);
			throw new SystemException(ex);
		}
	}

	protected RepositoryEntry retrieveRepositoryEntry(Fileable file,
			Class modelClass) throws SystemException {
		RepositoryEntry repositoryEntry = findEntryFromExpando(file);

		if (repositoryEntry != null) {
			return repositoryEntry;
		}

		long repositoryEntryId = counterLocalService.increment();
		repositoryEntry = RepositoryEntryUtil.create(repositoryEntryId);
		repositoryEntry.setGroupId(getGroupId());
		repositoryEntry.setRepositoryId(getRepositoryId());
		repositoryEntry.setMappedId(LocalFileSystemRepository.class.getName()
				+ String.valueOf(repositoryEntryId));
		RepositoryEntryUtil.update(repositoryEntry);
		try {
			saveFileToExpando(repositoryEntry, file);
		} catch (Exception ex) {
			throw new SystemException(ex.getMessage(), ex);
		}

		try {
			long userId = UserLocalServiceUtil.getDefaultUserId(getCompanyId());
			if (PermissionThreadLocal.getPermissionChecker() != null) {
				userId = PermissionThreadLocal.getPermissionChecker()
						.getUserId();
			}
			ResourceLocalServiceUtil.addResources(getCompanyId(), getGroupId(),
					userId, modelClass.getName(), repositoryEntryId, false,
					addGroupPermissions(), addGuestPermissions());
		} catch (PortalException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}

		return repositoryEntry;
	}

	public Folder fileToFolder(Fileable folder) throws SystemException,
			PortalException {
		if (folder.getId().length() <= getRootFolder().getAbsolutePath()
				.length()) {
			Folder mountFolder = DLAppLocalServiceUtil
					.getMountFolder(getRepositoryId());
			if (!LocalFileSystemPermissionsUtil.containsFolder(getGroupId(),
					mountFolder.getFolderId(), ActionKeys.VIEW)) {
				return null;
			}
			return mountFolder;
		}

		RepositoryEntry entry = retrieveRepositoryEntry(folder, DLFolder.class);
		if (!LocalFileSystemPermissionsUtil.containsFolder(getGroupId(),
				entry.getRepositoryEntryId(), ActionKeys.VIEW)) {
			return null;
		}

		return new FileSystemFolder(this, entry.getUuid(),
				entry.getRepositoryEntryId(), (WebdavFolderImpl) folder);
	}

	public FileVersion fileToFileVersion(Fileable file) throws SystemException {
		return fileToFileVersion(file, null);
	}

	public FileVersion fileToFileVersion(Fileable file, FileEntry fileEntry)
			throws SystemException {
		RepositoryEntry entry = retrieveRepositoryEntry(file, DLFileEntry.class);

		if (!LocalFileSystemPermissionsUtil.containsFileEntry(getGroupId(),
				entry.getRepositoryEntryId(), ActionKeys.VIEW)) {
			return null;
		}

		FileSystemFileVersion fileVersion = new FileSystemFileVersion(this,
				entry.getRepositoryEntryId(), fileEntry, file);

		return fileVersion;
	}

	public FileEntry fileToFileEntry(Fileable file) throws SystemException {
		return fileToFileEntry(file, null);
	}

	public FileEntry fileToFileEntry(Fileable file, FileVersion fileVersion)
			throws SystemException {
		RepositoryEntry entry = retrieveRepositoryEntry(file, DLFileEntry.class);

		if (!LocalFileSystemPermissionsUtil.containsFileEntry(getGroupId(),
				entry.getRepositoryEntryId(), ActionKeys.VIEW)) {
			return null;
		}

		FileSystemFileEntry fileEntry = new FileSystemFileEntry(this,
				entry.getUuid(), entry.getRepositoryEntryId(), null,
				(WebdavDocumentImpl) file, fileVersion);

		try {
			long userId = PrincipalThreadLocal.getUserId();
			if (userId == 0) {
				userId = UserLocalServiceUtil.getDefaultUserId(getCompanyId());
			}
			dlAppHelperLocalService.checkAssetEntry(userId, fileEntry,
					fileEntry.getFileVersion());
		} catch (Exception e) {
			_log.error("Unable to update asset", e);
		}

		return fileEntry;
	}

	protected Fileable fileEntryIdToFile(long fileEntryId)
			throws PortalException, SystemException {

		RepositoryEntry repositoryEntry = RepositoryEntryUtil
				.fetchByPrimaryKey(fileEntryId);

		if (repositoryEntry == null) {
			throw new NoSuchFileEntryException(
					"No LocalFileSystem file entry with {fileEntryId="
							+ fileEntryId + "}");
		}
		if (!LocalFileSystemPermissionsUtil.containsFileEntry(getGroupId(),
				repositoryEntry.getRepositoryEntryId(), ActionKeys.VIEW)) {
			return null;
		}

		try {
			return getFileFromRepositoryEntry(repositoryEntry);
		} catch (FileNotFoundException ex) {
			RepositoryEntryUtil.remove(repositoryEntry.getRepositoryEntryId());
			throw new NoSuchFolderException(
					"File is no longer present on the file system!", ex);
		}
	}

	protected Fileable fileVersionIdToFile(long fileVersionId)
			throws PortalException, SystemException {

		RepositoryEntry repositoryEntry = RepositoryEntryUtil
				.fetchByPrimaryKey(fileVersionId);

		if (repositoryEntry == null) {
			throw new NoSuchFileVersionException(
					"No LocalFileSystem file version with {fileVersionId="
							+ fileVersionId + "}");
		}

		if (!LocalFileSystemPermissionsUtil.containsFileEntry(getGroupId(),
				repositoryEntry.getRepositoryEntryId(), ActionKeys.VIEW)) {
			return null;
		}

		try {
			return getFileFromRepositoryEntry(repositoryEntry);
		} catch (FileNotFoundException ex) {
			RepositoryEntryUtil.remove(repositoryEntry.getRepositoryEntryId());
			throw new NoSuchFolderException(
					"File is no longer present on the file system!", ex);
		}
	}

	protected Fileable folderIdToFile(long folderId) throws PortalException,
			SystemException {

		RepositoryEntry repositoryEntry = RepositoryEntryUtil
				.fetchByPrimaryKey(folderId);

		if (repositoryEntry != null) {

			if (!LocalFileSystemPermissionsUtil.containsFolder(getGroupId(),
					repositoryEntry.getRepositoryEntryId(), ActionKeys.VIEW)) {
				return null;
			}

			try {
				Fileable result = getFileFromRepositoryEntry(repositoryEntry);
				return result;
			} catch (FileNotFoundException ex) {
				RepositoryEntryUtil.remove(repositoryEntry
						.getRepositoryEntryId());
				throw new NoSuchFolderException(
						"Folder is no longer present on the file system!", ex);
			}
		}

		DLFolder dlFolder = DLFolderUtil.fetchByPrimaryKey(folderId);

		if (dlFolder == null) {
			throw new NoSuchFolderException(
					"No LocalFileSystem folder with {folderId=" + folderId
							+ "}");
		} else if (!dlFolder.isMountPoint()) {
			throw new RepositoryException(
					"LocalFileSystem repository should not be used with {folderId="
							+ folderId + "}");
		}

		repositoryEntry = retrieveRepositoryEntry(getRootFolder(),
				DLFolder.class);
		return getRootFolder();

	}

	protected Fileable getFileFromRepositoryEntry(RepositoryEntry entry)
			throws FileNotFoundException, SystemException, PortalException {
		return getFileFromExpando(entry);
	}

	protected String getCombinedExpandoValue(Fileable file)
			throws FileNotFoundException {
		String relativePath = file.getId().substring(
				getRootFolder().getAbsolutePath().length());
		return String.valueOf(getRepositoryId() + "-" + relativePath);
	}

	protected void saveFileToExpando(RepositoryEntry entry, Fileable file)
			throws FileNotFoundException, SystemException, PortalException {
		ExpandoValueLocalServiceUtil.addValue(getCompanyId(),
				expandoColumn.getTableId(), expandoColumn.getColumnId(),
				entry.getPrimaryKey(), getCombinedExpandoValue(file));
	}

	protected Fileable getFileFromExpando(RepositoryEntry entry)
			throws FileNotFoundException, SystemException, PortalException {
		ExpandoValue expandoValue = ExpandoValueLocalServiceUtil.getValue(
				expandoColumn.getTableId(), expandoColumn.getColumnId(),
				entry.getPrimaryKey());
		if (expandoValue == null) {
			throw new IllegalStateException(
					"Database is corrupted! Please recreate this repository!");
		}
		String value = expandoValue.getString();
		value = value.replace("-2F", "-%2F");
		String file = value.substring(value.indexOf("-") + 1);
		if (file == null) {
			throw new RuntimeException(
					"There is no absolute path in Expando for Repository Entry [id]: ["
							+ entry.getRepositoryEntryId() + "]");
		}
		// File f = new File(getRootFolder(), file);
		// if (!f.exists()) {
		// throw new
		// FileNotFoundException("File no longer exists on the file system: " +
		// f.getAbsolutePath());
		// }
		// return getRootFolder();
		return (Fileable) getWebdavRepository().getObjectById(file);
	}

	public WebdavFolderImpl getRootFolder() {
		String file = getTypeSettingsProperties().getProperty(ROOT_FOLDER);
		if (file == null) {
			throw new RuntimeException(
					"There is no ROOT_FOLDER configured for the repository [repositoryId]: ["
							+ getRepositoryId() + "]");
		}
		WebdavFolderImpl rootFolder = new WebdavFolderImpl(
				WebdavIdDecoderAndEncoder.LIFERAYROOTSYMBOL);

		try {
			final String rootDefaultPath = getSiteName();
			rootFolder.setId(WebdavIdDecoderAndEncoder.encode(rootDefaultPath));
			getWebdavRepository().createFolder(rootDefaultPath);
			createShareInOwncloud();			
		} catch (Exception e) {
			
		}

		return rootFolder;
	}

	private String getSiteName() throws PortalException, SystemException {
		Group group = GroupLocalServiceUtil.getGroup(this.getGroupId());
		String groupName = "liferay_workspace_" + group.getDescriptiveName();
		final String rootDefaultPath = "/" + groupName + "/";
		return rootDefaultPath;
	}

	private synchronized void createShareInOwncloud() throws SystemException,
			PortalException {

		if (System.currentTimeMillis() - this.lastShareUpdate < 30000) {
			return;
		}

		final String rootDefaultPath = getSiteName();
		final List<User> users = UserLocalServiceUtil.getGroupUsers(this
				.getGroupId());

		final Set<String> userStrings = new HashSet<String>();

		for (User user : users) {
			userStrings.add(user.getLogin());
		}

		if (!notInitialized) {
			long userId = PrincipalThreadLocal.getUserId();
			final String username = UserLocalServiceUtil.getUser(userId)
					.getLogin();
			final String password = PrincipalThreadLocal.getPassword();
			final WebdavObjectStore objectStore = getWebdavRepository();

			Thread shareCreatorthread = new Thread(new Runnable() {

				@Override
				public void run() {
					OwncloudService owncloudService = new OwncloudService(
							objectStore);
					owncloudService.createShare(getRepositoryId() + "",
							rootDefaultPath, userStrings, username, password);
				}
			});
			shareCreatorthread.start();
			this.lastShareUpdate = System.currentTimeMillis();
		}
	}

	public boolean addGuestPermissions() {
		return GetterUtil.getBoolean(
				getTypeSettingsProperties().getProperty(ADD_GUEST_PERMISSIONS),
				true);
	}

	public boolean addGroupPermissions() {
		return GetterUtil.getBoolean(
				getTypeSettingsProperties().getProperty(ADD_GROUP_PERMISSIONS),
				true);
	}

	protected List<Fileable> loadFilesFromDisk(Fileable dir, final int type) {
		List<Fileable> result = new ArrayList<Fileable>();
		// if(!dir.canRead()){
		// return result;
		// }
		String cacheKey = dir.getId();
		List<Fileable> cached = getFromCache(cacheKey);
		if (cached == null) {
			cached = getWebdavRepository().getChildrenForName(1000, 0,
					dir.getId()).getChildren();
			putToCache(cacheKey, cached);
		}
		for (Fileable f : cached) {
			switch (type) {
			case 2: {
				if (!(f instanceof WebdavFolderImpl)) {
					result.add(f);
				}
				break;
			}
			case 1: {
				if ((f instanceof WebdavFolderImpl)) {
					result.add(f);
				}
				break;
			}
			case 0:
			default: {
				result.add(f);
				break;
			}
			}
		}
		return result;
	}

	/*
	 * protected List<FileEntry> getFileEntriesFromDisk(File dir) throws
	 * SystemException { List<FileEntry> result = new ArrayList<FileEntry>();
	 * if(dir == null){ return result; } List cached =
	 * getFromCache(dir.getAbsolutePath()); if(cached != null){ return cached; }
	 * 
	 * if (dir.canRead()) { File[] files = loadFilesFromDisk(dir, 2); for (File
	 * file : files) { FileEntry f = fileToFileEntry(file); if (f != null) {
	 * result.add(f); } } }
	 * 
	 * }
	 * 
	 * protected List<Folder> getFoldersFromDisk(File dir) throws
	 * SystemException, PortalException { List<Folder> result = new
	 * ArrayList<Folder>(); if(dir == null){ return result; } List cached =
	 * getFromCache(dir.getAbsolutePath()); if(cached != null){ return cached; }
	 * 
	 * if (dir.canRead()) { File[] subDirectories = loadFilesFromDisk(dir, 1);
	 * for (File subDir : subDirectories) { Folder f = fileToFolder(subDir); if
	 * (f != null) { result.add(f); } } }
	 * 
	 * putToCache(dir.getAbsolutePath(), result); return result; }
	 */
	protected List<Fileable> getFromCache(String cacheKey) {
		String cacheName = new Exception().getStackTrace()[1].getMethodName();
		ThreadLocalCache<List<Fileable>> threadLocalCache = ThreadLocalCacheManager
				.getThreadLocalCache(Lifecycle.REQUEST, cacheName);
		return threadLocalCache != null ? threadLocalCache.get(cacheKey) : null;
	}

	protected void putToCache(String cacheKey, List<Fileable> value) {
		String cacheName = new Exception().getStackTrace()[1].getMethodName();
		ThreadLocalCache<List<Fileable>> threadLocalCache = ThreadLocalCacheManager
				.getThreadLocalCache(Lifecycle.REQUEST, cacheName);
		threadLocalCache.put(cacheKey, value);
	}

}
