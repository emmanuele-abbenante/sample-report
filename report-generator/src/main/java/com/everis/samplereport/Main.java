package com.everis.samplereport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
			if(args.length > 1 && "--countries".equals(args[0]) && !StringUtils.isBlank(args[1])){
				final String[] countries = args[1].split(",");
				final List<CountryCurrencyReportItem> reportItems = CountryCurrencyReportService.getInstance().importCountriesCurrenciesData(Arrays.asList(countries));
				final List<String> csvRows = new ArrayList<String>();
				for (final CountryCurrencyReportItem item : reportItems) {
					csvRows.add(CountryCurrencyReportItemCSVMapper.getInstance().toCSV(item));
				}
				CSVUtils.saveAsCsv(csvRows, config.getString("csv.basePath") + new Date().getTime() + REPORT_CSV_SUFFIX);
			} else {
				final List<CountryCurrencyReportItem> reportItems = CountryCurrencyReportService.getInstance().importCountriesCurrenciesData();
				final List<String> csvRows = new ArrayList<String>();
				for (final CountryCurrencyReportItem item : reportItems) {
					csvRows.add(CountryCurrencyReportItemCSVMapper.getInstance().toCSV(item));
				}
				CSVUtils.saveAsCsv(csvRows, config.getString("csv.basePath") + new Date().getTime() + REPORT_CSV_SUFFIX);
			}
		} catch (Exception e) {
			log.error("Error while generating report: ", e);
		}

	}

}
