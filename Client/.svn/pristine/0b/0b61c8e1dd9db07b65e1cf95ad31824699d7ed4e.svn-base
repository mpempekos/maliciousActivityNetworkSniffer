/**
 * MaliciousPatterns.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package server;

public class MaliciousPatterns  implements java.io.Serializable {
    private java.lang.String[] maliciousStringPatternsList;

    private java.lang.String[] maliciousIPsList;

    public MaliciousPatterns() {
    }

    public MaliciousPatterns(
           java.lang.String[] maliciousStringPatternsList,
           java.lang.String[] maliciousIPsList) {
           this.maliciousStringPatternsList = maliciousStringPatternsList;
           this.maliciousIPsList = maliciousIPsList;
    }


    /**
     * Gets the maliciousStringPatternsList value for this MaliciousPatterns.
     * 
     * @return maliciousStringPatternsList
     */
    public java.lang.String[] getMaliciousStringPatternsList() {
        return maliciousStringPatternsList;
    }


    /**
     * Sets the maliciousStringPatternsList value for this MaliciousPatterns.
     * 
     * @param maliciousStringPatternsList
     */
    public void setMaliciousStringPatternsList(java.lang.String[] maliciousStringPatternsList) {
        this.maliciousStringPatternsList = maliciousStringPatternsList;
    }

    public java.lang.String getMaliciousStringPatternsList(int i) {
        return this.maliciousStringPatternsList[i];
    }

    public void setMaliciousStringPatternsList(int i, java.lang.String _value) {
        this.maliciousStringPatternsList[i] = _value;
    }


    /**
     * Gets the maliciousIPsList value for this MaliciousPatterns.
     * 
     * @return maliciousIPsList
     */
    public java.lang.String[] getMaliciousIPsList() {
        return maliciousIPsList;
    }


    /**
     * Sets the maliciousIPsList value for this MaliciousPatterns.
     * 
     * @param maliciousIPsList
     */
    public void setMaliciousIPsList(java.lang.String[] maliciousIPsList) {
        this.maliciousIPsList = maliciousIPsList;
    }

    public java.lang.String getMaliciousIPsList(int i) {
        return this.maliciousIPsList[i];
    }

    public void setMaliciousIPsList(int i, java.lang.String _value) {
        this.maliciousIPsList[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MaliciousPatterns)) return false;
        MaliciousPatterns other = (MaliciousPatterns) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.maliciousStringPatternsList==null && other.getMaliciousStringPatternsList()==null) || 
             (this.maliciousStringPatternsList!=null &&
              java.util.Arrays.equals(this.maliciousStringPatternsList, other.getMaliciousStringPatternsList()))) &&
            ((this.maliciousIPsList==null && other.getMaliciousIPsList()==null) || 
             (this.maliciousIPsList!=null &&
              java.util.Arrays.equals(this.maliciousIPsList, other.getMaliciousIPsList())));
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
        if (getMaliciousStringPatternsList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMaliciousStringPatternsList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMaliciousStringPatternsList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMaliciousIPsList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMaliciousIPsList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMaliciousIPsList(), i);
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
        new org.apache.axis.description.TypeDesc(MaliciousPatterns.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server/", "maliciousPatterns"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maliciousStringPatternsList");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maliciousStringPatternsList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maliciousIPsList");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maliciousIPsList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
