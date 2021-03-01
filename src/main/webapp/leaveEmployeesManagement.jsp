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
        function LeaveConfirmation(leavesId) {
            window.location = 'ManagerController?action=LeaveConfirmation&leavesId=' + leavesId;

        }

        function rejectLeave(leavesId) {
            if (confirm("رد درخواست")) {
                window.location = 'ManagerController?action=rejectLeave&leavesId=' + leavesId;
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
            <button type="button" class="close" data-dismiss="alert"></button>
            <strong>
                درخواست مرخصی وجود ندارد.
           
            </strong>
        </div>
    </div>
</c:when>
<c:otherwise>
<div class="container" style="margin-top: 50px;border-radius: 6px;background-color: #F0E1BE;">
    <table class="table table-bordered table-hover table-responsive-lg">
        <thead class="thead-light ">
        <tr style="border-radius: 10px;">
            <th class="text-center" scope="col">
                نام
            </th>
            <th class="text-center" scope="col">
               نام خانوادگی
            </th>
            <th class="text-center" scope="col">
                از تاریخ
           </th>
            <th class="text-center" scope="col">
                تا تاریخ
            </th>
            <th class="text-center" scope="col">
               وضعیت مرخصی
            </th>
            <th class="text-center" scope="col">
                عملیات
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.RegisteredLeaves}" var="Employee">
            <c:forEach items="${Employee.leaveList}" var="leaves">
                <tr>
                    <td hidden><c:out value="${leave.id}"/></td>
                    <td><c:out value="${Employee.firstName}"/></td>
                    <td><c:out value="${Employee.lastName}"/></td>
                    <td><c:out value="${leaves.leaveFromDate}"/></td>
                    <td><c:out value="${leaves.leaveToDate}"/></td>
					<td><c:out value="${leaves.reason}"/></td>
                    <td><c:out value="${leaves.searchLeave.name}"/></td>
                    <td class="text-right" style="width: 21%;">
                        <button type="button"
                                class="btn btn-primary btn-rounded btn-lm my-0 badge-pill " value="update"
                                style="width: 82px;background-color: #F4C34E;border: none;" onclick="LeaveConfirmation(${leaves.id})"><span
                                class="fa fa-check"> 
                            تایید
                        </span></button>
                        <button type="button"
                                class="btn btn-danger btn-rounded btn-lm my-0 badge-pill " value="delete"
                                style="width: 80px;margin-right:10px;background-color: #F4C34E;border: none;" onclick="rejectLeave(${leaves.id})"><span
                                class="fa fa-times"> 
                             رد
                         </span></button>
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
