package de.unipotsdam.elis.mensa7.provider.mensaParser;

public class MensaParserProxy implements
		de.unipotsdam.elis.mensa7.provider.mensaParser.MensaParser {
	private String _endpoint = null;
	private de.unipotsdam.elis.mensa7.provider.mensaParser.MensaParser mensaParser = null;

	public MensaParserProxy() {
		_initMensaParserProxy();
	}

	public MensaParserProxy(String endpoint) {
		_endpoint = endpoint;
		_initMensaParserProxy();
	}

	private void _initMensaParserProxy() {
		try {
			mensaParser = (new de.unipotsdam.elis.mensa7.provider.mensaParser.MensaParserWsLocator())
					.getMensaParserPort();
			if (mensaParser != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) mensaParser)
							._setProperty(
									"javax.xml.rpc.service.endpoint.address",
									_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) mensaParser)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (mensaParser != null)
			((javax.xml.rpc.Stub) mensaParser)._setProperty(
					"javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public de.unipotsdam.elis.mensa7.provider.mensaParser.MensaParser getMensaParser() {
		if (mensaParser == null)
			_initMensaParserProxy();
		return mensaParser;
	}

	public de.unipotsdam.elis.mensa7.provider.mensaParser.Speiseplan readCurrentMeals(
			de.unipotsdam.elis.mensa7.provider.mensaParser.CampusTyp location)
			throws java.rmi.RemoteException,
			de.unipotsdam.elis.mensa7.provider.mensaParser.MensaNotAvailable {
		if (mensaParser == null)
			_initMensaParserProxy();
		return mensaParser.readCurrentMeals(location);
	}

}