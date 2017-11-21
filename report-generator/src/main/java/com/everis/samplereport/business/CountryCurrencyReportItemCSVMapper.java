package com.everis.samplereport.business;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.everis.samplereport.model.CountryCurrencyReportItem;

public class CountryCurrencyReportItemCSVMapper {

    private static final String COMMA_SEPARATOR = ",";
    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateFormat TIMESTAMP_FORMATTER = new SimpleDateFormat(TIMESTAMP_PATTERN);
    
	private static CountryCurrencyReportItemCSVMapper instance;
    
    private CountryCurrencyReportItemCSVMapper(){}
    
    public static CountryCurrencyReportItemCSVMapper getInstance(){
        if(instance == null){
            instance = new CountryCurrencyReportItemCSVMapper();
        }
        return instance;
    }
    
    public String toCSV(final CountryCurrencyReportItem item){
    	final StringBuilder builder = new StringBuilder();
    	// Replacing any comma in the country name to avoid corrupting the CSV row
    	builder.append(item.getCountryName().replace(',', ';')).append(COMMA_SEPARATOR);
    	builder.append(item.getCountryCode()).append(COMMA_SEPARATOR);
    	final Integer gmt = item.getGmt();
    	builder.append(gmt != null ? gmt : "").append(COMMA_SEPARATOR);
    	final String currencyCode = item.getCurrencyCode();
    	builder.append(currencyCode != null ? currencyCode : "").append(COMMA_SEPARATOR);
    	final String currency= item.getCurrency();
    	builder.append(currency != null ? currency : "").append(COMMA_SEPARATOR);
    	builder.append(TIMESTAMP_FORMATTER.format(item.getItalianTime())).append(COMMA_SEPARATOR);
    	final Date localTime = item.getLocalTime();
    	builder.append(localTime != null ? TIMESTAMP_FORMATTER.format(item.getLocalTime()) : "");
    	return builder.toString();
    }
    
}
