package de.unipotsdam.elis.webdav;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import com.github.sardine.impl.SardineImpl;

public class WebdavEndpoint {
	private Sardine sardine;
	private String endpoint;
	private String user;
	private String password;

	public WebdavEndpoint(String username, String password) {		
		this.sardine = SardineFactory.begin();
		this.user = username;
		this.password =  password;		
		this.sardine.setCredentials(user,password);		
		this.endpoint = WebdavConfigurationLoader.getOwnCloudAddress();		
	}
	
	public WebdavEndpoint(String user, String password, String endpoint) {
		this.sardine = SardineFactory.begin();
		this.user = user;
		this.password = password;
		this.sardine.setCredentials(user,password);		
		this.endpoint = endpoint;
	}
	
	public Sardine getSardine() {
		return sardine;
	}

	public String getEndpoint() {
		return endpoint;
	}
	
	public String getUser() {
		return user;
	}	
	
	public Boolean isValidCredentialinDebug() {
		if (user == null || password == null) {
			return false;
		}
		return (user.equals("test") && password.equals("test"));
	}
	
	public Boolean isUserContextSet() {
		return user != null && password != null && !password.equals("false");
	}
	
}
