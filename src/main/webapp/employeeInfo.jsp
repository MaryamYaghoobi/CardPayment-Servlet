
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <jsp:include page="employeeHeader.jsp"/>
    <script src="resources/js/jquery.min.js"></script>
    <script src="resources/js/jquery.validate.min.js"></script>
    <script src="resources/js/input.validation.js"></script>
</head>
<body dir="rtl">
<jsp:include page="body.jsp"/>
<div class="container" name="container"
     style="background-color: deepskyblue; width: 700px; margin-top: 55px;border-radius: 5px;">
    <form action="EmployeeController" method="post" id="editForm">
        <input type="hidden" name="action" value="update">
        <input hidden type="hidden" name="id" value="${requestScope.Employee.id}">
        <div class="form-row">
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><fmt:bundle basename="resource_fa">
                    <fmt:message key="username"/>
                </fmt:bundle></label>
                <input type="text" readonly class="form-control" id="username" name="username"
                       value="<c:out value="${requestScope.Employee.username}"/>">
            </div>
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><fmt:bundle basename="resource_fa">
                    <fmt:message key="password"/>
                </fmt:bundle></label>
                <input type="text" readonly class="form-control" id="password" name="password"
                       value="<c:out value="${requestScope.Employee.password}"/>">
            </div>
        </div>
        <div class="form-row">

            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label><fmt:bundle basename="resource_fa">
                    <fmt:message key="firstName"/>
                </fmt:bundle>
                </label>
                <input type="text" class="form-control" id="firstName" name="firstName"
                       value="<c:out value="${requestScope.Employee.firstName}"/>">
            </div>

            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label><fmt:bundle basename="resource_fa">
                    <fmt:message key="lastName"/>
                </fmt:bundle></label>
                <input type="text" name="lastName" class="form-control" id="lastName"
                       value="<c:out value='${requestScope.Employee.lastName}'/>">
            </div>
        </div>
        <div class="form-row">                                                                             
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label><fmt:bundle basename="resource_fa">
                    <fmt:message key="email"/>
                </fmt:bundle></label>
                <input type="email" class="form-control" id="email" name="email"
                       value="<c:out value='${requestScope.Employee.email}'/>">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><fmt:bundle basename="resource_fa">
                    <fmt:message key="manager"/>
                </fmt:bundle></label>
                <input type="text" readonly class="form-control" id="manager" name="manager"
                       value="<c:out value='${requestScope.Employee.manager.firstName} ${requestScope.Employee.manager.lastName}'/>"/>
            </div>

            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><fmt:bundle basename="resource_fa">
                    <fmt:message key="status"/>
                </fmt:bundle> : </label>
                <input type="text" readonly class="form-control" id="employeeStatus" name="employeeStatus"
                       value="<c:out value='${requestScope.Employee.employeeStatus.name}'/> ">
                <label class="form-check-label"></label>
            </div>
        </div>
        <div class="form-row" dir="ltr">
            <div class=" col-md-3" style="margin-top: 17px;margin-bottom: 10px;">
                <button type="submit" class="btn btn-primary" style="width:90px;"><fmt:bundle basename="resource_fa">
                    <fmt:message key="edit"/>
                </fmt:bundle></button>
            </div>
        </div>
    </form>

</div>
</body>
</html>
