package com.webservicex.country;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webservicex.country.bindings.Country;
import com.webservicex.country.bindings.CountrySoap;

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
			final Country countryService = new Country();
			instance.countrySoap = countryService.getCountrySoap();
		}
		return instance;
	}
	
//	public List<Table> getCountries() throws JAXBException, UnsupportedEncodingException {
//        final Unmarshaller u = context.createUnmarshaller();
//        final String countriesStr = countrySoap.getCountries();
//		final NewDataSet countriesList = (NewDataSet)u.unmarshal(new ByteArrayInputStream(countriesStr.getBytes(StandardCharsets.UTF_8.name())));
//		return countriesList.getCountries();
//	}

}