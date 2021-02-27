
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <title>employee</title>
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
    <link
            rel="stylesheet"
            href="https://cdn.rtlcss.com/bootstrap/v4.2.1/css/bootstrap.min.css"
            integrity="sha384-vus3nQHTD+5mpDiZ4rkEPlnkcyTP+49BhJ4wJeJunw06ZAp+wzzeBPUXr42fi8If"
             crossorigin="anonymous">
	 
    <jsp:include page="employeeHeader.jsp"/>

</head>
<body>
<jsp:include page="body.jsp"/>
<c:if test="${requestScope['sendEmail']}">
    <div style="width: 450px;border-radius: 5px; margin: 10px auto;">
        <div class="alert alert-info" id="info-alert" style="background-color: #F0DCAC;border: none;">
            <button type="button" class="close" data-dismiss="alert">x</button>
            <strong><fmt:bundle basename="resource_fa">
                <!--<fmt:message key="email successfully sent"/></fmt:bundle>-->پیام با موفقیت ارسال شد.
            </strong>
        </div>
    </div>
</c:if>
</body>
</html>