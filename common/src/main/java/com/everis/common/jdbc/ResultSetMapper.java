package com.everis.common.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetMapper {
    
    public Object map(final ResultSet rs) throws SQLException;

}
