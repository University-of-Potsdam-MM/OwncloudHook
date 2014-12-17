package de.unipotsdam.elis.webdav;

// TODO add user to key
public class WebdavResourceKey {
	private String encodedId;
	private Boolean getDirectory;
	private String userName;
	
	public WebdavResourceKey(String encodedId, Boolean getDirectory, String username) {		
		this.encodedId = encodedId;
		this.getDirectory = getDirectory;
		this.userName = username;
	}
	
	public String getEncodedId() {
		return encodedId;
	}
	public void setEncodedId(String encodedId) {
		this.encodedId = encodedId;
	}
	public Boolean getGetDirectory() {
		return getDirectory;
	}
	public void setGetDirectory(Boolean getDirectory) {
		this.getDirectory = getDirectory;
	}
	
	public String getUserName() {
		return userName;
	}
	
	@Override
	public boolean equals(Object obj) {
		WebdavResourceKey toCompare = (WebdavResourceKey) obj;
		return toCompare.getGetDirectory().equals(this.getDirectory) && toCompare.getEncodedId().equals(this.encodedId) && this.userName.equals(toCompare.getUserName());
	}
	
	@Override
	public int hashCode() {
		return (this.encodedId+this.getDirectory.toString()+this.getUserName()).hashCode();
	}
	
	
	
}
