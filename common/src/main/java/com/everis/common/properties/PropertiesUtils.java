package com.everis.common.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PropertiesUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesUtils.class);
    
    private static final String DEFAULT_SEPARATOR = "";
    
    public static String getMultipleLinesProperty(Properties props, String keysPrefix){
        return getMultipleLinesProperty(props, keysPrefix, DEFAULT_SEPARATOR);
    }
    
    @SuppressWarnings("unchecked")
    public static String getMultipleLinesProperty(Properties props, String keysPrefix, String separator){
        StringBuilder value = new StringBuilder();
        
        @SuppressWarnings("rawtypes")
        List keyList = new ArrayList(props.keySet());
        Collections.sort(keyList);
        
        try {
            for(Object key : keyList){
                if(((String)key).startsWith(keysPrefix)) {
                    value.append(props.getProperty((String)key) + separator);
                }
            }
        } catch(Exception e){
            LOG.error("", e);
        }
        
        return value.toString();
    }

    public static Configuration buildConfiguration(final String propertiesFileName) throws ConfigurationException {
        final Parameters params = new Parameters();
        final FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties().setFileName(propertiesFileName));
        return builder.getConfiguration();
    }

}
