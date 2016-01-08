/**
 * AvailableNodes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package server;

public class AvailableNodes  implements java.io.Serializable {
    private java.lang.String nodes;

    public AvailableNodes() {
    }

    public AvailableNodes(
           java.lang.String nodes) {
           this.nodes = nodes;
    }


    /**
     * Gets the nodes value for this AvailableNodes.
     * 
     * @return nodes
     */
    public java.lang.String getNodes() {
        return nodes;
    }


    /**
     * Sets the nodes value for this AvailableNodes.
     * 
     * @param nodes
     */
    public void setNodes(java.lang.String nodes) {
        this.nodes = nodes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AvailableNodes)) return false;
        AvailableNodes other = (AvailableNodes) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nodes==null && other.getNodes()==null) || 
             (this.nodes!=null &&
              this.nodes.equals(other.getNodes())));
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
        if (getNodes() != null) {
            _hashCode += getNodes().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AvailableNodes.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server/", "availableNodes"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nodes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
