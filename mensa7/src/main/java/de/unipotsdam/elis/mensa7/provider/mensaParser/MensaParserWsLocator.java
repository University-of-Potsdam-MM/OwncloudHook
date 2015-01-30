/**
 * MensaParserWsLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package de.unipotsdam.elis.mensa7.provider.mensaParser;

public class MensaParserWsLocator extends org.apache.axis.client.Service implements de.unipotsdam.elis.mensa7.provider.mensaParser.MensaParserWs {

    public MensaParserWsLocator() {
    }


    public MensaParserWsLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MensaParserWsLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MensaParserPort
    private java.lang.String MensaParserPort_address = "https://fossa.soft.cs.uni-potsdam.de:8243/services/mensaParser/ws";

    public java.lang.String getMensaParserPortAddress() {
        return MensaParserPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MensaParserPortWSDDServiceName = "MensaParserPort";

    public java.lang.String getMensaParserPortWSDDServiceName() {
        return MensaParserPortWSDDServiceName;
    }

    public void setMensaParserPortWSDDServiceName(java.lang.String name) {
        MensaParserPortWSDDServiceName = name;
    }

    public de.unipotsdam.elis.mensa7.provider.mensaParser.MensaParser getMensaParserPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MensaParserPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMensaParserPort(endpoint);
    }

    public de.unipotsdam.elis.mensa7.provider.mensaParser.MensaParser getMensaParserPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            de.unipotsdam.elis.mensa7.provider.mensaParser.MensaParserPortBindingStub _stub = new de.unipotsdam.elis.mensa7.provider.mensaParser.MensaParserPortBindingStub(portAddress, this);
            _stub.setPortName(getMensaParserPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMensaParserPortEndpointAddress(java.lang.String address) {
        MensaParserPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (de.unipotsdam.elis.mensa7.provider.mensaParser.MensaParser.class.isAssignableFrom(serviceEndpointInterface)) {
                de.unipotsdam.elis.mensa7.provider.mensaParser.MensaParserPortBindingStub _stub = new de.unipotsdam.elis.mensa7.provider.mensaParser.MensaParserPortBindingStub(new java.net.URL(MensaParserPort_address), this);
                _stub.setPortName(getMensaParserPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("MensaParserPort".equals(inputPortName)) {
            return getMensaParserPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://mensaParser.provider.elis.unipotsdam.de/", "mensaParserWs");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://mensaParser.provider.elis.unipotsdam.de/", "MensaParserPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MensaParserPort".equals(portName)) {
            setMensaParserPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
