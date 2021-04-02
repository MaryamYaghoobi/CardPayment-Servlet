<%--
  Created by IntelliJ IDEA.
  User: M.yaghoobi
  Date: 28/2/2021
  Time: 3:33 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<title>مدیریت کاربران</title>

	<style>
.disabledClass {
	background-color: #FBA538 ;
}
.deletededClass {
	background-color: red ;
}
.enabledClass
{
	background-color:  #F0E1BE ;
}

  </style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />  
<!--==============================================================================================================-->	
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!--==============================================================================================================-->
<link href="css/status.css" rel="stylesheet" />
<!--==============================================================================================================-->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!--==============================================================================================================-->

    <jsp:include page="adminHeader.jsp"/>
</head>
<body dir="rtl">
<jsp:include page="body.jsp"/>
<script>
 function deActive(employeeId) {
        if (confirm('کاربر غیر فعال شود؟')) {
            window.location = 'AdminController?action=getAllInActiveEmployees&employeeId=' + employeeId;
        }
    }
	 function active(employeeId) {
        if (confirm('کاربر فعال شود؟')) {
            window.location = 'AdminController?action=getAllActiveEmployees&employeeId=' + employeeId;
        }
    }
    function del(employeeId) {
        if (confirm('کاربر حذف شود؟')) {
            window.location = 'AdminController?action=delete&employeeId=' + employeeId;
        }
    }

    function search(employeeId) {
        window.location = 'AdminController?action=editAndAppointmentOfManager&employeeId=' + employeeId;
    }
       
</script>
<div class="container" style="margin-top: 50px;border-radius: 6px;background-color: #F0E1BE;">
    <form action="AdminController" method="post">
        <div>
            <div class="form-row">
                <input hidden type="hidden" name="action" value="search">
                <div class="form-group col-md-3">
                    <label for="firstName" style="font-weight: 600;">
                        نام
                    </label>
                    <input type="text" name="firstName" class="form-control" id="firstName"
                           value="${requestScope.firstName}"
                          placeholder=''>                                          
                </div>
                <div class="form-group col-md-3">
                    <label for="lastName" style="font-weight: 600;">
                       نام خانوادگی
                   </label>
                    <input type="text" name="lastName" class="form-control" id="lastName"
                           value="${requestScope.lastName}"
                           placeholder=''>
                </div>

                <div class="form-group col-md-3">
                    <label for="username" style="font-weight: 600;">
                       نام کاربری
                    </label>
                    <input type="text" name="username" class="form-control" id="username"
                           value="${requestScope.username}"
                           placeholder=''>                                    
                </div>
                <button type="submit" class="btn btn-lg btn-block btn btn btn-info " 
				style="margin-top: 31px;width:260px; height: 38px;background-color: #F4C34E;border: none;"> <span
                       id="form_search" name="search" class="fa fa-search">                  
                </span></button>
            </div>
            
        </div>
    </form>


    <table class="table table-bordered table-hover table-responsive-lg;">
        <thead class="thead-light " style="background-color: #F4C34E;">
        <tr style="border-radius: 10px;">
            <th class="text-center" scope="col">نام</th>                        
            <th class="text-center" scope="col">نام خانوادگی</th>            
            <th class="text-center" scope="col">مدیر</th>    
            <th class="text-center" scope="col"> نقش</th>
            <th class="text-center" scope="col">وضعیت</th>
            <th class="text-center" scope="col">عملیات</th>                           
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.employeeList}" var="employee">
			 	  <tr  class="${employee.active=='true' && employee.disabled=='false' ? 'deletededClass' :
	                           employee.active=='false' && employee.disabled=='true' ? 'disabledClass' :
                               employee.active=='true' && employee.disabled=='true' ? 'deletededClass' :
                               employee.active=='false' && employee.disabled=='false' ? 'enabledClass' : 'deletededClass'}">
			 
                <td hidden><c:out value="${employee.id}"/></td>
                <td><c:out value="${employee.firstName}"/></td>
                <td><c:out value="${employee.lastName}"/></td>
                <td><c:out value="${employee.manager.firstName}${employee.manager.lastName}"/></td>
                <td><c:out value="${employee.role.name}"/></td>            
				  <td><!--<c:out value="${employee.disabled}"/>-->
                    <div class="switch">
                       <input type="radio" class="switch-input"  name="employeeStatus_active${employee.id}" onchange="active(${employee.id})"
	                    value="active" 
	                    id="active${employee.id}" ${employee.disabled=='false'?'checked':''}  >
                          <label for="active${employee.id}" class="switch-label switch-label-off">فعال</label>
	  
                        <input type="radio" class="switch-input" name="employeeStatus_inactive${employee.id}" onchange="deActive(${employee.id})"
	                     value="inactive" ${employee.disabled=='true'?'checked':''} 
	                     id="inactive${employee.id}">
                          <label for="inactive${employee.id}" class="switch-label switch-label-on">غیر فعال</label>
						  
                     <span class="switch-selection"></span>                   
                   </div>
 				   
                </td>	 				
                <td class="text-right" style="width: 21%;">
                   <button type="button"
                            class="btn btn-primary btn-rounded btn-lm my-0 badge-pill " value="updateProfile"
                            style="width: 82px;background-color: #F4C34E;border: none;" onclick="search(${employee.id})">
							
							<span class="fa fa-edit"></span>
							</button>                      
                    <button type="button"
                            class="btn btn-danger btn-rounded btn-lm my-0 badge-pill " value="delete"
                            style="width: 80px;margin-right:10px;background-color: #F4C34E;border: none;"
							onclick="del(${employee.id})">						                         
						 <span class="fa fa-trash" aria-hidden="true"> </span> </button> 				  						                        
                </td>
            </tr>			
     </c:forEach>
        </tbody>
    </table>
	<a class="nav-link" href="AdminController?action=insertEmployee" style="width: 50px;color: black;margin-right:1080px; font-size: 30px;">&#43;</a>
	
</div>
 
 
 <script>   

        $('#inactive').on('click', function () {
    $(this).closest('tr').removeClass('enabledClass');
    $(this).closest('tr').addClass('disabledClass');
});
 </script>
 <script>   

        $('#deleted${employee.id}').on('click', function () {
    $(this).closest('tr').removeClass('enabledClass');
    $(this).closest('tr').addClass('deletededClass');
});
<script>   

        $('#deleted${employee.id}').on('click', function () {
    $(this).closest('tr').removeClass('disabledClass');
    $(this).closest('tr').addClass('deletededClass');
});
 </script>
</body>
</html>