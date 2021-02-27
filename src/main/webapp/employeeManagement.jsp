
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <jsp:include page="managerHeader.jsp"/>
    <title>مدیریت کاربران</title>
	<style>
body {
 background-image: radial-gradient(circle, #f0dcac, #f1dfb7, #f1e3c2, #f2e6ce, #f2ead9, #f2ebdc, #f1ebe0, #f1ece3, #f1eae0, #f0e8dc, #f0e6d9, #f0e4d6);

button-family:fontfamilyIcon;}
</style>
	
</head>
<body dir="rtl">
<!--<jsp:include page="body.jsp"/>-->
<script>
    function inactiveEmployee(employeeId) {
        if (confirm('<fmt:bundle basename="resource_fa"><fmt:message key="inactive employee"/></fmt:bundle>')) {
            window.location = 'ManagerController?action=inactive&employeeId=' + employeeId;
        }
    }

    function findEmployee(employeeId) {
        window.location = 'ManagerController?action=editAndAppointmentOfManager&employeeId=' + employeeId;
    }
</script>

<div class="container" style="margin-top: 50px;border-radius: 6px;background-color: #F0E1BE;">
    <form action="ManagerController" method="post">
        <div>
            <div class="form-row">
                <input hidden type="hidden" name="action" value="search">
                <div class="form-group col-md-3">
                    <label for="firstName" style="font-weight: 600;"><fmt:bundle basename="resource_fa">
                         <!--<fmt:message key="firstName"/>-->نام
                    </fmt:bundle></label>
                    <input type="text" name="firstName" class="form-control" id="firstName"
                           value="${requestScope.firstName}"
                           <!--placeholder='<fmt:bundle basename="resource_fa">-->
                       <fmt:message key="firstName"/>
                    </fmt:bundle>'>
                </div>
                <div class="form-group col-md-3">
                    <label for="lastName" style="font-weight: 600;"><fmt:bundle basename="resource_fa">
                        <!--<fmt:message key="lastName"/>-->نام خانوادگی
                    </fmt:bundle></label>
                    <input type="text" name="lastName" class="form-control" id="lastName"
                           value="${requestScope.lastName}"
                           <!--placeholder='<fmt:bundle basename="resource_fa">-->
                        <fmt:message key="lastName"/>
                    </fmt:bundle>'>
                </div>


                <div class="form-group col-md-3">
                    <label for="username" style="font-weight: 600;"><fmt:bundle basename="resource_fa">
                        <!--<fmt:message key="username"/>-->نام کاربری
                    </fmt:bundle></label>
                    <input type="text" name="username" class="form-control" id="username"
                           value="${requestScope.username}"
                           <!--placeholder='<fmt:bundle basename="resource_fa">-->
                       <fmt:message key="lastName"/>
                    </fmt:bundle>'>
                </div>
                <button type="submit" class="btn btn-lg btn-block btn btn btn-info " 
				style="margin-top: 31px;width:260px; height: 38px;background-color: #F4C34E;border: none;"> <span
                        class="fa fa-search"><fmt:bundle basename="resource_fa">
                    <!--<fmt:message key="search"/>-->
                </fmt:bundle></span></button>
            </div>
            
        </div>
    </form>


    <table class="table table-bordered table-hover table-responsive-lg;">
        <thead class="thead-light " style="background-color: #F4C34E;">
        <tr style="border-radius: 10px;">
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <!--<fmt:message key="firstName"/>-->نام
            </fmt:bundle></th>
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <!--<fmt:message key="lastName"/>-->نام خانوادگی
            </fmt:bundle></th>
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <!--<fmt:message key="manager"/>-->مدیر
            </fmt:bundle></th>
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <!--<fmt:message key="role"/>-->نقش
            </fmt:bundle></th>
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <!--<fmt:message key="status"/>-->وضعیت
            </fmt:bundle></th>
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <!--<fmt:message key="action"/>-->عملیات
            </fmt:bundle></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.employeeList}" var="employee">
            <tr>
                <td hidden><c:out value="${employee.id}"/></td>
                <td><c:out value="${employee.firstName}"/></td>
                <td><c:out value="${employee.lastName}"/></td>
                <td><c:out value="${employee.manager.firstName} ${employee.manager.lastName}"/></td>
                <td><c:out value="${employee.role.name}"/></td>
                <td><c:out value="${employee.employeeStatus.name}"/></td>
                <td class="text-right" style="width: 21%;">
                   <button type="button"
                            class="btn btn-primary btn-rounded btn-lm my-0 badge-pill " value="update"
                            style="width: 82px;background-color: #F4C34E;border: none;" onclick="findEmployee(${employee.id})">
                            <span class="fa fa-edit"> <fmt:bundle basename="resource_fa">
                        <!--<fmt:message key="edit"/>-->
                    </fmt:bundle></span></button>
                    <button type="button"
                            class="btn btn-danger btn-rounded btn-lm my-0 badge-pill " value="delete"
                            style="width: 80px;margin-right:10px;background-color: #F4C34E;border: none;"
							onclick="inactiveEmployee(${employee.id})">						
                           <span class="fa fa-trash" aria-hidden="true"> <fmt:bundle basename="resource_fa">
                        <!--<fmt:message key="delete"/>-->
                    </fmt:bundle> </span></button>
                </td>
            </tr>
			
     </c:forEach>
        </tbody>
    </table>
	<a class="nav-link" href="ManagerController?action=insertEmployee" style="width: 50px;color: black;margin-right:1080px; font-size: 30px;">&#43;</a>
	
</div>
 
</body>
</html>