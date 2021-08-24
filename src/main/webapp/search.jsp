<%-- 
    Document   : search
    Created on : Mar 2, 2021, 1:01:44 PM
    Author     : khang nguyen

 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <jsp:include  page="welcome.jsp" flush="true"/>
    <c:set var="listCategory" value="${requestScope.CATEGORY}"/>

    <form action ="search" method="POST">
        Car Name:<input type="text" name="txtSearchName" value="${param.txtSearchName}"/></br>
        Car Category:
        <select name="tagCategory">
            <option value="default">---</option>
            <c:forEach var="category" items="${listCategory}">
                <c:choose>
                    <c:when test="${category.name == param.tagCategory}">
                        <option value="${category.name}" selected>
                            ${category.name}
                        </option>
                    </c:when>
                    <c:otherwise>
                        <option value="${category.name}">
                            ${category.name}
                        </option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select></br>
        Rental Date:<input type="text" name="txtSearchRentalDate" value="${param.txtSearchRentalDate}"/>
        </br>
        Return Date:<input type="text" name="txtSearchReturnDate" value="${param.txtSearchReturnDate}"/>
        </br>
        <input type="submit" name="btnAction" value="Search"></br>
    </form>

