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
import khangnh.feedbacks.FeedBackDTO;
import khangnh.interfaces.FeedBackDAO;
import khangnh.utils.DBUtil;

/**
 *
 * @author khang nguyen
 */
public class FeedBackImpl implements FeedBackDAO {

    @Override
    public void insert(FeedBackDTO feedBack) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "INSERT INTO FeedBack(accountID, carID, rentalDetailID, rate, content) VALUES(?,?,?,?,?)";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setString(1, feedBack.getEmail());
                    statement.setInt(2, feedBack.getCarID());
                    statement.setInt(3, feedBack.getRentalDetailID());
                    statement.setInt(4, feedBack.getRate());
                    statement.setString(5, feedBack.getContent());
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
    public FeedBackDTO getFeedBack(String email, int carID, int rentalDetailID) throws SQLException, NamingException {
        FeedBackDTO feedback = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "SELECT id, rate, content FROM FeedBack WHERE accountID = ? AND carID = ? AND rentalDetailID = ?";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setString(1, email);
                    statement.setInt(2, carID);
                    statement.setInt(3, rentalDetailID);
                    resultSet = statement.executeQuery();
                    if (resultSet != null) {
                        if (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            int rate = resultSet.getInt("rate");
                            String content = resultSet.getString("content");
                            feedback = new FeedBackDTO(content, rate, id);
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
        return feedback;
    }

    @Override
    public void update(FeedBackDTO feedBack) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "UPDATE FeedBack SET rate = ?, content = ? WHERE id = ?";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setInt(1, feedBack.getRate());
                    statement.setString(2, feedBack.getContent());
                    statement.setInt(3, feedBack.getId());
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

}
