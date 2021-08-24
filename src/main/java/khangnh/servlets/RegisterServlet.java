/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import khangnh.accounts.AccountDTO;
import khangnh.interfaces.AccountDAO;
import khangnh.interfaces.TokenDAO;
import khangnh.makers.AccountMaker;
import khangnh.principles.Constant;
import khangnh.principles.Role;
import khangnh.principles.Status;
import khangnh.validators.AccountValidator;

/**
 *
 * @author khang nguyen
 */
public class RegisterServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        boolean isError = false;
        String email = request.getParameter("txtAccountEmail");
        String password = request.getParameter("txtAccountPassword");
        String confirmPassword = request.getParameter("txtAccountConfirmPassword");
        String fullName = request.getParameter("txtAccountFullName");
        String address = request.getParameter("txtAccountAddress");
        String phone = request.getParameter("txtAccountPhone");

        AccountMaker accountMaker = new AccountMaker(email, password, confirmPassword, phone, fullName, address);
        AccountValidator validator = new AccountValidator(accountMaker);
        
        String url = "registerForm";
        Map<String, String> map = (Map<String,String>) request.getServletContext().getAttribute("AUTHENTICATION");
        url = map.get(url);
        
        try {
            validator.validate();
            if (validator.isError()) {
                request.setAttribute("ERROR", validator.getError());
            } else {
                Date date = new Date();
                long createDate = date.getTime();
                AccountDTO account = new AccountDTO(email, password, fullName, address, phone, createDate, Status.ACTIVE);
                AccountDAO accountDAO = AccountDAO.getInstance();
                accountDAO.insert(account, Role.CUSTOMER);
                String token = getToken();
                TokenDAO tokenDAO = TokenDAO.getInstance();
                tokenDAO.insert(token, email);
                request.setAttribute("CODE", token);
                url = "activationForm";
                url = map.get(url);
            }
        } catch (SQLException ex) {
            isError = true;
            log(ex.getMessage());
            ex.printStackTrace();
            response.sendError(500);
        } catch (NamingException ex) {
            isError = true;
            log(ex.getMessage());
            ex.printStackTrace();
            response.sendError(500);
        }
        if (!isError) {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private String getToken() {
        Random random = new Random();
        while (true) {
            boolean isUpper = false, isLower = false, isDigit = false;
            char[] str = new char[Constant.FIXED_LENGTH];
            for (int i = 0; i < Constant.FIXED_LENGTH; i++) {
                char index = Constant.SYMBOL.charAt(random.nextInt(Constant.SYMBOL.length()));
                if (Character.isDigit(index)) {
                    isDigit = true;
                }
                if (Character.isUpperCase(index)) {
                    isUpper = true;
                }
                if (Character.isLowerCase(index)) {
                    isLower = true;
                }
                str[i] = index;
            }
            if (isDigit && isUpper && isLower) {
                return new String(str);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
