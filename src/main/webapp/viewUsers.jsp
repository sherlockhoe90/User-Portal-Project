<%--
  Created by IntelliJ IDEA.
  User: ND
  Date: 11/07/2023
  Time: 13:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.inexture.userportal.userportalproject.model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="WEB-INF/views/header.jsp"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>View Users</title>
    <link rel="icon" href="./assets/images/inexture-favicon-purple.png" type="image/x-icon">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.css">
    <!-- <link href="https://cdn.datatables.net/responsive/2.2.9/css/dataTables.responsive.css"> -->
    <link href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="./assets/css/style.css" rel="stylesheet">
    <style type="text/css">
        .switch {
            position: relative;
            display: inline-block;
            width: 50px;
            height: 25px;
        }

        .switch input {
            opacity: 0;
            width: 0;
            height: 0;
        }

        .slider {
            position: absolute;
            cursor: pointer;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: #ccc;
            -webkit-transition: .4s;
            transition: .4s;
        }

        .slider:before {
            position: absolute;
            content: "";
            height: 20px;
            width: 20px;
            left: 4px;
            bottom: 3px;
            background-color: white;
            -webkit-transition: .4s;
            transition: .4s;
        }

        input:checked + .slider {
            background-color: #2196F3;
        }

        input:focus + .slider {
            box-shadow: 0 0 1px #2196F3;
        }

        input:checked + .slider:before {
            -webkit-transform: translateX(26px);
            -ms-transform: translateX(26px);
            transform: translateX(26px);
        }

        .slider.round {
            border-radius: 34px;
        }

        .slider.round:before {
            border-radius: 50%;
        }
    </style>


</head>
<body class="all_page_background">
<%-- <jsp:include page="header.jsp" /> --%>
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
<div class="container">
    <table id="viewUsersTable"
           class="table table-striped table-bordered table_css">
        <thead>
        <tr>
            <th colspan="9" style="text-align: center">User Details</th>
        </tr>

        <tr>
            <th>Firstname</th>
            <th>Middlename</th>
            <th>Lastname</th>
            <th>Email</th>
            <th>Username</th>
            <th>Hobby</th>
            <%--            <th>Profile Picture</th>--%>
            <th>Date of Birth</th>
            <th>Age</th>
            <th>Edit/Delete</th>
            <%--here lies the extra th in root--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${userList}">
            <tr>
                <td>${user.userFirstname}</td>
                <td>${user.userMiddlename}</td>
                <td>${user.userLastname}</td>
                <td>${user.userEmailID}</td>
                <td>${user.userUsername}</td>
                <td>${user.userHobbies}</td>
                    <%--                <td><img src="data:image/jpg;base64,${user.base64Image}"--%>
                    <%--                         width="100" height="100"></td>--%>
                <td>${user.userDOB}</td>
                <td>${user.userAge}</td>
                <td>
                    <%--edit icon--%>
                    <a href="AdminEdit?user=adminEdit&userId=${user.userId}"><i
                        class="fa fa-pencil-square-o fa-lg " aria-hidden="true"></i></a>
                    <%--delete icon--%>
                    &nbsp;&nbsp;<a id="${user.userId}" class="delete"><i
                            class="fa fa-trash fa-lg " aria-hidden="true"></i></a></td>
                    <%--here lies the extra TD in root--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="https://cdn.datatables.net/responsive/2.2.9/js/dataTables.responsive.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>

<%--js for cr in root--%>
<script>
    $(document).ready(function () {

        $('#viewUsersTable').DataTable({
            "lengthMenu": [[5, 10, 25, 50, 1000000], [5, 10, 25, 50, 'All' ]]
        });

        $(document).on("click", ".delete", function () {

            var rowToDelete = this;
            var userId = +this.id;
            //THIS CAN ALSO BE DONE WITH URL REWRITING
            // CALLING DeleteUserController Servlet with href="DeleteUserController?userId="+userId;
            //But using this above URL rewriting method would send the data as a GET request,
            // and there's no hidden form for a POST method request... eg. href="DeleteUserController?userId=$ [ user.userId ]"
            $.ajax({
                url: "DeleteUser",
                type: "POST",
                data: ({
                    userId: userId,
                }),
                success: function (data) {
                    alert("deleted successfully..");
                    $(rowToDelete).closest('tr').fadeOut(100, function () {
                        $(this).remove();
                    });
                }
            });
        });

    });
</script>

<jsp:include page="WEB-INF/views/footer.jsp"/>
</body>
</html>
