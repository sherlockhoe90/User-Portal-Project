<%--
  Created by IntelliJ IDEA.
  User: ND
  Date: 01/07/2023
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
<%
    // Set cache control directives to prevent caching of the page
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // Proxies
%>
<c:set var="authStatus" scope="request" value='<%=request.getParameter("authenticationStatus")%>'/>
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

</head>
<body>

<%@ include file="./WEB-INF/views/header.jsp" %> <%--adding the HEADER file here--%>
<div class="container center-div">
    <c:choose>
    <c:when
            test="${(authStatus == 'wrong')}">
        <h2 class="text-center text-danger">The Email-ID or password you entered is wrong!</h2>
    </c:when>
    <c:otherwise>
        <h2 class="text-center">WELCOME from webapp!</h2>
    </c:otherwise>
    </c:choose>
    <p class="text-center">Please login to access your account</p>
    <form action="${pageContext.request.contextPath}/UserLogin" method="post">
        <input type="hidden" id="pageIdentification" value="comingFromLoginPage"> <%--hidden field--%>
        <div class="row justify-content-center text-white">
            <div class="form-group col-sm-4 col-sm-offset-4 ">
                <input type="email" class="form-control col-sm" id="emailid_from_login" name="emailid_from_login" placeholder="Email-ID" required>
                <span id="email_error"></span>
            </div>
        </div>
        <div class="row justify-content-center text-white">
            <div class="form-group col-sm-4 col-sm-offset-4 ">
                <input type="password" class="form-control col-sm" id="password_from_login" name="password_from_login" placeholder="Password" required>
                <span id="password_error"></span>
            </div>
        </div>
        <div class="row justify-content-center text-center">
            <div class="form-group col-sm-4 col-sm-offset-4">
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


