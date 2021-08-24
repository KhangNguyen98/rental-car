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
import khangnh.cars.CarDTO;
import khangnh.interfaces.CartDetailDAO;

/**
 *
 * @author khang nguyen
 */
public class UpdateInCartServlet extends HttpServlet {

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
        String url = "viewCart";
        String txtCarQuantity = request.getParameter("txtCarQuantity");
        String txtCarID = request.getParameter("txtCarID");
        Map<String, String> map = (Map<String, String>) request.getServletContext().getAttribute("AUTHENTICATION");
        url = map.get(url);
        int newQuantity = -1;
        try {
            newQuantity = Integer.valueOf(txtCarQuantity);
            if (newQuantity <= 0) {
                isError = true;
                request.setAttribute("ERROR_IN_UPDATE", "Quantity must > 0");
                request.setAttribute("ERROR_ID", txtCarID);
                request.getRequestDispatcher(url).forward(request, response);
            }
        } catch (NumberFormatException e) {
            isError = true;
            request.setAttribute("ERROR_IN_UPDATE", "Quantity must be a digit");
            request.setAttribute("ERROR_ID", txtCarID);
            request.getRequestDispatcher(url).forward(request, response);
        }
        if (!isError) {
            CartDetailDAO cartDetailDAO = CartDetailDAO.getInstance();
            HttpSession session = request.getSession(false);
            AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
            int id = Integer.parseInt(txtCarID);
            try {
                CarDTO car = cartDetailDAO.getCar(id);
                cartDetailDAO.updateQuantityInCart(car, account.getEmail(), newQuantity);
                request.setAttribute("MESSAGE", "You have update Car successfully");
                request.getRequestDispatcher(url).forward(request, response);
            } catch (NamingException e) {
                log(e.getMessage());
                isError = true;
                response.sendError(500);
            } catch (SQLException e) {
                log(e.getMessage());
                isError = true;
                response.sendError(500);
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
