
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
 *         &lt;element name="GetCurrencyCodeByCurrencyNameResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "getCurrencyCodeByCurrencyNameResult"
})
@XmlRootElement(name = "GetCurrencyCodeByCurrencyNameResponse")
public class GetCurrencyCodeByCurrencyNameResponse {

    @XmlElement(name = "GetCurrencyCodeByCurrencyNameResult")
    protected String getCurrencyCodeByCurrencyNameResult;

    /**
     * Recupera il valore della proprietà getCurrencyCodeByCurrencyNameResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetCurrencyCodeByCurrencyNameResult() {
        return getCurrencyCodeByCurrencyNameResult;
    }

    /**
     * Imposta il valore della proprietà getCurrencyCodeByCurrencyNameResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetCurrencyCodeByCurrencyNameResult(String value) {
        this.getCurrencyCodeByCurrencyNameResult = value;
    }

}
