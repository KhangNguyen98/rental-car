/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.interfaces;

import java.sql.SQLException;
import javax.naming.NamingException;
import khangnh.implementations.DiscountImpl;

/**
 *
 * @author khang nguyen
 */
public interface DiscountDAO {
    public boolean checkValidDiscount(String id, int status, long today) throws SQLException, NamingException;
    public static DiscountImpl getInstance(){
        return new DiscountImpl();
    }
    public int getRate(String id) throws SQLException, NamingException;
}
