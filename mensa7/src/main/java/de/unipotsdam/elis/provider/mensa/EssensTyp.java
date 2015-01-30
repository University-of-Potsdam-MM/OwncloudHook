/**
 * EssensTyp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package de.unipotsdam.elis.provider.mensa;

public class EssensTyp implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected EssensTyp(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Schwein = "Schwein";
    public static final java.lang.String _Vegan = "Vegan";
    public static final java.lang.String _Vegetarisch = "Vegetarisch";
    public static final java.lang.String _Rind = "Rind";
    public static final java.lang.String _Fisch = "Fisch";
    public static final java.lang.String _Huhn = "Huhn";
    public static final EssensTyp Schwein = new EssensTyp(_Schwein);
    public static final EssensTyp Vegan = new EssensTyp(_Vegan);
    public static final EssensTyp Vegetarisch = new EssensTyp(_Vegetarisch);
    public static final EssensTyp Rind = new EssensTyp(_Rind);
    public static final EssensTyp Fisch = new EssensTyp(_Fisch);
    public static final EssensTyp Huhn = new EssensTyp(_Huhn);
    public java.lang.String getValue() { return _value_;}
    public static EssensTyp fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        EssensTyp enumeration = (EssensTyp)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static EssensTyp fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EssensTyp.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mensa.provider.elis.unipotsdam.de/", "essensTyp"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
