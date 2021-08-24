<%-- 
    Document   : register
    Created on : Mar 19, 2021, 10:16:57 AM
    Author     : khang nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
    </head>
    <c:set var="account" value="${sessionScope.ACCOUNT}"/>
    <c:set var="error" value="${requestScope.ERROR}"/>
    <c:if test="${empty account}">
        <form action="loginForm" method="POST">
            <input type="submit" value="Login"/>
        </form>
    </c:if>
    <body>
        <jsp:include page="mainPage.jsp" flush="true"/>
        <form action="register" method="POST">
            Email:<input type="text" name="txtAccountEmail" value="${param.txtAccountEmail}"/>
            <font color="red">
            ${error['email']}</br>
            </font>

            Password:<input type="password" name="txtAccountPassword"/>
            <font color="red">
            ${error['password']}</br>
            </font>

            Confirm Password:<input type="password" name="txtAccountConfirmPassword"/>
            <font color="red">
            ${error['confirmPassword']}</br>
            </font>

            FullName:<input type="text" name="txtAccountFullName" value="${param.txtAccountFullName}"/>
            <font color="red">
            ${error['fullName']}</br>
            </font>

            Phone Number:<input type="text" name="txtAccountPhone" value="${param.txtAccountPhone}"/>
            <font color="red">
            ${error['phone']}</br>
            </font>

            Address:<input type="text" name="txtAccountAddress" value="${param.txtAccountAddress}"/></br>
            <font color="red">
            ${error['address']}</br>
            </font>

            <input type="submit" value="Registry">
        </form>           
    </body>
</html>
