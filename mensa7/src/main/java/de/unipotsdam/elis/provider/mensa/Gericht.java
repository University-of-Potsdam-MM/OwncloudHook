/**
 * Gericht.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package de.unipotsdam.elis.provider.mensa;

public class Gericht  implements java.io.Serializable {
    private java.lang.String description;

    private de.unipotsdam.elis.provider.mensa.EssensTyp[] type;

    private de.unipotsdam.elis.provider.mensa.Preise prices;

    private java.util.Calendar date;

    private java.lang.String title;

    private de.unipotsdam.elis.provider.mensa.ZusatzstoffeTyp zusatzstoffe;

    private int order;  // attribute

    public Gericht() {
    }

    public Gericht(
           java.lang.String description,
           de.unipotsdam.elis.provider.mensa.EssensTyp[] type,
           de.unipotsdam.elis.provider.mensa.Preise prices,
           java.util.Calendar date,
           java.lang.String title,
           de.unipotsdam.elis.provider.mensa.ZusatzstoffeTyp zusatzstoffe,
           int order) {
           this.description = description;
           this.type = type;
           this.prices = prices;
           this.date = date;
           this.title = title;
           this.zusatzstoffe = zusatzstoffe;
           this.order = order;
    }


    /**
     * Gets the description value for this Gericht.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Gericht.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the type value for this Gericht.
     * 
     * @return type
     */
    public de.unipotsdam.elis.provider.mensa.EssensTyp[] getType() {
        return type;
    }


    /**
     * Sets the type value for this Gericht.
     * 
     * @param type
     */
    public void setType(de.unipotsdam.elis.provider.mensa.EssensTyp[] type) {
        this.type = type;
    }

    public de.unipotsdam.elis.provider.mensa.EssensTyp getType(int i) {
        return this.type[i];
    }

    public void setType(int i, de.unipotsdam.elis.provider.mensa.EssensTyp _value) {
        this.type[i] = _value;
    }


    /**
     * Gets the prices value for this Gericht.
     * 
     * @return prices
     */
    public de.unipotsdam.elis.provider.mensa.Preise getPrices() {
        return prices;
    }


    /**
     * Sets the prices value for this Gericht.
     * 
     * @param prices
     */
    public void setPrices(de.unipotsdam.elis.provider.mensa.Preise prices) {
        this.prices = prices;
    }


    /**
     * Gets the date value for this Gericht.
     * 
     * @return date
     */
    public java.util.Calendar getDate() {
        return date;
    }


    /**
     * Sets the date value for this Gericht.
     * 
     * @param date
     */
    public void setDate(java.util.Calendar date) {
        this.date = date;
    }


    /**
     * Gets the title value for this Gericht.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this Gericht.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    /**
     * Gets the zusatzstoffe value for this Gericht.
     * 
     * @return zusatzstoffe
     */
    public de.unipotsdam.elis.provider.mensa.ZusatzstoffeTyp getZusatzstoffe() {
        return zusatzstoffe;
    }


    /**
     * Sets the zusatzstoffe value for this Gericht.
     * 
     * @param zusatzstoffe
     */
    public void setZusatzstoffe(de.unipotsdam.elis.provider.mensa.ZusatzstoffeTyp zusatzstoffe) {
        this.zusatzstoffe = zusatzstoffe;
    }


    /**
     * Gets the order value for this Gericht.
     * 
     * @return order
     */
    public int getOrder() {
        return order;
    }


    /**
     * Sets the order value for this Gericht.
     * 
     * @param order
     */
    public void setOrder(int order) {
        this.order = order;
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
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              java.util.Arrays.equals(this.type, other.getType()))) &&
            ((this.prices==null && other.getPrices()==null) || 
             (this.prices!=null &&
              this.prices.equals(other.getPrices()))) &&
            ((this.date==null && other.getDate()==null) || 
             (this.date!=null &&
              this.date.equals(other.getDate()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle()))) &&
            ((this.zusatzstoffe==null && other.getZusatzstoffe()==null) || 
             (this.zusatzstoffe!=null &&
              this.zusatzstoffe.equals(other.getZusatzstoffe()))) &&
            this.order == other.getOrder();
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
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getType() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getType());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getType(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPrices() != null) {
            _hashCode += getPrices().hashCode();
        }
        if (getDate() != null) {
            _hashCode += getDate().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        if (getZusatzstoffe() != null) {
            _hashCode += getZusatzstoffe().hashCode();
        }
        _hashCode += getOrder();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Gericht.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mensa.provider.elis.unipotsdam.de/", "gericht"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("order");
        attrField.setXmlName(new javax.xml.namespace.QName("", "order"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://mensa.provider.elis.unipotsdam.de/", "essensTyp"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prices");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prices"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://mensa.provider.elis.unipotsdam.de/", "preise"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("date");
        elemField.setXmlName(new javax.xml.namespace.QName("", "date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("", "title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zusatzstoffe");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zusatzstoffe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://mensa.provider.elis.unipotsdam.de/", "zusatzstoffeTyp"));
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
