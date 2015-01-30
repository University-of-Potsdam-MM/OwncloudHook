/**
 * MensaParserWs.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package de.unipotsdam.elis.mensa7.provider.mensaParser;

public interface MensaParserWs extends javax.xml.rpc.Service {
    public java.lang.String getMensaParserPortAddress();

    public de.unipotsdam.elis.mensa7.provider.mensaParser.MensaParser getMensaParserPort() throws javax.xml.rpc.ServiceException;

    public de.unipotsdam.elis.mensa7.provider.mensaParser.MensaParser getMensaParserPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
