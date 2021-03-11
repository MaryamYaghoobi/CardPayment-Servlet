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
<title> پیام های ارسالی</title>
<!--===============================================================================================-->	
<link rel="stylesheet" href="https://cdn.rtlcss.com/bootstrap/v4.2.1/css/bootstrap.min.css" 
integrity="sha384-vus3nQHTD+5mpDiZ4rkEPlnkcyTP+49BhJ4wJeJunw06ZAp+wzzeBPUXr42fi8If" crossorigin="anonymous">
<!--===============================================================================================-->				
    <jsp:include page="employeeHeader.jsp"/>
</head>
<body dir="rtl">
<jsp:include page="body.jsp"/>

<c:choose>
 <c:when test="${empty requestScope.sentMessages}">
        <div style="width: 400px;border-radius: 5px; margin: 10px auto;text-align: center;">
            <div class="alert alert-info">
                <button type="button" class="close" data-dismiss="alert">x</button>
                <strong>پیام
                </strong>
            </div>
        </div>
    </c:when>
    <c:otherwise>
<div class="container" style="margin-top: 50px;border-radius: 6px;background-color: #F0E1BE;">
<br>
    <span style="font-weight: bold;font-size: 17px;color:darkblue">لیست پیام های ارسال شده</span>
    <table class="table table-bordered table-hover table-responsive-lg">
        <thead class="thead-light ">
		<br><br>
        <tr style="border-radius: 10px;">
          
            <th class="text-center" scope="col" style="background-color:#F4C34E;">نام دریافت کننده</th>
            <th class="text-center" scope="col" style="background-color:#F4C34E;">نام خانوادگی دریافت کننده</th>
            <th class="text-center" scope="col" style="background-color:#F4C34E;">متن پیام ارسال شده</th>
			<th class="text-center" scope="col" style="background-color:#F4C34E;">دلیل</th>
            <th class="text-center" scope="col" style="background-color:#F4C34E;">فایل پیوست</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.sentMessages}" var="detailsMessagesSent">
            <tr>
                <td hidden><c:out value="${detailsMessagesSent[0]}"/></td>
                <td><c:out value="${detailsMessagesSent[1]}"/></td>
                <td><c:out value="${detailsMessagesSent[2]}"/></td>
                <td><c:out value="${detailsMessagesSent[3]}"/></td>
                <td><c:out value="${detailsMessagesSent[4]}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${empty detailsMessagesSent[5]}">
                            <span>فایلی پیوست نشده است</span>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="btn btn-primary btn-lm my-0 badge-pill"
                                    style="width: 110px; border-radius: 7px; align-self: center;"
                                    onclick="downloadAttachment('${detailsMessagesSent[5]}')">ارسال فایل</button>                            
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </c:otherwise>
    </c:choose>
</div>
<!--===============================================================================================================================-->	
<script>function downloadAttachment(fileName) {window.location = 'EmployeeController?action=downloadAttachment&fileName=' + fileName; }</script>   
<!--================================================================================================================================-->		
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<!--================================================================================================================================-->		
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<!--================================================================================================================================-->		
</body>
</html>
