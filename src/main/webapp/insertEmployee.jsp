<%--
  Created by IntelliJ IDEA.
  User: M.yaghoobi
  Date: 28/2/2021
  Time: 3:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdn.rtlcss.com/bootstrap/v4.2.1/js/bootstrap.min.js"
            integrity="sha384-a9xOd0rz8w0J8zqj1qJic7GPFfyMfoiuDjC9rqXlVOcGO/dmRqzMn34gZYDTel8k"
            crossorigin="anonymous"></script>
    <script src="resources/js/jquery.min.js"></script>
    <script src="resources/js/jquery.validate.min.js"></script>
    <link
            rel="stylesheet"
            href="https://cdn.rtlcss.com/bootstrap/v4.2.1/css/bootstrap.min.css"
            integrity="sha384-vus3nQHTD+5mpDiZ4rkEPlnkcyTP+49BhJ4wJeJunw06ZAp+wzzeBPUXr42fi8If"
            crossorigin="anonymous"
    >
    <title>افزودن کاربر</title>

    <jsp:include page="managerHeader.jsp"/>
</head>
<body dir="rtl">
<jsp:include page="body.jsp"/>
<c:if test="${sessionScope['invalidUsername']}">

    <div style="width: 300px;border-radius: 5px; margin: 10px auto;">
        <div class="alert alert-success" id="success-alert">
            <button type="button" class="close" data-dismiss="alert">x</button>
            <strong>
               نام کاربری تکراری است.
            </strong>
        </div>
    </div>
</c:if>


<div class="container" name="container"
     style="background-color: #F0E1BE; width: 700px; margin-top: 55px;border-radius: 5px;">
    <form action="ManagerController" method="post" id="saveForm">
        <input type="hidden" name="action" value="addUser">
        <div class="form-row">
            <div class="form-group col-md-4" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label>
                    نام
                </label>
                <input type="text" class="form-control" id="firstName" name="firstName"
                       placeholder="نام">
            </div>
            <div class="form-group col-md-4" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label>
                    نام خانوادگی
					</label>
                <input type="text" class="form-control" id="lastName" name="lastName" placeholder="نام خانوادگی">
            </div>
            <div class="form-group col-md-4" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label>
                    نام پدر
                </label>
                <input type="text" class="form-control" id="fatherName" name="fatherName"
                       placeholder="نام پدر" >
                         
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-4" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label>
                   پست الکترونیک
					</label>
                <input type="email" name="email" class="form-control" id="email" placeholder="پست الکترونیک" >
            </div>
            <div class="form-group col-md-4" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label>
                  نام کاربری
                </label>
                <input type="text" class="form-control" id="username" name="username" placeholder="نام کاربری">
                                                  
            </div>
            <div class="form-group col-md-4" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label>
                   رمز عبور
                </label>
                <input type="password" class="form-control" id="password" name="password" placeholder="رمز عبور">                                  
                
            </div>
        </div>


        <div class="form-row">
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label>
                    تاریخ تولد
                </label>
                <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth" placeholder="تاریخ تولد">
                  
            </div>
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label><label style="color: #ff4626">*</label>
                    انتخاب مدیر
                </label>
                <select name='selectedManager' id="selectedManager" class="form-control">
                    <c:forEach items="<%=request.getAttribute(\"managerList\") %>" var="manager">
                        <option value="<c:out value="${manager}"/>"><c:out value="${manager}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label>
                    جنسیت
                </label>

                <div class="form-check form-check-inline">
                    <input type="radio" class="form-check-input" id="male" name="gender" value="male" checked>
                    <label class="form-check-label">
                       مرد
                    </label>
                </div>


                <div class="form-check form-check-inline">
                    <input type="radio" class="form-check-input" id="female" name="gender" value="female">
                    <label class="form-check-label">
                        زن
                    </label>
                </div>
            </div>
            <div class="form-group col-md-6" style="margin-top: 17px;">
                <label>
                    وضعیت
                </label>
                <div class="form-check form-check-inline">
                    <input type="radio" class="form-check-input" id="active" name="employeeStatus" value="active"
                           checked>
                    <label class="form-check-label">
                        فعال
                    </label>
                </div>


                <div class="form-check form-check-inline">
                    <input type="radio" class="form-check-input" id="inactive" name="employeeStatus" value="inactive">
                    <label class="form-check-label">
                        غیر فعال
                   </label>
                </div>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-8" style="margin-top: 17px;">
                <label>
                     نقش
                 </label>
<br>
                <div class="form-check form-check-inline">
                    <input type="radio" class="form-check-input" id="manager" name="post" value="manager" checked>
                    <label class="form-check-label">
                       مدیر
                    </label>
                </div>
<br>

                <div class="form-check form-check-inline">
                    <input type="radio" class="form-check-input" id="programmer" name="post" value="programmer">
                    <label class="form-check-label">
                        برنامه نویس
                    </label>
                </div>
				<br>
                <div class="form-check form-check-inline">
                    <input type="radio" class="form-check-input" id="tester" name="post" value="tester">
                    <label class="form-check-label">
                       آزمونگر
                    </label>
                </div>
				<br>
				 <div class="form-check form-check-inline">
                    <input type="radio" class="form-check-input" id="analyzer" name="post" value="analyzer">
                    <label class="form-check-label">
                      تحلیلگر
                    </label>
                </div>
				<br>
				  <div class="form-check form-check-inline">
                    <input type="radio" class="form-check-input" id="technicalSupport" name="post" value="technicalSupport">
                    <label class="form-check-label">
                       پشتیبان فنی
                    </label>
                </div>
				<br>
            </div>

            <div class=" col-md-4" style="margin-top: 17px;"><br><br><br><br>
                <button type="submit" class="btn btn-primary"style="background-color: #F4C34E;border: none;">
                    ثبت اطلاعات
              </button>
            </div>
        </div>
    </form>

</div>

</body>
</html>
