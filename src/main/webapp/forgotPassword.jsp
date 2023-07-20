<%@ page import="com.inexture.userportal.userportalproject.model.User" %><%--
  Created by IntelliJ IDEA.
  User: ND
  Date: 14/07/2023
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="./WEB-INF/views/header.jsp" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="emailStatus" scope="request"
       value='<%=request.getParameter("emailExists")%>'/>

<%
    User user = (User) session.getAttribute("CurrentUser");
%>

<c:choose>
    <c:when test="${(emailStatus == 'exists')}">
        <c:set var="user" scope="session" value="${sessionScope.CurrentUser}"/>
    </c:when>
</c:choose>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password</title>
    <link rel="icon" href="./assets/images/inexture-favicon-purple.png" type="image/x-icon">
    <link rel="stylesheet" href="./assets/css/header_and_footer.css">
    <%--custom css for login--%>
    <link rel="stylesheet" href="./assets/css/maxcdn.bootstrapcdn.com_bootstrap_3.4.1_css_bootstrap.min.css">
    <%--bootstrap css--%>
    <link rel="stylesheet" href="./assets/css/css_login.css">
    <%--custom css for login--%>
    <script type="text/javascript" src="./assets/js/loginValidation.js"></script>
    <script type="text/javascript" src="./assets/js/customValidation.js"></script>
    <script type="text/javascript" src="./assets/js/forgotPasswordValidation.js"></script>

    <script>
        // Check if the page is loaded from the cache
        if (performance.navigation.type === 2) {
            // Page is loaded from the cache, redirect to the login page
            window.location.href = 'login.jsp';
        }
    </script>
</head>
<body>
<%--adding the HEADER file here--%>
<div class="container center-div">
    <%--    <h2 class="text-center">Enter you e-mail id:</h2>--%>
    <%--    <p class="text-center">You'll be sent a code that you'll have to enter here.</p>--%>
    <%--    <form action="${pageContext.request.contextPath}/ForgotPassword" method="post">--%>
    <%--        <input type="hidden" id="pageIdentification" value="comingFromForgotPassword"> &lt;%&ndash;hidden field&ndash;%&gt;--%>
    <%--        <div class="row justify-content-center text-white">--%>
    <%--            <div class="form-group col-sm-4 col-sm-offset-4 ">--%>
    <%--                <input type="text" class="form-control col-sm" id="emailid_from_login"--%>
    <%--                       name="emailid_from_forgotpassword" placeholder="Email-ID">--%>
    <%--                <span id="email_error"></span>--%>
    <%--            </div>--%>
    <%--        </div>--%>
    <c:choose> <%--initially the <otherwise> code block will get executed --%>
    <c:when test="${(emailStatus == 'exists')}"> <%--execute when it is found that email exists, enter the verification code--%>
    <h2 class="text-center">Enter the Verification Code :</h2>
    <p class="text-center">You have been sent a code on your e-mail id that you have to enter below.</p>
    <form action="${pageContext.request.contextPath}/ForgotPassword" method="get">
        <input type="hidden" id="pageIdentification" name="pageIdentification"
               value="sendingVerificationCode"> <%--hidden field--%>
        <div class="row justify-content-center text-white">
            <div class="form-group col-sm-4 col-sm-offset-4 ">
                <input type="text" class="form-control col-sm" id="emailid"
                       name="emailid" placeholder="Email-ID" value="${user.userEmailID}"
                       pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"
                       readonly required>
                <span id="email_error"></span>
            </div>
        </div>
        <div class="row justify-content-center text-white">
            <div class="form-group col-sm-4 col-sm-offset-4 ">
                <input type="password" class="form-control col-sm" id="pwd" name="verificationcode"
                       placeholder="Enter Verification Code">
                <span id="password_error"></span>
            </div>
        </div>
        <div class="row justify-content-center text-center">
            <div class="form-group col-sm-4 col-sm-offset-4">
                    <%--                <a href="registeration.html" class="btn" role="button" aria-pressed="true">LOGIN</a>--%>
                <button type="submit" class="btn" value="RESETPASSWORD">Reset Password</button>
            </div>
        </div>
        </c:when>
        <c:when test="${(emailStatus == 'doesNotExist')}"> <%--executed when email does not exist in the database, ask for it again--%>

        <h2 class="text-center text-danger">The e-mail you entered doesn't have an account associated with it.</h2>
        <p class="text-center">Enter a valid e-mail.</p>
        <p class="text-center">You'll be sent a code that you'll have to enter here.</p>
        <form action="${pageContext.request.contextPath}/ForgotPassword" method="get">
            <input type="hidden" id="pageIdentification" name="pageIdentification"
                   value="emailDoesntExist"> <%--hidden field--%>
            <div class="row justify-content-center text-white">
                <div class="form-group col-sm-4 col-sm-offset-4 ">
                    <input type="text" class="form-control col-sm" id="emailid"
                           name="emailid" placeholder="Email-ID"
                           pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" required>
                    <span id="email_error"></span>
                </div>
            </div>
            <div class="row justify-content-center text-center">
                <div class="form-group col-sm-4 col-sm-offset-4">
                        <%--                <a href="registeration.html" class="btn" role="button" aria-pressed="true">LOGIN</a>--%>
                    <button type="submit" class="btn" value="VERIFICATIONCODE">Get Verification Code</button>
                </div>
            </div>
            </c:when>
            <c:when test="${(emailStatus == 'getNewPassword')}"> <%--executed when the verification code was Correct, enter New Password--%>
            <h2 class="text-center">Enter your New Password :</h2>
            <p class="text-center">Enter you new password below.</p>
            <form action="${pageContext.request.contextPath}/ForgotPassword" method="post">
                <input type="hidden" id="pageIdentification" name="pageIdentification"
                       value="sendingNewPassword"> <%--hidden field--%>
                <div class="row justify-content-center text-white">
                    <div class="form-group col-sm-4 col-sm-offset-4 ">
                        <input type="text" class="form-control col-sm" id="emailid"
                               name="emailid" placeholder="Email-ID" value="${user.userEmailID}"
                               pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"
                               readonly required>
                        <span id="email_error"></span>
                    </div>
                </div>
                <div class="row justify-content-center text-white">
                    <div class="form-group col-sm-4 col-sm-offset-4 ">
                        <label for="pwd">New Password:</label>
                        <input type="password" class="form-control col-sm" id="pwd" name="newpassword"
                               placeholder="Enter New Password" required>
                        <span id="password_error"></span>
                    </div>
                </div>
                <div class="row justify-content-center text-white">
                    <div class="form-group col-sm-4 col-sm-offset-4 ">
                        <label for="cpwd">Confirm Password:</label>
                        <input type="password" class="form-control text-width" id="cpwd" name="cpwd"
                               placeholder="Confirm-New Password" required/>
                        <span id="confirm_password_error"></span>
                    </div>
                </div>
                <div class="row justify-content-center text-center">
                    <div class="form-group col-sm-4 col-sm-offset-4">
                            <%--                <a href="registeration.html" class="btn" role="button" aria-pressed="true">LOGIN</a>--%>
                        <button type="submit" class="btn" value="RESETPASSWORD">Reset Password</button>
                    </div>
                </div>
                </c:when>
                <c:when test="${(emailStatus == 'passwordChangedSuccessfully')}"> <%--executed password is changed and the process is over--%>
                <h2 class="text-center text-success">Password Changed!</h2>
                <br>
                <p class="text-center">You can now head back to login/register.</p>
                <br><br><br><br>
                </c:when>
                <c:otherwise>
                <h2 class="text-center">Enter your e-mail id:</h2>
                <p class="text-center">You'll be sent a code that you'll have to enter here.</p>
                <form action="${pageContext.request.contextPath}/ForgotPassword" method="get">
                    <input type="hidden" id="pageIdentification" name="pageIdentification"
                           value="getVerificationCode"> <%--hidden field--%>
                    <div class="row justify-content-center text-white">
                        <div class="form-group col-sm-4 col-sm-offset-4 ">
                            <input type="text" class="form-control col-sm" id="emailid"
                                   name="emailid" placeholder="Email-ID"
                                   pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" required>
                            <span id="email_error"></span>
                        </div>
                    </div>
                    <div class="row justify-content-center text-center">
                        <div class="form-group col-sm-4 col-sm-offset-4">
                                <%--                <a href="registeration.html" class="btn" role="button" aria-pressed="true">LOGIN</a>--%>
                            <button type="submit" class="btn" value="VERIFICATIONCODE">Get Verification Code
                            </button>
                        </div>
                    </div>
                    </c:otherwise>
                    </c:choose>

                    <p class="text-center">New user? You can
                        <a href="./registration.jsp">Register here!</a>
                    </p>
                    <br>
                    <p class="text-center">Already have an account? You can Log-In
                        <a href="./login.jsp">here!</a>
                    </p>

                </form>
</div>
</body>
</html>

<%--code for the footer--%>
<jsp:include page="./WEB-INF/views/footer.jsp"/>


