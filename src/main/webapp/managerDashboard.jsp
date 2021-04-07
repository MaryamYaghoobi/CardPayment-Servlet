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
    <title>manager</title>
<jsp:include page="managerHeader.jsp"/>

</head>
<body>
<jsp:include page="body.jsp"/>
<% if (session.getAttribute("username") == null) {
    response.sendRedirect("login.jsp");
}
%>
</body>
</html>
