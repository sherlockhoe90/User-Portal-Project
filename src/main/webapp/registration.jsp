<%--
  Created by IntelliJ IDEA.
  User: ND
  Date: 02/07/2023
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="com.inexture.userportal.userportalproject.controller.UserRegisterController" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ include file="./WEB-INF/views/header.jsp" %>

<%@ page import="com.inexture.userportal.userportalproject.model.User" %>
<%@ page import="com.inexture.userportal.userportalproject.model.Address" %>

<%
    User user = (User) session.getAttribute("CurrentUser");
    String userName = request.getParameter("user"); //only this and the next line is useful
    session.setAttribute("userName", userName); // this one. this is used when Updating values from UpdateProfileController
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Registration Form</title>
    <%--    <link rel="icon" href="assets/images/inexture-favicon-purple.png" type="image/x-icon">--%>

    <%--    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;1,100;1,200;1,300;1,400;1,600;1,700;1,800;1,900&family=Raleway:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"--%>
    <%--          rel="stylesheet"/>--%>
    <%--    <link href="https://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">--%>
    <%--    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet">--%>
    <%--    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/css/all.min.css" rel="stylesheet">--%>
    <%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.3.1/minty/bootstrap.min.css">--%>
    <%--    <link rel="stylesheet" type="text/css"--%>
    <%--          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.css">--%>
    <%--    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/select2/3.3.2/select2.css">--%>
    <%--    <link rel="stylesheet" type="text/css"--%>
    <%--          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/css/bootstrap-select.css">--%>

    <%--    <!-- Bootstrap -->--%>
    <%--    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css">--%>
    <%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">--%>
    <%--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">--%>
    <%--    <link href="./assets/css/" rel="stylesheet" type="text/css"/>--%>
    <%--    <link href="assets/css/header_and_footer.css" rel="stylesheet" type="text/css"/>--%>
    <%--    <link href="assets/css/style.css" rel="stylesheet" type="text/css"/>--%>
    <link rel="icon" href="assets/images/inexture-favicon-purple.png" type="image/x-icon">

    <link href="./assets/fonts/Poppins-Regular.ttf" rel="script"/>
    <link href="./assets/fonts/Roboto.css" rel="script"/>
    <link href="./assets/css/CDN/jquery_app_jqueryscripttop.css" rel="stylesheet" type="text/css">
    <link href="./assets/css/CDN/font_awesome_free.css" rel="stylesheet">
    <link href="./assets/css/CDN/font_awesome_pro.css" rel="stylesheet">
    <link rel="stylesheet" href="./assets/css/CDN/bootswatch_MINTY_bootstrap.min.css" type="text/css">
    <link rel="stylesheet" type="text/css"
          href="./assets/css/CDN/bootstrap-datepicker_1.6.4.min.css">
    <link rel="stylesheet" type="text/css" href="./assets/css/CDN/select2_3.3.2.min.css">
    <link rel="stylesheet" type="text/css"
          href="./assets/css/CDN/bootstrap-select_1.13.18.css">

    <!-- Bootstrap -->
    <link rel="stylesheet" href="./assets/css/CDN/bootstrap_4.0.0.css">
    <link href="./assets/css/CDN/bootstrap_5.1.3.css" rel="stylesheet">
    <link rel="stylesheet" href="./assets/css/CDN/bootstrap_3.3.7.css">
    <link href="assets/css/header_and_footer.css" rel="stylesheet" type="text/css"/>
    <link href="assets/css/style.css" rel="stylesheet" type="text/css"/>

    <%--PREVENTS USER FROM COMING BACK AFTER HE/SHE HAS LOGGED OUT--%>
    <script>
        // Check if the page is loaded from the cache
        const navigationEntries = window.performance.getEntriesByType("navigation");
        if (navigationEntries.length && navigationEntries[0].type === "back_forward") {
            // Page is loaded from the cache, redirect to the login page
            window.location.href = "login.jsp";
        }
    </script>
</head>

<body class="bg-info bg_custom_color">

<%--getting the parameter 'user' and saving it in the variable 'profile'--%>
<c:set var="profile" scope="request"
       value='<%=request.getParameter("user")%>'/>

<%--check whether the user is admin or normal user
and whether he/she wants to edit/add information --%>
<c:choose>
    <c:when test="${(profile == 'admin') || (profile == 'adminEdit')}">
        <c:set var="user" scope="session" value="${sessionScope.CurrentUser}"/>
        <%--        <c:set var="address" scope="session" value="${sessionScope.AddressList}"/>--%>
    </c:when>
    <c:when test="${(profile == 'userEdit')}">
        <c:set var="user" scope="session" value="${sessionScope.specificUserData}"/>
    </c:when>
</c:choose>


<div class="container">

    <form action="UserRegister" method="Post" id="registration_form"
          enctype="multipart/form-data">
        <h3 style="text-align: center; font-family: 'Poppins Regular'" class="margin_top_file header_tag">Registration
            Happens Here!</h3>
        <c:choose>
        <c:when
                test="${(profile == 'admin') || (profile == 'userEdit') || (profile == 'adminEdit')}">

        <div class="row form-row">
            <div class="col-md-6">

                <div class="form-group">
                        <%--commented on 11/07/2023, uncomment soon after fixing the profile picture issues--%>
                        <%--                    <label for="img">Profile picture:</label><input type="file"--%>
                        <%--                                                                    id="img" name="img"/> <img--%>
                        <%--                        src="./assets/images/default_profile.jpg" name="default_img"--%>
                        <%--                        style="width: 170px; height: 170px;" id="default_img">--%>
                        <%--                    <img src="data:image/jpg;base64,${user.base64Image}"--%>
                        <%--                        id="image_preview" style="display: none"/><br/>--%>
                        <%--                    <input type="hidden" value="${user.base64Image}" name="oldImage">--%>
                        <%--                    <br/>--%>
                        <%--                    <span id="image_error"></span>--%>
                </div>

            </div>
        </div>


        <div class="row form-row ">
            <div class="col-md-6">
                <!-- First Name -->
                <div class="form-group">
                    <label for="firstname">Firstname:</label> <input type="text"
                                                                     class="form-control text-width firstname"
                                                                     id="firstname"
                                                                     name="firstname"
                                                                     placeholder="Enter your firstname"
                                                                     value="${user.userFirstname}" pattern="^[a-zA-Z\s]*$" required/><span
                        class="firstname_error" id="firstname_error"></span>
                </div>
            </div>
            <div class="col-md-6">

                <!-- Lastname -->
                <div class="form-group">
                    <label>Lastname:</label> <input type="text"
                                                    class="form-control lastname" id="lastname" placeholder="Lastname"
                                                    name="lastname" value="${user.userLastname} pattern="^[a-zA-Z\s]*$" required"/>
                    <span
                            class="lastname_error" id="lastname_error"></span>
                </div>

            </div>
        </div>

        <div class="row form-row ">
            <div class="col-md-6">
                <!-- Middle Name -->
                <div class="form-group">
                    <label for="middlename">Middlename:</label> <input type="text"
                                                                       class="form-control text-width middlename"
                                                                       id="middlename"
                                                                       name="middlename"
                                                                       placeholder="Enter your middlename"
                                                                       value="${user.userMiddlename}" pattern="^[a-zA-Z\s]*$"/><span
                        class="middlename_error" id="middlename_error"></span>
                </div>
            </div>
            <div class="col-md-6">

                <!-- Username -->
                <div class="form-group">
                    <label>Username:</label> <input type="text"
                                                    class="form-control username" id="username"
                                                    placeholder="Enter Username"
                                                    name="username" value="${user.userUsername}" pattern="^[a-zA-Z0-9!@#$%^&*()_+{}\[\]:<>.?~-\s]*$" required/>
                    <span
                            class="username_error" id="username_error"></span>
                </div>

            </div>
        </div>

        <div class="row form-row">
            <div class="col-md-6">
                <!-- Email  -->
                <div class="form-group">
                    <label for="emailid">Email:</label> <input type="text"
                                                               class="form-control text-width emailid"
                                                               id="emailid"
                                                               placeholder="Email-id" name="emailid"
                                                               value="${user.userEmailID}" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" required/>
                    <span class="email_error"
                          id="email_error"></span><br>
                    <span class="result" id="result"></span>
                </div>
            </div>
            <div class="col-md-6">
                <!-- password -->
                <div class="form-group">
                    <label for="pwd">Password:</label> <input type="password"
                                                              class="form-control text-width pwd" id="pwd"
                                                              placeholder="Password"
                                                              name="pwd"
                                                              value="${user.userPassword}" required/> <span
                        class="password_error" id="password_error"></span>
                </div>
            </div>
        </div>

        <div class="row form-row">


            <!-- confirm password -->
                <%--commenting cuz its read-only and hence causes errors when submitting after UPDATE/EDIT--%>
                <%--            <div class="col-md-6">--%>
                <%--                <div class="form-group">--%>
                <%--                    <label for="cpwd">Confirm Password:</label> <input--%>
                <%--                        type="password" class="form-control text-width" id="cpwd"--%>
                <%--                        name="cpwd" placeholder="Confirm-Password"/> <span--%>
                <%--                        id="confirm_password_error"></span>--%>
                <%--                </div>--%>
                <%--            </div>--%>

        </div>

        <div class="row form-row">
            <div class="">

                <!-- hobbies -->
                <div class="form-group">
                    <label for="hobbies">Hobby:</label> <input type="text"
                                                               class="form-control text-width hobbies" id="hobbies"
                                                               placeholder="Hobbies"
                                                               name="hobbies"
                                                               value="${user.userHobbies}" pattern="^[a-zA-Z\s]*$" required/> <span
                        class="hobbies_error" id="hobbies_error"></span>
                </div>
                <div class="">
                    <!-- image upload -->
                    <div class="form-group">
                        <label for="dob">Date Of Birth:</label> <input type="date"
                                                                       class="form-control text-width dob" id="dob"
                                                                       name="dob" min="1900-01-01"
                                                                       max="2023-07-19" value="${user.userDOB}" required/>
                        <span class="dob_error" id="dob_error"></span>
                            <%--                            <p style="display: none">${user.userDOB}</p> --%>
                            <%--printed this 'p' tag before i fixed the visible date format on the edit page, changing it from dd-mm-yyyy to yyyy-mm-dd. but as of after adding the datepicker, teh default input format is yyyy-mm-dd-%>
                        <span id="dob_error"></span>
                    </div>
                </div>
            </div>
        </div>
        <%--            ---------------------------------------------done-----------------------------------------------------------------------------------------------------------------------%>
                        </c:when>
                        <c:otherwise>
                        <div class="row form-row">
                            <div class="col-md-6">

                                <div class="form-group">
                                    <!-- <label for="img">Profile picture:</label><input type="file"
                                        id="img" name="img" /> <img
                                        src="custom/image/default_profile.jpg" name="default_img"
                                        style="width: 170px; height: 170px;" id="default_img"> <br />
                                    <span id="image_error"></span> -->

                                    <label for="img">Profile picture:</label> <input type="file"
                                                                                     id="img2" name="img"/> <img
                                        src="./assets/images/default_profile.jpg" name="default_img"
                                        style="width: 170px; height: 170px;" id="default_img2"><img
                                        src="data:image/jpg;base64,${user.base64Image}"
                                        id="image_preview2" style="display: none"/> <br/> <span
                                        id="image_error2"></span>
                                </div>

                            </div>
                        </div>

                        <div class="row form-row ">
                            <div class="col-md-6">
                                <!-- First Name -->
                                <div class="form-group">
                                    <label for="firstname">Firstname:</label> <input type="text"
                                                                                     class="form-control text-width firstname"
                                                                                     id="firstname"
                                                                                     name="firstname"
                                                                                     placeholder="Enter your firstname" pattern="^[a-zA-Z\s]*$" required/><span
                                        class="firstname_error" id="firstname_error"></span>
                                </div>
                            </div>
                            <div class="col-md-6">

                                <!-- Lastname -->
                                <div class="form-group">
                                    <label>Lastname:</label> <input type="text"
                                                                    class="form-control lastname" id="lastname"
                                                                    placeholder="Lastname"
                                                                    name="lastname" pattern="^[a-zA-Z\s]*$" required/> <span
                                        class="lastname_error" id="lastname_error"></span>
                                </div>

                            </div>
                        </div>

                        <div class="row form-row ">
                            <div class="col-md-6">
                                <!-- Middle Name -->
                                <div class="form-group">
                                    <label for="middlename">Middlename:</label> <input type="text"
                                                                                       class="form-control text-width middlename"
                                                                                       id="middlename"
                                                                                       name="middlename"
                                                                                       placeholder="Enter your middlename" pattern="^[a-zA-Z\s]*$"/><span
                                        class="middlename" id="middlename_error"></span>
                                </div>
                            </div>
                            <div class="col-md-6">

                                <!-- Username -->
                                <div class="form-group">
                                    <label>Username:</label> <input type="text"
                                                                    class="form-control username" id="username"
                                                                    placeholder="Enter Username"
                                                                    name="username" pattern="^[a-zA-Z0-9!@#$%^&*()_+{}\[\]:<>.?~-\s]*$" required/> <span
                                        class="username_error" id="username_error"></span>
                                </div>

                            </div>
                        </div>

                        <div class="row form-row">
                            <div class="col-md-6">
                                <!-- Email  -->
                                <div class="form-group">
                                    <label for="emailid">Email:</label> <input type="text"
                                                                               class="form-control text-width emailid"
                                                                               id="emailid"
                                                                               placeholder="Email-id"
                                                                               name="emailid" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" required/>
                                    <span
                                            class="email_error" id="email_error"></span><br>
                                    <span class="result" id="result"></span>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <!-- password -->
                                <div class="form-group">
                                    <label for="pwd">Password:</label> <input type="password"
                                                                              class="form-control text-width pwd"
                                                                              id="pwd"
                                                                              placeholder="Password"
                                                                              name="pwd"/> <span
                                        class="password_error" id="password_error"></span>
                                </div>
                            </div>
                        </div>

                        <div class="row form-row">


                            <!-- confirm password -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="cpwd">Confirm Password:</label> <input
                                        type="password" class="form-control text-width cpwd" id="cpwd"
                                        name="cpwd" placeholder="Confirm-Password" required/> <span
                                        class="confirm_password_error" id="confirm_password_error"></span>
                                </div>
                            </div>
                        </div>

                        <div class="row form-row">
                            <div class="col-md-6">

                                <!-- hobbies -->
                                <div class="form-group">
                                    <label for="hobbies">Hobby:</label> <input type="text"
                                                                               class="form-control text-width hobbies"
                                                                               id="hobbies"
                                                                               placeholder="Hobbies"
                                                                               name="hobbies" pattern="^[a-zA-Z0-9\s]*$" required/> <span
                                        class="hobbies_error" id="hobbies_error"></span>
                                </div>
                                <div class="col-md-6">
                                    <!-- image upload -->
                                    <div class="form-group">
                                        <label for="dob">Date Of Birth:</label> <input type="date"
                                                                                       class="form-control text-width dob"
                                                                                       id="dob"
                                                                                       name="dob" min="1900-01-01"
                                                                                       max="2023-07-19" required/> <span
                                            class="dob_error" id="dob_error"></span>
                                    </div>
                                </div>
                            </div>
                                <%--                    -----------------------------done------------------------------------------------------------------------------------------------%>
                            </c:otherwise>
                            </c:choose>
                            <!-- jQuery plugin code -->
                            <label class="address_label_margin">Address:</label>

                            <div class="margin-t-md">
                                <div class="customer-form">
                                    <div class="card"></div>
                                    <c:choose>
                                        <c:when
                                                test="${(profile == 'admin') || (profile == 'userEdit') || (profile == 'adminEdit')}">
                                            <div id="main-container">
                                                <c:forEach items="${AddressList}" var="address"
                                                           varStatus="count">
                                                    <div class="panel card container-item">
                                                        <div class="panel-body">
                                                            <div class="panel-body">
                                                                <input type="hidden" name="addressId[]"
                                                                       value="${address.addId}" required>

                                                                <div class="row">
                                                                    <div class="col-sm-12 col-md-4 cold-lg-2 col-xs-2">
                                                                        <div class="form-group">
                                                                            <label class="control-label"
                                                                                   for="address_line_one_0">House
                                                                                No.</label>
                                                                            <input type="text" id="houseno"
                                                                                   class="form-control houseno"
                                                                                   name="houseno[]" maxlength="10"
                                                                                   value="${address.addHouseNo}" required>
                                                                            <span class="houseno_error"
                                                                                  id="houseno_error"></span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-sm-12 col-md-4 cold-lg-2 col-xs-2">
                                                                        <div class="form-group">
                                                                            <label class="control-label"
                                                                                   for="address_line_one_0">Street</label>
                                                                            <input type="text" id="address"
                                                                                   class="form-control address"
                                                                                   name="address[]" maxlength="20"
                                                                                   value="${address.addStreet}" required/>
                                                                            <span class="street_error"
                                                                                  id="street_error"></span>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-sm-12 col-md-4 cold-lg-2 col-xs-2">
                                                                        <div class="form-group">
                                                                            <label class="control-label"
                                                                                   for="address_line_two_0">Landmark</label>
                                                                            <input type="text" id="landmark"
                                                                                   class="form-control landmark"
                                                                                   name="landmark[]" maxlength="50"
                                                                                   value="${address.addLandmark}" required> <span
                                                                                class="landmark_error"
                                                                                id="landmark_error"></span>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-sm-12 col-md-4 cold-lg-2 col-xs-2">
                                                                        <div class="form-group">
                                                                            <label class="control-label"
                                                                                   for="address_line_two_0">Zipcode</label>
                                                                            <input type="text" id="pincode"
                                                                                   class="form-control pincode"
                                                                                   name="zipcode[]" maxlength="10"
                                                                                   value="${address.addZipcode}" pattern="^[a-zA-Z0-9_]*$" required> <span
                                                                                class="pincode_error"
                                                                                id="pincode_error"></span>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="row">
                                                                    <c:forEach var="item" items="${address.addCity}">
                                                                        <div class="col-sm-6">
                                                                            <div class="form-group">
                                                                                <label class="control-label"
                                                                                       for="city_0">City</label> <select
                                                                                    class="form-control city"
                                                                                    name="city[]"
                                                                                    id="city"
                                                                                    style="height: auto;" required>
                                                                                <option value="0">Select City</option>
                                                                                <option value="Ahmedabad"
                                                                                    ${item  eq 'Ahmedabad' ? 'selected' : ''}>
                                                                                    Ahmedabad
                                                                                </option>
                                                                                <option value="Junagadh"
                                                                                    ${item  eq 'Junagadh' ? 'selected' : ''}>
                                                                                    Junagadh
                                                                                </option>
                                                                                <option value="Mumbai"
                                                                                    ${item  eq 'Mumbai' ? 'selected' : ''}>
                                                                                    Mumbai
                                                                                </option>
                                                                                <option value="Surat"
                                                                                    ${item  eq 'Surat' ? 'selected' : ''}>
                                                                                    Surat
                                                                                </option>
                                                                                <option value="Vadodara"
                                                                                    ${address.addCity  eq 'Vadodara' ? 'selected' : ''}>
                                                                                    Vadodara
                                                                                </option>
                                                                            </select> <span class="city_error"
                                                                                            id="city_error"></span>
                                                                            </div>
                                                                        </div>
                                                                    </c:forEach>

                                                                    <c:forEach var="item" items="${address.addState}">

                                                                        <div class="col-sm-6">
                                                                            <div class="form-group">
                                                                                <label class="control-label"
                                                                                       for="state_0">State</label>
                                                                                <select class="form-control state"
                                                                                        name="state[]"
                                                                                        id="state"
                                                                                        style="height: auto;" required>
                                                                                    <option value="0">Select State
                                                                                    </option>
                                                                                    <option value="Gujarat"
                                                                                        ${item  eq 'Gujarat' ? 'selected' : ''}>
                                                                                        Gujarat
                                                                                    </option>
                                                                                    <option value="Maharashtra"
                                                                                        ${item eq 'Maharashtra' ? 'selected' : ''}>
                                                                                        Maharashtra
                                                                                    </option>
                                                                                    <option value="Goa"
                                                                                        ${item  eq 'Goa' ? 'selected' : ''}>
                                                                                        Goa
                                                                                    </option>
                                                                                    <option value="Punjab"
                                                                                        ${item  eq 'Punjab' ? 'selected' : ''}>
                                                                                        Punjab
                                                                                    </option>
                                                                                    <option value="Assam"
                                                                                        ${item  eq 'Assam' ? 'selected' : ''}>
                                                                                        Assam
                                                                                    </option>
                                                                                </select> <span class="state_error"
                                                                                                id="state_error"></span>
                                                                            </div>
                                                                        </div>
                                                                    </c:forEach>
                                                                </div>

                                                                <div class="row">
                                                                    <div class="col-sm-6">
                                                                        <div class="form-group">
                                                                            <label class="control-label" for="country">Country</label>
                                                                            <input type="text" id="country"
                                                                                   class="form-control country"
                                                                                   name="country[]" maxlength="255"
                                                                                   value="${address.addCountry}" pattern="^[a-zA-Z\s]$" required>
                                                                            <span class="country_error"
                                                                                  id="country_error"></span>
                                                                        </div>
                                                                        <div class="col-sm-6">
                                                                            <div class="form-group">
                                                                                <label class="control-label"
                                                                                       for="postaladdress">Postal
                                                                                    Address</label>
                                                                                <input type="text" id="postaladdress"
                                                                                       class="form-control postaladdress"
                                                                                       name="postaladdress[]"
                                                                                       maxlength="250"
                                                                                       value="${address.addPostalAdd}" required>
                                                                                <span class="postaladdress_error"
                                                                                      id="postaladdress_error"></span>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="row">
                                                                    <div class="col-sm-12">
                                                                        <div>
                                                                            <a href="javascript:void(0)"
                                                                               class="remove-item btn btn-sm btn-danger remove-social-media">Remove</a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>

                                            <div>
                                                <a class="btn btn-success btn-sm" id="add-more"
                                                   href="javascript:;" role="button"><i class="fa fa-plus"></i>
                                                    Add address</a>
                                            </div>
                                            <%--          --------------------------------done-----------------------------------------------------------------------------------------------------------------------%>
                                        </c:when>
                                        <c:otherwise>
                                            <div id="main-container">
                                                <div class="panel card container-item">
                                                    <div class="panel-body">
                                                        <div class="panel-body">

                                                            <div class="row">
                                                                <div class="col-sm-12 col-md-4 cold-lg-2 col-xs-2">
                                                                    <div class="form-group">
                                                                        <label class="control-label"
                                                                               for="address_line_one_0">House
                                                                            No.</label>
                                                                        <input type="text" id="houseno"
                                                                               class="form-control houseno"
                                                                               name="houseno[]" maxlength="10" required>
                                                                        <span class="houseno_error"
                                                                              id="houseno_error"></span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-sm-12 col-md-4 cold-lg-2 col-xs-2">
                                                                    <div class="form-group">
                                                                        <label class="control-label"
                                                                               for="address_line_one_0">Street</label>
                                                                        <input type="text" id="street"
                                                                               class="form-control street"
                                                                               name="address[]" maxlength="20" required>
                                                                        <span class="street_error"
                                                                              id="street_error"></span>
                                                                    </div>
                                                                </div>
                                                                <div class="col-sm-12 col-md-4 cold-lg-2 col-xs-2">
                                                                    <div class="form-group">
                                                                        <label class="control-label"
                                                                               for="address_line_two_0">Landmark</label>
                                                                        <input type="text" id="landmark"
                                                                               class="form-control landmark"
                                                                               name="landmark[]" maxlength="50"> <span
                                                                            class="landmark_error"
                                                                            id="landmark_error"></span>
                                                                    </div>
                                                                </div>
                                                                <div class="col-sm-12 col-md-4 cold-lg-2 col-xs-2">
                                                                    <div class="form-group">
                                                                        <label class="control-label"
                                                                               for="address_line_two_0">Zipcode</label>
                                                                        <input type="text" id="pincode"
                                                                               class="form-control pincode"
                                                                               name="zipcode[]" maxlength="10" pattern="^[a-zA-Z0-9_]*$" required> <span
                                                                            class="pincode_error"
                                                                            id="pincode_error"></span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">

                                                                <div class="col-sm-12 col-md-4 cold-lg-2 col-xs-2">
                                                                    <div class="form-group">
                                                                        <label class="control-label"
                                                                               for="city">City</label> <select
                                                                            class="form-control city" name="city[]"
                                                                            id="city"
                                                                            style="height: auto;" required>
                                                                        <option value="0">Select City</option>
                                                                        <option value="Ahmedabad">Ahmedabad</option>
                                                                        <option value="Junagadh">Junagadh</option>
                                                                        <option value="Mumbai">Mumbai</option>
                                                                        <option value="Surat">Surat</option>
                                                                        <option value="Vadodara">Vadodara</option>
                                                                    </select> <span class="city_error"
                                                                                    id="city_error"></span>
                                                                    </div>
                                                                </div>


                                                                <div class="col-sm-12 col-md-4 cold-lg-2 col-xs-2">
                                                                    <div class="form-group">
                                                                        <label class="control-label"
                                                                               for="state">State</label> <select
                                                                            class="form-control state" name="state[]"
                                                                            id="state"
                                                                            style="height: auto;" required>
                                                                        <option value="0">Select State</option>
                                                                        <option value="Gujarat">Gujarat</option>
                                                                        <option value="Maharashtra">Maharashtra</option>
                                                                        <option value="Goa">Goa</option>
                                                                        <option value="Punjab">Punjab</option>
                                                                        <option value="Assam">Assam</option>
                                                                    </select> <span class="state_error"
                                                                                    id="state_error"></span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-sm-12 col-md-4 cold-lg-2 col-xs-2">
                                                                    <div class="form-group">
                                                                        <label class="control-label" for="country">Country</label>
                                                                        <input type="text" id="country"
                                                                               class="form-control country"
                                                                               name="country[]" maxlength="255" required>
                                                                        <span class="country_error"
                                                                              id="country_error"></span>
                                                                    </div>
                                                                    <div class="col-sm-12 col-md-4 cold-lg-2 col-xs-2">
                                                                        <div class="form-group">
                                                                            <label class="control-label"
                                                                                   for="postaladdress">Postal
                                                                                Address</label>
                                                                            <input type="text" id="postaladdress"
                                                                                   class="form-control postaladdress"
                                                                                   name="postaladdress[]"
                                                                                   maxlength="250"  required>
                                                                            <span class="postaladdress_error"
                                                                                  id="postaladdress_error"></span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>


                                                            <div class="row">
                                                                <div class="col-sm-12 col-md-4 cold-lg-2 col-xs-2">
                                                                    <div>
                                                                        <a href="javascript:void(0)"
                                                                           class="remove-item btn btn-sm btn-danger remove-social-media">Remove</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div>
                                                <a class="btn btn-success btn-sm" id="add-more"
                                                   href="javascript:;" role="button"><i class="fa fa-plus"></i>
                                                    Add address</a>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <%--------------------------------------------------------done---------------------------------------------------------------------------------------------------------%>
                            <c:choose>
                            <c:when
                                    test="${profile == 'userEdit' || profile == 'adminEdit' || profile == 'admin' || profile == 'ADD'}">
                            <div class="row btn-margin form-row">

                                <div class="col-sm-1  submit_btn">
                                    <label for="submit">Submit</label>
                                    <input type="submit" id="submit" class="btn btn-default btn-primary register_btn"
                                           value="Submit">
                                </div>
                                <div class="col-sm-2">
                                    <a href="" class="btn btn-default btn-primary cancel">Cancel</a>
                                </div>
                            </div>

                            </c:when>
                            <c:otherwise>
                            <div class="row btn-margin form-row">

                                <div class="col-sm-2  submit_btn">

                                    <input type="submit"
                                           class="btn btn-default btn-primary register_btn" value="Submit">
                                </div>
                                <div class="col-sm-2">

                                    <a href="./login.jsp"
                                       class="btn btn-default btn-primary btn_hide">Back to login</a>
                                </div>
                            </div>

                            </c:otherwise>
                            </c:choose>
    </form>
</div>

<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>--%>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>--%>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>--%>
<%--<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>--%>

<%--&lt;%&ndash;    Custom Validation JS &ndash;%&gt;--%>
<%--<script src="./assets/js/customValidation.js"></script>--%>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>--%>
<%--<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>--%>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>--%>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/3.3.2/select2.js"></script>--%>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/js/bootstrap-select.js"></script>--%>
<%--<script src="https://cdn.ckeditor.com/4.5.1/standard/ckeditor.js"></script>--%>
<%--<script src="assets/js/plugin.js" type="text/javascript"></script>--%>
<script src="./assets/js/CDNs/jquery_1.9.1.js"></script>
<script src="./assets/js/CDNs/code.jquery.com_jquery-3.4.1.js"></script>
<script src="./assets/js/CDNs/code.jquery.com_jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script src="./assets/js/CDNs/popper_1.14.7.js"></script>
<script src="./assets/js/CDNs/bootstrap_4.3.1.bundle.js"></script>
<script src="./assets/js/CDNs/bootstrap-datepicker_1.6.4.min.js"></script>
<script src="./assets/js/CDNs/select2_3.3.2.min.js"></script>
<script src="./assets/js/CDNs/bootstrap-select_1.13.18.js"></script>
<script src="./assets/js/CDNs/ckeditor.js"></script>
<script src="./assets/js/CDNs/ckeditorstyles.js.js"></script>
<script src="assets/js/plugin.js" type="text/javascript"></script>
<%--    Custom Validation JS --%>
<script src="./assets/js/customValidation.js"></script>

<script type="text/javascript">
    var parsed = new URL(location);
    var uname = parsed.searchParams.get("user");
    if (uname === 'ADD') {
        $(".btn_hide").hide();
        // $(".cancel").attr("href", "adminHomePage.jsp")
        $(".cancel").click(function () {
            // window.history.back();
            window.location.href = "adminHomePage.jsp";
            return false; // Prevents the default link behavior, and doesn't navigate to the link's href attribute value.
        });
    } else if (uname === 'adminEdit') {
        $(".btn_hide").hide();
        $("#default_img").hide();
        $(".header_tag").text("Update Profile");
        $(".register_btn").prop("value", "Update");
        $("#registration_form").attr("action", "UpdateProfile");
        $("#emailid").attr("readonly", "readonly");
        $("#pwd").attr("readonly", "readonly");
        $("#cpwd").attr("readonly", "readonly");
        $("#image_preview").css({
            "display": "block",
            "max-width": "170px",
            "max-height": "170px"
        });
// $(".cancel").attr("href", "adminHomePage.jsp")
        $(".cancel").click(function () {
// window.history.back();
            window.location.href = "adminHomePage.jsp";
            return false; // Prevents the default link behavior, and doesn't navigate to the link's href attribute value.
        });
    } else if (uname === 'userEdit') {
        $(".btn_hide").hide();
        $("#default_img").hide();
        $(".header_tag").text("Update Profile");
        $(".register_btn").prop("value", "Update");
        $("#registration_form").attr("action", "UpdateProfile");
        $("#emailid").attr("readonly", "readonly");
        $("#pwd").attr("readonly", "true");
        $("#cpwd").attr("readonly", "true");
        $("#image_preview").css({
            "display": "block",
            "max-width": "170px",
            "max-width": "170px"
        });
// $(".cancel").attr("href", "userHomePage.jsp")
        $(".cancel").click(function () {
// window.history.back();
            window.location.href = "userHomePage.jsp";
            return false; // Prevents the default link behavior, and doesn't navigate to the link's href attribute value.
        });

    } else if (uname === 'admin') {
        $(".btn_hide").hide();
        $("#default_img").hide();
        $(".header_tag").text("Update Profile");
        $(".register_btn").prop("value", "Update");
        $("#registration_form").attr("action", "UpdateProfile");
        $("#emailid").attr("readonly", "readonly");
        $("#pwd").attr("readonly", "true");
        $("#cpwd").attr("readonly", "true");
        $("#image_preview").css({
            "display": "block",
            "max-width": "170px",
            "max-width": "170px"
        });

        // $(".cancel").attr("href", "adminHomePage.jsp")
        $(".cancel").click(function () {
            // window.history.back();
            window.location.href = "adminHomePage.jsp";
            return false; // Prevents the default link behavior, and doesn't navigate to the link's href attribute value.
        });

    }

    //BELOW IS VV IMPORTANT IF DATE FORMAT NOT RIGHT or Different
    //setting the right DATE format WHILE EDITING
    <%--if (uname === 'adminEdit' || uname === 'userEdit' || uname === 'admin') {--%>
    <%--    $(document).ready(function () {--%>
    <%--        // Input date in 'mm-dd-yyyy' format--%>
    <%--        var inputDate = '${user.userDOB}';--%>

    <%--        // Split the input date into day, month, and year--%>
    <%--        var parts = inputDate.split('-');--%>
    <%--        var day = parts[0];--%>
    <%--        var month = parts[1];--%>
    <%--        var year = parts[2];--%>

    <%--        // Create a new date string in 'yyyy-mm-dd' format--%>
    <%--        var formattedDate = year + '-' + month + '-' + day;--%>

    <%--        // Set the formatted date as the value attribute of the date element--%>
    <%--        document.getElementById('dob').value = formattedDate;--%>
    <%--    });--%>
    <%--}--%>
</script>


</body>
<%--the include tag for footer is at the end of the document, waaaay below the commented code--%>
</html>



<%--<body class="bg-info bg_custom_color">--%>

<%--<div class="container">--%>

<%--    <form action="UserRegisterController" method="post" id="registration_form">--%>
<%--        &lt;%&ndash;will need to add enctype ABOVE in form tag for letting the user add an image&ndash;%&gt;--%>
<%--        <h3 style="text-align: center; font-family: Poppins;" class="margin_top_file header_tag">? Registration happens--%>
<%--            right here! ?</h3>--%>
<%--        &lt;%&ndash; cw &ndash;%&gt;--%>
<%--        <div class="row form-row">--%>
<%--            <div class="col-md-6">--%>

<%--                <div class="form-group">--%>

<%--                    <label for="img">Profile picture:</label> <input type="file"--%>
<%--                                                                     id="img2" name="img"/> <img--%>
<%--                        src="./assets/images/default_profile.jpg" name="default_img"--%>
<%--                        style="width: 170px; height: 170px;" id="default_img2"><img--%>
<%--                        src="data:image/jpg;base64,${user.base64Image}"--%>
<%--                        id="image_preview2" style="display: none"/> <br/> <span--%>
<%--                        id="image_error2"></span>--%>
<%--                </div>--%>

<%--            </div>--%>
<%--        </div>--%>

<%--        <div class="row form-row ">--%>
<%--            <div class="col-md-6">--%>
<%--                <!-- First Name -->--%>
<%--                <div class="form-group">--%>
<%--                    <label for="firstname">Firstname:</label> <input type="text"--%>
<%--                                                                     class="form-control text-width" id="firstname"--%>
<%--                                                                     name="firstname_from_registration"--%>
<%--                                                                     placeholder="Enter your firstname"/><span--%>
<%--                        id="firstname_error"></span>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <div class="col-md-6">--%>

<%--                <!-- Lastname -->--%>
<%--                <div class="form-group">--%>
<%--                    <label>Lastname:</label> <input type="text"--%>
<%--                                                    class="form-control" id="lastname" placeholder="Lastname"--%>
<%--                                                    name="lastname_from_registration"/> <span--%>
<%--                        id="lastname_error"></span>--%>
<%--                </div>--%>

<%--            </div>--%>
<%--        </div>--%>

<%--        <div class="row form-row ">--%>
<%--            <div class="col-md-6">--%>
<%--                <!-- Middle Name -->--%>
<%--                <div class="form-group">--%>
<%--                    <label for="middlename">Middlename:</label> <input type="text"--%>
<%--                                                                       class="form-control text-width" id="middlename"--%>
<%--                                                                       name="middlename_from_registration"--%>
<%--                                                                       placeholder="Enter your middlename"/><span--%>
<%--                        id="middlename_error"></span>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <div class="col-md-6">--%>

<%--                <!-- Username -->--%>
<%--                <div class="form-group">--%>
<%--                    <label>Username:</label> <input type="text"--%>
<%--                                                    class="form-control" id="username" placeholder="Enter Username"--%>
<%--                                                    name="username_from_registration"/> <span--%>
<%--                        id="username_error"></span>--%>
<%--                </div>--%>

<%--            </div>--%>
<%--        </div>--%>

<%--        <div class="row form-row">--%>
<%--            <div class="col-md-6">--%>
<%--                <!-- Email  -->--%>
<%--                <div class="form-group">--%>
<%--                    <label for="emailid">Email:</label> <input type="text"--%>
<%--                                                               class="form-control text-width"--%>
<%--                                                               id="emailid"--%>
<%--                                                               placeholder="Email-id" name="emailid_from_registration"/>--%>
<%--                    <span--%>
<%--                            id="email_error"></span><br>--%>
<%--                    <span id="result"></span>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <div class="col-md-6">--%>
<%--                <!-- password -->--%>
<%--                <div class="form-group">--%>
<%--                    <label for="pwd">Password:</label> <input type="password"--%>
<%--                                                              class="form-control text-width" id="pwd"--%>
<%--                                                              placeholder="Password"--%>
<%--                                                              name="password_from_registration"/> <span--%>
<%--                        id="password_error"></span>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>

<%--        <div class="row form-row">--%>


<%--            <!-- confirm password -->--%>
<%--            <div class="col-md-6">--%>
<%--                <div class="form-group">--%>
<%--                    <label for="cpwd">Confirm Password:</label> <input--%>
<%--                        type="password" class="form-control text-width" id="cpwd"--%>
<%--                        name="cpassword" placeholder="Confirm-Password"/> <span--%>
<%--                        id="confirm_password_error"></span>--%>
<%--                </div>--%>
<%--            </div>--%>

<%--        </div>--%>

<%--        <div class="row form-row">--%>
<%--            <div class="col-md-6">--%>

<%--                <!-- Hobby -->--%>

<%--                &lt;%&ndash;                temporarily commenting this&ndash;%&gt;--%>

<%--                &lt;%&ndash;&lt;%&ndash;                <label>Hobby</label> <br/>&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                <div class="form-check">&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                    <input type="checkbox" class="form-check-input" id="check1"&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                           name="options" value="Sports"> <label&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                        class="form-check-label" for="check1">Sports</label>&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                </div>&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                <div class="form-check">&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                    <input type="checkbox" class="form-check-input" id="check2"&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                           name="options" value="Study"> <label&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                        class="form-check-label" for="check2">Study</label>&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                </div>&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                <div class="form-check">&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                    <input type="checkbox" class="form-check-input" id="check3"&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                           name="options" value="Dance"> <label&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                        class="form-check-label">Dance</label>&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                </div>&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                <div class="form-check">&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                    <input type="checkbox" class="form-check-input" id="check4"&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                           name="options" value="Music"> <label&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                        class="form-check-label">Music</label> <br/> <span&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                        id="check_error"></span>&ndash;%&gt;&ndash;%&gt;--%>
<%--                &lt;%&ndash;&lt;%&ndash;                </div>&ndash;%&gt;&ndash;%&gt;--%>

<%--                &lt;%&ndash;            </div>&ndash;%&gt;--%>

<%--                <!-- hobbies -->--%>
<%--                <div class="form-group">--%>
<%--                    <label for="hobbies">Hobby:</label> <input type="text"--%>
<%--                                                               class="form-control text-width" id="hobbies"--%>
<%--                                                               placeholder="Hobbies"--%>
<%--                                                               name="hobbies_from_registration"/> <span--%>
<%--                        id="hobbies_error"></span>--%>
<%--                </div>--%>
<%--                <div class="col-md-6">--%>
<%--                    <!-- image upload -->--%>
<%--                    <div class="form-group">--%>
<%--                        <label for="dob">Date Of Birth:</label> <input type="date"--%>
<%--                                                                       class="form-control text-width" id="dob"--%>
<%--                                                                       name="dob_from_registration" min="1900-01-01"--%>
<%--                                                                       max=""/> <span--%>
<%--                            id="dob_error"></span>--%>
<%--                    </div>--%>
<%--                </div>--%>

<%--            </div>--%>
<%--            &lt;%&ndash;  /cw &ndash;%&gt;--%>

<%--            <!-- jQuery plugin code -->--%>
<%--            <label class="address_label_margin">Address:</label>--%>

<%--            <div class="margin-t-md">--%>
<%--                <div class="customer-form">--%>
<%--                    <div class="card"></div>--%>
<%--                    &lt;%&ndash; cw &ndash;%&gt;--%>
<%--                    <div id="main-container">--%>
<%--                        <div class="panel card container-item">--%>
<%--                            <div class="panel-body">--%>
<%--                                <div class="panel-body">--%>
<%--                                    <div class="row">--%>
<%--                                        <div class="col-sm-4">--%>
<%--                                            <div class="form-group">--%>
<%--                                                <label class="control-label" for="houseno">House No.</label>--%>
<%--                                                <input type="text" id="houseno" class="form-control"--%>
<%--                                                       name="houseno" maxlength="10">--%>
<%--                                                <span id="houseno_error"></span>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                    <div class="row">--%>
<%--                                        <div class="col-sm-4">--%>
<%--                                            <div class="form-group">--%>
<%--                                                <label class="control-label" for="street">Street</label>--%>
<%--                                                <input type="text" id="street" class="form-control"--%>
<%--                                                       name="address" maxlength="20">--%>
<%--                                                <span id="street_error"></span>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                        <div class="col-sm-4">--%>
<%--                                            <div class="form-group">--%>
<%--                                                <label class="control-label" for="landmark">Landmark</label>--%>
<%--                                                <input type="text" id="landmark" class="form-control"--%>
<%--                                                       name="landmark" maxlength="50"> <span--%>
<%--                                                    id="landmark_error"></span>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                        <div class="col-sm-4">--%>
<%--                                            <div class="form-group">--%>
<%--                                                <label class="control-label" for="pincode">Zipcode</label>--%>
<%--                                                <input type="text" id="pincode" class="form-control"--%>
<%--                                                       name="zipcode" maxlength="10"> <span--%>
<%--                                                    id="pincode_error"></span>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                    <div class="row">--%>

<%--                                        <div class="col-sm-6">--%>
<%--                                            <div class="form-group">--%>
<%--                                                <label class="control-label" for="city">City</label> <select--%>
<%--                                                    class="form-control" name="city" id="city"--%>
<%--                                                    style="height: auto;">--%>
<%--                                                <option value="0">Select City</option>--%>
<%--                                                <option value="Delhi">Delhi</option>--%>
<%--                                                <option value="Bangalore">Bangalore</option>--%>
<%--                                                <option value="Chennai">Chennai</option>--%>
<%--                                                <option value="Ladakh">Ladakh</option>--%>
<%--                                                <option value="Gandhinagar">Gandhinagar</option>--%>
<%--                                            </select> <span id="city_error"></span>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>


<%--                                        <div class="col-sm-6">--%>
<%--                                            <div class="form-group">--%>
<%--                                                <label class="control-label" for="state">State</label> <select--%>
<%--                                                    class="form-control" name="state" id="state"--%>
<%--                                                    style="height: auto;">--%>
<%--                                                <option value="0">Select State</option>--%>
<%--                                                <option value="Punjab">Punjab</option>--%>
<%--                                                <option value="Haryana">Haryana</option>--%>
<%--                                                <option value="Karnataka">Karnataka</option>--%>
<%--                                                <option value="Sikkim">Sikkim</option>--%>
<%--                                                <option value="Maharashtra">Maharashtra</option>--%>
<%--                                            </select> <span id="state_error"></span>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                    <div class="row">--%>
<%--                                        <div class="col-sm-6">--%>
<%--                                            <div class="form-group">--%>
<%--                                                <label class="control-label" for="country">Country</label>--%>
<%--                                                <input type="text" id="country" class="form-control"--%>
<%--                                                       name="country" maxlength="255">--%>
<%--                                                <span id="country_error"></span>--%>
<%--                                            </div>--%>
<%--                                            <div class="col-sm-6">--%>
<%--                                                <div class="form-group">--%>
<%--                                                    <label class="control-label" for="postaladdress">Postal--%>
<%--                                                        Address</label>--%>
<%--                                                    <input type="text" id="postaladdress" class="form-control"--%>
<%--                                                           name="postaladdress" maxlength="250">--%>
<%--                                                    <span id="postaladdress_error"></span>--%>
<%--                                                </div>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>

<%--                                    <div class="row">--%>
<%--                                        <div class="col-sm-12">--%>
<%--                                            <div>--%>
<%--                                                <a href="javascript:void(0)"--%>
<%--                                                   class="remove-item btn btn-sm btn-danger remove-social-media">Remove</a>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                    <div>--%>
<%--                        <a class="btn btn-success btn-sm" id="add-more"--%>
<%--                           href="javascript:;" role="button"><i class="fa fa-plus"></i>--%>
<%--                            Add one more address</a>--%>
<%--                    </div>--%>
<%--                    &lt;%&ndash; /cw &ndash;%&gt;--%>
<%--                </div>--%>
<%--            </div>--%>


<%--            &lt;%&ndash; cw &ndash;%&gt;--%>
<%--            <div class="row btn-margin form-row" style="margin-bottom: 70px">--%>

<%--                <div class="col-sm-1 submit_btn">--%>

<%--                    <input type="submit"--%>
<%--                           class="btn btn-default btn-primary register_btn" value="Submit">--%>
<%--                </div>--%>
<%--                <div class="col-sm-2">--%>

<%--                    <a href="login.jsp"--%>
<%--                       class="btn btn-default btn-primary btn_hide">Already have an account? LOGIN!</a>--%>
<%--                </div>--%>
<%--            </div>--%>

<%--            &lt;%&ndash; /cw &ndash;%&gt;--%>
<%--    </form>--%>
<%--</div>--%>

<%--<!-- address code ends -->--%>

<%--<script--%>
<%--        src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js">--%>

<%--</script>--%>
<%--<script--%>
<%--        src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>--%>
<%--<script--%>
<%--        src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>--%>

<%--<script--%>
<%--        src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>--%>
<%--<script--%>
<%--        src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>--%>
<%--<script--%>
<%--        src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>--%>
<%--<script--%>
<%--        src="https://cdnjs.cloudflare.com/ajax/libs/select2/3.3.2/select2.js"></script>--%>
<%--<script--%>
<%--        src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/js/bootstrap-select.js"></script>--%>
<%--<script src="https://cdn.ckeditor.com/4.5.1/standard/ckeditor.js"></script>--%>
<%--<script src="assets/js/plugin.js" type="text/javascript"></script>--%>
<%--<script type="text/javascript" src="assets/js/customValidation.js"></script>--%>

<%--<script type="text/javascript">--%>
<%--    var parsed = new URL(location);--%>
<%--    var uname = parsed.searchParams.get("user");--%>
<%--    if (uname === 'ADD') {--%>
<%--        $(".btn_hide").hide();--%>
<%--        $(".cancel").attr("href", "adminHomePage.jsp")--%>

<%--    } else if (uname === 'adminEdit') {--%>
<%--        $(".btn_hide").hide();--%>
<%--        $("#default_img").hide();--%>
<%--        $(".header_tag").text("Update Profile");--%>
<%--        $(".register_btn").prop("value", "Update");--%>
<%--        $("#registration_form").attr("action", "UpdateProfile");--%>
<%--        $("#mail").attr("readonly", "readonly");--%>
<%--        $("#pwd").attr("readonly", "readonly");--%>
<%--        $("#cpwd").attr("readonly", "readonly");--%>
<%--        $("#image_preview").css({--%>
<%--            "display": "block",--%>
<%--            "max-width": "170px",--%>
<%--            "max-height": "170px"--%>
<%--        });--%>
<%--        $(".cancel").attr("href", "adminHomePage.jsp")--%>
<%--    } else if (uname === 'userEdit') {--%>
<%--        $(".btn_hide").hide();--%>
<%--        $("#default_img").hide();--%>
<%--        $(".header_tag").text("Update Profile");--%>
<%--        $(".register_btn").prop("value", "Update");--%>
<%--        $("#registration_form").attr("action", "UpdateProfile");--%>
<%--        $("#mail").attr("readonly", "readonly");--%>
<%--        $("#pwd").attr("readonly", "true");--%>
<%--        $("#cpwd").attr("readonly", "true");--%>
<%--        $("#image_preview").css({--%>
<%--            "display": "block",--%>
<%--            "max-width": "170px",--%>
<%--            "max-width": "170px"--%>
<%--        });--%>
<%--        $(".cancel").attr("href", "UserHomePage.jsp")--%>

<%--    } else if (uname === 'admin') {--%>
<%--        $(".btn_hide").hide();--%>
<%--        $("#default_img").hide();--%>
<%--        $(".header_tag").text("Update Profile");--%>
<%--        $(".register_btn").prop("value", "Update");--%>
<%--        $("#registration_form").attr("action", "UpdateProfile");--%>
<%--        $("#mail").attr("readonly", "readonly");--%>
<%--        $("#pwd").attr("readonly", "true");--%>
<%--        $("#cpwd").attr("readonly", "true");--%>
<%--        $("#image_preview").css({--%>
<%--            "display": "block",--%>
<%--            "max-width": "170px",--%>
<%--            "max-width": "170px"--%>
<%--        });--%>
<%--        $(".cancel").attr("href", "adminHomePage.jsp")--%>
<%--    }--%>
<%--</script>--%>

<%--</body>--%>
<%--</html>--%>

<jsp:include page="./WEB-INF/views/footer.jsp"/>

<%-- fiels for SIMPLE TRIAL FORM --%>
<%--    <input type="text" id="firstname_from_registration" name="firstname_from_registration" placeholder="enter firstname">--%>
<%--    <input type="text" id="middlename_from_registration" name="middlename_from_registration" placeholder="enter middlename">--%>
<%--    <input type="text" id="lastname_from_registration" name="lastname_from_registration" placeholder="enter lastname">--%>
<%--    <input type="text" id="emailid_from_registration" name="emailid_from_registration" placeholder="enter emailid">--%>
<%--    <input type="text" id="dob_from_registration" name="dob_from_registration" placeholder="enter date of birth">--%>
<%--    <input type="text" id="hobbies_from_registration" name="hobbies_from_registration" placeholder="enter hobbies">--%>
<%--    <input type="text" id="username_from_registration" name="username_from_registration" placeholder="enter username">--%>
<%--    <input type="text" id="password_from_registration" name="password_from_registration" placeholder="enter password">--%>
<%--    <input type="hidden" id="pageIdentification" name="pageIdentification" value="comingFromRegistrationPage">--%>
<%--    <input type="submit" value="submit">--%>
