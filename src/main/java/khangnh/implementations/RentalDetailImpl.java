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
import khangnh.interfaces.CarDAO;
import khangnh.interfaces.RentalDetailDAO;
import khangnh.rentalDetails.RentalDetailDTO;
import khangnh.utils.DBUtil;

/**
 *
 * @author khang nguyen
 */
public class RentalDetailImpl implements RentalDetailDAO {

    @Override
    public boolean insert(List<CartDetailDTO> list, int rentalID) throws SQLException, NamingException {
        boolean confirm = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                CarDAO carDAO = CarDAO.getInstance();
                connection.setAutoCommit(false);
                String sql = "INSERT INTO RentalDetail(rentalID, carID, quantity, price, name, color, year, category) VALUES(?,?,?,?,?,?,?,?)";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    for (CartDetailDTO cartDetailDTO : list) {
                        statement.setInt(1, rentalID);
                        statement.setInt(2, cartDetailDTO.getCarID());
                        CarDTO car = carDAO.getOneCar(cartDetailDTO.getCarID());
                        statement.setInt(3, cartDetailDTO.getQuantity());
                        statement.setFloat(4, cartDetailDTO.getPrice());
                        statement.setString(5, car.getName());
                        statement.setString(6, car.getColor());
                        statement.setInt(7, car.getYear());
                        statement.setString(8, car.getCategory());
                        //
                        statement.executeUpdate();
                    }
                    connection.commit();
                    connection.setAutoCommit(true);

                    confirm = true;
                }
            }
        } finally {
            if (!confirm) {
                connection.rollback();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
            return confirm;
        }
    }

    @Override
    public List<RentalDetailDTO> viewDetail(int rentalID) throws SQLException, NamingException {
        List<RentalDetailDTO> list = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "SELECT id, name, color, year, category, quantity, price, carID FROM RentalDetail WHERE rentalID IN "
                        + " (SELECT rentalID FROM Rental WHERE rentalID = ?)";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setInt(1, rentalID);
                    resultSet = statement.executeQuery();
                    if (resultSet != null) {
                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            String name = resultSet.getString("name");
                            String color = resultSet.getString("color");
                            int year = resultSet.getInt("year");
                            String category = resultSet.getString("category");
                            int quantity = resultSet.getInt("quantity");
                            float price = resultSet.getFloat("price");
                            int carID = resultSet.getInt("carID");
                            RentalDetailDTO rentalDetail = new RentalDetailDTO(carID, quantity, id, price, name, color, category, year);
                            if (list == null) {
                                list = new ArrayList<>();
                            }
                            list.add(rentalDetail);
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

}
