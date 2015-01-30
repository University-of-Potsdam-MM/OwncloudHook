package de.unipotsdam.elis.mensa7.events;


import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.unipotsdam.elis.mensa7.MensaPortletUI;


/**
 * löst ein NextDayClicked Event aus, wenn ein Button geclickt wurde
 * @author Julian
 *
 */
public class NextDayListener implements ClickListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5994432578691013655L;
	
	
	public void buttonClick(ClickEvent event) {		    		 
		MensaPortletUI.eventbus.publish(GUIEvents.NextDayClicked);    
	}
		

}
