/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.convertions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import khangnh.principles.Constant;

/**
 *
 * @author khang nguyen
 */
public class DateConvertion {
    public static long parse(String date){
        SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT);
        Date result = null;
        try {
           result = format.parse(date);
        } catch (ParseException pe) {
            return 0;
        }
        return result.getTime();
    }
    public static String parseToString(long date){
        Date form = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT);
        return format.format(form);
    }
}
