package com.webservicex.country.bindings.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name="NewDataSet")
@XmlSeeAlso(Table.class)
@XmlAccessorType(XmlAccessType.FIELD)
public class NewDataSet {
	
    @XmlElement(name = "Table")
    private List<Table> data;

	public List<Table> getData() {
		return data;
	}

	public void setData(List<Table> data) {
		this.data = data;
	}

}