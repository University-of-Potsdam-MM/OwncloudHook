package de.unipotsdam.elis.workspacegrid.model;

import java.net.URL;
import java.util.List;

import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.vaadin.shared.ui.colorpicker.Color;

public class GroupToSlideConverter {
	public static void convertGroupsToSlides(List<WorkspaceSlide> workspaceSlides,
			List<Group> courses, String filterLabel) {
		for (Group course : courses) {								
			try {
				String courseUrL2 = PortalUtil.getPortalURL(CompanyLocalServiceUtil.getCompany(CompanyLocalServiceUtil.getCompanyIdByUserId(PrincipalThreadLocal.getUserId())).getVirtualHostname(), PortalUtil.getPortalPort(), true);								
				String courseUrL = courseUrL2 + "/web/" + course.getName()+ "/home";								
				workspaceSlides.add(new WorkspaceSlide(course.getName(), filterLabel, Color.GREEN, new URL(courseUrL), WorkspaceUtilService.getNumberOfActivitiesForSite(course)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
