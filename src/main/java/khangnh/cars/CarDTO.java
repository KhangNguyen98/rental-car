/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.cars;

import java.io.Serializable;

/**
 *
 * @author khang nguyen
 */
public class CarDTO implements Serializable{
    private int id, year, quantity, status;
    private String name, color, category;
    float price;

    public CarDTO(int id, int quantity, float price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public CarDTO(int id, int year, int quantity, int status, String name, String color, String category, float price) {
        this.id = id;
        this.year = year;
        this.quantity = quantity;
        this.status = status;
        this.name = name;
        this.color = color;
        this.category = category;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
}
