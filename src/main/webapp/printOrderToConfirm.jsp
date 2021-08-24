<%-- 
    Document   : printOrderToConfirm
    Created on : Mar 19, 2021, 4:29:24 AM
    Author     : khang nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Page</title>
    </head>
    <body>
        <c:set var="cars" value="${requestScope.CARS}"/>
        <c:set var="discountID" value="${requestScope.DISCOUNTID}"/>
        <c:set var="rentalDay" value="${requestScope.RENTAL_DAY}"/>
        <c:set var="returnDay" value="${requestScope.RETURN_DAY}"/>
        <c:set var="total" value="${requestScope.TOTAL}"/>
        <c:set var="subTotal" value="${requestScope.SUBTOTAL}"/>
        <c:set var="rate" value="${requestScope.RATE}"/>
        <jsp:include page="mainPage.jsp" flush="true"/>
        <table border="1">
            <thead>
                <tr>
                    <th>
                        No
                    </th>
                    <th>
                        Car Name
                    </th>
                    <th>
                        Car Color
                    </th>
                    <th>
                        Year
                    </th>
                    <th>
                        Category
                    </th>
                    <th>
                        Price
                    </th>
                    <th>
                        Quantity
                    </th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="car" items="${cars}" varStatus="counter">
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
                        </td>
                        <td>
                            ${car.quantity}
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        RentalDay:${rentalDay}</br>
        ReturnDay:${returnDay}</br>
        <c:choose>
            <c:when test="${empty discountID}">
                Total:${total}</br>
            </c:when>
            <c:otherwise>
                Sub Total:${subTotal}</br>
                DiscountID:${discountID}</br>
                Rate:${rate}</br>
                Total:${total}</br>
            </c:otherwise>
        </c:choose>
        <form action="renting">
            <input type="hidden" name="txtDiscountID" value="${discountID}"/>
            <input type="hidden" name="txtReturnDay" value="${returnDay}"/>
            <input type="hidden" name="txtRentalDay" value="${rentalDay}"/>
            <input type="hidden" name="txtTotal" value="${total}"/>
            <input type="submit" value="Confirm"/>
        </form>
        <form action="viewCart" method="POST">
            <input type="submit" value="View Cart"/>
        </form>
    </body>
</html>
