package org.up.liferay.owncloud;

import java.util.Set;

import org.up.liferay.webdav.WebdavObjectStore;

public class OwncloudService  {

	private WebdavObjectStore sm;


	public OwncloudService(WebdavObjectStore sm) {		
		this.sm = sm;
	}
	
		
	public void createShare(String repositoryId, String objectId, Set<String> users, String username, String password ) {			
		OwncloudShareCreator creator = new OwncloudShareCreator();		
		creator.createShare(users, username, password, objectId, sm);					
	}	
}
