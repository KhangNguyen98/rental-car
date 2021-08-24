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
import java.text.SimpleDateFormat;
import javax.naming.NamingException;
import khangnh.accounts.AccountDTO;
import khangnh.interfaces.AccountDAO;
import khangnh.utils.DBUtil;

/**
 *
 * @author khang nguyen
 */
public class AccountImpl implements AccountDAO {

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public AccountDTO getAccount(String email, String password) throws SQLException, NamingException {
        AccountDTO account = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "SELECT fullName, address, createDate, phoneNumber, roleID FROM Account WHERE email = ? AND password = ? AND status = 1";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setString(1, email);
                    statement.setString(2, password);
                    resultSet = statement.executeQuery();
                    if (resultSet != null) {
                        if (resultSet.next()) {
                            String fullName = resultSet.getString("fullName");
                            String phoneNumber = resultSet.getString("phoneNumber");
                            String address = resultSet.getString("address");
                            long dateOfSql = resultSet.getLong("createDate");
                            int role = resultSet.getInt("roleID");
                            account = new AccountDTO(email, password, fullName, address, phoneNumber, dateOfSql, role);
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
        return account;
    }

    @Override
    public void insert(AccountDTO account, int status) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "INSERT INTO Account (email, password, roleID, phoneNumber, fullName, address, createDate, status) VALUES(?,?,?,?,?,?,?,?)";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setString(1, account.getEmail());
                    statement.setString(2, account.getPassword());
                    statement.setInt(3, account.getRole());
                    statement.setString(4, account.getPhoneNumber());
                    statement.setString(5, account.getFullName());
                    statement.setString(6, account.getAddress());
                    statement.setLong(7, account.getCreateDate());
                    statement.setInt(8, status);
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
    public boolean isExist(String email) throws SQLException, NamingException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "SELECT email FROM Account WHERE email = ?";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    statement.setString(1, email);
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

}
