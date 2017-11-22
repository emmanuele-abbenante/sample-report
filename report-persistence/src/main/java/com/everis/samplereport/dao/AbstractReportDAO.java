package com.everis.samplereport.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.configuration2.Configuration;

import com.everis.common.properties.PropertiesUtils;

public abstract class AbstractReportDAO {
    
    private static Configuration config;
    static {
        try {
            config = PropertiesUtils.buildConfiguration("jdbc.properties");
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        } catch(Exception e) {
        }
    }
    
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(config.getString("url"), config.getString("user"), config.getString("password"));
    }

}