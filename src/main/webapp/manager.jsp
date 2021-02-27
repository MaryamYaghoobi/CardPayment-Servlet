
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
