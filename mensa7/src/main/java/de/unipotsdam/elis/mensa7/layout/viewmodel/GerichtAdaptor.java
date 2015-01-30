package de.unipotsdam.elis.mensa7.layout.viewmodel;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.RootLogger;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.unipotsdam.elis.mensa7.events.MensaEventManager;
import de.unipotsdam.elis.mensa7.layout.icons.IconHashMap;
import de.unipotsdam.elis.mensa7.provider.mensaParser.EssensTyp;
import de.unipotsdam.elis.mensa7.provider.mensaParser.Gericht;
import de.unipotsdam.elis.mensa7.provider.mensaParser.PreisTyp;
import de.unipotsdam.elis.mensa7.provider.mensaParser.SpeiseplanIconHashMapEntry;

/**
 * wrapped das SpeiseplanGUIElement mit dynamischem Code
 * 
 * @author Julian
 * 
 */
public class GerichtAdaptor {

	private Label titel;
	private Button merkenButton;
	private Label beschreibung;
	private VerticalLayout essenstypContainer;
	private Label gastPreis;
	private Label mitarbeiterPreis;
	private Label studentPreis;

	// public GerichtAdaptor(Label price,
	// Button merkenButton, Label beschreibung, Label titel, VerticalLayout
	// essenstypContainer) {
	// this.setPrice(price);
	// this.setMerkenButton(merkenButton);
	// this.setBeschreibung(beschreibung);
	// this.setTitel(titel);
	// this.essenstypContainer = essenstypContainer;
	// }

	public GerichtAdaptor(Label studentLabel, Label studentPreis, Label mitarbeiterLabel, Label mitarbeiterPreis, Label gastLabel, Label gastPreis, Button merkenButton, Label beschreibung, Label titel, VerticalLayout essenstypContainer) {
		this.gastPreis = gastPreis;
		this.mitarbeiterPreis = mitarbeiterPreis;
		this.studentPreis = studentPreis;
		this.setMerkenButton(merkenButton);
		this.setBeschreibung(beschreibung);
		this.setTitel(titel);
		this.essenstypContainer = essenstypContainer;
	}

	public void setTitel(Label titel) {
		this.titel = titel;
	}

	public Label getTitel() {
		return titel;
	}

	public void setMerkenButton(Button merkenButton) {
		this.merkenButton = merkenButton;
	}

	public Button getMerkenButton() {
		return merkenButton;
	}

	public void setBeschreibung(Label beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Label getBeschreibung() {
		return beschreibung;
	}

	public void doyourjob(Gericht gericht, IconHashMap iconHashMap1) {
		Logger logger = RootLogger.getLogger(GerichtAdaptor.class);
		
		// set values for Preislabels
		try {
			setPreisLabelValues(gericht);
		} catch (NoPriceAvailableException e) {						
			logger.warn("Keine Preise für Gericht" + gericht.getTitel());
		}

		if (gericht.getBeschreibung() == null) {			
			logger.warn("Keine Beschreibung für Gericht" + gericht.getTitel());
		} else {
			this.beschreibung.setValue(gericht.getBeschreibung());
		}
		this.titel.setValue(gericht.getTitel());
		if (gericht.getEssenstyp() != null) {
			for (EssensTyp essensTyp : gericht.getEssenstyp()) {
				if (iconHashMap1.get(essensTyp) != null) {					
					Embedded c = new Embedded("", (Resource) iconHashMap1.get(essensTyp));
					this.essenstypContainer.addComponent(c);
				}
			}
		}

	}

	private void setPreisLabelValues(Gericht gericht) throws NoPriceAvailableException {
		for (int i = 0; i < gericht.getPreise().length; i++) {
			setPreisLabels(gericht, i);
		}
		
	}

	/**
	 * sets the labels for the different preise
	 * 
	 * @param gericht
	 * @param i
	 * @throws NoPriceAvailableException 
	 */
	private void setPreisLabels(Gericht gericht, int i) throws NoPriceAvailableException {
		if (gericht.getPreise()[i].getKey().equals(PreisTyp.Gast)) {
			this.gastPreis.setValue(printPrice(gericht, i));
		} else if (gericht.getPreise()[i].getKey().equals(PreisTyp.Mitarbeiter)) {
			this.mitarbeiterPreis.setValue(printPrice(gericht, i));
		} else if (gericht.getPreise()[i].getKey().equals(PreisTyp.Student)) {
			this.studentPreis.setValue(printPrice(gericht, i));
		}

	}

	/**
	 * formatiert die Preisangaben in einen lesbaren String
	 * @param gericht
	 * @param i
	 * @return
	 * @throws NoPriceAvailableException 
	 */
	private String printPrice(Gericht gericht, int i) throws NoPriceAvailableException {
		if (gericht.getPreise()[i].getValue() == null) {
			throw new NoPriceAvailableException(gericht);
		}
		return gericht.getPreise()[i].getValue() + " Euro";
	}

}
