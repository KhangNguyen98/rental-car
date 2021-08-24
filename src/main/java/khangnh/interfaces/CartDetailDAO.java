/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.interfaces;

import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import khangnh.cars.CarDTO;
import khangnh.cartDetails.CartDetailDTO;
import khangnh.implementations.CartDetailImpl;
import khangnh.shortages.CarShortage;

/**
 *
 * @author khang nguyen
 */
public interface CartDetailDAO {
    public static CartDetailImpl getInstance(){
        return new CartDetailImpl();
    }
    public void addToCart(CarDTO car, String email) throws SQLException, NamingException;
    public void updateQuantityInCart(CarDTO car, String email, int quantity) throws SQLException, NamingException;
    public CarDTO getCar(int id) throws SQLException, NamingException;
    public List<CartDetailDTO> getCart(String email) throws SQLException, NamingException;
    public void removeFromCart(int id, String email) throws SQLException, NamingException;
    public CarShortage getInvalidCarInCart(int id, String email) throws SQLException, NamingException;
}
