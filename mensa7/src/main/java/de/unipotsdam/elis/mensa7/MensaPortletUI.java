package de.unipotsdam.elis.mensa7;

import javax.servlet.ServletContext;

import org.bushe.swing.event.ThreadSafeEventService;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.unipotsdam.elis.mensa7.layout.MensaLayouter;
import de.unipotsdam.elis.mensa7.logic.PortletSessionManager;
import de.unipotsdam.elis.mensa7.logic.UIPersistanceLayer;

@Theme("MensaTheme")
@SuppressWarnings("serial")
@Widgetset("de.unipotsdam.elis.mensa7.AppWidgetSet")
public class MensaPortletUI extends UI {

	private static Log log = LogFactoryUtil.getLog(MensaPortletUI.class);
	public static ThreadSafeEventService eventbus = new ThreadSafeEventService();

	@Override
	protected void init(VaadinRequest request) {

		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		
//		VaadinServlet servlet = VaadinServlet.getCurrent();

		

		UIPersistanceLayer portletSessionManager = new PortletSessionManager();
		MensaLayouter mensaLayouter = new MensaLayouter(portletSessionManager);
		layout.addComponent(mensaLayouter.getFinalLayout());
		
		
	}

}
