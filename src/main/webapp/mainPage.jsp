<%-- 
    Document   : mainPage
    Created on : Mar 16, 2021, 4:06:33 PM
    Author     : khang nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:set var="account" value="${sessionScope.ACCOUNT}"/>
<c:if test="${not empty account}">
    <font color="red">
    Welcome ${account.fullName}</br>
    </font>
</c:if>
    <c:choose>
        <c:when test="${account.role == 2}">
            <a href="default">Back to search for Admin</a>
        </c:when>
        <c:otherwise>
            <a href="default">Back to search</a>
        </c:otherwise>
    </c:choose>
