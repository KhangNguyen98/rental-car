/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.interfaces;

import java.sql.SQLException;
import javax.naming.NamingException;
import khangnh.accounts.AccountDTO;
import khangnh.implementations.AccountImpl;

/**
 *
 * @author khang nguyen
 */
public interface AccountDAO {
    public AccountDTO getAccount(String name, String password) throws SQLException, NamingException;
    public static AccountImpl getInstance(){
        return new AccountImpl();
    }
    public boolean isExist(String email) throws SQLException, NamingException;
    public void insert(AccountDTO account, int status) throws SQLException, NamingException;
} 
