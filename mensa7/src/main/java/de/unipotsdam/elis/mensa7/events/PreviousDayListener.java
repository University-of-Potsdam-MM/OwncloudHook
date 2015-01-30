package de.unipotsdam.elis.mensa7.events;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.unipotsdam.elis.mensa7.MensaPortletUI;


public class PreviousDayListener implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5226874598930292710L;

	public void buttonClick(ClickEvent event) {	
//		ThreadSafeEventService eventbus = new ThreadSafeEventService();		
		MensaPortletUI.eventbus.publish(GUIEvents.PreviousDayClicked);   
	}

}
