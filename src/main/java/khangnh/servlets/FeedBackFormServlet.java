/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.servlets;

import java.io.IOException;
import java.sql.SQLException;
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
public class FeedBackFormServlet extends HttpServlet {

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
        String url = "feedBackForm.jsp";
        boolean isError = false;
        boolean isExist = false;
        int carID = Integer.valueOf(request.getParameter("txtCarID"));
        int rentalDetailID = Integer.valueOf(request.getParameter("txtRentalDetailID"));
        String txtCarName = request.getParameter("txtCarName");
        HttpSession session = request.getSession(false);
        AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
        FeedBackDAO feedBackDAO = FeedBackDAO.getInstance();
        try {
            FeedBackDTO feedBack = feedBackDAO.getFeedBack(account.getEmail(), carID, rentalDetailID);
            if ( feedBack != null) {
                isExist = true;
                url = "feedBackExisted";
                url +="?" + "txtFeedBackContent=" + feedBack.getContent() + "&txtFeedBackRate="+ feedBack.getRate() + "&txtFeedBackID=" + feedBack.getId()
                        +"&txtCarName=" + txtCarName;
                response.sendRedirect(url);
            } 
        } catch (SQLException ex) {
            isError = true;
            log(ex.getMessage());
            response.sendError(500);
        } catch (NamingException ex) {
            isError = true;
            log(ex.getMessage());
            response.sendError(500);
        }
        if (!isError && !isExist) {
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
