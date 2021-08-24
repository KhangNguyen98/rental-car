/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.validates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import khangnh.principles.Constant;

/**
 *
 * @author khang nguyen
 */
public class ValidateDate {

    public static boolean checkDate(String date)  {
        Date checkDate = null;
        SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT);
        format.setLenient(false);
        try {
            checkDate = format.parse(date);
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
     public static boolean checkDateToOrder(String date)  {
        Date checkDate = null;
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT);
        format.setLenient(false);
        try {
            checkDate = format.parse(date);
            if(checkDate.before(today)){
                return false;
            }
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
    public static boolean compareDate(String searchRentalDate, String searchReturnDate){
        Date rentalDate, returnDate;
        SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT);
        try {
            rentalDate = format.parse(searchRentalDate);
            returnDate = format.parse(searchReturnDate);
        } catch (ParseException pe) {
            return false;
        }
        return rentalDate.before(returnDate);
    }
}
