<%--
  Created by IntelliJ IDEA.
  User: M.yaghoobi
  Date: 28/2/2021
  Time: 3:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html dir="rtl">
<head>
<!--==============================================================-->	
    <link href="<c:url value="/css/login.css" rel="stylesheet"/>
<!--==============================================================-->
    <script type="text/javascript">

        function validateForm() {
            var username = document.forms["Form"]["username"].value;
            var password = document.forms["Form"]["password"].value;
            if (username == null || username == "" ) {
                window.alert('نام کاربری نمیتواند خالی باشد.');
                return false;
            }if(password == null || password == ""){
                window.alert('رمز عبور نمی تواند خالی باشد.');
                return false;
            }
        }
    </script>
</head>

<body style="background-color:black">


<h2><fmt:bundle basename="resource_fa">
    <fmt:message key="login"/></fmt:bundle></h2><br>


<c:if test= "${requestScope['invalidUser'] && empty sessionScope['username']}">
    <div>
    <label id="" style=" vertical-align: middle;
    display:inline-block;
    width:100%;
    text-align:center;
    height:30px;
    line-height:30px;
    color: #ff4626;
"><b><fmt:bundle basename="resource_fa">
        <fmt:message key="invalid username or password"/></fmt:bundle>
    </b>
    </label>
    </div>
    </c:if>


<div class="login">

    <form id="login" name="Form" action="/loginController.do" method="post" onsubmit="return validateForm()">
        <label><b>نام کاربری
        </b>
        </label>
        <input type="text" name="username" id="Uname" placeholder="Username">
        <br><br>
        <label><b>رمز عبور
        </b>
        </label>
        <input type="Password" name="password" id="Pass" placeholder="Password">
        <br><br>
        <button type="submit" name="log" id="log">ورود</button>
        <br><br>

    </form>
	<div class="login100-more" style="background-image: url('images/bg-01.jpg');">
				</div>
</div>



</body>
</html>
