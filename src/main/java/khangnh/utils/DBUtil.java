/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author khang nguyen
 */
public class DBUtil {
    public static Connection getConnection() throws NamingException, SQLException{
        Connection connection = null;
        Context context = new InitialContext();
        Context environmentContext = (Context) context.lookup("java:comp/env");
        DataSource dataSource = (DataSource) environmentContext.lookup("DBCon");
        connection = dataSource.getConnection();
        return connection;
    }
}
