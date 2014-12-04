package org.up.liferay.webdav;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebdavConfigurationLoader {

	private static final Logger LOG = LoggerFactory
			.getLogger(WebdavConfigurationLoader.class);

	public static final String getOwnCloudAddress() {
		Properties prop = getProperties();
		return (String) prop.getProperty("owncloudaddress");
	}
	
	public static final Long getCacheTimeout() {
		Properties prop = getProperties();
		return Long.parseLong(prop.getProperty("cacheInvalidateTimeInSeconds")); 
	}
	
	public static final String getOwncloudShareAddress() {
		Properties prop = getProperties();
		return (String) prop.getProperty("owncloudshareaddress");	
	}


	private static Properties getProperties() {
		Properties prop = new Properties();
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("owncloud.properties");
		try {
			prop.load(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return prop;
	}
	
	
	public static final String getEndpointPath() {
		try {
			return new URL(getOwnCloudAddress()).getPath();
		} catch (MalformedURLException e) {
			throw new Error(e);
		}
	}
}
