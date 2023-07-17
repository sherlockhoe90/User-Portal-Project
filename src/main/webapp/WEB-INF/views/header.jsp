<%--<%@ page language="java" contentType="text/html" pageEncoding="utf-8"%>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%--getting the parameter 'user' and saving it in the variable 'profile'--%>
<c:set var="profile" scope="request"
       value='<%=request.getParameter("user")%>'/>

<!DOCTYPE html>
<html>
<head>
    <%--<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">--%>
    <link rel="icon" href="./assets/images/inexture-favicon-purple.png" type="favicon">
    <link rel="stylesheet" href="../../assets/css/header_and_footer.css">
    <!-- bootstrap 3.3.7 cdn -->
    <link rel="stylesheet" href="./assets/css/maxcdn.bootstrapcdn.com_bootstrap_3.4.1_css_bootstrap.min.css">
    <link rel="stylesheet" href="./assets/css/style.css">
</head>

<body>
<nav class="navbar container8 container-fluid top-nav-centered">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header ">
            <%-- company logo (inexture solutions) --%>
            <img class="img-logo" src="assets/images/inexture-logo-black.png" alt="company logo"

            <%-- CODE FOR REGISTRATION LINK (ONLY VISIBLE ON THE LOGIN.JSP PAGE)--%>
            <%
                String callingPage = (String) request.getAttribute("javax.servlet.include.request_uri");
                boolean showRegistrationLink = callingPage != null && callingPage.endsWith("/login.jsp");
            %>

            <%-- work on this later --%>
            <%--            <button type="button" class="navbar-toggle collapsed"--%>
            <%--                    data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"--%>
            <%--                    aria-expanded="false">--%>
            <%--                <span class="sr-only">Toggle navigation</span> <span--%>
            <%--                    class="icon-bar"></span> <span class="icon-bar"></span> <span--%>
            <%--                    class="icon-bar"></span>--%>
            <%--            </button>--%>
            <%-- Login Page Title --%>
            <!-- Conditionally include registration link -->
            <% if (showRegistrationLink) { %>
            <a class="header-text" href="registration.jsp">Registration</a>
            <% } %>

                <c:choose>
                    <c:when test="${(profile == 'admin') || (profile == 'adminEdit')}">
                        <a class="header-text userSpecificHomeLinkOnHeader" href="adminHomePage.jsp">Admin Home</a>
                    </c:when>
                    <c:when test="${(profile == 'userEdit')}">
                        <a class="header-text userSpecificHomeLinkOnHeader" href="userHomePage.jsp">User Home</a>
                    </c:when>
                </c:choose>

            <a class="navbar-brand content-center col-sm-0 d-md-none d-sm-none headerprojectname" href="login.jsp">Portal Project</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse"
             id="bs-example-navbar-collapse-1"></div>
    </div>
</nav>

<script
        src="../../assets/js/CDNs/maxcdn.bootstrapcdn.com_bootstrap_3.3.7_js_bootstrap.min.js"></script>
</body>
</html>