package de.unipotsdam.elis.mensa7.webservice;

import java.rmi.RemoteException;

import de.unipotsdam.elis.mensa7.logic.MensaService;
import de.unipotsdam.elis.mensa7.provider.mensaParser.CampusTyp;
import de.unipotsdam.elis.mensa7.provider.mensaParser.MensaNotAvailable;
import de.unipotsdam.elis.mensa7.provider.mensaParser.MensaParserProxy;
import de.unipotsdam.elis.mensa7.provider.mensaParser.Speiseplan;

public class MensaWebservice implements MensaService {

	@Override
	public Speiseplan getSpeiseplan(CampusTyp location)
			throws MensaNotAvailable, RemoteException {
		MensaParserProxy mensaParserProxy = new MensaParserProxy("http://elis.soft.cs.uni-potsdam.de:7000/mensaParser-1.0/ws?wsdl");
		return mensaParserProxy.getMensaParser().readCurrentMeals(location);	
	}

	

}
