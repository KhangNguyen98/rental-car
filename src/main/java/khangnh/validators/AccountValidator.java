/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.validators;

import java.sql.SQLException;
import javax.naming.NamingException;
import khangnh.interfaces.AccountDAO;
import khangnh.makers.AccountMaker;
import khangnh.principles.Constant;

/**
 *
 * @author khang nguyen
 */
public class AccountValidator extends Validator<AccountMaker>{

    public AccountValidator(AccountMaker object) {
        super(object);
    }
    private void checkEmail() throws SQLException, NamingException{
        if(!object.getEmail().trim().matches(Constant.EMAIL_FORMAT) || object.getEmail().trim().length() > 320){
            addError("email", "Invalid email and max length is 320");
        } else{
            AccountDAO accountDAO = AccountDAO.getInstance();
            if(accountDAO.isExist(object.getEmail())){
                addError("email", "Existed Email");
            }
        }
    }
    private void checkPassword(){
        if(object.getPassword().trim().length() == 0 || object.getPassword().trim().length() > 50){
            addError("password", "Password can't be null and max length is 50");
        }
    }
    private void checkConfirmPassword(){
        if(!object.getPassword().equals(object.getConfirmPassword())){
            addError("confirmPassword", "Not the same pass");
        }
    }
    private void checkPhone(){
        if(!object.getPhone().matches(Constant.PHONE_FORMAT)){
            addError("phone", "Must be ten digit");
        }
    }
    private void checkFullName(){
        if(object.getFullName().length() == 0 || object.getFullName().length() > 50){
            addError("fullName", "Name can't be null and max length is 50");
        }
    }
    private void checkAddress(){
        if(object.getAddress().length() == 0 || object.getAddress().length() > 150){
            addError("address", "Address can't be null and max length is 150");
        }
    }
    @Override
    public void validate() throws SQLException, NamingException {
        checkEmail();
        checkPassword();
        checkConfirmPassword();
        checkFullName();
        checkPhone();
        checkAddress();
    }
    
}
