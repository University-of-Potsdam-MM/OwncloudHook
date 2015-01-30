/**
 * Gericht.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package de.unipotsdam.elis.mensa7.provider.mensaParser;

import java.util.Comparator;

public class Gericht  implements java.io.Serializable, Comparable<Gericht> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int anzeigeprio;

    private java.lang.String beschreibung;

    private de.unipotsdam.elis.mensa7.provider.mensaParser.EssensTyp[] essenstyp;

    private de.unipotsdam.elis.mensa7.provider.mensaParser.GerichtPreiseEntry[] preise;

    private java.lang.String titel;

    private de.unipotsdam.elis.mensa7.provider.mensaParser.ZusatzstoffeTyp zusatzstoffe;

    public Gericht() {
    }

    public Gericht(
           int anzeigeprio,
           java.lang.String beschreibung,
           de.unipotsdam.elis.mensa7.provider.mensaParser.EssensTyp[] essenstyp,
           de.unipotsdam.elis.mensa7.provider.mensaParser.GerichtPreiseEntry[] preise,
           java.lang.String titel,
           de.unipotsdam.elis.mensa7.provider.mensaParser.ZusatzstoffeTyp zusatzstoffe) {
           this.anzeigeprio = anzeigeprio;
           this.beschreibung = beschreibung;
           this.essenstyp = essenstyp;
           this.preise = preise;
           this.titel = titel;
           this.zusatzstoffe = zusatzstoffe;
    }

   @Override
    public int compareTo(Gericht arg0) {
		if (this.anzeigeprio == arg0.getAnzeigeprio()) {
			return 0;
		}
		return this.anzeigeprio > arg0.getAnzeigeprio() ? 1:-1;
	}

    
    


    /**
     * Gets the anzeigeprio value for this Gericht.
     * 
     * @return anzeigeprio
     */
    public int getAnzeigeprio() {
        return anzeigeprio;
    }


    /**
     * Sets the anzeigeprio value for this Gericht.
     * 
     * @param anzeigeprio
     */
    public void setAnzeigeprio(int anzeigeprio) {
        this.anzeigeprio = anzeigeprio;
    }


    /**
     * Gets the beschreibung value for this Gericht.
     * 
     * @return beschreibung
     */
    public java.lang.String getBeschreibung() {
        return beschreibung;
    }


    /**
     * Sets the beschreibung value for this Gericht.
     * 
     * @param beschreibung
     */
    public void setBeschreibung(java.lang.String beschreibung) {
        this.beschreibung = beschreibung;
    }


    /**
     * Gets the essenstyp value for this Gericht.
     * 
     * @return essenstyp
     */
    public de.unipotsdam.elis.mensa7.provider.mensaParser.EssensTyp[] getEssenstyp() {
        return essenstyp;
    }


    /**
     * Sets the essenstyp value for this Gericht.
     * 
     * @param essenstyp
     */
    public void setEssenstyp(de.unipotsdam.elis.mensa7.provider.mensaParser.EssensTyp[] essenstyp) {
        this.essenstyp = essenstyp;
    }

    public de.unipotsdam.elis.mensa7.provider.mensaParser.EssensTyp getEssenstyp(int i) {
        return this.essenstyp[i];
    }

    public void setEssenstyp(int i, de.unipotsdam.elis.mensa7.provider.mensaParser.EssensTyp _value) {
        this.essenstyp[i] = _value;
    }


    /**
     * Gets the preise value for this Gericht.
     * 
     * @return preise
     */
    public de.unipotsdam.elis.mensa7.provider.mensaParser.GerichtPreiseEntry[] getPreise() {
        return preise;
    }


    /**
     * Sets the preise value for this Gericht.
     * 
     * @param preise
     */
    public void setPreise(de.unipotsdam.elis.mensa7.provider.mensaParser.GerichtPreiseEntry[] preise) {
        this.preise = preise;
    }


    /**
     * Gets the titel value for this Gericht.
     * 
     * @return titel
     */
    public java.lang.String getTitel() {
        return titel;
    }


    /**
     * Sets the titel value for this Gericht.
     * 
     * @param titel
     */
    public void setTitel(java.lang.String titel) {
        this.titel = titel;
    }


    /**
     * Gets the zusatzstoffe value for this Gericht.
     * 
     * @return zusatzstoffe
     */
    public de.unipotsdam.elis.mensa7.provider.mensaParser.ZusatzstoffeTyp getZusatzstoffe() {
        return zusatzstoffe;
    }


    /**
     * Sets the zusatzstoffe value for this Gericht.
     * 
     * @param zusatzstoffe
     */
    public void setZusatzstoffe(de.unipotsdam.elis.mensa7.provider.mensaParser.ZusatzstoffeTyp zusatzstoffe) {
        this.zusatzstoffe = zusatzstoffe;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Gericht)) return false;
        Gericht other = (Gericht) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.anzeigeprio == other.getAnzeigeprio() &&
            ((this.beschreibung==null && other.getBeschreibung()==null) || 
             (this.beschreibung!=null &&
              this.beschreibung.equals(other.getBeschreibung()))) &&
            ((this.essenstyp==null && other.getEssenstyp()==null) || 
             (this.essenstyp!=null &&
              java.util.Arrays.equals(this.essenstyp, other.getEssenstyp()))) &&
            ((this.preise==null && other.getPreise()==null) || 
             (this.preise!=null &&
              java.util.Arrays.equals(this.preise, other.getPreise()))) &&
            ((this.titel==null && other.getTitel()==null) || 
             (this.titel!=null &&
              this.titel.equals(other.getTitel()))) &&
            ((this.zusatzstoffe==null && other.getZusatzstoffe()==null) || 
             (this.zusatzstoffe!=null &&
              this.zusatzstoffe.equals(other.getZusatzstoffe())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getAnzeigeprio();
        if (getBeschreibung() != null) {
            _hashCode += getBeschreibung().hashCode();
        }
        if (getEssenstyp() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEssenstyp());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEssenstyp(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPreise() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPreise());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPreise(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTitel() != null) {
            _hashCode += getTitel().hashCode();
        }
        if (getZusatzstoffe() != null) {
            _hashCode += getZusatzstoffe().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Gericht.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mensaParser.provider.elis.unipotsdam.de/", "gericht"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anzeigeprio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anzeigeprio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beschreibung");
        elemField.setXmlName(new javax.xml.namespace.QName("", "beschreibung"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("essenstyp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "essenstyp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://mensaParser.provider.elis.unipotsdam.de/", "essensTyp"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preise");
        elemField.setXmlName(new javax.xml.namespace.QName("", "preise"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://mensaParser.provider.elis.unipotsdam.de/", ">>gericht>preise>entry"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "entry"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "titel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zusatzstoffe");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zusatzstoffe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://mensaParser.provider.elis.unipotsdam.de/", "zusatzstoffeTyp"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
