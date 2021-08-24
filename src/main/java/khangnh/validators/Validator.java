/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.validators;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author khang nguyen
 */
public abstract class Validator<T> {
    private Map<String, String> error;
    T object;
    public Validator(T object){
        error = new HashMap<>();
        this.object = object;
    }
    public boolean isError(){
        return !error.isEmpty();
    }
    public Map<String, String> getError(){
        return error;
    }
    public void addError(String errorCode, String errorMsg){
        error.put(errorCode, errorMsg);
    }
    public abstract void validate() throws SQLException, NamingException;
}
