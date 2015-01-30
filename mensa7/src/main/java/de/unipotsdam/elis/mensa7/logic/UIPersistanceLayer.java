package de.unipotsdam.elis.mensa7.logic;

import de.unipotsdam.elis.provider.mensa.CampusTyp;
import de.unipotsdam.elis.util.date.TruncatedDate;

/**
 * Die Idee hinter diesem Interface ist es, alle Daten, die schnell abrufbar in der GUI
 * verfügbar sein sollen zu kapseln, so dass sie von verschiedenen Portlets aus aufrufbar sind
 * @author Julian
 *
 */
public interface UIPersistanceLayer {

	public abstract TruncatedDate getSelectedDate();

	public abstract void setSelectedDate(TruncatedDate date);
	
	public abstract void setSelectedTab(CampusTyp caption);
	
	public abstract CampusTyp getSelectedTab();
	
}