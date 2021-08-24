/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.rentals;

import java.io.Serializable;

/**
 *
 * @author khang nguyen
 */
public class RentalDTO implements Serializable{
    private String email, discountID, rentalDay, returnDay;
    private long rentalDate, returnDate;
    private float total;
    private int status, id;

    public RentalDTO(String email, String discountID, long rentalDate, long returnDate, float total, int status) {
        this.email = email;
        this.discountID = discountID;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.total = total;
        this.status = status;
    }

    public RentalDTO(String email, String discountID, String rentalDay, String returnDay, float total, int status) {
        this.email = email;
        this.discountID = discountID;
        this.rentalDay = rentalDay;
        this.returnDay = returnDay;
        this.total = total;
        this.status = status;
    }

    public RentalDTO(String email, String discountID, String rentalDay, String returnDay, float total, int status, int id) {
        this.email = email;
        this.discountID = discountID;
        this.rentalDay = rentalDay;
        this.returnDay = returnDay;
        this.total = total;
        this.status = status;
        this.id = id;
    }
  
    public String getRentalDay() {
        return rentalDay;
    }

    public RentalDTO(String discountID, String rentalDay, String returnDay, float total, int id) {
        this.discountID = discountID;
        this.rentalDay = rentalDay;
        this.returnDay = returnDay;
        this.total = total;
        this.id = id;
    }

   

    public void setRentalDay(String rentalDay) {
        this.rentalDay = rentalDay;
    }

    public String getReturnDay() {
        return returnDay;
    }

    public void setReturnDay(String returnDay) {
        this.returnDay = returnDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public long getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(long rentalDate) {
        this.rentalDate = rentalDate;
    }

    public long getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(long returnDate) {
        this.returnDate = returnDate;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

   
    
}
