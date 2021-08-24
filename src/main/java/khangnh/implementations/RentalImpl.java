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
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import khangnh.convertions.DateConvertion;
import khangnh.interfaces.DiscountDAO;
import khangnh.interfaces.RentalDAO;
import khangnh.rentals.RentalDTO;
import khangnh.utils.DBUtil;

/**
 *
 * @author khang nguyen
 */
public class RentalImpl implements RentalDAO {

    @Override
    public void insert(RentalDTO rental) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            if(connection != null){
                String sql = "INSERT INTO Rental(accountID, rentalDate, returnDate, total, discountID, status) VALUES(?,?,?,?,?,?)";
                statement = connection.prepareStatement(sql);
                if(statement != null){
                    statement.setString(1, rental.getEmail());
                    statement.setLong(2, rental.getRentalDate());
                    statement.setLong(3, rental.getReturnDate());
                    statement.setFloat(4, rental.getTotal());
                    statement.setString(5, rental.getDiscountID());
                    statement.setInt(6, rental.getStatus());
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
    public int getRentalID() throws SQLException, NamingException {
        int rentalID = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            if(connection != null){
                String sql = "SELECT MAX(id) as rentalID FROM Rental";
                statement = connection.prepareStatement(sql);
                if(statement != null){
                    resultSet = statement.executeQuery();
                    if(resultSet != null){
                        if(resultSet.next()){
                            rentalID = resultSet.getInt("rentalID");
                        }
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
        return rentalID;
    }

    @Override
    public List<RentalDTO> viewOrder(String email) throws SQLException, NamingException {
        List<RentalDTO> list = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            if(connection != null){
                String sql = "SELECT id, rentalDate, returnDate, total, discountID, status FROM Rental WHERE accountID = ?"
                        + " ORDER BY rentalDate";
                statement = connection.prepareStatement(sql);
                if(statement != null){
                   statement.setString(1, email);
                   resultSet = statement.executeQuery();
                   if(resultSet != null){
                       while (resultSet.next()) {     
                           int id = resultSet.getInt("id");
                           long rentalDate = resultSet.getLong("rentalDate");
                           long returnDate = resultSet.getLong("returnDate");
                           float total = resultSet.getFloat("total");
                           String discountID = resultSet.getString("discountID");
                           String rentalDay = DateConvertion.parseToString(rentalDate);
                           String returnDay = DateConvertion.parseToString(returnDate);
                           if(discountID == null){
                               discountID = "No Discount";
                           } 
                           int status = resultSet.getInt("status");
                           RentalDTO rental = new RentalDTO(email, discountID, rentalDay, returnDay, total, status, id);
                           if(list == null){
                               list = new ArrayList<>();
                           }
                           list.add(rental);
                       }
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
        return list;
    }

    @Override
    public void cancelOrder(int id, String email, int status) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            if(connection != null){
                String sql = "UPDATE Rental SET status = ? WHERE accountID = ? AND id = ?";
                statement = connection.prepareStatement(sql);
                if(statement != null){
                    statement.setInt(1, status);
                    statement.setString(2, email);
                    statement.setInt(3, id);
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
