/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import khangnh.cars.CarDTO;
import khangnh.categories.CategoryDTO;
import khangnh.interfaces.CarDAO;
import khangnh.interfaces.CategoryDAO;
import khangnh.principles.Constant;

/**
 *
 * @author khang nguyen
 */
public class DefaultServlet extends HttpServlet {

    private int currentPage;

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

        String searchName = request.getParameter("txtSearchName");
        String searchCategory = request.getParameter("tagCategory");
        String searchRentalDate = request.getParameter("txtSearchRentalDate");
        String searchReturnDate = request.getParameter("txtSearchReturnDate");
        
        String url = "customer";
        Map<String, String> map = (Map<String, String>) request.getServletContext().getAttribute("AUTHENTICATION");
        url = map.get(url);
         
        try {
            String noResult = (String) request.getAttribute("NO_RESULT");
            if (noResult == null) {
                CarDAO carDAO = CarDAO.getInstance();

                String checkPage = request.getParameter("currentPage");
                if (checkPage == null || checkPage.isEmpty()) {
                    currentPage = 1;
                } else {
                    currentPage = Integer.parseInt(checkPage);
                }
                int totalRows = carDAO.totalCar(searchName, searchCategory, searchRentalDate, searchReturnDate);

                String convertIntoString = String.valueOf(Math.ceil((double) totalRows / Constant.ROWS_EACH_PAGE));
                int pos = convertIntoString.indexOf(".");
                int totalPage = Integer.parseInt(convertIntoString.substring(0, pos));
                if (currentPage <= totalPage) {
                    if (totalRows / ((currentPage + 1) * Constant.ROWS_EACH_PAGE) > 0) {
                        request.setAttribute("NEXT_PAGE", currentPage + 1);
                    }

                    if (currentPage > 1) {
                        request.setAttribute("PREVIOUS_PAGE", currentPage - 1);
                    }
                    List<CarDTO> listCar = carDAO.getCar(searchName, searchCategory, searchRentalDate, searchReturnDate, currentPage, Constant.ROWS_EACH_PAGE);
                    request.setAttribute("CAR", listCar);
                } else {
                    request.setAttribute("CAR", null);
                }
            }
            CategoryDAO categoryDAO = CategoryDAO.getInstance();
            List<CategoryDTO> listCategory = categoryDAO.getCategory();
            request.setAttribute("CATEGORY", listCategory);
        } catch (SQLException se) {
            log(se.getMessage());
            isError = true;
            response.sendError(500);
        } catch (NamingException ne) {
            log(ne.getMessage());
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
