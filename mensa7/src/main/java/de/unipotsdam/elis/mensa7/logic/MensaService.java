package de.unipotsdam.elis.mensa7.logic;

import java.rmi.RemoteException;

import de.unipotsdam.elis.provider.mensa.CampusTyp;
import de.unipotsdam.elis.provider.mensa.MensaNotAvailable;
import de.unipotsdam.elis.provider.mensa.Speiseplan;

	


public interface MensaService {
	
	public Speiseplan getSpeiseplan(CampusTyp location) throws MensaNotAvailable, RemoteException;
}
