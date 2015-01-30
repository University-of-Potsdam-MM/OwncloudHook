package de.unipotsdam.elis.mensa7.events;

import org.bushe.swing.event.EventSubscriber;

import de.unipotsdam.elis.util.date.TruncatedDate;

/**
 * propagiert das bei DateChooser gewählte Datum 
 */
public class TruncatedDateHandler implements EventSubscriber<TruncatedDate> {

	private MensaEventManager mensaEventManager;	

	public TruncatedDateHandler(MensaEventManager speiseplanLayouter) {		
		this.mensaEventManager = speiseplanLayouter;
		
	}		

	public void onEvent(TruncatedDate arg0) {		
		this.mensaEventManager.manageDateSelected(arg0);
	}

}
