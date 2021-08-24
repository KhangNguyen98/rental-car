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
import khangnh.convertions.DateConvertion;
import khangnh.interfaces.CarDAO;
import khangnh.utils.DBUtil;
import khangnh.validates.ValidateDate;
import sun.security.krb5.internal.ccache.FileCredentialsCache;

/**
 * String searchName, String searchCategory, String searchRentalDate, String
 * searchReturnDate
 *
 * @author khang nguyen
 */
public class CarImpl implements CarDAO {

    public List<CarDTO> getCar(Connection connection, PreparedStatement statement, ResultSet resultSet, int currentPage, int rowsEachPage) throws SQLException, NamingException {
        List<CarDTO> list = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "SELECT Car.id, Car.name, color, year, Category.name as Category, price, quantity FROM Car, Category WHERE Category.id = Car.categoryID "
                        + " AND status = 1 AND quantity > 0 ORDER BY year OFFSET (?-1)*? ROWS FETCH NEXT ? ROWS ONLY ";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setInt(1, currentPage);
                    statement.setInt(2, rowsEachPage);
                    statement.setInt(3, rowsEachPage);
                    resultSet = statement.executeQuery();
                    if (resultSet != null) {
                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            String name = resultSet.getString("name");
                            String color = resultSet.getString("color");
                            int year = resultSet.getInt("year");
                            String category = resultSet.getString("Category");
                            float price = resultSet.getFloat("price");
                            int quantity = resultSet.getInt("quantity");
                            CarDTO car = new CarDTO(id, year, quantity, year, name, color, category, price);
                            if (list == null) {
                                list = new ArrayList<>();
                            }
                            list.add(car);
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
    public List<CarDTO> getCar(String searchName, String searchCategory, String searchRentalDate, String searchReturnDate, int currentPage, int rowsEachPage) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<CarDTO> list = null;
        try {
            boolean checkValidCondition = true;
            boolean accept1 = searchName == null && searchCategory == null && searchRentalDate == null && searchReturnDate == null;
            boolean accept2 = searchName == "" && "".equals(searchCategory) && searchRentalDate == "" && searchReturnDate == "";
            boolean accept3 = searchName == "" && "default".equals(searchCategory) && searchRentalDate == "" && searchReturnDate == "";
            if (accept1 || accept2) {
                checkValidCondition = false;
                list = getCar(connection, statement, resultSet, currentPage, rowsEachPage);
            } else {
                if (accept3) {
                    checkValidCondition = false;
                } else if ((searchRentalDate.length() > 0 && !ValidateDate.checkDateToOrder(searchRentalDate)) || (searchReturnDate.length() > 0 && !ValidateDate.checkDateToOrder(searchReturnDate))) {
                    checkValidCondition = false;
                } else if (searchRentalDate.length() > 0 && searchReturnDate.length() > 0 && !ValidateDate.compareDate(searchRentalDate, searchReturnDate)) {
                    checkValidCondition = false;
                }
            }
            if (checkValidCondition) {
                int a[] = new int[4];//vi tri ung voi vi tri param
                for (int i = 0; i < a.length; i++) {
                    a[i] = 0;
                }
                String nameMapping[] = {searchName, searchCategory, searchRentalDate, searchReturnDate};
                String names[] = {"searchName", "searchCategory", "searchRentalDate", "searchReturnDate"};

                connection = DBUtil.getConnection();
                if (connection != null) {
                    String sql = "SELECT Car.id, Car.name, color, year, Category.name AS Category, price, quantity FROM Car, Category "
                            + " WHERE Category.id = Car.categoryID AND status = 1 AND quantity > 0 ";
                    if (searchName.length() > 0) {
                        sql += " AND Car.NAME LIKE ? ";
                        a[0] = 1;
                    }
                    if (!"default".equals(searchCategory)) {
                        sql += " AND Category.name = ? ";
                        a[1] = 1;
                    }

                    if (searchRentalDate.length() > 0 && searchReturnDate.length() > 0) {
                        sql += " AND Car.id NOT IN (SELECT carID FROM RentalDetail where rentalID = (SELECT id FROM Rental WHERE "
                                + " rentalDate >= ? AND  confirmReturnDate <= ?)) ";
                        a[2] = 1;
                        a[3] = 1;
                    }
                    sql += " ORDER BY Car.year OFFSET (?-1)*? ROWS FETCH NEXT ? ROWS ONLY";
                    statement = connection.prepareStatement(sql);
                    int posToInsert = 1;
                    if (statement != null) {
                        for (int i = 0; i < a.length; i++) {
                            if (a[i] == 1) {
                                if ("searchCategory".equals(names[i])) {
                                    statement.setString(posToInsert, nameMapping[i]);
                                } else if ("searchName".equals(names[i])) {
                                    statement.setString(posToInsert, "%" + nameMapping[i] + "%");
                                } else {
                                    long convertedDate = DateConvertion.parse(nameMapping[i]);
                                    statement.setLong(posToInsert, convertedDate);
                                }
                                posToInsert++;
                            }
                        }
                        statement.setInt(posToInsert, currentPage);
                        statement.setInt(posToInsert + 1, rowsEachPage);
                        statement.setInt(posToInsert + 2, rowsEachPage);
                        resultSet = statement.executeQuery();
                        if (resultSet != null) {
                            while (resultSet.next()) {
                                int id = resultSet.getInt("id");
                                String name = resultSet.getString("name");
                                String color = resultSet.getString("color");
                                int year = resultSet.getInt("year");
                                String category = resultSet.getString("Category");
                                float price = resultSet.getFloat("price");
                                int quantity = resultSet.getInt("quantity");
                                CarDTO car = new CarDTO(id, year, quantity, year, name, color, category, price);
                                if (list == null) {
                                    list = new ArrayList<>();
                                }
                                list.add(car);
                            }
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
    public int totalCar(String searchName, String searchCategory, String searchRentalDate, String searchReturnDate) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int total = 0;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                boolean accept1 = searchName == null && searchCategory == null && searchRentalDate == null && searchReturnDate == null;
                boolean accept3 = searchName == "" && "default".equals(searchCategory) && searchRentalDate == "" && searchReturnDate == "";
                boolean accept2 = searchName == "" && "".equals(searchCategory) && searchRentalDate == "" && searchReturnDate == "";
                if (accept1 || accept2) {
                    String sql = "SELECT COUNT(id) AS total_rows FROM Car WHERE status = 1 AND quantity > 0";
                    statement = connection.prepareStatement(sql);
                    if (statement != null) {
                        resultSet = statement.executeQuery();
                        if (resultSet != null) {
                            if (resultSet.next()) {
                                total = resultSet.getInt("total_rows");
                            }
                        }
                    }
                } else {
                    boolean checkValidCondition = true;
                    if (accept3) {
                        checkValidCondition = false;
                    } else if ((searchRentalDate.length() > 0 && !ValidateDate.checkDateToOrder(searchRentalDate)) || (searchReturnDate.length() > 0 && !ValidateDate.checkDateToOrder(searchReturnDate))) {
                        checkValidCondition = false;
                    } else if (searchRentalDate.length() > 0 && searchReturnDate.length() > 0 && !ValidateDate.compareDate(searchRentalDate, searchReturnDate)) {
                        checkValidCondition = false;
                    }
                    if (checkValidCondition) {
                        int a[] = new int[4];//vi tri ung voi vi tri param
                        for (int i = 0; i < a.length; i++) {
                            a[i] = 0;
                        }
                        String nameMapping[] = {searchName, searchCategory, searchRentalDate, searchReturnDate};
                        String names[] = {"searchName", "searchCategory", "searchRentalDate", "searchReturnDate"};

                        connection = DBUtil.getConnection();
                        if (connection != null) {
                            String sql = "SELECT Car.id, Car.name, color, year, Category.name AS Category, price, quantity FROM Car, Category WHERE Category.id = Car.categoryID "
                                    + " AND status = 1 AND quantity > 0 ";
                            if (searchName.length() > 0) {
                                sql += " AND Car.NAME LIKE ? ";
                                a[0] = 1;
                            }
                            if (!"default".equals(searchCategory)) {
                                sql += " AND Category.name = ? ";
                                a[1] = 1;
                            }

                            if (searchRentalDate.length() > 0 && searchReturnDate.length() > 0) {
                                sql += " AND Car.id NOT IN (SELECT carID FROM RentalDetail where rentalID = (SELECT id FROM Rental WHERE "
                                        + " rentalDate >= ? AND  confirmReturnDate <= ?))";
                                a[2] = 1;
                                a[3] = 1;
                            }
                            statement = connection.prepareStatement(sql);
                            int posToInsert = 1;
                            if (statement != null) {
                                for (int i = 0; i < a.length; i++) {
                                    if (a[i] == 1) {
                                        if ("searchCategory".equals(names[i])) {
                                            statement.setString(posToInsert, nameMapping[i]);
                                        } else if ("searchName".equals(names[i])) {
                                            statement.setString(posToInsert, "%" + nameMapping[i] + "%");
                                        } else {
                                            long convertedDate = DateConvertion.parse(nameMapping[i]);
                                            statement.setLong(posToInsert, convertedDate);
                                        }
                                        posToInsert++;
                                    }
                                }
                                resultSet = statement.executeQuery();
                                if (resultSet != null) {
                                    while (resultSet.next()) {
                                        total++;
                                    }
                                }
                            }
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
            return total;
        }
    }

    @Override
    public CarDTO getOneCar(int id) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        CarDTO car = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "SELECT  Car.name, color, year, Category.name as Category, price, quantity FROM Car, Category WHERE Category.id = Car.categoryID AND status = 1 AND quantity > 0 "
                        + " AND Car.id = ?";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setInt(1, id);
                    resultSet = statement.executeQuery();
                    if (resultSet != null) {
                        if (resultSet.next()) {
                            String name = resultSet.getString("name");
                            String color = resultSet.getString("color");
                            int year = resultSet.getInt("year");
                            String category = resultSet.getString("Category");
                            float price = resultSet.getFloat("price");
                            int quantity = resultSet.getInt("quantity");
                            car = new CarDTO(id, year, quantity, year, name, color, category, price);
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
