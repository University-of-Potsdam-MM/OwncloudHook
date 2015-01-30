package de.unipotsdam.elis.mensa7.logic;

import java.rmi.RemoteException;

import de.unipotsdam.elis.mensa7.provider.mensaParser.CampusTyp;
import de.unipotsdam.elis.mensa7.provider.mensaParser.MensaNotAvailable;
import de.unipotsdam.elis.mensa7.provider.mensaParser.Speiseplan;


public interface MensaService {
	
	public Speiseplan getSpeiseplan(CampusTyp location) throws MensaNotAvailable, RemoteException;
}
