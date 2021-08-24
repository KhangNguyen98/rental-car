/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.rentalDetails;

import java.io.Serializable;

/**
 *
 * @author khang nguyen
 */
public class RentalDetailDTO implements Serializable {

    private int rentalID, carID, quantity, id;
    private float price;

    private String name, color, category;
    private int year;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RentalDetailDTO(int carID, int quantity, int id, float price, String name, String color, String category, int year) {
        this.carID = carID;
        this.quantity = quantity;
        this.id = id;
        this.price = price;
        this.name = name;
        this.color = color;
        this.category = category;
        this.year = year;
    }


   

    
    public RentalDetailDTO(int rentalID, int carID, int quantity, float price) {
        this.rentalID = rentalID;
        this.carID = carID;
        this.quantity = quantity;
        this.price = price;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRentalID() {
        return rentalID;
    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
