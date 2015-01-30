/**
 * Mensa.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package de.unipotsdam.elis.provider.mensa;

public interface Mensa extends javax.xml.rpc.Service {
    public java.lang.String getMensaManagerPortAddress();

    public de.unipotsdam.elis.provider.mensa.MensaParser getMensaManagerPort() throws javax.xml.rpc.ServiceException;

    public de.unipotsdam.elis.provider.mensa.MensaParser getMensaManagerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
