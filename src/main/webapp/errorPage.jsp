<%--
  Created by IntelliJ IDEA.
  User: ND
  Date: 23/07/2023
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String userRole = (String) session.getAttribute("userRole");
    /*the code is implemented below further inside this code file, all of it is written in scriptlets '< % % >'*/
    /* when userRole is null, only the plain old registration fields will be shown */
    /* when the userRole has a role logged in, it'll rely on the Filter in order to authorize access */
%>
<c:set var="role" scope="session" value="${sessionScope.userRole}"/>

<!-- Your regular login page content goes here -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error <%= response.getStatus() %></title>
    <link rel="icon" href="assets/images/inexture-favicon-purple.png" type="image/x-icon">
    <link rel="stylesheet" href="assets/css/header_and_footer.css"> <%--custom css for login--%>
    <link rel="stylesheet" href="assets/css/CDN/bootstrap_3.3.7.css"> <%--bootstrap css--%>
    <link rel="stylesheet" href="assets/css/css_login.css"> <%--custom css for login--%>
</head>
<body>

<%@ include file="WEB-INF/views/header.jsp" %> <%--adding the HEADER file here--%>
<div class="container center-div">
    <h1 class="text-center text-danger">Error <%= response.getStatus() %>!</h1>
    <h3 class="text-center text-info">Uh oh! The error <%= response.getStatus() %> has occurred. </h3>
    <br><br>
    <p class="text-center">Please click below and continue your journey!</p>
    <br>
    <c:choose>
        <c:when
                test="${(role == 'admin') || (role == 'user')}">
            <h5 class="text-center">Head back to <a onclick="location.href='login.jsp';" class="redirectToLogin">Home-Page Dashboard</a>!</h5>
        </c:when>
        <c:otherwise>
            <h5 class="text-center">Head back to <a onclick="location.href='login.jsp';" class="redirectToLogin">Login</a>!</h5>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>

<%--code for the footer--%>
<jsp:include page="WEB-INF/views/footer.jsp"/>