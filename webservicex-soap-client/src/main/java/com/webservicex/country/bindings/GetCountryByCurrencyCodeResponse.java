
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
 *         &lt;element name="GetCountryByCurrencyCodeResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "getCountryByCurrencyCodeResult"
})
@XmlRootElement(name = "GetCountryByCurrencyCodeResponse")
public class GetCountryByCurrencyCodeResponse {

    @XmlElement(name = "GetCountryByCurrencyCodeResult")
    protected String getCountryByCurrencyCodeResult;

    /**
     * Recupera il valore della proprietà getCountryByCurrencyCodeResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetCountryByCurrencyCodeResult() {
        return getCountryByCurrencyCodeResult;
    }

    /**
     * Imposta il valore della proprietà getCountryByCurrencyCodeResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetCountryByCurrencyCodeResult(String value) {
        this.getCountryByCurrencyCodeResult = value;
    }

}
