package de.unipotsdam.elis.mensa7.logic;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.RootLogger;
import org.joda.time.DateTime;

import de.unipotsdam.elis.mensa7.events.GUIEvents;
import de.unipotsdam.elis.mensa7.events.MensaEventManager;
import de.unipotsdam.elis.mensa7.layout.viewmodel.SpeiseplanAdaptor;
import de.unipotsdam.elis.mensa7.provider.mensaParser.CampusTyp;
import de.unipotsdam.elis.mensa7.provider.mensaParser.MensaNotAvailable;
import de.unipotsdam.elis.mensa7.provider.mensaParser.Speiseplan;
import de.unipotsdam.elis.util.date.TruncatedDate;
public class SpeiseplanLogic implements MensaEventManager {

	Logger logger = RootLogger.getLogger(MensaEventManager.class);
	private SpeiseplanAdaptor speiseplanAdaptor;
	private UIPersistanceLayer portalSessionManager;
	private Speiseplan speiseplan;
	private StateManipulations stateManipulations;

	public SpeiseplanLogic(SpeiseplanAdaptor speiseplanLayouter, MensaService mensaService, UIPersistanceLayer portletSessionManager, CampusTyp campusTyp) throws MensaNotAvailable, RemoteException {
		this.speiseplanAdaptor = speiseplanLayouter;
		this.portalSessionManager = portletSessionManager;
		this.speiseplan = mensaService.getSpeiseplan(campusTyp);
		this.stateManipulations = new StateManipulations();
				
	}

	/* (non-Javadoc)
	 * @see de.unipotsdam.elis.mensa.layout.MensaEventManager#manageNextDayClicked()
	 */
	public synchronized void manageNextDayClicked() {
		Boolean correctState  = stateManipulations.checkState(portalSessionManager, speiseplan);
		if (!correctState) return;
		
		logger.trace(GUIEvents.NextDayClicked + "received");
		// get selected Date
		
		Long date = portalSessionManager.getSelectedDate().getTime();
		if (date == null) {
			logger.error("selected date is null");
		} 
		DateTime selectedDate = new DateTime(date);		
		// calculate tommorrow
		TruncatedDate tommorrow = new TruncatedDate(selectedDate.plusDays(1).toDate());						
		// show tommorrow
		speiseplanAdaptor.doyourjob(speiseplan, tommorrow);				
		// logging
		logger.trace("next day shown");		
		//persist new state
		stateManipulations.persisteState(tommorrow, portalSessionManager, speiseplanAdaptor);
		
	}
	
	/* (non-Javadoc)
	 * @see de.unipotsdam.elis.mensa.layout.MensaEventManager#manageDateSelected(de.unipotsdam.elis.mensa.model.TruncatedDate)
	 */
	public synchronized void manageDateSelected(TruncatedDate selectedDate) {
		Boolean correctState  = stateManipulations.checkState(portalSessionManager, speiseplan);
		if (!correctState) return;		
		
		logger.trace(GUIEvents.DateValueChanged + "received");					
		// show date	
		speiseplanAdaptor.doyourjob(speiseplan, selectedDate);				
		// logging
		logger.trace("previous day shown");
		//persisting new state
		stateManipulations.persisteState(selectedDate, portalSessionManager, speiseplanAdaptor);
	}


	/* (non-Javadoc)
	 * @see de.unipotsdam.elis.mensa.layout.MensaEventManager#managePreviousDayClicked()
	 */
	public synchronized void managePreviousDayClicked() {
		Boolean correctState  = stateManipulations.checkState(portalSessionManager, speiseplan);
		if (!correctState) return;				
		
		logger.trace(GUIEvents.PreviousDayClicked + "received");
		// get selected Date
		DateTime selectedDate = new DateTime(portalSessionManager.getSelectedDate().getTime());		
		// calculate tommorrow
		TruncatedDate yesterday = new TruncatedDate(selectedDate.minusDays(1).toDate());						
		// show tommorrow
		speiseplanAdaptor.doyourjob(speiseplan, yesterday);				
		// logging
		logger.trace("previous day shown");
		//persisting new state
		stateManipulations.persisteState(yesterday, portalSessionManager, speiseplanAdaptor);
	}




		
	
	

}
