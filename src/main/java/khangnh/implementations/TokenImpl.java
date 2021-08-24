/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import khangnh.interfaces.TokenDAO;
import khangnh.utils.DBUtil;

/**
 *
 * @author khang nguyen
 */
public class TokenImpl implements TokenDAO {

    @Override
    public void insert(String token, String email) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "INSERT INTO Token(tokenString, email) VALUES(?,?)";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setString(1, token);
                    statement.setString(2, email);
                    statement.executeUpdate();
                }
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public boolean isActivate(String email, int status) throws SQLException, NamingException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "SELECT id FROM Token WHERE email = ? AND activate = ?";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setString(1, email);
                    statement.setInt(2, status);
                    resultSet = statement.executeQuery();
                    if(resultSet != null){
                        result = resultSet.next();
                    }
                }
            }
        } finally {
            if(resultSet != null){
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    @Override
    public int update(String email, String token) throws SQLException, NamingException {
        int result = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "UPDATE Token SET activate = 1 WHERE email = ? AND tokenString = ?";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setString(1, email);
                    statement.setString(2, token);
                    result = statement.executeUpdate();
                }
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

}
