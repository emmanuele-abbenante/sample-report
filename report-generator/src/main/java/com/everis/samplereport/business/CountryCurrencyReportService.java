package com.everis.samplereport.business;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.samplereport.dao.CountryCurrencyReportDAO;
import com.everis.samplereport.model.CountryCurrencyReportItem;
import com.webservicex.country.CountrySoapClient;
import com.webservicex.country.bindings.data.Table;



public class CountryCurrencyReportService {
    
	private static final Pattern GMT_PATTERN = Pattern.compile("\\+[0-9][0-9]?");
	private static final DecimalFormat GMT_FORMATTER = new DecimalFormat("+#");
	private static final Logger log = LoggerFactory.getLogger(CountryCurrencyReportService.class);
	
    private static CountryCurrencyReportService instance;
    
    private CountryCurrencyReportService(){}
    
    public static CountryCurrencyReportService getInstance(){
        if(instance == null){
            instance = new CountryCurrencyReportService();
        }
        return instance;
    }
    
    public List<CountryCurrencyReportItem> importCountriesCurrenciesData() throws Exception {
    	final List<CountryCurrencyReportItem> items = new ArrayList<CountryCurrencyReportItem>();
    	
    	final Date currentItalianTime = new Date();
    	final Calendar gmtTimeCal = GregorianCalendar.getInstance();
		gmtTimeCal.setTime(currentItalianTime);
		gmtTimeCal.add(Calendar.HOUR_OF_DAY, -1);
		final Date currentGmtTime = gmtTimeCal.getTime();
		
    	final List<Table> currencies = CountrySoapClient.getInstance().getCurrencies();
		for (final Table currency : currencies) {
			final String countryName = currency.getName();
			final String gmtStr = CountrySoapClient.getInstance().getGMT(countryName).getGmt();
			if(StringUtils.isBlank(gmtStr)){
				log.error("No GMT available for country \"" + countryName + "\"");
				continue;
			}
			final Matcher matcher = GMT_PATTERN.matcher(gmtStr); 
			if(!matcher.matches()){
				log.warn("GMT for country \"" + countryName + "\" doesn't match the pattern \"+h\": " + gmtStr);
				continue;
			}
			
			final CountryCurrencyReportItem item = buildItem(currency);
			items.add(item);
			item.setItalianTime(currentItalianTime);
			final Integer gmt = ((Long)GMT_FORMATTER.parse(gmtStr)).intValue();
			item.setGmt(gmt);
			final Calendar localTimeCal = GregorianCalendar.getInstance();
			localTimeCal.setTime(currentGmtTime);
			localTimeCal.add(Calendar.HOUR_OF_DAY, gmt);
			item.setLocalTime(localTimeCal.getTime());
			CountryCurrencyReportDAO.getInstance().insertItem(item);
		}
		
		return items;
    }
    
    private CountryCurrencyReportItem buildItem(final Table currency){
    	final CountryCurrencyReportItem item = new CountryCurrencyReportItem();
    	item.setCountryName(currency.getName());
    	item.setCountryCode(currency.getCountryCode());
    	item.setCurrency(currency.getCurrency());
    	item.setCurrencyCode(currency.getCurrencyCode());
    	return item;
    }
    
}