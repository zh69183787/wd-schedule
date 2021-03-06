package com.wonders.task.workflow.ultimus;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetLaunchInformationResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="varList" type="{http://www.ultimus.com}ArrayOfVariable" minOccurs="0"/>
 *         &lt;element name="strError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getLaunchInformationResult",
    "varList",
    "strError"
})
@XmlRootElement(name = "GetLaunchInformationResponse")
public class GetLaunchInformationResponse {

    @XmlElement(name = "GetLaunchInformationResult")
    protected boolean getLaunchInformationResult;
    protected ArrayOfVariable varList;
    protected String strError;

    /**
     * Gets the value of the getLaunchInformationResult property.
     * 
     */
    public boolean isGetLaunchInformationResult() {
        return getLaunchInformationResult;
    }

    /**
     * Sets the value of the getLaunchInformationResult property.
     * 
     */
    public void setGetLaunchInformationResult(boolean value) {
        this.getLaunchInformationResult = value;
    }

    /**
     * Gets the value of the varList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfVariable }
     *     
     */
    public ArrayOfVariable getVarList() {
        return varList;
    }

    /**
     * Sets the value of the varList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfVariable }
     *     
     */
    public void setVarList(ArrayOfVariable value) {
        this.varList = value;
    }

    /**
     * Gets the value of the strError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrError() {
        return strError;
    }

    /**
     * Sets the value of the strError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrError(String value) {
        this.strError = value;
    }

}
