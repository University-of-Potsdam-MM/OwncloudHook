package org.up.ple.workspaces;

import java.util.LinkedList;
import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;

public class WorkspaceUtilService {
	
	public static List<Group> getAllUserSites() throws PortalException, SystemException {
		long userId = PrincipalThreadLocal.getUserId();
		User user = UserLocalServiceUtil.getUser(userId);
		return user.getGroups();						
	}
	
	public static List<Group> getAllPortfolioSites() throws PortalException, SystemException {
		String filterString = "Portfolio";
		return filterSitesByTemplateName(filterString);
	}
	
	public static List<Group> getAllCourseSites() throws PortalException, SystemException {
		String filterString = "Kurs";
		return filterSitesByTemplateName(filterString);
	}
	
	public static List<Group> getAllGruppenarbeitssitesSites() throws PortalException, SystemException {
		String filterString = "Gruppenarbeit";
		return filterSitesByTemplateName(filterString);
	}
	
	public static List<Group> getOtherSites() throws PortalException, SystemException {
		List<Group> result = getAllUserSites();		
		result.removeAll(getAllPortfolioSites());
		result.removeAll(getAllCourseSites());
		result.removeAll(getAllGruppenarbeitssitesSites());
		return result;
	}

	public static List<Group> filterSitesByTemplateName(String filterString) throws PortalException, SystemException
	
			 {
		List<Group> userGroups = getAllUserSites();
		List<Group> result = new LinkedList<Group>();
		for (Group group : userGroups) {			
			try {
				if (LayoutSetPrototypeServiceUtil
				.getLayoutSetPrototype(group.getPublicLayoutSet().getLayoutSetPrototypeId())
				.getName().contains(filterString)) {
					result.add(group);
				}
			} catch (PortalException e) {				
			} catch (SystemException e) {				
			}					
		}	
		return result;
	}
	
	public static int getNumberOfActivitiesForSite(Group group) {		
		try {
			return SocialActivityLocalServiceUtil.getGroupActivitiesCount(group.getGroupId());
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
}
