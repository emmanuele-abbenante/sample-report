package com.everis.samplereport.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

import com.everis.samplereport.model.CountryCurrencyReportItem;


public class CountryCurrencyReportDAO extends AbstractReportDAO {
    
	private static final String INSERT_REPORT_ITEM_STMT = 
		"INSERT INTO COUNTRIES_CURRENCIES_REPORT (COUNTRY_NAME, COUNTRY_CODE, GMT, CURRENCY_NAME, CURRENCY_CODE, ITALIAN_TIME, LOCAL_TIME) " +
		"VALUES (?, ?, ?, ?, ?, ?, ?)";
	
    private static CountryCurrencyReportDAO instance;
    
    private CountryCurrencyReportDAO() {}
    
    public static CountryCurrencyReportDAO getInstance() {
        if(instance == null){
            instance = new CountryCurrencyReportDAO();
        }
        return instance;
    }
    
    public List<Object[]> retrieveReportItems() throws Exception {
    	return select("SELECT * FROM COUNTRIES_CURRENCIES_REPORT");
    }
    
    public void insertItems(final List<CountryCurrencyReportItem> items) {
    	
    }
    
    public void insertItem(final CountryCurrencyReportItem item) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(INSERT_REPORT_ITEM_STMT);
            
            stmt.setString(1, item.getCountryName());
            stmt.setString(2, item.getCountryCode());
            stmt.setInt(3, item.getGmt());
            stmt.setString(4, item.getCurrency());
            stmt.setString(5, item.getCurrencyCode());
            stmt.setTimestamp(6, new Timestamp(item.getItalianTime().getTime()));
            stmt.setTimestamp(7, new Timestamp(item.getLocalTime().getTime()));
            
            stmt.executeUpdate();
            
            if(!conn.getAutoCommit()){
                conn.commit();
            }
        } catch(Exception e){
            if(conn != null && !conn.getAutoCommit()){
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
}