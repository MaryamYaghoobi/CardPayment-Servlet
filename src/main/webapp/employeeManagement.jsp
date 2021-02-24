
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <jsp:include page="managerHeader.jsp"/>
    <title>مدیریت کاربران</title>
</head>
<body dir="rtl">
<jsp:include page="body.jsp"/>
<script>
    function inactiveEmployee(employeeId) {
        if (confirm('<fmt:bundle basename="resource_fa"><fmt:message key="inactivated employee"/></fmt:bundle>')) {
            window.location = 'ManagerController?action=inactive&employeeId=' + employeeId;
        }
    }

    function findEmployee(employeeId) {
        window.location = 'ManagerController?action=editAndAppointmentOfManager&employeeId=' + employeeId;
    }
</script>

<div class="container" style="margin-top: 50px;border-radius: 6px;background-color: deepskyblue;">
    <form action="ManagerController" method="post">
        <div>
            <div class="form-row">
                <input hidden type="hidden" name="action" value="search">
                <div class="form-group col-md-3">
                    <label for="firstName" style="font-weight: 600;"><fmt:bundle basename="resource_fa">
                        <fmt:message key="firstName"/>
                    </fmt:bundle></label>
                    <input type="text" name="firstName" class="form-control" id="firstName"
                           value="${requestScope.firstName}"
                           placeholder='<fmt:bundle basename="resource_fa">
                        <fmt:message key="firstName"/>
                    </fmt:bundle>'>
                </div>
                <div class="form-group col-md-3">
                    <label for="lastName" style="font-weight: 600;"><fmt:bundle basename="resource_fa">
                        <fmt:message key="lastName"/>
                    </fmt:bundle></label>
                    <input type="text" name="lastName" class="form-control" id="lastName"
                           value="${requestScope.lastName}"
                           placeholder='<fmt:bundle basename="resource_fa">
                        <fmt:message key="lastName"/>
                    </fmt:bundle>'>
                </div>


                <div class="form-group col-md-3">
                    <label for="username" style="font-weight: 600;"><fmt:bundle basename="resource_fa">
                        <fmt:message key="username"/>
                    </fmt:bundle></label>
                    <input type="text" name="username" class="form-control" id="username"
                           value="${requestScope.username}"
                           placeholder='<fmt:bundle basename="resource_fa">
                        <fmt:message key="username"/>
                    </fmt:bundle>'>
                </div>
               
            </div>
            <div>

                <button type="submit" class="btn btn-lg btn-block btn btn btn-info " style="margin-bottom: 10px;"> <span
                        class="fa fa-search"><fmt:bundle basename="resource_fa">
                    <fmt:message key="search"/>
                </fmt:bundle></span></button>
            </div>
        </div>
    </form>


    <table class="table table-bordered table-hover table-responsive-lg">
        <thead class="thead-light ">
        <tr style="border-radius: 10px;">
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <fmt:message key="firstName"/>
            </fmt:bundle></th>
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <fmt:message key="lastName"/>
            </fmt:bundle></th>
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <fmt:message key="manager"/>
            </fmt:bundle></th>
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <fmt:message key="role"/>
            </fmt:bundle></th>
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <fmt:message key="status"/>
            </fmt:bundle></th>
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <fmt:message key="action"/>
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
                            style="width: 82px;" onclick="findEmployee(${employee.id})"><span
                            class="fa fa-edit"> <fmt:bundle basename="resource_fa">
                        <fmt:message key="edit"/>
                    </fmt:bundle></span></button>
                    <button type="button"
                            class="btn btn-danger btn-rounded btn-lm my-0 badge-pill " value="delete"
                            style="width: 80px;margin-right:10px;" onclick="inactiveEmployee(${employee.id})"><span
                            class="fa fa-trash"> <fmt:bundle basename="resource_fa">
                        <fmt:message key="delete"/>
                    </fmt:bundle> </span></button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
