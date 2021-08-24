<%-- 
    Document   : viewOrderDetail
    Created on : Mar 17, 2021, 3:42:39 PM
    Author     : khang nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Order Detail Page</title>
    </head>
    <body>
        <jsp:include page="mainPage.jsp" flush="true"/>
        </br><a href="viewOrderHistory">Back to view order</a>
        <font color="red">
        ${requestScope.MESSAGE}
        </font>
        <c:set var="details" value="${requestScope.DETAIL}"/>
        <table border="1">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Name</th>
                    <th>Color</th>
                    <th>Year</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>FeedBack</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="detail" items="${details}" varStatus="counter">
                    <tr>
                        <td>
                            ${counter.count}
                        </td>
                        <td>
                            ${detail.name}
                        </td>
                        <td>
                            ${detail.color}
                        </td>
                        <td>
                            ${detail.year}
                        </td>
                        <td>
                            ${detail.category}
                        </td>
                        <td>
                            ${detail.price}
                        </td>
                        <td>
                            ${detail.quantity}
                        </td>
                         <td>
                             <form action="feedBackForm" method="POST">
                                 <input type="hidden" name="txtRentalDetailID" value="${detail.id}"/>
                                 <input type="hidden" name="txtCarName" value="${detail.name}"/>
                                 <input type="hidden" name="txtCarID" value="${detail.carID}"/>
                                 <input type="submit" value="Feed Back">
                             </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </body>
</html>
