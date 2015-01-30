package de.unipotsdam.elis.mensa7.layout;

import java.rmi.RemoteException;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

import de.unipotsdam.elis.mensa7.logic.MensaService;
import de.unipotsdam.elis.mensa7.logic.StateManipulations;
import de.unipotsdam.elis.mensa7.logic.UIPersistanceLayer;
import de.unipotsdam.elis.mensa7.provider.mensaParser.CampusTyp;
import de.unipotsdam.elis.mensa7.provider.mensaParser.MensaNotAvailable;
import de.unipotsdam.elis.mensa7.webservice.MensaWebservice;

/**
 * 
 * @author Julian
 * 
 */
public class MensaLayouter {
	private final TabSheet result;
	private final StateManipulations stateManipulations;

	public MensaLayouter(final UIPersistanceLayer portletSessionManager) {
		this.result = new TabSheet();
		this.stateManipulations = new StateManipulations();
		
		try {
			layoutLocation(portletSessionManager, CampusTyp.NeuesPalais, result);

			layoutLocation(portletSessionManager, CampusTyp.Griebnitzsee, result);
			// layoutLocation(portletSessionManager, CampusTyp.Golm, result);
		} catch (MensaNotAvailable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initializeState(portletSessionManager);		
	}



	private void initializeState(final UIPersistanceLayer portletSessionManager) {
		stateManipulations.persistSelectedTab(portletSessionManager, result);	
		stateManipulations.persistDateAtTheMOment(portletSessionManager);

		this.result.addListener(new SelectedTabChangeListener() {

			/**
			 *	Whenever a new Tab is selected, the state should reflect his 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {				
				stateManipulations.persistSelectedTab(portletSessionManager, result);
				stateManipulations.persistDateAtTheMOment(portletSessionManager);
			}
		});
	}



	/**
	 * Erstellt die einzelnen Tabs für die verschiednen Locations (Griebnitzsee
	 * etc.)
	 * 
	 * @param portletSessionManager
	 * @param location
	 * @param result
	 * @return
	 * @throws RemoteException
	 * @throws MensaNotAvailable
	 */
	private VerticalLayout layoutLocation(
			UIPersistanceLayer portletSessionManager, CampusTyp location,
			TabSheet result) throws MensaNotAvailable, RemoteException {

		MensaService mensaService = new MensaWebservice();
		VerticalLayout mensaview = new VerticalLayout();
		SpeiseplanLayouter speiseplanLayouter = new SpeiseplanLayouter(
				location, portletSessionManager, mensaService);
		mensaview.addComponent(speiseplanLayouter.getSpeisePlanLayout());
		result.addTab(mensaview);
		setTabLabel(result, mensaview, location.toString());
		return mensaview;
	}

	public Component getFinalLayout() {
		return result;
	}

	private void setTabLabel(TabSheet tabsheet, Component component,
			String caption) {
		if (caption.equals("NeuesPalais")) {
			caption = "Neues Palais";
		}
		Tab tab = tabsheet.getTab(component);
		tab.setCaption(caption);
		component.setCaption(caption);
		
	}
}
