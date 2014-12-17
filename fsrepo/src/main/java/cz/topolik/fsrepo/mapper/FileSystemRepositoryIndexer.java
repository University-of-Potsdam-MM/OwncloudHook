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
package cz.topolik.fsrepo.mapper;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import de.unipotsdam.elis.chemistry.opencmis.inmemory.storedobj.api.Fileable;
import de.unipotsdam.elis.chemistry.opencmis.inmemory.storedobj.api.StoredObject;
import de.unipotsdam.elis.webdav.WebdavFolderImpl;
import de.unipotsdam.elis.webdav.WebdavObjectStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tomas Polesovsky
 */
public class FileSystemRepositoryIndexer {

    private static Log _log = LogFactoryUtil.getLog(FileSystemRepositoryIndexer.class);
    private FileSystemRepositoryEnvironment environment;
    private Thread asyncThread;
    private final List<Fileable> filesToIndex = new ArrayList<Fileable>();
	private WebdavObjectStore sm;

    public FileSystemRepositoryIndexer(FileSystemRepositoryEnvironment environment) {
        this.environment = environment;
//        this.sm = sm;
    }

    public List<Fileable> getActuallyIndexedFiles() {
        synchronized (filesToIndex) {
            List<Fileable> result = new ArrayList<Fileable>(filesToIndex.size());
            result.addAll(filesToIndex);
            return result;
        }
    }

    public boolean reIndex(boolean async) throws RepositoryException {
        final WebdavFolderImpl rootFolder = environment.getRepository().getRootFolder();
       
        try {
            final long companyId = environment.getRepository().getCompanyId();
            long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);
            if (LockLocalServiceUtil.hasLock(defaultUserId, FileSystemRepositoryIndexer.class.getName(), environment.getRepository().getRepositoryId())) {
                if (_log.isInfoEnabled()) {
                    _log.info("Skipping file system indexing of " + environment.getRepository().getRootFolder() + " because another file system indexing is in process");
                }
                return false;
            }
            LockLocalServiceUtil.lock(defaultUserId, FileSystemRepositoryIndexer.class.getName(), environment.getRepository().getRepositoryId(), FileSystemRepositoryIndexer.class.getName(), false, Time.HOUR);
            
            if (async) {
                //TODO: use message bus?
                asyncThread = new Thread() {

                    {
                        this.setDaemon(true);
                    }

                    @Override
                    public void run() {
                        try {
                            if (_log.isInfoEnabled()) {
                                _log.info("Indexing file system repository of: " + rootFolder);
                            }

                            FileSystemRepositoryIndexer.this.run(rootFolder);

                            if (!isInterrupted()) {
                                synchronized (filesToIndex) {
                                    environment.getMapper().addAll(filesToIndex);
                                    filesToIndex.clear();
                                }
                            }

                            if (_log.isInfoEnabled()) {
                                _log.info("Indexing file system repository of " + rootFolder + " finished.");
                            }
                        } finally {
                            try {
                                LockLocalServiceUtil.unlock(FileSystemRepositoryIndexer.class.getName(), environment.getRepository().getRepositoryId());
                            } catch (SystemException ex) {
                                _log.error("Cannot reindex filesystem " + rootFolder + ": " + ex.getMessage(), ex);
                            }
                        }
                    }
                };
                asyncThread.start();
            } else {
                try {
                    run(environment.getRepository().getRootFolder());
                    synchronized (filesToIndex) {
                        environment.getMapper().addAll(filesToIndex);
                        filesToIndex.clear();
                    }
                } finally {
                    try {
                        LockLocalServiceUtil.unlock(FileSystemRepositoryIndexer.class.getName(), environment.getRepository().getRepositoryId());
                    } catch (SystemException ex) {
                        _log.error("Cannot reindex filesystem " + rootFolder + ": " + ex.getMessage(), ex);
                    }
                }
            }
            return true;
        } catch (Exception ex) {
            _log.error("Cannot reindex file system " + rootFolder + ": " + ex.getMessage(), ex);
            return false;
        }
    }

    protected void run(StoredObject file) {
        if (asyncThread == null || !asyncThread.isInterrupted()) {
            index(file);

            if (file instanceof WebdavFolderImpl) {
                for (Fileable subFolder : sm.getFolderChildren((WebdavFolderImpl)file, 30, 0, null).getChildren()) {
                    run(subFolder);
                }
            }
        }
    }

    protected void index(StoredObject file) {
        if (_log.isDebugEnabled()) {
            _log.debug("Indexing: " + file.getId());
        }
        synchronized (filesToIndex) {
            filesToIndex.add((Fileable) file);
        }
    }
}
