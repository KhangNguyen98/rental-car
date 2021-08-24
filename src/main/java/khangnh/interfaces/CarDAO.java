/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import khangnh.cars.CarDTO;
import khangnh.implementations.CarImpl;

/**
 *String searchName, String searchCategory, String searchRentalDate, String searchReturnDate
 * @author khang nguyen
 */
public interface CarDAO {
    public List<CarDTO> getCar(Connection connection, PreparedStatement statement, ResultSet resutlSet,int currentPage, int rowsEachPage) throws SQLException, NamingException;
    public static CarImpl getInstance(){
        return new CarImpl();
    }
    public List<CarDTO> getCar(String searchName, String searchCategory, String searchRentalDate, String searchReturnDate,int currentPage, int rowsEachPage) throws SQLException, NamingException;
    public int totalCar(String searchName, String searchCategory, String searchRentalDate, String searchReturnDate) throws SQLException, NamingException;
    public CarDTO getOneCar(int id) throws SQLException, NamingException;
}
