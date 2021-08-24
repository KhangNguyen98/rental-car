<%-- 
    Document   : viewOrder
    Created on : Mar 17, 2021, 1:53:38 PM
    Author     : khang nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Order Page</title>
    </head>
    <body>
        <jsp:include page="mainPage.jsp" flush="true"/>
        <font color="red">
        </br>${requestScope.MESSAGE}</br>
        </font>
        <c:set var="listOrder" value="${requestScope.ORDER}"/>
        <c:choose>
            <c:when test="${empty listOrder}">
                <font color="red">
                No result in your history
                </font>
            </c:when>
            <c:otherwise>
                <table border="1">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Rental Day</th>
                            <th>Return Day</th>
                            <th>DiscountID</th>
                            <th>Total</th>
                            <th>View Detail</th>
                            <th>Cancel</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${listOrder}" varStatus="counter">
                            <tr>
                                <td>
                                    ${counter.count}
                                </td>
                                <td>
                                    ${order.rentalDay}
                                </td>
                                <td>
                                    ${order.returnDay}
                                </td>
                                <td>
                                    ${order.discountID}
                                </td>
                                <td>
                                    ${order.total}
                                </td>
                                <td>
                                    <form action="viewOrderDetail" method="POST">
                                        <input type="hidden" name="txtOrderID" value="${order.id}"/>
                                        <input type="submit" value="View Detail"/>
                                    </form>
                                </td>
                                <td>
                                    <c:if test="${order.status == 1}">
                                        <form action="cancelOrder" method="POST">
                                            <input type="hidden" name="txtOrderID" value="${order.id}"/>
                                            <input type="submit" value="Cancel"/>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </body>
</html>
