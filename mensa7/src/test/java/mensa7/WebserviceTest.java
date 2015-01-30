package mensa7;

import java.rmi.RemoteException;

import org.junit.Test;

import de.unipotsdam.elis.mensa7.webservice.MensaWebservice;
import de.unipotsdam.elis.provider.mensa.CampusTyp;
import de.unipotsdam.elis.provider.mensa.MensaNotAvailable;



public class WebserviceTest {

	@Test
	public void test() throws MensaNotAvailable, RemoteException {
		MensaWebservice mensaWebservice = new MensaWebservice();
		mensaWebservice.getSpeiseplan(CampusTyp.Golm);
	}

}
