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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SpaceZone complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SpaceZone"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ZoneID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FirstSpace" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="LastSpace" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="GracePeriodMinutes" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpaceZone", propOrder = {
    "zoneID",
    "description",
    "firstSpace",
    "lastSpace",
    "gracePeriodMinutes"
})
public class SpaceZone {

    @XmlElement(name = "ZoneID")
    protected int zoneID;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "FirstSpace")
    protected int firstSpace;
    @XmlElement(name = "LastSpace")
    protected int lastSpace;
    @XmlElement(name = "GracePeriodMinutes")
    protected int gracePeriodMinutes;

    /**
     * Gets the value of the zoneID property.
     * 
     */
    public int getZoneID() {
        return zoneID;
    }

    /**
     * Sets the value of the zoneID property.
     * 
     */
    public void setZoneID(int value) {
        this.zoneID = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the firstSpace property.
     * 
     */
    public int getFirstSpace() {
        return firstSpace;
    }

    /**
     * Sets the value of the firstSpace property.
     * 
     */
    public void setFirstSpace(int value) {
        this.firstSpace = value;
    }

    /**
     * Gets the value of the lastSpace property.
     * 
     */
    public int getLastSpace() {
        return lastSpace;
    }

    /**
     * Sets the value of the lastSpace property.
     * 
     */
    public void setLastSpace(int value) {
        this.lastSpace = value;
    }

    /**
     * Gets the value of the gracePeriodMinutes property.
     * 
     */
    public int getGracePeriodMinutes() {
        return gracePeriodMinutes;
    }

    /**
     * Sets the value of the gracePeriodMinutes property.
     * 
     */
    public void setGracePeriodMinutes(int value) {
        this.gracePeriodMinutes = value;
    }

}
