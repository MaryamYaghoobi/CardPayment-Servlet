
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <title>editEmployee</title>
    <jsp:include page="managerHeader.jsp"/>
    <script src="resources/js/jquery.min.js"></script>
    <script src="resources/js/jquery.validate.min.js"></script>
    <script src="resources/js/input.validation.js"></script>
</head>
<body dir="rtl">
<jsp:include page="body.jsp"/>

<div class="container" name="container"
     style="background-color: #F0E1BE; width: 700px; margin-top: 55px;border-radius: 5px;">
    <form action="ManagerController" method="post" id="editForm">
        <input type="hidden" name="action" value="update">
        <input hidden type="hidden" name="id" value="${requestScope.Employee.id}">
        <div class="form-row">
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><fmt:bundle basename="resource_fa">
                    <!--<fmt:message key="username"/>-->نام کاربری
                </fmt:bundle></label>
                <input type="text" readonly class="form-control" id="username" name="username"
                       value="<c:out value="${requestScope.Employee.username}"/>">
            </div>
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><fmt:bundle basename="resource_fa">
                    <!--<fmt:message key="password"/>-->رمز عبور
                </fmt:bundle></label>
                <input type="text" readonly class="form-control" id="password" name="password"
                       value="<c:out value="${requestScope.Employee.password}"/>">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label><fmt:bundle basename="resource_fa">
                    <!--<fmt:message key="firstName"/>-->نام
                </fmt:bundle>
                </label>
                <input type="text" class="form-control" id="firstName" name="firstName"
                       value="<c:out value="${requestScope.Employee.firstName}"/>">
            </div>
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label><fmt:bundle basename="resource_fa">
                    <!--<fmt:message key="lastName"/>-->نام خانوادگی
                </fmt:bundle></label>
                <input type="text" name="lastName" class="form-control" id="lastName"
                       value="<c:out value='${requestScope.Employee.lastName}'/>">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><fmt:bundle basename="resource_fa">
                    <!--<fmt:message key="fatherName"/>-->نام پدر
                </fmt:bundle></label>
                <input type="text" class="form-control" id="fatherName" name="fatherName"
                       value="<c:out value='${requestScope.Employee.fatherName}'/>">
            </div>
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label><fmt:bundle basename="resource_fa">
                    <!--<fmt:message key="email"/>-->پست الکترونیک
                </fmt:bundle></label>
                <input type="email" class="form-control" id="email" name="email"
                       value="<c:out value='${requestScope.Employee.email}'/>">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-5" style="margin-top: 17px;">
                <label><fmt:bundle basename="resource_fa">
                    <!--<fmt:message key="manager"/>-->مدیر 
                </fmt:bundle></label>
                <select name='selectedManager' id="selectedManager" class="form-control">
                    <c:forEach items="<%=request.getAttribute(\"managerList\") %>" var="manager">
                        <option value="<c:out value="${manager}"/>" ${manager.equals(Manager) ? 'selected="selected"' : ''} >
                            <c:out value="${manager}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group col-md-7" style="margin-top: 17px;">
                <br/>
                <label><fmt:bundle basename="resource_fa">
                    <!--<fmt:message key="status"/>-->وضعیت
                </fmt:bundle> : </label>
                <div class="form-check form-check-inline">
                    <input type="radio" class="form-check-input" id="active" name="employeeStatus"
                           value="active" ${requestScope.Employee.employeeStatus.code=='active'?'checked':''} >
                    <label class="form-check-label"><fmt:bundle basename="resource_fa">
                        <!--<fmt:message key="active"/>-->فعال
                    </fmt:bundle></label>
                </div>
                <div class="form-check form-check-inline">
                    <input type="radio" class="form-check-input" id="inactive" name="employeeStatus"
                           value="inactive" ${requestScope.Employee.employeeStatus.code=='inactive'?'checked':''} >
                    <label class="form-check-label"><fmt:bundle basename="resource_fa">
                        <!--<fmt:message key="inactive"/>-->غیر فعال
                    </fmt:bundle></label>
                </div>
            </div>
        </div>
        <div class="form-row" dir="ltr">
            <div class=" col-md-3" style="margin-top: 17px;margin-bottom: 10px;">

                <button type="submit" class="btn btn-primary" style="width:90px;margin-bottom: 10px;background-color: #F4C34E;border: none;"><fmt:bundle basename="resource_fa">
                    <!--<fmt:message key="edit"/>-->ویرایش
                </fmt:bundle></button>
            </div>
        </div>
    </form>
</div>

</body>
</html>
