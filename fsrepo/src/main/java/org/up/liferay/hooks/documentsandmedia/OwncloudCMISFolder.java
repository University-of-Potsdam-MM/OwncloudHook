/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package org.up.liferay.hooks.documentsandmedia;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.cmis.model.CMISFolder;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.CMISRepositoryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Julian Dehne
 */
public class OwncloudCMISFolder extends CMISFolder {

	private static final Logger log = LoggerFactory.getLogger(OwncloudCMISFolder.class
			.getName());
	private org.apache.chemistry.opencmis.client.api.Folder _cmisFolder;

	public OwncloudCMISFolder(OwncloudCMISRepository cmisRepository, String uuid,
			long folderId,
			org.apache.chemistry.opencmis.client.api.Folder cmisFolder) {		
		super(cmisRepository, uuid, folderId, cmisFolder);
		this._cmisFolder = cmisFolder; //cannot change visibility of _cmisFolder field
	}

		
	

	@Override
	public Folder getParentFolder() throws PortalException, SystemException {
		Folder parentFolder = null;

		try {
			parentFolder = super.getParentFolder();

			if (parentFolder != null) {
				return parentFolder;
			}
		} catch (Exception e) {
		}

		if (_cmisFolder.isRootFolder()) {
			parentFolder = returnRootFolder();
			return parentFolder;
		} else {
			String path = _cmisFolder.getPath();
						
			if (path.endsWith("/")) {
				log.trace("changed computation of parent path to assume / in the end of folders [Julian-UP]");
				String tmp = path.substring(0, path.lastIndexOf("/"));
				path = path.substring(0, tmp.lastIndexOf("/") + 1);

				if (path.length() == 0) {
					path = StringPool.SLASH;
				}

				log.trace("changed computation of parent path to assume / to be root folder path [Julian-UP]");
				if (path.equals("/")) {
					parentFolder = returnRootFolder();
					return parentFolder;
				}
				
				log.warn("using fix for LPS-51879");
				
			} else {
				path = path.substring(0, path.lastIndexOf(CharPool.SLASH));
			}

			Session session = (Session) CMISRepositoryLocalServiceUtil
					.getSession(getRepositoryId());

			CmisObject parentCmisFolder = session.getObjectByPath(path);

			parentFolder = CMISRepositoryLocalServiceUtil.toFolder(
					getRepositoryId(), parentCmisFolder);
		}

		setParentFolder(parentFolder);

		return parentFolder;
	}

	private Folder returnRootFolder() throws PortalException, SystemException {
		Folder parentFolder;
		Folder folder = DLAppLocalServiceUtil.getMountFolder(getRepositoryId());

		parentFolder = folder.getParentFolder();
		return parentFolder;
	}
	
	
}