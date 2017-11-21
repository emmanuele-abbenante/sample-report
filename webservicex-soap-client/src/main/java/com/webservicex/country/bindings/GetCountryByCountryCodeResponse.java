
package com.webservicex.country.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GetCountryByCountryCodeResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "getCountryByCountryCodeResult"
})
@XmlRootElement(name = "GetCountryByCountryCodeResponse")
public class GetCountryByCountryCodeResponse {

    @XmlElement(name = "GetCountryByCountryCodeResult")
    protected String getCountryByCountryCodeResult;

    /**
     * Recupera il valore della proprietà getCountryByCountryCodeResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetCountryByCountryCodeResult() {
        return getCountryByCountryCodeResult;
    }

    /**
     * Imposta il valore della proprietà getCountryByCountryCodeResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetCountryByCountryCodeResult(String value) {
        this.getCountryByCountryCodeResult = value;
    }

}
