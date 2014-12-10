package org.up.liferay.owncloud;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.up.liferay.webdav.WebdavConfigurationLoader;
import org.up.liferay.webdav.WebdavObjectStore;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;


public class OwncloudShareCreator {
	
//	private static final Logger log = LoggerFactory
//			.getLogger(OwncloudShareCreator.class.getName());
	
	private static Log log = LogFactoryUtil.getLog(OwncloudShareCreator.class);
	
	public synchronized void createShare(Set<String> users, final String authorName, final String authorpasswd, final String sharepath, WebdavObjectStore store) {					
		users.remove(authorName);
		users.remove("anyone");
		users.remove("test");
		for (final String user : users) {
			store.rename(sharepath, sharepath+"backup"+new Date(System.currentTimeMillis()));
			Thread t = new Thread(new Runnable() {				
				@Override
				public void run() {					
					createShare(user, authorName, authorpasswd, sharepath);
					log.debug("finished creating shares" + sharepath + "User: " + user);
				}
			});		
			t.start();			
		}		
	}
	
	private void createShare (String user, String authorName, String authorpasswd, String sharepath) {
		HttpClient client = new HttpClient();
		String auth = authorName+":"+authorpasswd;
		String encoding = Base64.encodeBase64String(auth.getBytes());
				
		BufferedReader br = null;
		
		PostMethod method = new PostMethod(WebdavConfigurationLoader.getOwncloudShareAddress());

	    method.setRequestHeader("Authorization", "Basic " + encoding);
		

		method.addParameter("path", sharepath);
		method.addParameter("shareType", "0"); // share to a user
		method.addParameter("shareWith",  user);
		method.addParameter("permissions", "31"); // all permissions

		try {
			int returnCode = client.executeMethod(method);

			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				log.error("The Post method is not implemented by this URI");
				// still consume the response body
				method.getResponseBodyAsString();
			} else {
				br = new BufferedReader(new InputStreamReader(
						method.getResponseBodyAsStream()));
				String readLine;
				while (((readLine = br.readLine()) != null)) {
					log.warn(readLine);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			method.releaseConnection();
			if (br != null)
				try {
					br.close();
				} catch (Exception fe) {
					log.error("The reader could not be closed after creating share");
				}
		}
	}
}
