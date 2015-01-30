/**
 * Preise.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package de.unipotsdam.elis.provider.mensa;

public class Preise  implements java.io.Serializable {
    private java.lang.Double guest;

    private java.lang.Double staff;

    private java.lang.Double student;

    public Preise() {
    }

    public Preise(
           java.lang.Double guest,
           java.lang.Double staff,
           java.lang.Double student) {
           this.guest = guest;
           this.staff = staff;
           this.student = student;
    }


    /**
     * Gets the guest value for this Preise.
     * 
     * @return guest
     */
    public java.lang.Double getGuest() {
        return guest;
    }


    /**
     * Sets the guest value for this Preise.
     * 
     * @param guest
     */
    public void setGuest(java.lang.Double guest) {
        this.guest = guest;
    }


    /**
     * Gets the staff value for this Preise.
     * 
     * @return staff
     */
    public java.lang.Double getStaff() {
        return staff;
    }


    /**
     * Sets the staff value for this Preise.
     * 
     * @param staff
     */
    public void setStaff(java.lang.Double staff) {
        this.staff = staff;
    }


    /**
     * Gets the student value for this Preise.
     * 
     * @return student
     */
    public java.lang.Double getStudent() {
        return student;
    }


    /**
     * Sets the student value for this Preise.
     * 
     * @param student
     */
    public void setStudent(java.lang.Double student) {
        this.student = student;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Preise)) return false;
        Preise other = (Preise) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.guest==null && other.getGuest()==null) || 
             (this.guest!=null &&
              this.guest.equals(other.getGuest()))) &&
            ((this.staff==null && other.getStaff()==null) || 
             (this.staff!=null &&
              this.staff.equals(other.getStaff()))) &&
            ((this.student==null && other.getStudent()==null) || 
             (this.student!=null &&
              this.student.equals(other.getStudent())));
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
        if (getGuest() != null) {
            _hashCode += getGuest().hashCode();
        }
        if (getStaff() != null) {
            _hashCode += getStaff().hashCode();
        }
        if (getStudent() != null) {
            _hashCode += getStudent().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Preise.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://mensa.provider.elis.unipotsdam.de/", "preise"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("guest");
        elemField.setXmlName(new javax.xml.namespace.QName("", "guest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("staff");
        elemField.setXmlName(new javax.xml.namespace.QName("", "staff"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("student");
        elemField.setXmlName(new javax.xml.namespace.QName("", "student"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
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
