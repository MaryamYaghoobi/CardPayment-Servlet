
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <link
            rel="stylesheet"
            href="https://cdn.rtlcss.com/bootstrap/v4.2.1/css/bootstrap.min.css"
            integrity="sha384-vus3nQHTD+5mpDiZ4rkEPlnkcyTP+49BhJ4wJeJunw06ZAp+wzzeBPUXr42fi8If"
            crossorigin="anonymous">
    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
	 <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <meta charset="utf-8">
    <style>
        ul.nav a:hover {
         color: white !important;
         
            background-blend-mode: screen;;
            border-radius: 3px;

        }
    </style>

</head>
<body dir="rtl">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark ;background-color:#3e4061"
     style="border-radius: 7px; margin-left: 10px;margin-right: 10px; margin-top: 12px;">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="nav navbar-nav mr-auto " style="left: 0px ;right: auto;">
                <li class="nav-item">
				 <li class="nav-item">
                    <a class="nav-link" href="ManagerController?action=getAllActiveEmployees"><fmt:bundle basename="resource_fa">
                        <!--<fmt:message key="EmployeeManagement"/>-->مدیریت کاربران
                    </fmt:bundle></a>
                </li>
				<li class="nav-item">
                    <a class="nav-link" href="ManagerController?action=insertEmployee"><fmt:bundle basename="resource_fa">
                        <!--<fmt:message key="insertNewEmployee"/>-->افزودن کاربر جدید
                    </fmt:bundle></a>
                </li>
                    <a class="nav-link" href="#"><fmt:bundle basename="resource_fa">
                        <!--<fmt:message key="personalInfo"/>-->پروفایل شخصی
                    </fmt:bundle><span class="sr-only">(current)</span></a>
                </li>
                
               
                </ul>

        </div>
    </div>
</nav>
<script
        src="https://cdn.rtlcss.com/bootstrap/v4.2.1/js/bootstrap.min.js"
        integrity="sha384-a9xOd0rz8w0J8zqj1qJic7GPFfyMfoiuDjC9rqXlVOcGO/dmRqzMn34gZYDTel8k"
        crossorigin="anonymous"></script>
</body>
</html>
