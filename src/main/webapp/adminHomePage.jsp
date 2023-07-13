<%--
  Created by IntelliJ IDEA.
  User: ND
  Date: 10/07/2023
  Time: 17:46
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="WEB-INF/views/header.jsp"/>

<%@ page import="com.inexture.userportal.userportalproject.model.User" %>
<%@ page import="com.inexture.userportal.userportalproject.model.Address" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Admin HomePage</title>
    <link rel="icon" href="./assets/images/inexture-favicon-purple.png" type="image/x-icon">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.css">
    <link href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/header_and_footer.css">
</head>

<body class=" bg_custom_color
    ">

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
                <a class="navbar-brand" href="adminHomePage.jsp">Admin</a>
            </div>
            <div class="collapse navbar-collapse"
                 id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li><a href="registration.jsp?user=admin">Edit My Profile<span
                            class="sr-only">(Edit My Profile)</span></a>
                    <li><a href="viewuserdetails">View All Users<span
                            class="sr-only">(View All of the Users)</span></a>
                    <li><a href="registration.jsp?user=ADD" id="addLink">Add User<span
                        class="sr-only">(Add Another User)</span>
                </a>
                </ul>
                <a class="navbar-brand content-center">User Portal Project</a>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="logout">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="container">
        <table id="example"
               class="table table-striped table-bordered table_css">
            <thead>
            <tr>
                <th colspan="7" style="text-align: center">Admin Details</th>
            </tr>
            <tr>
                <th>Firstname</th>
                <th>Middleame</th>
                <th>Lastname</th>
                <th>Email-ID</th>
                <th>Hobby</th>
                <th>Profile Picture</th>
                <th>Username</th>
                <th>Date of Birth</th>
                <th>Age</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="admin" items="${adminList}">
                <tr>
                    <td>${admin.userFirstname}</td>
                    <td>${admin.userMiddlename}</td>
                    <td>${admin.userLastname}</td>
                    <td>${admin.userEmailID}</td>
                    <td>${admin.userHobbies}</td>
                    <td><img src="data:image/jpg;base64,${admin.base64Image}"
                             width="100" height="100"></td>
                    <td>${admin.userUsername}</td>
                    <td>${admin.userDOB}</td>
                    <td>${admin.userAge}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap4.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
    <script src="./assets/js/adduser.js"></script>

    <script>
        $(document).ready(
            function () {
                $('#example').DataTable(
                    {
                        "lengthMenu": [[3, 5, 7, 10, "All"],
                            [3, 5, 7, 10, "All"]]
                    });

            });
    </script>

    <jsp:include page="WEB-INF/views/footer.jsp"/>
    </body>
</html>