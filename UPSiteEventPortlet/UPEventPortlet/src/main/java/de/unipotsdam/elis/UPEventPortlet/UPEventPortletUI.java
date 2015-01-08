package de.unipotsdam.elis.UPEventPortlet;

import java.util.List;

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedPortletSession;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("UPEventPortletTheme")
@SuppressWarnings("serial")
@Widgetset("de.unipotsdam.elis.UPEventPortlet.AppWidgetSet")
public class UPEventPortletUI extends UI {

	private static Log log = LogFactoryUtil.getLog(UPEventPortletUI.class);

	@Override
	protected void init(VaadinRequest request) {
		final String portletContextName = getPortletContextName(request);
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		
		// get group Id
		final ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		long groupId = themeDisplay.getScopeGroupId();
		long[] groupIds = new long[1];
		groupIds[0] = groupId;
		
		long startTime = System.currentTimeMillis();

		long maxDaysDisplayed = 7l;
		long endTime = 1000l*60l*60l*24l*maxDaysDisplayed;				
		endTime = endTime + startTime;		
		layout.addComponent(new Label(startTime+""));		
		layout.addComponent(new Label(endTime+""));
		
		
		List<CalEvent> result;
		try {
			result = CalEventLocalServiceUtil.getCalEvents(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			for (CalEvent calEvent : result) {
				System.out.println(calEvent);
			}
			
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
//		int[] statuses = { WorkflowConstants.STATUS_APPROVED };
//
//		try {
//			List<CalendarBooking> result = CalendarBookingServiceUtil.search(themeDisplay.getCompanyId(), groupIds, null, null, -1, null, System.currentTimeMillis(), endTime, true, statuses, QueryUtil.ALL_POS,
//					QueryUtil.ALL_POS, null);
//			
//			for (CalendarBooking calendarBooking : result) {
//				layout.addComponent(new Label(calendarBooking.toString()));
//				System.out.println(calendarBooking.toString());
//			}
//		} catch (PortalException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
				
		// layout.add...
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

}
