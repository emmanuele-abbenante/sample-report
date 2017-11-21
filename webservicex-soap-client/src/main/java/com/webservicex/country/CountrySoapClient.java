package com.webservicex.country;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webservicex.country.bindings.Country;
import com.webservicex.country.bindings.CountrySoap;
import com.webservicex.country.bindings.data.NewDataSet;
import com.webservicex.country.bindings.data.Table;

public class CountrySoapClient {
	
	private static final Logger log = LoggerFactory.getLogger(CountrySoapClient.class);
	
	// The JAXB context is thread-safe, so the same instance can be used through multiple calls.
	private static JAXBContext context = null;
	static {
		try {
			context = JAXBContext.newInstance("com.webservicex.country.bindings.data");
		} catch (JAXBException e) {
			log.error("Error while initializing logger", e);
		}
	}
	
	private static CountrySoapClient instance;
	private CountrySoap countrySoap;

	private CountrySoapClient() {}

	public static CountrySoapClient getInstance() {
		if (instance == null) {
			instance = new CountrySoapClient();
			final Country countryService = 
					new Country();
			instance.countrySoap = countryService.getCountrySoap();
		}
		return instance;
	}
	
	public List<Table> getCurrencies() throws JAXBException, UnsupportedEncodingException {
		List<Table> currencies = new ArrayList<Table>();
        final Unmarshaller u = context.createUnmarshaller();
        final String currenciesStr = countrySoap.getCurrencies();
		final NewDataSet currenciesList = (NewDataSet)u.unmarshal(new ByteArrayInputStream(currenciesStr.getBytes(StandardCharsets.UTF_8.name())));
		if(currenciesList != null) {
			currencies = currenciesList.getData();
		} else {
			log.warn("No currencies retrieved");
		}
		return currencies;
	}

	public Table getGMT(final String countryName) throws JAXBException, UnsupportedEncodingException {
		Table gmt = null;
        final Unmarshaller u = context.createUnmarshaller();
        final String gmtsStr = countrySoap.getGMTbyCountry(countryName);
		final NewDataSet gmtsList = (NewDataSet)u.unmarshal(new ByteArrayInputStream(gmtsStr.getBytes(StandardCharsets.UTF_8.name())));
		if(gmtsList != null && gmtsList.getData() != null) {
			gmt = gmtsList.getData().get(0);
		} else {
			log.warn("No GMT retrieved");
		}
		return gmt;
	}

}