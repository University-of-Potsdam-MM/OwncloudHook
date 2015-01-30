/**
 * MensaParser.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package de.unipotsdam.elis.mensa7.provider.mensaParser;

public interface MensaParser extends java.rmi.Remote {
    public de.unipotsdam.elis.mensa7.provider.mensaParser.Speiseplan readCurrentMeals(CampusTyp location) throws java.rmi.RemoteException, de.unipotsdam.elis.mensa7.provider.mensaParser.MensaNotAvailable;
}
