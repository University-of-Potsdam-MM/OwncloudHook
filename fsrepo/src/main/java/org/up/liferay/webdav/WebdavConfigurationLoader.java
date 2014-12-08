package org.up.liferay.webdav;

import java.net.MalformedURLException;
import java.net.URL;


import com.liferay.util.portlet.PortletProps;

public class WebdavConfigurationLoader {


	public static final String getOwnCloudAddress() {
		return PortletProps.get("owncloudaddress");
		
	}
	
	
	public static final String getOwncloudShareAddress() {		
		return PortletProps.get("owncloudshareaddress");
	}

	public static final String getEndpointPath() {
		try {
			return new URL(getOwnCloudAddress()).getPath();
		} catch (MalformedURLException e) {
			throw new Error(e);
		}
	}
}
