package org.up.liferay.webdav;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.expando.model.ExpandoBridge;

public class WebdavFolder implements Folder {
	
	
	public Object clone() { return null; }
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Map<String, Serializable> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getPrimaryKey() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEscapedModel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Folder toEscapedModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Folder toUnescapedModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGroupId(long groupId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCompanyId(long companyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCreateDate(Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setModifiedDate(Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUserId(long userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUserName(String userName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUserUuid(String userUuid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getModelClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModelClassName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StagedModelType getStagedModelType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUuid(String uuid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsPermission(PermissionChecker permissionChecker,
			String actionId) throws PortalException, SystemException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Long> getAncestorFolderIds() throws PortalException,
			SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Folder> getAncestors() throws PortalException, SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getCompanyId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date getCreateDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getFolderId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getGroupId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date getLastPostDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getModifiedDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Folder getParentFolder() throws PortalException, SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getParentFolderId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getRepositoryId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getUserId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserUuid() throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUuid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasInheritableLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDefaultRepository() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMountPoint() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRoot() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSupportsLocking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSupportsMetadata() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSupportsMultipleUpload() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSupportsShortcuts() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSupportsSocial() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSupportsSubscribing() {
		// TODO Auto-generated method stub
		return false;
	}

}
