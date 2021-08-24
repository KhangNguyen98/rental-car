/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khangnh.accounts.AccountDTO;
import khangnh.feedbacks.FeedBackDTO;
import khangnh.interfaces.FeedBackDAO;

/**
 *
 * @author khang nguyen
 */
public class SendFeedBackServlet extends HttpServlet {

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
        String url = "viewOrderHistory";
        Map<String, String> map = (Map<String, String>) request.getServletContext().getAttribute("AUTHENTICATION");
        url = map.get(url);
        boolean isError = false;
        String content = request.getParameter("txtFeedBackContent");
        int rate = Integer.valueOf(request.getParameter("txtFeedBackRate"));
        String txtFeedBackID = request.getParameter("txtFeedBackID");
        FeedBackDTO feedBack = null;
        FeedBackDAO feedBackDAO = FeedBackDAO.getInstance();
        try {
            if ("".equals(txtFeedBackID)) {
                HttpSession session = request.getSession(false);
                AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
                int carID = Integer.valueOf(request.getParameter("txtCarID"));
                int rentalDetailID = Integer.valueOf(request.getParameter("txtRentalDetailID"));
                feedBack = new FeedBackDTO(account.getEmail(), content, rate, carID, rentalDetailID);
                feedBackDAO.insert(feedBack);
                request.setAttribute("MESSAGE", "Send feedback successfully");
            } else {
                int id = Integer.valueOf(txtFeedBackID);
                feedBack = new FeedBackDTO(content, rate, id);
                feedBackDAO.update(feedBack);
                request.setAttribute("MESSAGE", "Update feedback successfully");
            }
        } catch (NamingException e) {
            log(e.getMessage());
            isError = true;
            response.sendError(500);
        } catch (SQLException e) {
            log(e.getMessage());
            isError = true;
            response.sendError(500);
        }
        if (!isError) {
            request.getRequestDispatcher(url).forward(request, response);
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
