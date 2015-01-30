package de.unipotsdam.elis.mensa7.layout;

import java.rmi.RemoteException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.RootLogger;

import com.vaadin.ui.Component;

import de.unipotsdam.elis.mensa7.MensaPortletUI;
import de.unipotsdam.elis.mensa7.events.GUIEvents;
import de.unipotsdam.elis.mensa7.events.GUIEventsHandler;
import de.unipotsdam.elis.mensa7.events.TruncatedDateHandler;
import de.unipotsdam.elis.mensa7.layout.components.SpeiseplanComposite;
import de.unipotsdam.elis.mensa7.logic.MensaService;
import de.unipotsdam.elis.mensa7.logic.SpeiseplanLogic;
import de.unipotsdam.elis.mensa7.logic.UIPersistanceLayer;
import de.unipotsdam.elis.provider.mensa.CampusTyp;
import de.unipotsdam.elis.provider.mensa.MensaNotAvailable;
import de.unipotsdam.elis.util.date.TruncatedDate;

public class SpeiseplanLayouter {
	public Logger logger = RootLogger.getLogger(SpeiseplanLayouter.class);
	private SpeiseplanComposite speiseplanComposite;
	private CampusTyp campusTyp;
	private SpeiseplanLogic speiseplanLogic;
	private final GUIEventsHandler guiEventsHandler;
	private final TruncatedDateHandler truncatedDateHandler;

	
	public SpeiseplanLayouter(CampusTyp campusTyp, UIPersistanceLayer portletSessionManager, MensaService mensaService) throws MensaNotAvailable, RemoteException {
		this.setCampusTyp(campusTyp);					
		this.speiseplanComposite = new SpeiseplanComposite();
		this.speiseplanComposite.getAdaptor().doyourjob(mensaService.getSpeiseplan(campusTyp), new TruncatedDate(new Date()));
		this.speiseplanLogic = new SpeiseplanLogic(this.speiseplanComposite.getAdaptor(), mensaService, portletSessionManager, campusTyp);
		// handling gui events 
		this.guiEventsHandler = new GUIEventsHandler(speiseplanLogic);
		this.truncatedDateHandler = new TruncatedDateHandler(speiseplanLogic);
		MensaPortletUI.eventbus.subscribeExactlyStrongly(GUIEvents.class, guiEventsHandler);
		MensaPortletUI.eventbus.subscribeExactlyStrongly(TruncatedDate.class, truncatedDateHandler);
	}

	public Component getSpeisePlanLayout() {		
		return speiseplanComposite;
	}

	public void setCampusTyp(CampusTyp campusTyp) {
		this.campusTyp = campusTyp;
	}

	public CampusTyp getCampusTyp() {
		return campusTyp;
	}

}
