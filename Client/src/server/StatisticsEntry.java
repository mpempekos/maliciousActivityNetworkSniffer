/**
 * StatisticsEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package server;

public class StatisticsEntry  implements java.io.Serializable {
    private java.lang.String nodeID;

    private java.lang.String interfaceName;

    private java.lang.String interfaceIP;

    private java.lang.String maliciousPattern;

    private int frequency;

    public StatisticsEntry() {
    }

    public StatisticsEntry(
           java.lang.String nodeID,
           java.lang.String interfaceName,
           java.lang.String interfaceIP,
           java.lang.String maliciousPattern,
           int frequency) {
           this.nodeID = nodeID;
           this.interfaceName = interfaceName;
           this.interfaceIP = interfaceIP;
           this.maliciousPattern = maliciousPattern;
           this.frequency = frequency;
    }


    /**
     * Gets the nodeID value for this StatisticsEntry.
     * 
     * @return nodeID
     */
    public java.lang.String getNodeID() {
        return nodeID;
    }


    /**
     * Sets the nodeID value for this StatisticsEntry.
     * 
     * @param nodeID
     */
    public void setNodeID(java.lang.String nodeID) {
        this.nodeID = nodeID;
    }


    /**
     * Gets the interfaceName value for this StatisticsEntry.
     * 
     * @return interfaceName
     */
    public java.lang.String getInterfaceName() {
        return interfaceName;
    }


    /**
     * Sets the interfaceName value for this StatisticsEntry.
     * 
     * @param interfaceName
     */
    public void setInterfaceName(java.lang.String interfaceName) {
        this.interfaceName = interfaceName;
    }


    /**
     * Gets the interfaceIP value for this StatisticsEntry.
     * 
     * @return interfaceIP
     */
    public java.lang.String getInterfaceIP() {
        return interfaceIP;
    }


    /**
     * Sets the interfaceIP value for this StatisticsEntry.
     * 
     * @param interfaceIP
     */
    public void setInterfaceIP(java.lang.String interfaceIP) {
        this.interfaceIP = interfaceIP;
    }


    /**
     * Gets the maliciousPattern value for this StatisticsEntry.
     * 
     * @return maliciousPattern
     */
    public java.lang.String getMaliciousPattern() {
        return maliciousPattern;
    }


    /**
     * Sets the maliciousPattern value for this StatisticsEntry.
     * 
     * @param maliciousPattern
     */
    public void setMaliciousPattern(java.lang.String maliciousPattern) {
        this.maliciousPattern = maliciousPattern;
    }


    /**
     * Gets the frequency value for this StatisticsEntry.
     * 
     * @return frequency
     */
    public int getFrequency() {
        return frequency;
    }


    /**
     * Sets the frequency value for this StatisticsEntry.
     * 
     * @param frequency
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StatisticsEntry)) return false;
        StatisticsEntry other = (StatisticsEntry) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nodeID==null && other.getNodeID()==null) || 
             (this.nodeID!=null &&
              this.nodeID.equals(other.getNodeID()))) &&
            ((this.interfaceName==null && other.getInterfaceName()==null) || 
             (this.interfaceName!=null &&
              this.interfaceName.equals(other.getInterfaceName()))) &&
            ((this.interfaceIP==null && other.getInterfaceIP()==null) || 
             (this.interfaceIP!=null &&
              this.interfaceIP.equals(other.getInterfaceIP()))) &&
            ((this.maliciousPattern==null && other.getMaliciousPattern()==null) || 
             (this.maliciousPattern!=null &&
              this.maliciousPattern.equals(other.getMaliciousPattern()))) &&
            this.frequency == other.getFrequency();
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
        if (getNodeID() != null) {
            _hashCode += getNodeID().hashCode();
        }
        if (getInterfaceName() != null) {
            _hashCode += getInterfaceName().hashCode();
        }
        if (getInterfaceIP() != null) {
            _hashCode += getInterfaceIP().hashCode();
        }
        if (getMaliciousPattern() != null) {
            _hashCode += getMaliciousPattern().hashCode();
        }
        _hashCode += getFrequency();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatisticsEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server/", "statisticsEntry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nodeID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nodeID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interfaceName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "interfaceName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interfaceIP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "interfaceIP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maliciousPattern");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maliciousPattern"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("frequency");
        elemField.setXmlName(new javax.xml.namespace.QName("", "frequency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
