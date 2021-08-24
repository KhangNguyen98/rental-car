/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.feedbacks;

import java.io.Serializable;

/**
 *
 * @author khang nguyen
 */
public class FeedBackDTO implements Serializable{
    private String email, content;
    private int rate, carID, rentalDetailID, id;

    public FeedBackDTO(String email, String content, int rate, int carID, int rentalDetailID) {
        this.email = email;
        this.content = content;
        this.rate = rate;
        this.carID = carID;
        this.rentalDetailID = rentalDetailID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FeedBackDTO(String content, int rate, int id) {
        this.content = content;
        this.rate = rate;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public int getRentalDetailID() {
        return rentalDetailID;
    }

    public void setRentalDetailID(int rentalDetailID) {
        this.rentalDetailID = rentalDetailID;
    }
    
    
}
