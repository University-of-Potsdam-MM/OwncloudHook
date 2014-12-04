package org.up.liferay.hooks.documentsandmedia;

import static cz.topolik.liferay.fsrepo.Constants.ADD_GROUP_PERMISSIONS;
import static cz.topolik.liferay.fsrepo.Constants.ADD_GUEST_PERMISSIONS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.up.liferay.owncloud.OwncloudShareCreator;
import org.up.liferay.webdav.WebdavEndpoint;
import org.up.liferay.webdav.WebdavObjectStore;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import cz.topolik.liferay.fsrepo.FSRepo;
import cz.topolik.liferay.fsrepo.PortalMapper;

public class OwncloudRepository extends FSRepo {

	private static Log _log = LogFactoryUtil.getLog(OwncloudRepository.class);

	@Override
	public void initRepository() throws PortalException, SystemException {
	
	}
	
	@Override
	public Folder getFolder(long folderId) throws SystemException,
			PortalException {
		// TODO Auto-generated method stub
		return super.getFolder(folderId);
	}
	
	@Override
	public Folder getFolder(long parentFolderId, String title)
			throws SystemException, PortalException {
		// TODO Auto-generated method stub
		return super.getFolder(parentFolderId, title);
	}
	
	@Override
	public List<Folder> getFolders(long parentFolderId,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc) throws SystemException, PortalException {
		// TODO Auto-generated method stub
		return super.getFolders(parentFolderId, includeMountFolders, start, end, obc);
	}
	
	@Override
	public List<Folder> getFolders(long parentFolderId, int status,
			boolean includeMountfolders, int start, int end,
			OrderByComparator obc) throws PortalException, SystemException {
		// TODO Auto-generated method stub
		return super.getFolders(parentFolderId, status, includeMountfolders, start,
				end, obc);
	}
	
	@Override
	public List<FileEntry> getFileEntries(long folderId, int start, int end,
			OrderByComparator obc) throws SystemException {
		// TODO Auto-generated method stub
		return super.getFileEntries(folderId, start, end, obc);
	}
	
	@Override
	public List<FileEntry> getFileEntries(long folderId, long fileEntryTypeId,
			int start, int end, OrderByComparator obc) throws SystemException {
		// TODO Auto-generated method stub
		return super.getFileEntries(folderId, fileEntryTypeId, start, end, obc);
	}
	
	@Override
	public List<FileEntry> getFileEntries(long folderId, String[] mimeTypes,
			int start, int end, OrderByComparator obc) throws SystemException {
		// TODO Auto-generated method stub
		return super.getFileEntries(folderId, mimeTypes, start, end, obc);
	}
	
	@Override
	public List<Object> getFoldersAndFileEntries(long folderId, int start,
			int end, OrderByComparator obc) throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Object> getFoldersAndFileEntries(long folderId,
			String[] mimeTypes, int start, int end, OrderByComparator obc)
			throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getFoldersAndFileEntriesCount(long folderId)
			throws SystemException {
		// TODO Auto-generated method stub
		return super.getFoldersAndFileEntriesCount(folderId);
	}
	
	@Override
	public int getFoldersAndFileEntriesCount(long folderId, String[] mimeTypes)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		return super.getFoldersAndFileEntriesCount(folderId, mimeTypes);
	}
	
	


	private void createShare() throws PortalException, SystemException {
		Group group = GroupLocalServiceUtil.getGroup(getGroupId());
		String groupName = "liferay_workspace_" + group.getDescriptiveName();

		List<User> users = UserLocalServiceUtil
				.getGroupUsers(this.getGroupId());
		Set<String> userNames = new HashSet<String>();
		for (User user : users) {
			userNames.add(user.getLogin());
		}

		OwncloudShareCreator creator = new OwncloudShareCreator();
		User loggedInUser = UserLocalServiceUtil.getUser(getLoggedInUserId());
		
		String loggedInUsername = loggedInUser.getLogin();
		String loggedinUserPassword = loggedInUser.getPassword();
		WebdavEndpoint endpoint = new WebdavEndpoint(loggedInUsername, loggedinUserPassword);
		WebdavObjectStore objectStore = new WebdavObjectStore(endpoint);
		creator.createShare(userNames, loggedInUsername, loggedinUserPassword , groupName, objectStore);
	}
	
	

	private long getLoggedInUserId() throws PortalException, SystemException {
		ServiceContext serviceContext = ServiceContextThreadLocal
				.getServiceContext();
		if (null == serviceContext) {
			_log.warn("ServiceContext is unavailable, returning default user");
			long companyId = PortalUtil.getDefaultCompanyId();
			long defaultUserId = UserLocalServiceUtil
					.getDefaultUserId(companyId);
			return defaultUserId;
		}

		return serviceContext.getUserId();
	}

}
