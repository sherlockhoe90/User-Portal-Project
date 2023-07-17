<jsp:useBean id="specificUserData" scope="session" type="com.inexture.userportal.userportalproject.model.User"/>
<%--
  Created by IntelliJ IDEA.
  User: ND
  Date: 09/07/2023
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.inexture.userportal.userportalproject.model.User"%>
<%@ page import="com.inexture.userportal.userportalproject.model.Address"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="WEB-INF/views/header.jsp"></jsp:include>

<!DOCTYPE html>
<html>
<head>
    <!--  <script type="text/javascript">
       function preventBack(){window.history.forward();}
        setTimeout("preventBack()", 0);
        window.onunload=function(){null};
    </script>  -->
    <%--CODE FOR NOT LETTING THEM GO BACK TO THIS PAGE AFTER USER/ADMIN HAS LOGGED OUT--%>
    <%--MOST IMPORTANT--%>
    <script>
        // Check if the page is loaded from the cache
        const navigationEntries = window.performance.getEntriesByType("navigation");
        if (navigationEntries.length && navigationEntries[0].type === "back_forward") {
            // Page is loaded from the cache, redirect to the login page
            window.location.href = "login.jsp";
        }

    </script>

    <meta charset="ISO-8859-1">
    <title>User Home-Page</title>
    <link rel="icon" href="assets/images/inexture-favicon-purple.png" type="image/x-icon">
    <link rel="stylesheet"
          href="./assets/css/CDN/bootstrap_3.3.7.css">
<%--    <link rel="stylesheet"--%>
<%--          href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css">--%>
    <link rel="stylesheet"
          href="./assets/css/searchjquery.dataTables.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <style>
        /*table {*/
        /*    background-color: #626262 !important;*/
        /*    color: white;*/
        /*    font-size: 17px;*/
        /*}*/

        table.table-bordered {
            border: 2px solid black;
            margin-top: 20px;
            margin-bottom: 100px;
        }

        /*table.table-bordered>tbody>tr>td {*/
        /*    border: 1px solid black;*/
        /*}*/

        td, h2 {
            text-align: center;
        }
    </style>
</head>

<body class="bg_custom_color">
 	<c:set var="user" scope="session" value="${sessionScope.specificUserData}" />
    <nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed"
                    data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
                    aria-expanded="false">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <a class="navbar-brand whitecolor" href="#">Home</a>
        </div>
        <div class="collapse navbar-collapse"
             id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <!-- <li><a href="#">View Profile<span
                        class="sr-only">(View Profile)</span></a> -->
                <li><a href="registration.jsp?user=userEdit">Edit My Profile<span
                        class="sr-only">(Edit My Profile)</span></a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mb-5">
    <h2>User Details</h2>
    <table class="table table-bordered table-striped table-dark table_border hover" id="myTable">

        <%-- 			<c:forEach items="${specificUserData}"  var="specificUserData">
         --%>
        <tr>
            <td>Firstname</td>
            <td>${specificUserData.userFirstname}</td>
        </tr
        ><tr>
            <td>Middlename</td>
            <td>${specificUserData.userMiddlename}</td>
        </tr>
            <tr>
            <td>Lastname</td>
            <td>${specificUserData.userLastname}</td>
        </tr>
        <tr>

            <td>Email-ID</td>
            <td>${specificUserData.userEmailID}</td>
        </tr>
            <tr>

            <td>Username</td>
            <td>${specificUserData.userUsername}</td>
        </tr>

        <tr>
            <td>Hobby</td>
            <td>${specificUserData.userHobbies}</td>
        </tr>
        <tr>
            <td>Profile Picture</td>
            <td><img src="data:image/jpg;base64,${specificUserData.base64Image}"
                     width="100" height="100"></td>
        </tr>
        <tr>
            <td>Date of Birth</td>
            <td>${specificUserData.userDOB}</td>
        </tr><tr>
            <td>Age</td>
            <td>${specificUserData.userAge}</td>
        </tr>
        <%-- 								</c:forEach>
         --%>
        <c:forEach var="address" items="${AddressList}" varStatus="count">
            <tr>
                <td>Address</td>
                <td>Address ${count.index + 1} <br/><br/>
                    House No. : ${address.addHouseNo} <br/>
                    Street : ${address.addStreet} <br/>
                    Landmark :  ${address.addLandmark} <br/>
                    City : ${address.addCity} <br/>
                    State: ${address.addState} <br/>
                    Pincode : ${address.addZipcode} <br/>
                    Country : ${address.addCountry} <br/>
                    Postal-Address : ${address.addPostalAdd} <br/>
                </td>
            </tr>
        </c:forEach>


    </table>
</div>

<jsp:include page="WEB-INF/views/footer.jsp"></jsp:include>

<script type="text/javascript" rel="script" src="./assets/js/searchjquery.dataTables.min.js"></script>
</body>
</html>