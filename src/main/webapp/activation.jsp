<%-- 
    Document   : activation
    Created on : Mar 19, 2021, 12:47:48 PM
    Author     : khang nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ACTIVATION Page</title>
    </head>
    <body>
    <c:set var="code" value="${requestScope.CODE}"/>
    <font color =red>
    ${requestScope.INVALID}</br>
    </font>
    <jsp:include page="mainPage.jsp" flush="true"/>
    <form action="activation" method="POST">
        Email:${param.txtAccountEmail}</br>
        Your code:<input type="text" name="txtToken" value="${param.txtToken}"/></br>
        <input type="hidden" name="txtAccountPassword" value="${param.txtAccountPassword}"/>
        <input typpe="hidden" name="txtAccountEmail" value="${param.txtAccountEmail}"/>
        <input type="submit" value="Activate"></br>
    </form>
    <c:if test="${not empty code}">
        Your code:${code}</br>
        Code just appear one times.Please be carefully
    </c:if>
</body>
</html>
