package de.unipotsdam.elis.mensa7.events;

import org.bushe.swing.event.EventSubscriber;


/**
 * Diese Klasse handelt NextDayClicked und PreviousDayClicked Events
 * @author Julian
 *
 */
public class GUIEventsHandler implements EventSubscriber<GUIEvents> {

	private MensaEventManager mensaEventManager;

	public GUIEventsHandler(MensaEventManager speiseplanLayouter) {		
		this.mensaEventManager = speiseplanLayouter;
		
	}
	
	/**
	 * propagieren der Events zu dem Layouter
	 */
	public void onEvent(GUIEvents arg0) {
		switch (arg0) {
		case NextDayClicked: 
			this.mensaEventManager.manageNextDayClicked();
			break;
		case PreviousDayClicked: 
			this.mensaEventManager.managePreviousDayClicked();
			break;
		default:
			break;
		}		
		
	}

}
