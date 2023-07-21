<%--
  Created by IntelliJ IDEA.
  User: ND
  Date: 01/07/2023
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>

<%--using this JAVA code to not let them go to the Login.jsp page when they're logged in and the session is valid--%>
<%
    String userRole = (String) session.getAttribute("userRole");
    if (userRole != null) {
        // If the session attribute is set, redirect the user to the appropriate homepage
        if (userRole.equals("user")) {
            response.sendRedirect("userHomePage.jsp");
        } else if (userRole.equals("admin")) {
            response.sendRedirect("adminHomePage.jsp");
        }
    }
%>

<!-- Your regular login page content goes here -->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page for Users</title>
    <link rel="icon" href="assets/images/inexture-favicon-purple.png" type="image/x-icon">
    <link rel="stylesheet" href="./assets/css/header_and_footer.css"> <%--custom css for login--%>
    <link rel="stylesheet" href="./assets/css/CDN/bootstrap_3.3.7.css"> <%--bootstrap css--%>
    <link rel="stylesheet" href="./assets/css/css_login.css"> <%--custom css for login--%>
    <script type="text/javascript" src="assets/js/loginValidation.js"></script>
    <script>
        // Check if the page is loaded from the cache
        if (performance.navigation.type === 2) {
            // Page is loaded from the cache, redirect to the login page
            window.location.href = 'login.jsp';
        }
    </script>
    <%--PREVENT BACK BUTTON every single time the page is opened, to prevent using a CACHED COPY--%>
    <head>
<%--        <script>--%>
<%--            function disableBackButton() {--%>
<%--                window.history.replaceState(null, "", window.location.href);--%>
<%--                window.onpopstate = function (event) {--%>
<%--                    window.history.go(1);--%>
<%--                };--%>
<%--            }--%>
<%--        </script>--%>
    </head>
</head>
<body>

<%@ include file="./WEB-INF/views/header.jsp" %> <%--adding the HEADER file here--%>
<div class="container center-div">
    <h2 class="text-center">WELCOME from webapp!</h2>
    <p class="text-center">Please login to access your account</p>
    <form action="${pageContext.request.contextPath}/UserLogin" method="post">
        <input type="hidden" id="pageIdentification" value="comingFromLoginPage"> <%--hidden field--%>
        <div class="row justify-content-center text-white">
            <div class="form-group col-sm-4 col-sm-offset-4 ">
                <input type="text" class="form-control col-sm" id="emailid_from_login" name="emailid_from_login" placeholder="Email-ID">
                <span id="email_error"></span>
            </div>
        </div>
        <div class="row justify-content-center text-white">
            <div class="form-group col-sm-4 col-sm-offset-4 ">
                <input type="password" class="form-control col-sm" id="password_from_login" name="password_from_login" placeholder="Password">
                <span id="password_error"></span>
            </div>
        </div>
        <div class="row justify-content-center text-center">
            <div class="form-group col-sm-4 col-sm-offset-4">
<%--                <a href="registeration.html" class="btn" role="button" aria-pressed="true">LOGIN</a>--%>
                <button type="submit" class="btn" value="LOGIN">LOGIN</button>
            </div>
        </div>
        <p class="text-center">New user? You can
        <a href="registration.jsp">Register here!</a>
        </p>
        <br>
        <p class="text-center">Forgot Password? You can get a new one
            <a href="forgotPassword.jsp">here!</a>
        </p>

    </form>
</div>
</body>
</html>

<%--code for the footer--%>
<jsp:include page="./WEB-INF/views/footer.jsp"/>


