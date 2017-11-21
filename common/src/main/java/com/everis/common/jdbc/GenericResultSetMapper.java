package com.everis.common.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GenericResultSetMapper implements ResultSetMapper {
    
    public Object map(final ResultSet rs) throws SQLException {
        final List<Object[]> list = new LinkedList<Object[]>();
        
        final Integer columnCount = rs.getMetaData().getColumnCount();
        
        while (rs.next()) {
            final Object[] currentRow = new Object[columnCount];
            list.add(currentRow);
            for (int i = 0; i < currentRow.length; i++) {
                currentRow[i] = rs.getObject(i+1);
            }
        }
        return list;            
    }

}
