/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.cartDetails;

import java.io.Serializable;

/**
 *
 * @author khang nguyen
 */
public class CartDetailDTO implements Serializable{
    private int id, carID, quantity;
    private String email;
    private float price;

    public CartDetailDTO(int id, int carID, int quantity, String email, float price) {
        this.id = id;
        this.carID = carID;
        this.quantity = quantity;
        this.email = email;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
}
