package com.everis.common.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ParamsSetter {
    
    public void setParams(PreparedStatement ps, Object object) throws SQLException;

}
