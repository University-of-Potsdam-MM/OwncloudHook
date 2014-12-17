package de.unipotsdam.workspacegrid;

import java.util.LinkedList;
import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Group;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.unipotsdam.elis.workspacegrid.model.GroupToSlideConverter;
import de.unipotsdam.elis.workspacegrid.model.WorkspaceSlide;
import de.unipotsdam.elis.workspacegrid.model.WorkspaceUtilService;
import de.unipotsdam.elis.workspacegrid.view.WorkspaceSlideView;

@Theme("workspacesgridtheme")
@SuppressWarnings("serial")
@Widgetset("org.up.ple.AppWidgetSet")
public class MyPortletUI extends UI {

	private static Log log = LogFactoryUtil.getLog(MyPortletUI.class);

	@Override
	protected void init(VaadinRequest request) {
		// final String portletContextName = getPortletContextName(request);
		// final Integer numOfRegisteredUsers =
		// getPortalCountOfRegisteredUsers();
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		List<WorkspaceSlide> workspaceSlides = new LinkedList<WorkspaceSlide>();
		addCourseSites(workspaceSlides);
		addPortfolioSites(workspaceSlides);
		addGruppenarbeitSlides(workspaceSlides);
		addOtherSlides(workspaceSlides);
		GridLayout gridLayout_1 = initGrid();
		addWorkSpaceSlides(workspaceSlides, gridLayout_1);
		layout.addComponent(gridLayout_1);
	}

	private void addWorkSpaceSlides(List<WorkspaceSlide> workspaceSlides,
			GridLayout gridLayout_1) {
		for (WorkspaceSlide slide : workspaceSlides) {
			WorkspaceSlideView space = new WorkspaceSlideView(slide);
			space.setVisible(true);
			gridLayout_1.addComponent(space);
		}
	}

	private GridLayout initGrid() {
		GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.setColumns(6);
		gridLayout_1.setRows(4);
		gridLayout_1.setWidth("600px");
		gridLayout_1.setHeight("200px");
		gridLayout_1.setVisible(true);
		return gridLayout_1;
	}

	private void addOtherSlides(List<WorkspaceSlide> workspaceSlides) {
		List<Group> andere = null;
		String filterLabel4 = "Andere";

		try {
			andere = WorkspaceUtilService.getOtherSites();
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}

		GroupToSlideConverter.convertGroupsToSlides(workspaceSlides, andere,
				filterLabel4);
	}

	private void addGruppenarbeitSlides(List<WorkspaceSlide> workspaceSlides) {
		List<Group> gruppenArbeiten = null;				
		
		
		try {
			gruppenArbeiten = WorkspaceUtilService
					.getAllGruppenarbeitssitesSites();
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}

		GroupToSlideConverter.convertGroupsToSlides(workspaceSlides,
				gruppenArbeiten, "template1");
	}

	private void addPortfolioSites(List<WorkspaceSlide> workspaceSlides) {
		List<Group> portfolios = null;		

		try {
			portfolios = WorkspaceUtilService.getAllPortfolioSites();
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}

		GroupToSlideConverter.convertGroupsToSlides(workspaceSlides,
				portfolios, "template2");
	}

	private void addCourseSites(List<WorkspaceSlide> workspaceSlides) {
		List<Group> courses = null;		

		try {
			courses = WorkspaceUtilService.getAllCourseSites();
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}

		GroupToSlideConverter.convertGroupsToSlides(workspaceSlides, courses,
				"template3");
	}

	// private String getPortletContextName(VaadinRequest request) {
	// WrappedPortletSession wrappedPortletSession = (WrappedPortletSession)
	// request
	// .getWrappedSession();
	// PortletSession portletSession = wrappedPortletSession
	// .getPortletSession();
	//
	// final PortletContext context = portletSession.getPortletContext();
	// final String portletContextName = context.getPortletContextName();
	// return portletContextName;
	// }
	//
	// private Integer getPortalCountOfRegisteredUsers() {
	// Integer result = null;
	//
	// try {
	// result = UserLocalServiceUtil.getUsersCount();
	// } catch (SystemException e) {
	// log.error(e);
	// }
	//
	// return result;
	// }
}
