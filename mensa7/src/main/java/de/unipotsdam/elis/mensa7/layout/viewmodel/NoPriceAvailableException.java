package de.unipotsdam.elis.mensa7.layout.viewmodel;

import de.unipotsdam.elis.provider.mensa.Gericht;

public class NoPriceAvailableException extends Exception {

	private Gericht gericht;

	public NoPriceAvailableException(Gericht gericht) {
		this.setGericht(gericht);
	}

	public Gericht getGericht() {
		return gericht;
	}

	public void setGericht(Gericht gericht) {
		this.gericht = gericht;
	}

}
