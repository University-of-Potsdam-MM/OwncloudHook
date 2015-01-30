/**
 * Speiseplan.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package de.unipotsdam.elis.provider.mensa;

public class Speiseplan  implements java.io.Serializable {
    private de.unipotsdam.elis.provider.mensa.Gericht[] meal;

    private de.unipotsdam.elis.provider.mensa.CampusTyp campus;

    private de.unipotsdam.elis.provider.mensa.SpeiseplanIconHashMapEntry[] iconHashMap;

    public Speiseplan() {
    }

    public Speiseplan(
           de.unipotsdam.elis.provider.mensa.Gericht[] meal,
           de.unipotsdam.elis.provider.mensa.CampusTyp campus,
           de.unipotsdam.elis.provider.mensa.SpeiseplanIconHashMapEntry[] iconHashMap) {
           this.meal = meal;
           this.campus = campus;
           this.iconHashMap = iconHashMap;
    }


    /**
     * Gets the meal value for this Speiseplan.
     * 
     * @return meal
     */
    public de.unipotsdam.elis.provider.mensa.Gericht[] getMeal() {
        return meal;
    }


    /**
     * Sets the meal value for this Speiseplan.
     * 
     * @param meal
     */
    public void setMeal(de.unipotsdam.elis.provider.mensa.Gericht[] meal) {
        this.meal = meal;
    }

    public de.unipotsdam.elis.provider.mensa.Gericht getMeal(int i) {
        return this.meal[i];
    }

    public void setMeal(int i, de.unipotsdam.elis.provider.mensa.Gericht _value) {
        this.meal[i] = _value;
    }


    /**
     * Gets the campus value for this Speiseplan.
     * 
     * @return campus
     */
    public de.unipotsdam.elis.provider.mensa.CampusTyp getCampus() {
        return campus;
    }


    /**
     * Sets the campus value for this Speiseplan.
     * 
     * @param campus
     */
    public void setCampus(de.unipotsdam.elis.provider.mensa.CampusTyp campus) {
        this.campus = campus;
    }


    /**
     * Gets the iconHashMap value for this Speiseplan.
     * 
     * @return iconHashMap
     */
    public de.unipotsdam.elis.provider.mensa.SpeiseplanIconHashMapEntry[] getIconHashMap() {
        return iconHashMap;
    }


    /**
     * Sets the iconHashMap value for this Speiseplan.
     * 
     * @param iconHashMap
     */
    public void setIconHashMap(de.unipotsdam.elis.provider.mensa.SpeiseplanIconHashMapEntry[] iconHashMap) {
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
            ((this.meal==null && other.getMeal()==null) || 
             (this.meal!=null &&
              java.util.Arrays.equals(this.meal, other.getMeal()))) &&
            ((this.campus==null && other.getCampus()==null) || 
             (this.campus!=null &&
              this.campus.equals(other.getCampus()))) &&
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
        if (getMeal() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMeal());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMeal(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCampus() != null) {
            _hashCode += getCampus().hashCode();
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
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mensa.provider.elis.unipotsdam.de/", "speiseplan"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("meal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "meal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://mensa.provider.elis.unipotsdam.de/", "gericht"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "campus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://mensa.provider.elis.unipotsdam.de/", "campusTyp"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("iconHashMap");
        elemField.setXmlName(new javax.xml.namespace.QName("", "iconHashMap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://mensa.provider.elis.unipotsdam.de/", ">>speiseplan>iconHashMap>entry"));
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

}
