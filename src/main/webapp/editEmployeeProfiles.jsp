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
    <jsp:include page="employeeHeader.jsp"/>
	 <title>ویرایش پروفایل</title>
</head>
<body dir="rtl">
<jsp:include page="body.jsp"/>
<div class="container" name="container"
     style="background-color: #F0E1BE; width: 700px; margin-top: 55px;border-radius: 5px;">
    <form action="EmployeeController" method="post" id="editForm">
        <input type="hidden" name="action" value="updateProfile">
        <input hidden type="hidden" name="id" value="${requestScope.Employee.id}">
		
        <div class="form-row">
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label>
                   نام کاربری
                </label>
                <input type="text" readonly class="form-control" id="username" name="username"
                       value="<c:out value="${requestScope.Employee.username}"/>">
            </div>
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label>
                    رمز عبور
                </label>
                <input type="text" readonly class="form-control" id="password" name="password"
                       value="<c:out value="${requestScope.Employee.password}"/>">
            </div>
        </div>
        <div class="form-row">

            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label>
                   نام
                
                </label>
                <input type="text" class="form-control" id="firstName" name="firstName"
                       value="<c:out value="${requestScope.Employee.firstName}"/>">
            </div>

            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label>
                    نام خانوادگی
               </label>
                <input type="text" name="lastName" class="form-control" id="lastName"
                       value="<c:out value="${requestScope.Employee.lastName}"/>">
            </div>
        </div>
        <div class="form-row"> 
 <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><label>*</label>
                   نام پدر
                </label>
                <input type="text" class="form-control" id="fatherName" name="fatherName"
                       value="<c:out value="${requestScope.Employee.fatherName}"/>">
            </div>		
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label>
                    پست الکترونیک
                </label>
                <input type="email" class="form-control" id="email" name="email"
                       value="<c:out value='${requestScope.Employee.email}'/>">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label>
                    مدیر
                </label>
                <input type="text" readonly class="form-control" id="manager" name="manager"
                       value="<c:out value='${requestScope.Employee.manager.firstName} ${requestScope.Employee.manager.lastName}'/>"/>
            </div>

            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label>
                   پست سازمانی
                 </label>
                <input type="text" readonly class="form-control" id="role" name="role"
                      
					    value="<c:out value='${requestScope.Employee.role.name}'/> ">
					  
                <label class="form-check-label"></label>
            </div>
        </div>
        <div class="form-row" dir="ltr">
            <div class=" col-md-3" style="margin-top: 17px;margin-bottom: 10px;">
                <button type="submit" class="btn btn-primary" style="width:90px;background-color: #F4C34E;border: none;">
                    ویرایش
               </button>
            </div>
        </div>
    </form>

</div>
<!--======================================================-->	
<script src="resources/js/jquery.min.js"></script>
<!--======================================================-->	 
<script src="resources/js/jquery.validate.min.js"></script>
<!--=======================================================-->		
<script src="resources/js/input.validation.js"></script>
<!--======================================================-->		
</body>
</html>
