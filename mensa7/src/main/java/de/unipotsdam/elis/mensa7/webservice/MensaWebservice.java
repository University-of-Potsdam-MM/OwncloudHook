package de.unipotsdam.elis.mensa7.webservice;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.SOAPHeaderElement;

import de.unipotsdam.elis.mensa7.logic.MensaService;
import de.unipotsdam.elis.provider.mensa.CampusTyp;
import de.unipotsdam.elis.provider.mensa.MensaNotAvailable;
import de.unipotsdam.elis.provider.mensa.MensaParserProxy;
import de.unipotsdam.elis.provider.mensa.Speiseplan;


public class MensaWebservice implements MensaService {

	@Override
	public Speiseplan getSpeiseplan(CampusTyp location)
			throws MensaNotAvailable, RemoteException {
//		MensaParserProxy mensaParserProxy = new MensaParserProxy("http://elis.soft.cs.uni-potsdam.de:7000/mensaParser-1.0/ws?wsdl");		
		
		MensaParserProxy mensaParserProxy = new MensaParserProxy("http://fossa.soft.cs.uni-potsdam.de:7000/mensa-1.0/ws?wsdl");		
//		Stub stub = ((Stub)mensaParserProxy.getMensaParser());		
//		SOAPHeaderElement authentication = new SOAPHeaderElement("Bearer","c06156e119040a27a4b43fa933f130");		
//		stub.setHeader(authentication);
		return mensaParserProxy.getMensaParser().readCurrentMeals(location);	
	}

	

}
