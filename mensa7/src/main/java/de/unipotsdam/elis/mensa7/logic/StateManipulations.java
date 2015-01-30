package de.unipotsdam.elis.mensa7.logic;

import java.util.Date;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import de.unipotsdam.elis.mensa7.layout.viewmodel.SpeiseplanAdaptor;
import de.unipotsdam.elis.provider.mensa.CampusTyp;
import de.unipotsdam.elis.provider.mensa.Speiseplan;
import de.unipotsdam.elis.util.date.TruncatedDate;

public class StateManipulations {
	public void persistDateAtTheMOment(UIPersistanceLayer portletSessionManager) {
		portletSessionManager.setSelectedDate(new TruncatedDate(new Date()));		
	}

	public void persistSelectedTab(
			final UIPersistanceLayer portletSessionManager, TabSheet result) {
		VerticalLayout selectedTab = (VerticalLayout) result.getSelectedTab();
		String caption =  selectedTab.getCaption();
		CampusTyp campusTyp = mapCaption(caption);
		portletSessionManager.setSelectedTab(campusTyp);
	}

	protected CampusTyp mapCaption(String caption) {
		if (caption.contains("Griebnitzsee")) {
			return CampusTyp.Griebnitzsee;			
		} else if (caption.contains("Palais")) {
			return CampusTyp.NeuesPalais;			
		} else {
			return CampusTyp.Golm;
		}
	}
	

	/**
	 * neuen Zustand in der Session abspeichern
	 * @param selectedDate
	 * @param portalSessionManager 
	 * @param speiseplanAdaptor 
	 */
	public void persisteState(TruncatedDate selectedDate, UIPersistanceLayer portalSessionManager, SpeiseplanAdaptor speiseplanAdaptor) {		
		portalSessionManager.setSelectedDate(selectedDate);
		speiseplanAdaptor.update(selectedDate);		
	}

	Boolean checkState(UIPersistanceLayer portalSessionManager, Speiseplan speiseplan) {
		CampusTyp campusTyp = portalSessionManager.getSelectedTab();
		if (campusTyp == null) return false;
		return campusTyp.equals(speiseplan.getCampus());
	}
}
