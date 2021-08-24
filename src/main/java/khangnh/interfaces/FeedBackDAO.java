/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.interfaces;

import java.sql.SQLException;
import javax.naming.NamingException;
import khangnh.feedbacks.FeedBackDTO;
import khangnh.implementations.FeedBackImpl;

/**
 *
 * @author khang nguyen
 */
public interface FeedBackDAO {
    public static FeedBackImpl getInstance(){
        return new FeedBackImpl();
    }
    public void insert(FeedBackDTO feedBack) throws SQLException, NamingException;
    public FeedBackDTO getFeedBack(String email, int carID, int rentalDetailID) throws SQLException, NamingException;
    public void update(FeedBackDTO feedBack) throws SQLException, NamingException;
}
