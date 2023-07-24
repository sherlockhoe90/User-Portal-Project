<%--
  Created by IntelliJ IDEA.
  User: ND
  Date: 01/07/2023
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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


