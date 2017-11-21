package com.everis.common.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractJDBCDAO {
    
    private static final GenericResultSetMapper GENERIC_RESULT_SET_MAPPER = new GenericResultSetMapper();
    
    protected abstract Connection getConnection() throws SQLException;
    
    public List<Object[]> select(final String query) throws Exception {
        return select(query, null, null, GENERIC_RESULT_SET_MAPPER);
    }
    
    public <T> List<T> select(final String query, final ResultSetMapper mapper) throws Exception {
        return select(query, null, null, mapper);
    }
    
    public  List<Object[]> select(final String query, final ParamsSetter setter, final Object paramsObject) throws Exception {
        return select(query, setter, paramsObject, GENERIC_RESULT_SET_MAPPER);
    }
    
    public <T> List<T> select(final String query, final ParamsSetter setter, final Object paramsObject, final ResultSetMapper mapper) throws Exception {
        List<T> result = new ArrayList<T>();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            if(setter != null && paramsObject != null){
                setter.setParams(preparedStatement, paramsObject);
            }
            final ResultSet rs = preparedStatement.executeQuery();
            result = (List<T>)mapper.map(rs);
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
    
    public Object selectSingleResult(final String query, final ResultSetMapper mapper) throws Exception {
        return selectSingleResult(query, null, null, mapper);
    }
    
    public Object selectSingleResult(final String query, final ParamsSetter setter, final Object paramsObject, final ResultSetMapper mapper) throws Exception {
        Object result = new Object();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            if(setter != null && paramsObject != null){
                setter.setParams(preparedStatement, paramsObject);
            }
            final ResultSet rs = preparedStatement.executeQuery();
            result = mapper.map(rs);
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
    
    public void update(final String stmt) throws Exception {
        update(stmt, null, null);
    }
    
    public void update(final String stmt, final ParamsSetter setter, final Object paramsObject) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(stmt);
            
            if(setter != null && paramsObject != null){
                setter.setParams(preparedStatement, paramsObject);
            }
            
            preparedStatement.executeUpdate();
            
            if(!connection.getAutoCommit()){
                connection.commit();
            }
        } catch(Exception e){
            if(connection != null && !connection.getAutoCommit()){
                connection.rollback();
            }
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    
}
