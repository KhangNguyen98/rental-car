/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.interfaces;

import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import khangnh.cartDetails.CartDetailDTO;
import khangnh.implementations.RentalDetailImpl;
import khangnh.rentalDetails.RentalDetailDTO;

/**
 *
 * @author khang nguyen
 */
public interface RentalDetailDAO {
    public static RentalDetailImpl getInstance(){
        return new RentalDetailImpl();
    }
    public boolean insert(List<CartDetailDTO> list, int rentalID) throws SQLException, NamingException;
    public List<RentalDetailDTO> viewDetail(int id) throws SQLException, NamingException;
}
