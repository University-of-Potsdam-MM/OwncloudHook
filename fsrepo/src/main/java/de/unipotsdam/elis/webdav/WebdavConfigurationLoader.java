package de.unipotsdam.elis.webdav;

import java.net.MalformedURLException;
import java.net.URL;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.util.portlet.PortletProps;

import cz.topolik.fsrepo.LocalFileSystemRepository;

public class WebdavConfigurationLoader {

	private static Log _log = LogFactoryUtil
			.getLog(WebdavConfigurationLoader.class);

	public static final String getOwnCloudAddress() {
		String result = PortletProps.get("owncloudaddress");

		if (result == null) {
			_log.error("could not load owncloudaddress from properties");
			result = "https://boxup.uni-potsdam.de/remote.php/webdav";
		}

		return result;

	}

	public static final String getOwncloudShareAddress() {
		String result = PortletProps.get("owncloudshareaddress");
		if (result == null) {
			_log.error("could not load owncloudaddress from properties");
			result = "https://boxup.uni-potsdam.de/ocs/v1.php/apps/files_sharing/api/v1/shares";
		}
		
		return result;
	}

	public static final String getEndpointPath() {
		try {
			return new URL(getOwnCloudAddress()).getPath();
		} catch (MalformedURLException e) {
			throw new Error(e);
		}
	}
}
