/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khangnh.accounts.AccountDTO;
import khangnh.cars.CarDTO;
import khangnh.cartDetails.CartDetailDTO;
import khangnh.convertions.DateConvertion;
import khangnh.interfaces.CarDAO;
import khangnh.interfaces.CartDetailDAO;
import khangnh.interfaces.DiscountDAO;
import khangnh.principles.Status;
import khangnh.shortages.CarShortage;
import khangnh.validates.ValidateDate;

/**
 *
 * @author khang nguyen
 */
public class PrintOrderToConfirmServlet extends HttpServlet {

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
        String url = "viewCart";
        Map<String, String> map = (Map<String, String>) request.getServletContext().getAttribute("AUTHENTICATION");
        url = map.get(url);
        boolean pass = true;
        boolean isError = false;
        int numberOfRentalDate = -1;
        int rate = -1;
        String txtRentalDate = request.getParameter("txtRentalDate");
        if(!ValidateDate.checkDateToOrder(txtRentalDate)){
            request.setAttribute("RENTAL_DATE", "Your order date is invalid.At least it's today and following pattern dd/MM/yyyy");
            pass = false;
        }
        String txtReturnDate = request.getParameter("txtReturnDate");
        try {
            numberOfRentalDate = Integer.valueOf(txtReturnDate);
            if (numberOfRentalDate <= 0 || numberOfRentalDate > 28) {
                pass = false;
                request.setAttribute("RETURN_DATE", "Number of days you hire must > 0 and max is 28 ");;
            }
        } catch (NumberFormatException e) {
            pass = false;
            request.setAttribute("RETURN_DATE", "Number of days you hire must > 0 and max is 28");
        }
        HttpSession session = request.getSession(false);
        AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
        CartDetailDAO cartDetailDAO = CartDetailDAO.getInstance();
        List<CartDetailDTO> list = null;
        try {
            list = cartDetailDAO.getCart(account.getEmail());
            List<CarShortage> listOfInvalidCar = null;
            if (list != null) {
                for (CartDetailDTO cartDetailDTO : list) {
                    CarShortage car = cartDetailDAO.getInvalidCarInCart(cartDetailDTO.getCarID(), account.getEmail());
                    if (car != null) {
                        if (listOfInvalidCar == null) {
                            listOfInvalidCar = new ArrayList<>();
                        }
                        listOfInvalidCar.add(car);
                    }
                }
                if (listOfInvalidCar != null) {
                    request.setAttribute("INVALID_CARS", listOfInvalidCar);
                    pass = false;
                }
            }
            String txtDiscountID = request.getParameter("txtDiscountID");
            if (!"".equals(txtDiscountID)) {
                DiscountDAO discountDAO = DiscountDAO.getInstance();
                Date date = new Date();
                long today = date.getTime();
                if (!discountDAO.checkValidDiscount(txtDiscountID, Status.ACTIVE, today)) {
                    request.setAttribute("DISCOUNT", "Your discount is invalid.Maybe it is used or has inexpired date");
                    pass = false;
                } else {
                    rate = discountDAO.getRate(txtDiscountID);
                    request.setAttribute("DISCOUNTID", txtDiscountID);

                }
            } else {
                txtDiscountID = null;
            }
            if (pass) {
                String txtTotal = request.getParameter("txtTotal");
                long retalDate = DateConvertion.parse(txtRentalDate);
                float total = Float.valueOf(txtTotal);
                if (rate < 0) {
                    request.setAttribute("TOTAL", total);
                    request.setAttribute("DISCOUNTID", null);
                } else {
                    request.setAttribute("RATE", rate);
                    request.setAttribute("SUBTOTAL", total);
                    request.setAttribute("TOTAL", total -= total * rate / 100);
                }
                long returnDate = 0;
                try {
                    returnDate = transferReturnDate(txtRentalDate, numberOfRentalDate);
                    url = "printOrder";
                    url = map.get(url);
                    String rentalDay = DateConvertion.parseToString(retalDate);
                    String returnDay = DateConvertion.parseToString(returnDate);
                    request.setAttribute("RENTAL_DAY", rentalDay);
                    request.setAttribute("RETURN_DAY", returnDay);

                    CarDAO carDAO = CarDAO.getInstance();
                    List<CarDTO> listCar = null;
                    for (int i = 0; i < list.size(); i++) {
                        CarDTO car = carDAO.getOneCar(list.get(i).getCarID());
                        if (listCar == null) {
                            listCar = new ArrayList<>();
                        }
                        car.setQuantity(list.get(i).getQuantity());
                        listCar.add(car);
                    }
                    request.setAttribute("CARS", listCar);

                } catch (ParseException e) {
                    log(e.getMessage());
                    e.printStackTrace();
                    pass = false;
                    response.sendError(500);
                }
            }
        } catch (NamingException e) {
            isError = true;
            log(e.getMessage());
            response.sendError(500);
        } catch (SQLException e) {
            isError = true;
            log(e.getMessage());
            response.sendError(500);
        }
        if (!isError) {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private long transferReturnDate(String transferDate, int numberOfHiredDate) throws ParseException {
        String delimeter = "/";
        StringTokenizer tokenizer = new StringTokenizer(transferDate, delimeter);
        int day = Integer.valueOf(tokenizer.nextToken());
        int month = Integer.valueOf(tokenizer.nextToken());
        int year = Integer.valueOf(tokenizer.nextToken());
        if (day + numberOfHiredDate <= 31) {
            day += numberOfHiredDate;
        } else {
            int countDay = 31 - day;
            day = numberOfHiredDate - countDay;
            month++;
            if(month > 12){
                month  = 1;
                year++;
            }
        }
        String strDate = day + "/" + month + "/" + year;
        return  DateConvertion.parse(strDate);
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
