/**
 * MensaLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package de.unipotsdam.elis.provider.mensa;

public class MensaLocator extends org.apache.axis.client.Service implements de.unipotsdam.elis.provider.mensa.Mensa {

    public MensaLocator() {
    }


    public MensaLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MensaLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MensaManagerPort
    private java.lang.String MensaManagerPort_address = "http://fossa.soft.cs.uni-potsdam.de:7000/mensa-1.0/ws";

    public java.lang.String getMensaManagerPortAddress() {
        return MensaManagerPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MensaManagerPortWSDDServiceName = "MensaManagerPort";

    public java.lang.String getMensaManagerPortWSDDServiceName() {
        return MensaManagerPortWSDDServiceName;
    }

    public void setMensaManagerPortWSDDServiceName(java.lang.String name) {
        MensaManagerPortWSDDServiceName = name;
    }

    public de.unipotsdam.elis.provider.mensa.MensaParser getMensaManagerPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MensaManagerPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMensaManagerPort(endpoint);
    }

    public de.unipotsdam.elis.provider.mensa.MensaParser getMensaManagerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            de.unipotsdam.elis.provider.mensa.MensaManagerPortBindingStub _stub = new de.unipotsdam.elis.provider.mensa.MensaManagerPortBindingStub(portAddress, this);
            _stub.setPortName(getMensaManagerPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMensaManagerPortEndpointAddress(java.lang.String address) {
        MensaManagerPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (de.unipotsdam.elis.provider.mensa.MensaParser.class.isAssignableFrom(serviceEndpointInterface)) {
                de.unipotsdam.elis.provider.mensa.MensaManagerPortBindingStub _stub = new de.unipotsdam.elis.provider.mensa.MensaManagerPortBindingStub(new java.net.URL(MensaManagerPort_address), this);
                _stub.setPortName(getMensaManagerPortWSDDServiceName());
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
        if ("MensaManagerPort".equals(inputPortName)) {
            return getMensaManagerPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://mensa.provider.elis.unipotsdam.de/", "mensa");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://mensa.provider.elis.unipotsdam.de/", "MensaManagerPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MensaManagerPort".equals(portName)) {
            setMensaManagerPortEndpointAddress(address);
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
