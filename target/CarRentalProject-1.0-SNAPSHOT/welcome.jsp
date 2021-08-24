<%-- 
    Document   : header
    Created on : Mar 14, 2021, 10:31:50 PM
    Author     : khang nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="account" value="${sessionScope.ACCOUNT}"/>
<c:choose>
    <c:when test="${empty account}">
        <form action="loginForm" method="POST">
            <input type="submit" value="Login"/>
        </form>
        <form action="registerForm" method="POST">
            <input type="submit" value="Registry"/>
        </form>

    </c:when>
    <c:when test="${account.role == 1}">
        <font color="red">
        Welcome ${account.fullName}</br>
        </font>
        <form action="logOut">
            <input type="submit" value="Log out"/></br>
        </form>
        <form action="viewCart">
            <input type="submit" value="View Cart"/></br>
        </form>
        <form action="viewOrderHistory" method="POST">
            <input type="submit" value="View Order"/></br>
        </form>
    </c:when>
    <c:otherwise>
        <font color="red">
        Welcome ${account.fullName}</br>
        </font>
        <form action="logOut">
            <input type="submit" value="Log out"/></br>
        </form>
    </c:otherwise>    
</c:choose>
