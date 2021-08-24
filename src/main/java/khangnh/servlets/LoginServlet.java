/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khangnh.accounts.AccountDTO;
import khangnh.interfaces.AccountDAO;
import khangnh.interfaces.TokenDAO;
import khangnh.principles.Role;
import khangnh.principles.Status;

/**
 *
 * @author khang nguyen
 */
public class LoginServlet extends HttpServlet {

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
        String url = "loginForm";
        String email = request.getParameter("txtAccountEmail");
        String password = request.getParameter("txtAccountPassword");
        Map<String, String> map = (Map<String, String>) request.getServletContext().getAttribute("AUTHENTICATION");
        url = map.get(url);
        AccountDAO accountDAO = AccountDAO.getInstance();
        try {
            AccountDTO account = accountDAO.getAccount(email, password);
            if (account == null) {
                request.setAttribute("ERROR_IN_LOGIN", "Your account or password is invalid");
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                /*  String recaptcha = request.getParameter("recaptcha");
                boolean checkRecaptcha = GoogleUtil.verify(recaptcha);
                if(!checkRecaptcha){
                    request.setAttribute("ERROR_IN_LOGIN", "Captcha is invalid");
                } else{*/
                TokenDAO tokenDAO = TokenDAO.getInstance();
                if (tokenDAO.isActivate(email, Status.ACTIVE)) {
                    HttpSession session = request.getSession();
                    String action = (String) session.getAttribute("SAVED_RESOURCE");
                    session.setAttribute("ACCOUNT", account);
                    if (action != null) {
                        Map<String, String> mapParameter = (Map<String, String>) session.getAttribute("PARAMETERS");
                        session.removeAttribute("SAVED_RESOURCE");
                        if (mapParameter.size() > 0) {
                            action += "?";
                        }
                        for (String key : mapParameter.keySet()) {
                            action += key + "=";
                            String value = mapParameter.get(key);

                            action += value + "&";
                        }
                        action = action.substring(0, action.length() - 1);
                        session.removeAttribute("PARAMETERS");
                        response.sendRedirect(action);
                    } else {
                        if (account.getRole() == Role.ADMIN) {
                            url = "default?admin=true";
                        } else {
                            url = "default";
                        }
                        response.sendRedirect(url);
                    }
                } else {
                    url = "activationForm";
                    url = map.get(url);
                    request.getRequestDispatcher(url).forward(request, response);
                }
            }
        } catch (NamingException e) {
            log(e.toString());
            e.printStackTrace();
            isError = true;
            response.sendError(500);
        } catch (SQLException e) {
            log(e.getMessage());
            e.printStackTrace();
            isError = true;
            response.sendError(500);
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
