<%-- 
    Document   : customerPage
    Created on : Mar 15, 2021, 12:21:40 PM
    Author     : khang nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Page</title>
    </head>
    <body>
        <font color="red">
        ${requestScope.MESSAGE}</br>
        </font>
        <jsp:include flush="true" page="search.jsp"/>    
        <c:set var="listCar" value="${requestScope.CAR}"/>    
        <c:choose>
            <c:when test="${empty listCar}">    
                <font color="red">
                No Result.Please search again
                </font>
            </c:when>    
            <c:otherwise>
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
                            <th>Add To Cart</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="car" items="${listCar}" varStatus="counter">
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
                                <td>
                                    <form action="addToCart" method="POST">
                                        <input type="hidden" name="txtSearchName" value="${param.txtSearchName}"/>
                                        <input type="hidden" name="tagCategory" value="${param.tagCategory}"/>
                                        <input type="hidden" name="txtSearchReturnDate" value="${param.txtSearchReturnDate}"/>
                                        <input type="hidden" name="txtSearchRentalDate" value="${param.txtSearchRentalDate}"/>
                                        <input type="hidden" name="productIDToCart" value="${car.id}"/>
                                        <input type="hidden" name="currentPage"  value="${param.currentPage}"/>
                                        <input type="submit" value="Add To Cart"/>
                                    </form>
                                </td>
                            </tr>                           
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
        </br>


        <c:if test="${not empty requestScope.PREVIOUS_PAGE}">
            <c:url var="previousPage" value="default">
                <c:param name="currentPage" value="${requestScope.PREVIOUS_PAGE}"/>
                <c:param name="txtSearchName" value="${param.txtSearchName}"/>
                <c:param name="tagCategory" value="${param.tagCategory}"/>
                <c:param name="txtSearchRentalDate" value="${param.txtSearchRentalDate}"/>
                <c:param name="txtSearchReturnDate" value="${param.txtSearchReturnDate}"/>
            </c:url>
            <a href="${previousPage}"> << </a>
        </c:if>                
        <c:choose>
            <c:when test="${empty param.currentPage}">
                <font color="red">
                Page:1
                </font> 
            </c:when>
            <c:otherwise>
                <font color="red">
                Page:${param.currentPage}
                </font> 
            </c:otherwise>
        </c:choose>             
        <c:if test="${not empty requestScope.NEXT_PAGE}">
            <c:url var="nextPage" value="default">
                <c:param name="currentPage" value="${requestScope.NEXT_PAGE}"/>
                <c:param name="txtSearchName" value="${param.txtSearchName}"/>
                <c:param name="tagCategory" value="${param.tagCategory}"/>
                <c:param name="txtSearchRentalDate" value="${param.txtSearchRentalDate}"/>
                <c:param name="txtSearchReturnDate" value="${param.txtSearchReturnDate}"/>
            </c:url>
            <a href="${nextPage}"> >> </a>
        </c:if>  

    </body>
</html>
