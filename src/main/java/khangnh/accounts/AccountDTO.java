/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.accounts;

import java.io.Serializable;

/**
 *
 * @author khang nguyen
 */
public class AccountDTO implements Serializable{
    private String email, password, fullName, address, phoneNumber;
    public long createDate;
    private int role;

    public AccountDTO(String email, String password, String fullName, String address, String phoneNumber, long createDate, int role) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.role = role;
    }

    public AccountDTO(String email, String fullName, int role) {
        this.email = email;
        this.fullName = fullName;
        this.role = role;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

}
