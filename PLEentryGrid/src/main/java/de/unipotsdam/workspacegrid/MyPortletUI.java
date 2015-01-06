package de.unipotsdam.workspacegrid;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeServiceUtil;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.theme.ThemeDisplay;
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

	
	public final String finalWidth = "600px";
	public final String finalLength = "200px";
	public final String template1Name = "template1";
	public final String template2Name = "template2";
	public final String template3Name = "template3";

	@Override
	protected void init(VaadinRequest request) {
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
	
	/**
	 *  converts workspaceSlides to display items
	 * @param workspaceSlides
	 * @param gridLayout_1
	 */
	private void addWorkSpaceSlides(List<WorkspaceSlide> workspaceSlides,
			GridLayout gridLayout_1) {
		for (WorkspaceSlide slide : workspaceSlides) {
			WorkspaceSlideView space = new WorkspaceSlideView(slide);
			space.setVisible(true);
			gridLayout_1.addComponent(space);
		}
	}

	/**
	 * create grid layout
	 * @return
	 */
	private GridLayout initGrid() {
		GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.setColumns(6);
		gridLayout_1.setRows(4);
		gridLayout_1.setWidth(finalWidth); 
		gridLayout_1.setHeight(finalLength);
		gridLayout_1.setVisible(true);
		return gridLayout_1;
	}

	/**
	 * 
	 * @param workspaceSlides
	 */
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

	/**
	 * add Workspaces filered by template1Name
	 * @param workspaceSlides
	 */
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
				gruppenArbeiten, template1Name);
	}

	/**
	 * add Workspaces filered by template2Name
	 * @param workspaceSlides
	 */
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
				portfolios, template2Name);
	}

	/**
	 * add Workspaces filered by template3Name
	 * @param workspaceSlides
	 */
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
				template3Name);
	}	
}
