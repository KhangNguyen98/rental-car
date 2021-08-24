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
import khangnh.interfaces.DiscountDAO;
import khangnh.utils.DBUtil;

/**
 *
 * @author khang nguyen
 */
public class DiscountImpl implements DiscountDAO {

    @Override
    public boolean checkValidDiscount(String id, int status, long today) throws SQLException, NamingException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "SELECT id FROM Discount WHERE id = ? AND status = ? AND ?<=expiredDate ";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setString(1, id);
                    statement.setInt(2, status);
                    statement.setLong(3, today);
                    resultSet = statement.executeQuery();
                    if (resultSet != null) {
                        result = resultSet.next();
                    }
                }
            }
        } finally {
            if (resultSet != null) {
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
    public int getRate(String id) throws SQLException, NamingException {
        int rate = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "SELECT rate FROM Discount WHERE id = ?";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setString(1, id);
                    resultSet = statement.executeQuery();
                    if (resultSet != null) {
                        if (resultSet.next()) {
                            rate = resultSet.getInt("rate");
                            System.out.println("On ko rate:"+ rate);
                        }
                    }
                }
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return rate;
    }

}
