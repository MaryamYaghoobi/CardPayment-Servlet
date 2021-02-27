
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <title>مدیریت مرخصی</title>
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
    <link
            rel="stylesheet"
            href="https://cdn.rtlcss.com/bootstrap/v4.2.1/css/bootstrap.min.css"
            integrity="sha384-vus3nQHTD+5mpDiZ4rkEPlnkcyTP+49BhJ4wJeJunw06ZAp+wzzeBPUXr42fi8If"
            crossorigin="anonymous">
    <jsp:include page="managerHeader.jsp"/>
    <script>
        function LeaveConfirmation(leaveId) {
            window.location = 'ManagerController?action=LeaveConfirmation&leaveId=' + leaveId;

        }

        function rejectLeave(leaveId) {
            if (confirm("رد درخواست")) {
                window.location = 'ManagerController?action=rejectLeave&leaveId=' + leaveId;
            }
        }
    </script>
</head>
<body dir="rtl">
<jsp:include page="body.jsp"/>
<c:choose>
<c:when test="${empty requestScope.RegisteredLeaves}">
    <div style="width: 400px;border-radius: 5px; margin: 10px auto;text-align: center;">
        <div class="alert alert-info" id="info-alert"style="background-color: #F0DCAC;border: none;">
            <button type="button" class="close" data-dismiss="alert">x</button>
            <strong><fmt:bundle basename="resource_fa">
                <!--<fmt:message key="no leave request"/>-->درخواست مرخصی وجود ندارد.
            </fmt:bundle>
            </strong>
        </div>
    </div>
</c:when>
<c:otherwise>
<div class="container" style="margin-top: 50px;border-radius: 6px;background-color: #F0E1BE;">
    <table class="table table-bordered table-hover table-responsive-lg">
        <thead class="thead-light ">
        <tr style="border-radius: 10px;">
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <!--<fmt:message key="firstName"/>-->نام
            </fmt:bundle></th>
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <!--<fmt:message key="lastName"/>-->نام خانوادگی
            </fmt:bundle></th>
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <!--<fmt:message key="leaveFromDate"/>-->از تاریخ
            </fmt:bundle></th>
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <!--<fmt:message key="leaveToDate"/>-->تا تاریخ
            </fmt:bundle></th>
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <!--<fmt:message key="leave status"/>-->وضعیت مرخصی
            </fmt:bundle></th>
            <th class="text-center" scope="col"><fmt:bundle basename="resource_fa">
                <!--<fmt:message key="action"/>-->عملیات
            </fmt:bundle></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.RegisteredLeaves}" var="Employee">
            <c:forEach items="${Employee.leaveList}" var="leaves">
                <tr>
                    <td hidden><c:out value="${leave.id}"/></td>
                    <td><c:out value="${Employee.firstName}"/></td>
                    <td><c:out value="${Employee.lastName}"/></td>
                    <td><c:out value="${leave.leaveFromDate}"/></td>
                    <td><c:out value="${leave.leaveToDate}"/></td>
                    <td><c:out value="${leave.leaveStatus.name}"/></td>
                    <td class="text-right" style="width: 21%;">
                        <button type="button"
                                class="btn btn-primary btn-rounded btn-lm my-0 badge-pill " value="update"
                                style="width: 82px;background-color: #F4C34E;border: none;" onclick="acceptLeave(${leave.id})"><span
                                class="fa fa-check"> <fmt:bundle basename="resource_fa">
                            <!--<fmt:message key="accept"/>-->تایید شده
                        </fmt:bundle></span></button>
                        <button type="button"
                                class="btn btn-danger btn-rounded btn-lm my-0 badge-pill " value="delete"
                                style="width: 80px;margin-right:10px;background-color: #F4C34E;border: none;" onclick="rejectLeave(${leave.id})"><span
                                class="fa fa-times"> <fmt:bundle basename="resource_fa">
                            <!--<fmt:message key="reject"/>--> رد شده
                        </fmt:bundle> </span></button>
                    </td>
                </tr>
            </c:forEach>
        </c:forEach>
        </tbody>
    </table>
    </c:otherwise>
    </c:choose>
</div>
</body>
</html>
