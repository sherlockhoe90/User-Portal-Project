<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page import="com.inexture.userportal.userportalproject.model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--using this JAVA code to not let them go to the Login.jsp page when they're logged in and the session is valid--%>
<%
    String userRole = (String) session.getAttribute("userRole");
    if (userRole != null) {
        // If the session attribute is set, redirect the user to the appropriate homepage
        if (!userRole.equals("admin")) {
            response.sendRedirect("login.jsp");
            return;
        }
    } else if (userRole == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%
    // Set cache control directives to prevent caching of the page
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // Proxies
%>

<%@ include file="WEB-INF/views/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>View Users</title>
    <link rel="icon" href="./assets/images/inexture-favicon-purple.png" type="image/x-icon">
    <link href="./assets/css/CDN/bootstrap_4.5.3.css">
    <link href="./assets/js/CDNs/dataTables.responsive.js">
    <link rel="stylesheet" href="./assets/css/CDN/font_awesome_free.css">
    <link rel="stylesheet" href="./assets/css/CDN/font_awesome_pro.css">
    <link rel="stylesheet" href="./assets/css/CDN/http_cdnjs.cloudflare.com_ajax_libs_font-awesome_4.7.0_css_font-awesome.css">
    <link href="./assets/css/style.css" rel="stylesheet">
    <style>
        .switch input {
            opacity: 0;
            width: 0;
            height: 0;
        }

        input:focus + .slider {
            box-shadow: 0 0 1px #2196F3;
        }
    </style>
</head>
<body class="all_page_background">
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
            <a class="navbar-brand" href="adminHomePage.jsp">Admin</a> <a
                class="navbar-brand content-center">User Portal Project</a>
        </div>
        <div class="collapse navbar-collapse"
             id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container" id="viewUsersTableContainer">
    <div class="row mb-2">
        <div class="col-sm-12 col-md-6">
            <label for="recordsPerPageSelect">Rows per page:</label>
            <select class="form-control" id="recordsPerPageSelect">
                <option value="5">5</option>
                <option value="10" selected>10</option>
                <option value="25">25</option>
                <option value="50">50</option>
                <!-- Add more options as needed -->
            </select>
        </div>
        <div class="col-sm-12 col-md-6 text-md-right">
            <p>Total records: <span id="totalRecords"></span></p>
        </div>
    </div>
    <table id="viewUsersTable" class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>Firstname</th>
            <th>Middlename</th>
            <th>Lastname</th>
            <th>Email</th>
            <th>Username</th>
            <th>Hobby</th>
            <th>Date of Birth</th>
            <th>Age</th>
            <th>Edit/Delete</th>
        </tr>
        </thead>
        <tbody>
        <!-- The rows will be populated using AJAX -->
        </tbody>
    </table>
    <div id="paginationContainer" class="d-flex justify-content-center mt-3"></div>
</div>

<script src="./assets/js/CDNs/jquery_3.6.0.js"></script>
<script src="./assets/js/CDNs/code.jquery.com_jquery-1.12.4.js"></script>
<script src="./assets/js/CDNs/jquery_3.5.1.js"></script>
<script src="./assets/js/CDNs/dataTables.responsive.js"></script>
<script src="./assets/js/CDNs/datatables.js"></script>
<script src="./assets/js/CDNs/dataTables.bootstrap4.js"></script>
<script src="./assets/js/CDNs/bootstrap_3.3.7.js"></script>
<script>
    $(document).ready(function () {
        var currentPage = 1;
        var recordsPerPage = 10;
        var totalPages = 1;

        function fetchUsers() {
            $.ajax({
                url: "viewuserdetails",
                type: "GET",
                data: {
                    currentPage: currentPage,
                    recordsPerPage: recordsPerPage
                },
                success: function (response) {
                    // Parse the JSON response
                    var userList = JSON.parse(response.data);
                    var totalRecords = response.recordsTotal;
                    totalPages = response.totalPages;
                    // Update the total records count
                    $("#totalRecords").text(totalRecords);

                    // Clear existing table rows
                    $("#viewUsersTable tbody").empty();

                    // Populate table rows with data
                    userList.forEach(function (user) {
                        var rowHtml = "<tr>";
                        rowHtml += "<td>" + user.userFirstname + "</td>";
                        rowHtml += "<td>" + user.userMiddlename + "</td>";
                        rowHtml += "<td>" + user.userLastname + "</td>";
                        rowHtml += "<td>" + user.userEmailID + "</td>";
                        rowHtml += "<td>" + user.userUsername + "</td>";
                        rowHtml += "<td>" + user.userHobbies + "</td>";
                        rowHtml += "<td>" + user.userDOB + "</td>";
                        rowHtml += "<td>" + user.userAge + "</td>";
                        rowHtml += "<td>";
                        rowHtml += '<a href="AdminEdit?user=adminEdit&userId=' + user.userId + '"><i class="fa fa-pencil-square-o fa-lg" aria-hidden="true"></i></a>';
                        rowHtml += '&nbsp;&nbsp;<a id="' + user.userId + '" class="delete"><i class="fa fa-trash fa-lg" aria-hidden="true"></i></a>';
                        rowHtml += "</td>";
                        rowHtml += "</tr>";
                        $("#viewUsersTable tbody").append(rowHtml);
                    });

                    // Update pagination buttons
                    updatePaginationButtons();
                },
                error: function (xhr, status, error) {
                    console.error("Error occurred:", error);
                }
            });
        }

        // Fetch users on page load
        fetchUsers();

        // Function to update pagination buttons
        function updatePaginationButtons() {
            var paginationContainer = $("#paginationContainer");
            paginationContainer.empty();

            for (var i = 1; i <= totalPages; i++) {
                var buttonClass = (i === currentPage) ? "active" : "";
                var buttonHtml = '<button class="btn btn-sm ' + buttonClass + '">' + i + '</button>';
                paginationContainer.append(buttonHtml);
            }
        }

        // Handle pagination button click
        $(document).on("click", "#paginationContainer button", function () {
            currentPage = parseInt($(this).text(), 10);
            fetchUsers();
        });

        // handling pagination when user changes rows per page
        $("#recordsPerPageSelect").change(function () {
            recordsPerPage = parseInt($(this).val(), 10);
            currentPage = 1;
            fetchUsers();
        });

        // adding the delete event listener to delete a user.
        $(document).on("click", ".delete", function () {
            var rowToDelete = this;
            var userId = +this.id;

            $.ajax({
                url: "DeleteUser",
                type: "POST",
                data: {
                    userId: userId,
                },
                success: function (data) {
                    alert("Deleted successfully.");
                    fetchUsers();
                }
            });
        });
    });
</script>

<%@ include file="WEB-INF/views/footer.jsp" %>
</body>
</html>

<%--below is the code for the working body of code just after i made the code work for the first time, with a tinyyyyy bit of help from Bharat sir.--%>

<%--<body class="all_page_background">--%>
<%--<nav class="navbar navbar-inverse">--%>
<%--    <!-- The rest of the navigation code -->--%>
<%--</nav>--%>
<%--<div class="container" id="viewUsersTableContainer">--%>
<%--    <div class="row mb-2">--%>
<%--        <div class="col-sm-12 col-md-6">--%>
<%--            <label for="recordsPerPageSelect">Rows per page:</label>--%>
<%--            <select class="form-control" id="recordsPerPageSelect">--%>
<%--                <option value="5">5</option>--%>
<%--                <option value="10" selected>10</option>--%>
<%--                <option value="25">25</option>--%>
<%--                <option value="50">50</option>--%>
<%--                <!-- Add more options as needed -->--%>
<%--            </select>--%>
<%--        </div>--%>
<%--        <div class="col-sm-12 col-md-6 text-md-right">--%>
<%--            <p>Total records: <span id="totalRecords"></span></p>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <table id="viewUsersTable" class="table table-striped table-bordered table_css">--%>
<%--        <thead>--%>
<%--        <tr>--%>
<%--            <th>Firstname</th>--%>
<%--            <th>Middlename</th>--%>
<%--            <th>Lastname</th>--%>
<%--            <th>Email</th>--%>
<%--            <th>Username</th>--%>
<%--            <th>Hobby</th>--%>
<%--            <th>Date of Birth</th>--%>
<%--            <th>Age</th>--%>
<%--            <th>Edit/Delete</th>--%>
<%--        </tr>--%>
<%--        </thead>--%>
<%--        <tbody>--%>
<%--        <!-- The rows will be populated using AJAX -->--%>
<%--        </tbody>--%>
<%--    </table>--%>
<%--</div>--%>
<%--<script src="./assets/js/CDNs/jquery_3.6.0.js"></script>--%>
<%--<script src="./assets/js/CDNs/code.jquery.com_jquery-1.12.4.js"></script>--%>
<%--<script src="./assets/js/CDNs/jquery_3.5.1.js"></script>--%>
<%--<script src="./assets/js/CDNs/dataTables.responsive.js"></script>--%>
<%--<script src="./assets/js/CDNs/datatables.js"></script>--%>
<%--<script src="./assets/js/CDNs/dataTables.bootstrap4.js"></script>--%>
<%--<script src="./assets/js/CDNs/bootstrap_3.3.7.js"></script>--%>
<%--&lt;%&ndash;js for cr in root&ndash;%&gt;--%>
<%--<script>--%>
<%--    $(document).ready(function () {--%>
<%--        var currentPage = 1;--%>
<%--        var recordsPerPage = 10;--%>

<%--        function fetchUsers() {--%>
<%--            $.ajax({--%>
<%--                url: "viewuserdetails",--%>
<%--                type: "GET",--%>
<%--                data: {--%>
<%--                    currentPage: currentPage,--%>
<%--                    recordsPerPage: recordsPerPage--%>
<%--                },--%>
<%--                success: function (response) {--%>
<%--                    alert(response);--%>
<%--                    // debugger;--%>
<%--                    /*Parsing the JSON response*/--%>
<%--                    var userList = JSON.parse(response.data);--%>
<%--                    var totalRecords = response.recordsTotal;--%>

<%--                    // this updates the total records based on the actual count.--%>
<%--                    $("#totalRecords").text(totalRecords);--%>

<%--                    //clear existing table rows--%>
<%--                    $("#viewUsersTable tbody").empty();--%>

<%--                    // Populate table rows with data--%>
<%--                    userList.forEach(function (user) {--%>
<%--                        var rowHtml = "<tr>";--%>
<%--                        rowHtml += "<td>" + user.userFirstname + "</td>";--%>
<%--                        rowHtml += "<td>" + user.userMiddlename + "</td>";--%>
<%--                        rowHtml += "<td>" + user.userLastname + "</td>";--%>
<%--                        rowHtml += "<td>" + user.userEmailID + "</td>";--%>
<%--                        rowHtml += "<td>" + user.userUsername + "</td>";--%>
<%--                        rowHtml += "<td>" + user.userHobbies + "</td>";--%>
<%--                        rowHtml += "<td>" + user.userDOB + "</td>";--%>
<%--                        rowHtml += "<td>" + user.userAge + "</td>";--%>
<%--                        rowHtml += "<td>";--%>
<%--                        rowHtml += '<a href="AdminEdit?user=adminEdit&userId=' + user.userId + '"><i class="fa fa-pencil-square-o fa-lg" aria-hidden="true"></i></a>';--%>
<%--                        rowHtml += '&nbsp;&nbsp;<a id="' + user.userId + '" class="delete"><i class="fa fa-trash fa-lg" aria-hidden="true"></i></a>';--%>
<%--                        rowHtml += "</td>";--%>
<%--                        rowHtml += "</tr>";--%>
<%--                        $("#viewUsersTable tbody").append(rowHtml);--%>
<%--                    });--%>
<%--                },--%>
<%--                error: function (xhr, status, error) {--%>
<%--                    console.error("Error occurred:", error);--%>
<%--                }--%>
<%--            });--%>
<%--        }--%>

<%--        // Fetch users on page load--%>
<%--        fetchUsers();--%>
<%--        --%>
<%--        // Handle pagination when user changes rows per page--%>
<%--        $("#recordsPerPageSelect").change(function () {--%>
<%--            recordsPerPage = parseInt($(this).val(), 10);--%>
<%--            currentPage = 1;--%>
<%--            fetchUsers();--%>
<%--        });--%>

<%--        // Add the delete event listener--%>
<%--        $(document).on("click", ".delete", function () {--%>
<%--            var rowToDelete = this;--%>
<%--            var userId = +this.id;--%>

<%--            $.ajax({--%>
<%--                url: "DeleteUser",--%>
<%--                type: "POST",--%>
<%--                data: {--%>
<%--                    userId: userId,--%>
<%--                },--%>
<%--                success: function (data) {--%>
<%--                    alert("Deleted successfully.");--%>
<%--                    fetchUsers();--%>
<%--                }--%>
<%--            });--%>
<%--        });--%>
<%--    });--%>
<%--</script>--%>

<%--<%@ include file="WEB-INF/views/footer.jsp" %>--%>
<%--</body>--%>
