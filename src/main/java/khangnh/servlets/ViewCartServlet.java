/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khangnh.accounts.AccountDTO;
import khangnh.cars.CarDTO;
import khangnh.cartDetails.CartDetailDTO;
import khangnh.interfaces.CarDAO;
import khangnh.interfaces.CartDetailDAO;

/**
 *
 * @author khang nguyen
 */
public class ViewCartServlet extends HttpServlet {

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
        String url = "viewCart.jsp";
        HttpSession session = request.getSession(false);
        AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
        CartDetailDAO cartDetail = CartDetailDAO.getInstance();
        boolean isError = false;
        try {
            List<CartDetailDTO> listOfCartDetail = cartDetail.getCart(account.getEmail());
            request.setAttribute("CART", listOfCartDetail);
            if(listOfCartDetail != null){
                List<CarDTO> listOfCar = new ArrayList<>();
                CarDAO carDAO = CarDAO.getInstance();
                for (int i = 0; i < listOfCartDetail.size(); i++) {
                    CarDTO car = carDAO.getOneCar(listOfCartDetail.get(i).getCarID());
                    //warning do cai car lay ra so luong va price ko giong voi trong cart
                    car.setQuantity(listOfCartDetail.get(i).getQuantity());
                    listOfCar.add(car);
                }
                request.setAttribute("CAR", listOfCar);
            }
        } catch (SQLException e) {
            log(e.getMessage());
            isError = true;
            response.sendError(500);
        } catch (NamingException e) {
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
