//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.02.21 at 03:15:42 PM EET 
//


package com.eden.enforcementService.metric.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsPlateExpiredResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "isPlateExpiredResult"
})
@XmlRootElement(name = "IsPlateExpiredResponse")
public class IsPlateExpiredResponse {

    @XmlElement(name = "IsPlateExpiredResult")
    protected boolean isPlateExpiredResult;

    /**
     * Gets the value of the isPlateExpiredResult property.
     * 
     */
    public boolean isIsPlateExpiredResult() {
        return isPlateExpiredResult;
    }

    /**
     * Sets the value of the isPlateExpiredResult property.
     * 
     */
    public void setIsPlateExpiredResult(boolean value) {
        this.isPlateExpiredResult = value;
    }

}
