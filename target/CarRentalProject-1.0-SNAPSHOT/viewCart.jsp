<%-- 
    Document   : viewCart
    Created on : Mar 16, 2021, 11:57:20 AM
    Author     : khang nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart Page</title>
    </head>
    <body>
        <jsp:include page="mainPage.jsp" flush="true"/>
        <font color="red">
        </br>${requestScope.MESSAGE}
        </font>
        <c:set var="listOfCart" value="${requestScope.CART}"/>
        <c:set var="listOfCar" value="${requestScope.CAR}"/>
        <c:set var="invalidCars" value="${requestScope.INVALID_CARS}"/>
        <c:set var="total" value="${0}"/>

        <c:if test="${not empty invalidCars}">
            <font color="red">
            </br>List of cars which don't have enough quantity:</br>
            </font>
            <c:forEach var="car" items="${invalidCars}">
                <font color="red">
                -Name:${car.name}/Quantity:${car.quantity}</br>
                </font>
            </c:forEach>
        </c:if>
        <c:choose>
            <c:when test="${empty listOfCart}">
                <font color="red">
                There is no product in your cart
                </font>
            </c:when>
            <c:otherwise>
                <table border="1">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Car Name</th>
                            <th>Car Color</th>
                            <th>Year</th>
                            <th>Category</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Update</th>
                            <th>Remove</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach varStatus="counter" var="car" items="${listOfCar}">
                            <c:choose>
                                <c:when test="${car.id == requestScope.ERROR_ID}">
                                    <tr>
                                        <td>
                                            ${counter.count}
                                        </td>
                                        <td>
                                            ${car.name}
                                        </td>
                                        <td>
                                            ${car.color}
                                        </td>
                                        <td>
                                            ${car.year}
                                        </td>
                                        <td>
                                            ${car.category}
                                        </td>
                                        <td>
                                            ${car.price}
                                            <c:set var="total" value="${total+car.price*car.quantity}"/>
                                        </td>
                                <form action="updateInCart" method="POST">
                                    <td>
                                        <input type="hidden" name="txtRentalDate"" value="${param.txtRentalDate}"/>
                                        <input type="hidden" name="txtDiscountID" value="${param.txtDiscountID}"/>
                                        <input type="hidden" name="txtReturnDate"" value="${param.txtReturnDate}"/>
                                        <input type="text" name="txtCarQuantity" value="${car.quantity}"/>
                                        <input type="hidden" name="txtCarID" value="${car.id}">
                                        <font color="red">
                                        ${requestScope.ERROR_IN_UPDATE}
                                        </font>
                                    </td>
                                    <td>
                                        <input type="submit" value="Update">
                                    </td>
                                </form>
                                <td>
                                    <form action="deleteInCart" method="POST">
                                        <input type="hidden" name="txtRentalDate"" value="${param.txtRentalDate}"/>
                                        <input type="hidden" name="txtDiscountID" value="${param.txtDiscountID}"/>
                                        <input type="hidden" name="txtReturnDate"" value="${param.txtReturnDate}"/>
                                        <input type="hidden" name="txtCarID" value="${car.id}">
                                        <input type="submit" value="Delete">
                                    </form>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td>
                                    ${counter.count}
                                </td>
                                <td>
                                    ${car.name}
                                </td>
                                <td>
                                    ${car.color}
                                </td>
                                <td>
                                    ${car.year}
                                </td>
                                <td>
                                    ${car.category}
                                </td>
                                <td>
                                    ${car.price}
                                    <c:set var="total" value="${total+car.price*car.quantity}"/>
                                </td>
                            <form action="updateInCart" method="POST">
                                <td>
                                    <input type="hidden" name="txtRentalDate"" value="${param.txtRentalDate}"/>
                                    <input type="hidden" name="txtDiscountID" value="${param.txtDiscountID}"/>
                                    <input type="hidden" name="txtReturnDate"" value="${param.txtReturnDate}"/>
                                    <input type="text" name="txtCarQuantity" value="${car.quantity}"/>
                                    <input type="hidden" name="txtCarID" value="${car.id}"/>
                                </td>
                                <td>
                                    <input type="submit" value="Update">
                                </td>
                            </form>
                            <td>
                                <form action="deleteInCart" method="POST">
                                    <input type="hidden" name="txtRentalDate"" value="${param.txtRentalDate}"/>
                                    <input type="hidden" name="txtDiscountID" value="${param.txtDiscountID}"/>
                                    <input type="hidden" name="txtReturnDate"" value="${param.txtReturnDate}"/>
                                    <input type="hidden" name="txtCarID" value="${car.id}"/>
                                    <input type="submit" value="Delete" onclick="return confirm('Do you want to remove this car from cart')"/>
                                </form>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tbody>
    </table>
    Total:${total}</br>
    <form action="printOrderToConfirm" method="POST">
        Day To Rent:<input type="text" name="txtRentalDate" value="${param.txtRentalDate}"/>
        <font color="red">
        ${requestScope.RENTAL_DATE}</br>
        </font>
        Number Day To Rent:<input type="text" name="txtReturnDate" value="${param.txtReturnDate}"/>
        <font color="red">
        ${requestScope.RETURN_DATE}</br>
        </font>

        DiscountID:<input type="text" name="txtDiscountID" value="${param.txtDiscountID}"/>
        <font color="red">
        ${requestScope.DISCOUNT} 
        </font>
        </br>            
        <input type="hidden" name="txtTotal" value="${total}"/>
        <input type="submit" value="Renting"/>
    </form>           
</c:otherwise>    
</c:choose>
</body>
</html>
