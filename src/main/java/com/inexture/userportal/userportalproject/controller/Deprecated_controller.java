package com.inexture.userportal.userportalproject.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Objects;

@Deprecated //IN ORDER TO DEPRECATE IT AND SHOW IT WITH A Strikethrough

//@WebServlet(name = "Deprecated_controller", value = "/Deprecated_controller")
public class Deprecated_controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        String comingFrom = request.getParameter("pageIdentification");
//
//        if(Objects.equals(comingFrom, "comingFromLoginPage")) {
//            String username_entered_login = request.getParameter("username_from_login");
//            String password_entered_login = request.getParameter("password_from_login");
//
//
//        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //getting the information about where it is called from
        String comingFrom = request.getParameter("pageIdentification");

        /*if coming from registration page*/
        if (Objects.equals(comingFrom, "comingFromRegistrationPage")) {
            System.out.println("yeah coming from registration page");

            HttpSession session = request.getSession();

            String sessionid = session.getId();
            System.out.println("the session id is: " + sessionid);

            String firstname = request.getParameter("firstname_from_registration");
            String middlename = request.getParameter("middlename_from_registration");
            String lastname = request.getParameter("lastname_from_registration");
            String dob = request.getParameter("dob_from_registration");
            String emailid = request.getParameter("emailid_from_registration");
            String hobbies = request.getParameter("hobbies_from_registration");
            String username = request.getParameter("username_from_registration");
            String password = request.getParameter("password_from_registration");

            System.out.println("from Deprecated_controller :\n" + firstname+ " " + middlename + " " + lastname + " " + dob + " " + emailid + " " + hobbies + " " + username + " " + password);

            request.setAttribute("firstnameFromController", firstname);
            request.setAttribute("middlenameFromController", middlename);
            request.setAttribute("lastnameFromController", lastname);
            request.setAttribute("dobFromController", dob );
            request.setAttribute("emailidFromController", emailid);
            request.setAttribute("hobbiesFromController",hobbies);
            request.setAttribute("usernameFromController", username);
            request.setAttribute("passwordFromController", password);

            RequestDispatcher rd = request.getRequestDispatcher("zDeprecated_userDetails");
            rd.forward(request,response);
        }
    }
}
