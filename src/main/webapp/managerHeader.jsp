<%--
  Created by IntelliJ IDEA.
  User: M.yaghoobi
  Date: 28/2/2021
  Time: 3:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
<!--========================================================================================================-->	
<link rel="stylesheet" href="https://cdn.rtlcss.com/bootstrap/v4.2.1/css/bootstrap.min.css"
  integrity="sha384-vus3nQHTD+5mpDiZ4rkEPlnkcyTP+49BhJ4wJeJunw06ZAp+wzzeBPUXr42fi8If"  crossorigin="anonymous">                     
<!--=========================================================================================================-->				
<link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
<!--=========================================================================================================-->		
    <meta charset="utf-8">

<style>


.sidenav {
  height: 100%;
  width: 0;
  position: fixed;
  z-index: 1;
  top: 0;
  right: 0;
   background-image: radial-gradient(circle, #f0dcac, #f1dfb7, #f1e3c2, #f2e6ce, #f2ead9, #f2ebdc, #f1ebe0, #f1ece3, #f1eae0, #f0e8dc, #f0e6d9, #f0e4d6);

  overflow-x: hidden;
  transition: 0.5s;
  padding-top: 60px;
}
.sidenav a, .dropdown-btn {
  padding: 6px 8px 6px 16px;
  text-decoration: none;
  font-size: 18px;
   font-weight: bold;
  color: black;
  display: block;
  border: none;
  background: none;
  width: 100%;
  text-align: right;
  cursor: pointer;
  outline: none;
}
.sidenav a {
  padding: 8px 8px 8px 32px;
  text-decoration: none;
  font-size: 14px;
   font-weight: bold;
  color: black;
  display: block;
  transition: 0.3s;
}

.sidenav a:hover {
  color: #EEAD18;
}
.sidenav .closebtn {
  position: absolute;
  top: 0;
  right: 25px;
  font-size: 36px;
  margin-left: 50px;
}

@media screen and (max-height: 450px) {
  .sidenav {padding-top: 15px;}
  .sidenav a {font-size: 18px;}
}
</style>
</head>
<body>

<div id="mySidenav" class="sidenav">
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
   <button class="dropdown-btn">مدیریت کاربران 
    <i class="fa fa-caret-down"></i>
  </button>
  <div class="dropdown-container">
   <a class="nav-link" href="ManagerController?action=getAllActiveEmployees" >
                       جستجوی کاربران
                    </a>
  </div>
  
  <button class="dropdown-btn">مدیریت مرخصی 
    <i class="fa fa-caret-down"></i>
  </button>
  <div class="dropdown-container">
 <a class="nav-link" href="ManagerController?action=RegisteredLeaves" >
                         مدیریت مرخصی
                    </a>
  </div>
 
 <button class="dropdown-btn">مدیریت پیام ها 
    <i class="fa fa-caret-down"></i>
  </button>
  <div class="dropdown-container">
 <a class="nav-link" href="EmployeeController?action=sendMessages">
                       ارسال پیام
                    </a>
					<a class="nav-link" href="EmployeeController?action=ReceiveMessages">
                       پیام های دریافتی
                    </a>
					<a class="nav-link" href="EmployeeController?action=sentMessages">
                      پیام های ارسالی
                    </a>
  </div>
					
 <a class="nav-link " href="ManagerController?action=logout" style=" font-size: 18px" >
                    خروج
                </a>
</div>


<span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776; </span>

<script
        >
function openNav() {
  document.getElementById("mySidenav").style.width = "250px";
}

function closeNav() {
  document.getElementById("mySidenav").style.width = "0";
}
</script>
<script>
var dropdown = document.getElementsByClassName("dropdown-btn");
var i;

for (i = 0; i < dropdown.length; i++) {
  dropdown[i].addEventListener("click", function() {
  this.classList.toggle("active");
  var dropdownContent = this.nextElementSibling;
  if (dropdownContent.style.display === "block") {
  dropdownContent.style.display = "none";
  } else {
  dropdownContent.style.display = "block";
  }
  });
}
</script>
   
</body>
</html> 
