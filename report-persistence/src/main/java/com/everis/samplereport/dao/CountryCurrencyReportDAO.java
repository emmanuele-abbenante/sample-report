package com.everis.samplereport.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.everis.samplereport.model.CountryCurrencyReportItem;


public class CountryCurrencyReportDAO extends AbstractReportDAO {
    
	private static final String INSERT_REPORT_ITEM_STMT = 
		"INSERT INTO COUNTRIES_CURRENCIES_REPORT (COUNTRY_NAME, COUNTRY_CODE, GMT, CURRENCY_NAME, CURRENCY_CODE, ITALIAN_TIME, LOCAL_TIME) " +
		"VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String RETRIEVE_LATEST_DATA_IN_DATE_RANGE_QUERY = 
		"SELECT COUNTRY_NAME, COUNTRY_CODE, GMT, CURRENCY_NAME, CURRENCY_CODE, ITALIAN_TIME, LOCAL_TIME " +
		"FROM COUNTRIES_CURRENCIES_REPORT " +
		"WHERE ITALIAN_TIME IN ( " +
			"SELECT MAX(ITALIAN_TIME) " +
			"FROM COUNTRIES_CURRENCIES_REPORT " +
			"WHERE ITALIAN_TIME >= ? " +
			"AND ITALIAN_TIME <= ? " +
		")";
	private static final String RETRIEVE_LATEST_DATA_IN_DATE_RANGE_BY_COUNTRY_QUERY = 
			"SELECT COUNTRY_NAME, COUNTRY_CODE, GMT, CURRENCY_NAME, CURRENCY_CODE, ITALIAN_TIME, LOCAL_TIME " +
			"FROM COUNTRIES_CURRENCIES_REPORT " +
			"WHERE ITALIAN_TIME IN ( " +
				"SELECT MAX(ITALIAN_TIME) " +
				"FROM COUNTRIES_CURRENCIES_REPORT " +
				"WHERE ITALIAN_TIME >= ? " +
				"AND ITALIAN_TIME <= ? " +
				"AND COUNTRY_NAME = ? " +
			")" +
			"AND COUNTRY_NAME = ? ";
	private static final String RETRIEVE_LATEST_DATA = 
			"SELECT COUNTRY_NAME, COUNTRY_CODE, GMT, CURRENCY_NAME, CURRENCY_CODE, ITALIAN_TIME, LOCAL_TIME " +
			"FROM COUNTRIES_CURRENCIES_REPORT " +
			"WHERE ITALIAN_TIME IN ( " +
				"SELECT MAX(ITALIAN_TIME) " +
				"FROM COUNTRIES_CURRENCIES_REPORT " +
			")";
	
    private static CountryCurrencyReportDAO instance;
    
    private CountryCurrencyReportDAO() {}
    
    public static CountryCurrencyReportDAO getInstance() {
        if(instance == null){
            instance = new CountryCurrencyReportDAO();
        }
        return instance;
    }
    
    public void insertItem(final CountryCurrencyReportItem item) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            // Opening transaction
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(INSERT_REPORT_ITEM_STMT);
            
            stmt.setString(1, item.getCountryName());
            stmt.setString(2, item.getCountryCode());
            stmt.setInt(3, item.getGmt());
            stmt.setString(4, item.getCurrency());
            stmt.setString(5, item.getCurrencyCode());
            stmt.setTimestamp(6, new Timestamp(item.getItalianTime().getTime()));
            stmt.setTimestamp(7, new Timestamp(item.getLocalTime().getTime()));
            
            stmt.executeUpdate();
            
            conn.commit();
        } catch(Exception e){
            if(conn != null){
                conn.rollback();
            }
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public List<CountryCurrencyReportItem> retrieveLatestDataInDateRange(final Date fromDate, final Date toDate) throws Exception {
        List<CountryCurrencyReportItem> result = new ArrayList<CountryCurrencyReportItem>();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE_LATEST_DATA_IN_DATE_RANGE_QUERY);
            
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(fromDate.getTime()));
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(toDate.getTime()));
            
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
    			result.add(mapRow(rs));
            }
            rs.close();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        
        return result;
    }

    public CountryCurrencyReportItem retrieveLatestDataInDateRangeByCountry(final String country, final Date fromDate, final Date toDate) throws Exception {
        CountryCurrencyReportItem result = null;
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE_LATEST_DATA_IN_DATE_RANGE_BY_COUNTRY_QUERY);
            
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(fromDate.getTime()));
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(toDate.getTime()));
            preparedStatement.setString(3, country);
            preparedStatement.setString(4, country);
            
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
    			result = mapRow(rs);
            }
            rs.close();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        
        return result;
    }

    public List<CountryCurrencyReportItem> retrieveLatestData() throws Exception {
        List<CountryCurrencyReportItem> result = new ArrayList<CountryCurrencyReportItem>();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE_LATEST_DATA);
            
            final ResultSet rs = preparedStatement.executeQuery();
    		while (rs.next()) {
    			result.add(mapRow(rs));
            }
            rs.close();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        
        return result;
    }

	private CountryCurrencyReportItem mapRow(final ResultSet rs) throws SQLException {
		final CountryCurrencyReportItem item = new CountryCurrencyReportItem();
		item.setCountryName(rs.getString("COUNTRY_NAME"));
		item.setCountryCode(rs.getString("COUNTRY_CODE"));
        item.setGmt(rs.getInt("GMT"));
        item.setCurrency(rs.getString("CURRENCY_NAME"));
        item.setCurrencyCode(rs.getString("CURRENCY_CODE"));
        final Timestamp italianTime = rs.getTimestamp("ITALIAN_TIME");
        if(italianTime != null){
        	item.setItalianTime(new Date(italianTime.getTime()));
        }
        final Timestamp localTime = rs.getTimestamp("LOCAL_TIME");
        if(localTime != null){
        	item.setLocalTime(new Date(localTime.getTime()));
        }
		return item;
	}
    
}