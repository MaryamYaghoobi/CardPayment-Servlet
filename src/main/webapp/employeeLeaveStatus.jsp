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
    <title>وضعیت مرخصی</title>
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
    <link
            rel="stylesheet"
            href="https://cdn.rtlcss.com/bootstrap/v4.2.1/css/bootstrap.min.css"
            integrity="sha384-vus3nQHTD+5mpDiZ4rkEPlnkcyTP+49BhJ4wJeJunw06ZAp+wzzeBPUXr42fi8If"
            crossorigin="anonymous">
    <jsp:include page="employeeHeader.jsp"/>
</head>
<body dir="rtl">
<jsp:include page="body.jsp"/>
<c:choose>
<c:when test="${empty requestScope.leaveEmployeeList}">
    <div style="width: 400px;border-radius: 5px; margin: 10px auto;text-align: center;">
        <div class="alert alert-info" id="info-alert" style="background-color: #F0DCAC;border: none;">
            <button type="button" class="close" data-dismiss="alert"></button>
            <strong>
               درخواست مرخصی وجود ندارد.
           
            </strong>
        </div>
    </div>
</c:when>
<c:otherwise>
<div class="container" style="margin-top: 50px;border-radius: 6px;background-color: #F4C34E;">
    <table class="table table-bordered table-hover table-responsive-lg">
        <thead class="thead-light ">
        <tr style="border-radius: 10px;">
          
            <th class="text-center" scope="col">
                مرخصی از تاریخ
            </th>
            <th class="text-center" scope="col">
                 مرخصی تا تاریخ
            </th>
            <th class="text-center" scope="col">
                وضعیت مرخصی
           </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.leaveEmployeeList}" var="leaveEmployee">
            <tr>
                <td hidden><c:out value="${leaveEmployee.id}"/></td>               
                <td><c:out value="${leaves.leaveFromDate}"/></td>
                <td><c:out value="${leaves.leaveToDate}"/></td>
				<td><c:out value="${leaves.reason}"/></td>
                <td><c:out value="${leaves.searchLeave.name}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </c:otherwise>
    </c:choose>
</div>
</body>
</html>
