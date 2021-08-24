/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.interfaces;

import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import khangnh.implementations.RentalImpl;
import khangnh.rentals.RentalDTO;

/**
 *
 * @author khang nguyen
 */
public interface RentalDAO {
    public void insert(RentalDTO rental) throws SQLException, NamingException; 
    public static RentalImpl getInstance(){
        return new RentalImpl();
    }
    public int getRentalID() throws SQLException, NamingException;
    public List<RentalDTO> viewOrder(String email) throws SQLException, NamingException;
    public void cancelOrder(int id, String email, int status) throws SQLException, NamingException;
}
