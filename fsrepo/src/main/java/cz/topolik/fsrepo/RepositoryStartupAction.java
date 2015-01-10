/**
 * Copyright (c) 2012 Tomáš Polešovský
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
package cz.topolik.fsrepo;


import java.util.List;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.service.RepositoryEntryLocalServiceUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.persistence.RepositoryEntryUtil;

/**
 *
 * @author Tomas Polesovsky
 */
public class RepositoryStartupAction extends SimpleAction {

    private static Log _log = LogFactoryUtil.getLog(RepositoryStartupAction.class);
  
    public static long repoId = 007l;

    @Override
    public void run(String[] strings) throws ActionException {
    	System.out.println("start initialized owncloud repo 2222");
    	long companyId = GetterUtil.getLong(strings[0]);    	
    

//    	
    	       
    }

//    protected void initAll(long companyId) throws PortalException, SystemException {
//        List<Repository> repositories = RepositoryLocalServiceUtil.getRepositories(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
//        for (Repository repo : repositories) {
//            if (repo.getCompanyId() == companyId) {
//                init(repo);
//            }
//        }
//    }
//
//    protected void init(Repository repo) throws PortalException, SystemException {
//    	System.out.println(repo.getRepositoryId());
//        if (LocalFileSystemRepository.class.getName().equals(repo.getClassName())) {
//            // repository is initialized also during this instantiation
//            RepositoryLocalServiceUtil.getRepositoryImpl(repo.getRepositoryId());
//            repoId = repo.getRepositoryId();                        
//        }
//    }
}
