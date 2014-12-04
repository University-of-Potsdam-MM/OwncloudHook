package org.up.liferay.hooks.documentsandmedia;

import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.DefaultLocalRepositoryImpl;
import com.liferay.portal.kernel.util.OrderByComparator;

import cz.topolik.liferay.fsrepo.FSRepoRepositoryImpl;

public class OwncloudBaseRepository extends FSRepoRepositoryImpl {

	// public static final String ROOT_FOLDER = "ROOT_FOLDER";

	public OwncloudBaseRepository() {
		setFsRepo(new OwncloudRepository());
		setDefaultLocalRepository(new DefaultLocalRepositoryImpl(getFsRepo()));
	}
	

//	@Override
//	public String[][] getSupportedParameters() {
//		return new String[][] { {} };
//	}

}
