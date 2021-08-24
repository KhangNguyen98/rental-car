<%-- 
    Document   : login
    Created on : Mar 14, 2021, 4:23:42 PM
    Author     : khang nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            function isClick() {
                if (!grecaptcha.getResponse()) {
                    alert("You must prove that you are not robot");
                    return false;
                } else {
                    document.getElementById("formLogin").submit();
                    return true;
                }
            }

        </script>
        <script type="text/javascript">
            var onloadCallback = function () {
                grecaptcha.render('form_recaptcha', {
                    'sitekey': '6LdzZYUaAAAAADp7QiVxKPvBEqKuk2oFFI0_WXK5'
                });
            };
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <font color="red">
        ${requestScope.MUST_LOGIN}${requestScope.MESSAGE}</br>
        </font>
        <jsp:include page="mainPage.jsp" flush="true"/>
        <form action="login" method="POST" id="formLogin" name="loginAction" onsubmit="return isClick()">
            Email:<input type="text" name="txtAccountEmail" value="${param.txtAccountEmail}" /></br>
            Password:<input type="password" name="txtAccountPassword" /></br>
            <font color="red">
            ${requestScope.ERROR_IN_LOGIN}
            </font>
            <div id="form_recaptcha"></div>
            
            <button>Log In</button>
        </form>
        <script src="https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit"
                async defer>
        </script>
    </body>
</html>
