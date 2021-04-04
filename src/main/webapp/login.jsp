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

<c:if test= "${requestScope['invalidationUserOrPassword'] && empty sessionScope['username']}">
    <div>
    <label>
<br><br><br>
<h3 style="color:red; text-indent: 620px;">نام کاربری یا رمز عبور نادرست است</h3>     
    </label>
    </div>
    </c:if>
<div id="login">
 <link rel="stylesheet" href="css/login.css">
   <form  name="Form" action="LoginController" method="post" onsubmit="return validateForm()">
    <input type="hidden" name="action" value="login">
    <h1>ورود</h1>
    <input type="text" name="username" id="Uname" placeholder="نام کاربری">
    <input type="Password" name="password" id="Pass" placeholder="رمز عبور">
	 
    <button type="submit" name="log" >ورود</button>
  </form>
  <br>
  	<a class="nav-link" href="LoginController?action=insertUser" style="width: 50px;color: black;margin-right:1080px; font-size: 10px;">ایجادحساب کاربری</a>

</div>
  





