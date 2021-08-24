/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.interfaces;

import java.sql.SQLException;
import javax.naming.NamingException;
import khangnh.implementations.TokenImpl;

/**
 *
 * @author khang nguyen
 */
public interface TokenDAO {
    public void insert(String token, String email) throws SQLException, NamingException;
    public static TokenImpl getInstance(){
        return new TokenImpl();
    }
    public boolean isActivate(String email, int status) throws SQLException, NamingException;
    public int update(String email, String token) throws SQLException, NamingException;
}
