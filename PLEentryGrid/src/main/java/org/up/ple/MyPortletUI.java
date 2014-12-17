package org.up.ple;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;

import org.up.ple.workspaces.WorkspaceSlide;
import org.up.ple.workspaces.WorkspaceUtilService;
import org.up.ple.workspaces.view.WorkspaceSlideView;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedPortletSession;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
@SuppressWarnings("serial")
@Widgetset("org.up.ple.AppWidgetSet")
public class MyPortletUI extends UI {

	private static Log log = LogFactoryUtil.getLog(MyPortletUI.class);

	@Override
	protected void init(VaadinRequest request) {
		final String portletContextName = getPortletContextName(request);
		final Integer numOfRegisteredUsers = getPortalCountOfRegisteredUsers();
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

//		final Button button = new Button("Click Me");
//		button.addClickListener(new Button.ClickListener() {
//			public void buttonClick(ClickEvent event) {
//				layout.addComponent(new Label(
//						"Hello, World!<br>This is portlet "
//								+ portletContextName
//								+ ".<br>This portal has "
//								+ numOfRegisteredUsers
//								+ " registered users (according to the data returned by Liferay API call).",
//						ContentMode.HTML));
//
//			}
//		});
//		layout.addComponent(button);

		
		List<WorkspaceSlide> workspaceSlides = new LinkedList<WorkspaceSlide>();
		
		List<Group> courses = null;
		String filterLabel = "Course";
		
		try {
			courses = WorkspaceUtilService.getAllCourseSites();
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		convertGroupsToSlides(workspaceSlides, courses, filterLabel);
			
		List<Group> portfolios = null;
		String filterLabel2 = "Portfolio";
		
		try {
			portfolios = WorkspaceUtilService.getAllPortfolioSites();
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		convertGroupsToSlides(workspaceSlides, portfolios, filterLabel2);
		
		List<Group> gruppenArbeiten = null;
		String filterLabel3 = "Gruppenarbeit";
		
		try {
			gruppenArbeiten = WorkspaceUtilService.getAllGruppenarbeitssitesSites();
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		convertGroupsToSlides(workspaceSlides, gruppenArbeiten, filterLabel3);
		
		
		List<Group> andere = null;
		String filterLabel4 = "Andere";
		
		try {
			andere = WorkspaceUtilService.getOtherSites();
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		convertGroupsToSlides(workspaceSlides, andere, filterLabel4);
		
		
//		printDebug(layout, courses, filterLabel);
//		printDebug(layout, portfolios, filterLabel2);
//		printDebug(layout, gruppenArbeiten, filterLabel3);
		
		GridLayout gridLayout_1 = new GridLayout();	
		gridLayout_1.setColumns(6);
		gridLayout_1.setRows(4);		
		gridLayout_1.setWidth("600px");
		gridLayout_1.setHeight("200px");

		
		for (WorkspaceSlide slide : workspaceSlides) {
			WorkspaceSlideView space = new WorkspaceSlideView(slide);
			space.setVisible(true);
			gridLayout_1.addComponent(space);
//			layout.addComponent(space);
		}
		gridLayout_1.setVisible(true);
		layout.addComponent(gridLayout_1);
	}

	private void convertGroupsToSlides(List<WorkspaceSlide> workspaceSlides,
			List<Group> courses, String filterLabel) {
		for (Group course : courses) {								
			try {
				String courseUrL2 = PortalUtil.getPortalURL(CompanyLocalServiceUtil.getCompany(CompanyLocalServiceUtil.getCompanyIdByUserId(PrincipalThreadLocal.getUserId())).getVirtualHostname(), PortalUtil.getPortalPort(), true);								
				String courseUrL = courseUrL2 + "/web/" + course.getName()+ "/home";
				System.out.println(courseUrL);
				
				workspaceSlides.add(new WorkspaceSlide(course.getName(), filterLabel, Color.GREEN, new URL(courseUrL)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void printDebug(final VerticalLayout layout, List<Group> groups,
			String filterLabel) {
		String groupString = "";
		
		try {
						
			for (Group group : groups) {
				groupString+=group.getDescriptiveName();
			}
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {		

		}

		Label portfolioLabel = new Label(
				"the user is member of the following " + filterLabel + " sites: " + groupString);
		layout.addComponent(portfolioLabel);
	}

	private String getPortletContextName(VaadinRequest request) {
		WrappedPortletSession wrappedPortletSession = (WrappedPortletSession) request
				.getWrappedSession();
		PortletSession portletSession = wrappedPortletSession
				.getPortletSession();

		final PortletContext context = portletSession.getPortletContext();
		final String portletContextName = context.getPortletContextName();
		return portletContextName;
	}

	private Integer getPortalCountOfRegisteredUsers() {
		Integer result = null;

		try {
			result = UserLocalServiceUtil.getUsersCount();
		} catch (SystemException e) {
			log.error(e);
		}

		return result;
	}
}
