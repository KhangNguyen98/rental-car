<%-- 
    Document   : feedBackForm
    Created on : Mar 18, 2021, 11:08:57 AM
    Author     : khang nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feed Back Page</title>
    </head>
    <body>
        <jsp:include page="mainPage.jsp" flush="true"/>
        </br><a href="viewOrderHistory">Back to view order</a>
        <form action="sendFeedBack" method="POST" id="form">
            Car Name:${param.txtCarName}</br>
            </br>Rate:
            <select name="txtFeedBackRate">
                <c:forEach begin="1" end="10" step="1" varStatus="counter">
                    <c:choose>
                        <c:when test="${counter.count == param.txtFeedBackRate}">
                            <option value="${counter.count}" selected>${counter.count}
                            </option>
                        </c:when>
                        <c:otherwise>
                            <option value="${counter.count}">${counter.count}
                            </option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>                           
            </select>
            </br> Your comment: 
            <textarea name="txtFeedBackContent" id="content" >${param.txtFeedBackContent}</textarea>
            </br> 
            <input type="hidden" name="txtFeedBackID" value="${param.txtFeedBackID}"/>
            <input type="hidden" name="txtCarID" value="${param.txtCarID}"/>
            <input type="hidden" name="txtRentalDetailID" value="${param.txtRentalDetailID}"/>           
            <button type="button" id="form-send__btn">Send</button>
        </form>
        <script>
            const sendBtnEl = document.getElementById("form-send__btn");
            const formEl = document.getElementById("form");
            
            
            sendBtnEl.addEventListener("click", validate);
            function validate(){
                var content = document.getElementById("content").value;
                content = content.trim();
                if(content.length == 0){
                    alert("Please input something in you comment");
                } else{
                    formEl.submit();
                }
            }
        </script>
    </body>
</html>
