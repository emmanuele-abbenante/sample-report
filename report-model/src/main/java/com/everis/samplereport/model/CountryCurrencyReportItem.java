package com.everis.samplereport.model;

import java.util.Date;

public class CountryCurrencyReportItem {
	
	private String countryName;
	private String countryCode;
	private Integer gmt;
	private String currencyCode;
	private String currency;
	private Date italianTime;
	private Date localTime;
	
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Integer getGmt() {
		return gmt;
	}
	public void setGmt(Integer gmt) {
		this.gmt = gmt;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Date getItalianTime() {
		return italianTime;
	}
	public void setItalianTime(Date italianTime) {
		this.italianTime = italianTime;
	}
	public Date getLocalTime() {
		return localTime;
	}
	public void setLocalTime(Date localTime) {
		this.localTime = localTime;
	}
	
}
