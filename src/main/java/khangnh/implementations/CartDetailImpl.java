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
import khangnh.cars.CarDTO;
import khangnh.cartDetails.CartDetailDTO;
import khangnh.interfaces.CartDetailDAO;
import khangnh.shortages.CarShortage;
import khangnh.utils.DBUtil;

/**
 *
 * @author khang nguyen
 */
public class CartDetailImpl implements CartDetailDAO {

    @Override
    public void addToCart(CarDTO car, String email) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "INSERT INTO CartDetail(accountID, carID, quantity, price) VALUES(?,?,1,?) ";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setString(1, email);
                    statement.setInt(2, car.getId());
                    statement.setFloat(3, car.getPrice());
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
    public void updateQuantityInCart(CarDTO car, String email, int quantity) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "UPDATE CartDetail SET  quantity = ? WHERE carID = ? AND accountID = ?";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setString(3, email);
                    statement.setInt(2, car.getId());
                    statement.setFloat(1, quantity);
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
    public CarDTO getCar(int id) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        CarDTO car = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "SELECT quantity, price FROM CartDetail WHERE carID = ? ";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setInt(1, id);
                    resultSet = statement.executeQuery();
                    if (resultSet != null) {
                        if (resultSet.next()) {
                            float price = resultSet.getFloat("price");
                            int quantity = resultSet.getInt("quantity");
                            car = new CarDTO(id, quantity, price);
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
            return car;
        }
    }

    @Override
    public List<CartDetailDTO> getCart(String email) throws SQLException, NamingException {
        List<CartDetailDTO> list = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "SELECT id, carID, quantity,price FROM CartDetail WHERE accountID = ?";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setString(1, email);
                    resultSet = statement.executeQuery();
                    if (resultSet != null) {
                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            int carID = resultSet.getInt("carID");
                            int quantity = resultSet.getInt("quantity");
                            float price = resultSet.getFloat("price");
                            CartDetailDTO cartDetail = new CartDetailDTO(id, carID, quantity, email, price);
                            if (list == null) {
                                list = new ArrayList();
                            }
                            list.add(cartDetail);
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
        return list;
    }

    @Override
    public void removeFromCart(int id, String email) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            if(connection != null){
                String sql = "DELETE CartDetail WHERE carID = ? AND accountID = ?";
                statement = connection.prepareStatement(sql);
                if(statement != null){
                    statement.setInt(1, id);
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
    public CarShortage getInvalidCarInCart(int id, String email) throws SQLException, NamingException {
        CarShortage car = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            if(connection != null){
                String sql = "SELECT Car.name, Car.quantity FROM Car, CartDetail WHERE Car.id = CartDetail.carID AND CartDetail.accountID = ? AND carID = ? "
                        + " AND CartDetail.quantity > Car.quantity";
                statement = connection.prepareStatement(sql);
                if(statement != null){
                    statement.setString(1, email);
                    statement.setInt(2, id);
                    resultSet = statement.executeQuery();
                    if(resultSet != null){
                        if(resultSet.next()){
                            String name = resultSet.getString("name");
                            int quantity = resultSet.getInt("quantity");
                            car = new CarShortage(name, quantity);
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
            return car;
        }
    }
}
