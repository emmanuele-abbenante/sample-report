package com.everis.samplereport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.common.file.CSVUtils;
import com.everis.common.properties.PropertiesUtils;
import com.everis.samplereport.business.CountryCurrencyReportItemCSVMapper;
import com.everis.samplereport.business.CountryCurrencyReportService;
import com.everis.samplereport.model.CountryCurrencyReportItem;

public class Main {
	
	private static final String REPORT_CSV_SUFFIX = "_report.csv";
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
    private static Configuration config;
    static {
        try {
            config = PropertiesUtils.buildConfiguration("config.properties");
        } catch(Exception e) {
        	log.error("Error while loading configuration: ", e);
        }
    }
    
	public static void main(String[] args) {
		try {
			log.info("============================================================================");
			log.info("Generating report");
			log.info("============================================================================");
			final Map<String, String> argsMap = buildArgsMap(args);
			final String countriesStr = argsMap.get("--countries");
			final String country = argsMap.get("--country");
			final String dateStr = argsMap.get("--date");
			
			List<CountryCurrencyReportItem> reportItems = new ArrayList<CountryCurrencyReportItem>();
			
			// Both "countryStr" and "date" params are passed
			if(!StringUtils.isBlank(country) && !StringUtils.isBlank(dateStr)) {
				log.info("Country param: " + country);
				log.info("Date param: " + dateStr);
				reportItems = Arrays.asList(CountryCurrencyReportService.getInstance().retrieveLatestDataInDateRangeByCountry(country, dateStr));
			// Only "date" param is passed
			} else if(!StringUtils.isBlank(dateStr)) {
				log.info("Date param: " + dateStr);
				reportItems = CountryCurrencyReportService.getInstance().retrieveLatestDataInDateRange(dateStr);
				if(reportItems.size() == 0){
					log.warn("No data retrieved for the selected time range.");
					log.warn("Retrieving latest data.");
					reportItems = CountryCurrencyReportService.getInstance().retrieveLatestData();
				}
			// Only "countries" param is passed
			} else if(!StringUtils.isBlank(countriesStr)) {
				log.info("Countries param: " + countriesStr);
				final String[] countries = countriesStr.split(",");
				reportItems = CountryCurrencyReportService.getInstance().importCountriesCurrenciesData(Arrays.asList(countries));
			} else {
				log.info("No parameters passed. Retrieving data for all the countries.");
				reportItems = CountryCurrencyReportService.getInstance().importCountriesCurrenciesData();
			}
			final List<String> csvRows = CountryCurrencyReportItemCSVMapper.getInstance().toCSV(reportItems);
			final String filePath = config.getString("csv.basePath") + new Date().getTime() + REPORT_CSV_SUFFIX;
			CSVUtils.saveAsCsv(csvRows, filePath);
			
			log.info("============================================================================");
			log.info("Report generated");
			log.info("File path: " + filePath);
			log.info("============================================================================");
		} catch (Exception e) {
			log.error("Error while generating report: ", e);
		}

	}
	
	private static Map<String, String> buildArgsMap(final String[] args){
		final Map<String, String> map = new HashMap<String, String>();
		
		for (final String arg : args) {
			final String[] argItems = arg.split("=");
			map.put(argItems[0], argItems[1]);
		}
		
		return map;
	}
	
}
