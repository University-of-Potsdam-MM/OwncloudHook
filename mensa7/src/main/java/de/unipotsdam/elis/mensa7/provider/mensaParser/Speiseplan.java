/**
 * Speiseplan.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package de.unipotsdam.elis.mensa7.provider.mensaParser;

import java.util.HashMap;
import java.util.TreeSet;

import com.vaadin.server.ThemeResource;

import de.unipotsdam.elis.mensa7.layout.icons.IconHashMap;
import de.unipotsdam.elis.util.date.TruncatedDate;

public class Speiseplan  implements java.io.Serializable {
    private de.unipotsdam.elis.mensa7.provider.mensaParser.CampusTyp campus;

    private de.unipotsdam.elis.mensa7.provider.mensaParser.SpeiseplanGerichteEntry[] gerichte;

    private de.unipotsdam.elis.mensa7.provider.mensaParser.SpeiseplanIconHashMapEntry[] iconHashMap;

    public Speiseplan() {
    }

    public Speiseplan(
           de.unipotsdam.elis.mensa7.provider.mensaParser.CampusTyp campus,
           de.unipotsdam.elis.mensa7.provider.mensaParser.SpeiseplanGerichteEntry[] gerichte,
           de.unipotsdam.elis.mensa7.provider.mensaParser.SpeiseplanIconHashMapEntry[] iconHashMap) {
           this.campus = campus;
           this.gerichte = gerichte;
           this.iconHashMap = iconHashMap;
    }


    /**
     * Gets the campus value for this Speiseplan.
     * 
     * @return campus
     */
    public de.unipotsdam.elis.mensa7.provider.mensaParser.CampusTyp getCampus() {
        return campus;
    }


    /**
     * Sets the campus value for this Speiseplan.
     * 
     * @param campus
     */
    public void setCampus(de.unipotsdam.elis.mensa7.provider.mensaParser.CampusTyp campus) {
        this.campus = campus;
    }


    /**
     * Gets the gerichte value for this Speiseplan.
     * 
     * @return gerichte
     */
    public de.unipotsdam.elis.mensa7.provider.mensaParser.SpeiseplanGerichteEntry[] getGerichte() {
        return gerichte;
    }


    /**
     * Sets the gerichte value for this Speiseplan.
     * 
     * @param gerichte
     */
    public void setGerichte(de.unipotsdam.elis.mensa7.provider.mensaParser.SpeiseplanGerichteEntry[] gerichte) {
        this.gerichte = gerichte;
    }


    /**
     * Gets the iconHashMap value for this Speiseplan.
     * 
     * @return iconHashMap
     */
    public de.unipotsdam.elis.mensa7.provider.mensaParser.SpeiseplanIconHashMapEntry[] getIconHashMap() {
        return iconHashMap;
    }


    /**
     * Sets the iconHashMap value for this Speiseplan.
     * 
     * @param iconHashMap
     */
    public void setIconHashMap(de.unipotsdam.elis.mensa7.provider.mensaParser.SpeiseplanIconHashMapEntry[] iconHashMap) {
        this.iconHashMap = iconHashMap;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Speiseplan)) return false;
        Speiseplan other = (Speiseplan) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.campus==null && other.getCampus()==null) || 
             (this.campus!=null &&
              this.campus.equals(other.getCampus()))) &&
            ((this.gerichte==null && other.getGerichte()==null) || 
             (this.gerichte!=null &&
              java.util.Arrays.equals(this.gerichte, other.getGerichte()))) &&
            ((this.iconHashMap==null && other.getIconHashMap()==null) || 
             (this.iconHashMap!=null &&
              java.util.Arrays.equals(this.iconHashMap, other.getIconHashMap())));
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
        if (getCampus() != null) {
            _hashCode += getCampus().hashCode();
        }
        if (getGerichte() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGerichte());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGerichte(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIconHashMap() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIconHashMap());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIconHashMap(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Speiseplan.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mensaParser.provider.elis.unipotsdam.de/", "speiseplan"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "campus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://mensaParser.provider.elis.unipotsdam.de/", "campusTyp"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gerichte");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gerichte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://mensaParser.provider.elis.unipotsdam.de/", ">>speiseplan>gerichte>entry"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "entry"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("iconHashMap");
        elemField.setXmlName(new javax.xml.namespace.QName("", "iconHashMap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://mensaParser.provider.elis.unipotsdam.de/", ">>speiseplan>iconHashMap>entry"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "entry"));
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

    public HashMap<TruncatedDate, TreeSet<Gericht>> getGerichteHashMap() {
		HashMap<TruncatedDate, TreeSet<Gericht>> hashMap = new HashMap<TruncatedDate, TreeSet<Gericht>>();
		for (SpeiseplanGerichteEntry entry : gerichte) {
			hashMap.put(new TruncatedDate(entry.getKey().getTime()), new TreeSet<Gericht>(java.util.Arrays.asList(entry.getValue())));
		}
		return hashMap;
	}

	public IconHashMap getIconHashMapConverted() {
		IconHashMap hashMap = new IconHashMap();
		for (SpeiseplanIconHashMapEntry speiseplanIconHashMapEntry : this.getIconHashMap()) {
			if (speiseplanIconHashMapEntry.getValue() != null)
				hashMap.put(speiseplanIconHashMapEntry.getKey(), new ThemeResource(speiseplanIconHashMapEntry.getValue().toString()));
		}
		return hashMap;
	}

}
