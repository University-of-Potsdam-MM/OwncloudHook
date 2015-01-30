package de.unipotsdam.elis.mensa7.events;

import de.unipotsdam.elis.util.date.TruncatedDate;


public interface MensaEventManager {

	/**
	 * black magic will call this if the "morgen" button is clicked
	 */
	public abstract void manageNextDayClicked();

	/**
	 * black magic will call this, if a new date has been selected
	 * @param arg0
	 */
	public abstract void manageDateSelected(TruncatedDate date);

	/**
	 * black magic will call this if the "gestern" button is clicked
	 * @param arg0
	 */
	public abstract void managePreviousDayClicked();

}