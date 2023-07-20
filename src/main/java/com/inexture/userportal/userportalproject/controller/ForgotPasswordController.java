package com.inexture.userportal.userportalproject.controller;

import com.inexture.userportal.userportalproject.model.User;
import com.inexture.userportal.userportalproject.services.UserService;
import com.inexture.userportal.userportalproject.services.UserServiceImp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.*;
import java.sql.SQLException;
import java.util.Objects;

//@WebServlet(name = "ForgotPasswordController", value = "/ForgotPasswordController")








/*USE HIDDEN INPUT FIELDS TO CONTROL WHAT METHOD THIS GOES INTO .....
 * IF THE PARAMETER IS EQUAL TO comingFromExists THEN IT WON'T CHECK THE EMAIL AGAIN,
 * IT'LL GO STRAIGHT TO VERIFYING THE VERIFICATION CODE */


public class ForgotPasswordController extends HttpServlet {

    private static Logger logger = LogManager.getLogger("ForgotPasswordController");

    UserService service = new UserServiceImp();
    User user = new User();
    HttpSession session;
    int RandomNumber;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (session == null) {
            session = request.getSession();
        }
        //random number between 0 and 1 is generated, then multiplied manually by 10^6 to get a six digit number
        if (RandomNumber == 0) {
            RandomNumber = (int) Math.floor((Math.random()) * Math.pow(10, 6)); //for verification code
        }
        String emailid = request.getParameter("emailid");
        String pageIdentification = request.getParameter("pageIdentification");

        //check if email exists, if it does, send the verification codes
        if (pageIdentification.equals("emailDoesntExist") || pageIdentification.equals("getVerificationCode")) {
            if (service.checkEmail(emailid)) { //if emailid EXISTS in the database
                user.setUserEmailID(emailid);
                session.setAttribute("CurrentUser", user);
                session.setAttribute("VerificationCode", RandomNumber);

                logger.info("the Verification Code is : " + session.getAttribute("VerificationCode"));
                //response.sendRedirect(request.getContextPath() + "/forgotPassword.jsp?emailExists=exists");
                RequestDispatcher rd = request.getRequestDispatcher("forgotPassword.jsp?emailExists=exists");
                rd.forward(request, response);
            } else if (!service.checkEmail(emailid)) { // if emailid DOESN'T EXIST in the database
                //response.sendRedirect(request.getContextPath() +  "/forgotPassword.jsp?emailExists=doesNotExist");
                RequestDispatcher rd = request.getRequestDispatcher("forgotPassword.jsp?emailExists=doesNotExist");
                rd.forward(request, response);
            }
        }

        if (pageIdentification.equals("sendingVerificationCode")) {
            //verification code check, and redirection to get new password
            String verificationcode = request.getParameter("verificationcode");
//        if (!(verificationcode == null) && !(verificationcode.equals(""))) {
            if (verificationcode.equals(String.valueOf(session.getAttribute("VerificationCode")))) { //if Verification Code matches
                //response.sendRedirect(request.getContextPath() + "/forgotPassword.jsp?emailExists=getNewPassword");
                RequestDispatcher rd = request.getRequestDispatcher("forgotPassword.jsp?emailExists=getNewPassword");
                rd.forward(request, response);
                //session.setAttribute("CurrentUser", user);
            } else if (!verificationcode.equals(String.valueOf(session.getAttribute("VerificationCode")))) { // if verification code is wrong
                //response.sendRedirect(request.getContextPath() + "/forgotPassword.jsp?emailExists=exists");
                RequestDispatcher rd = request.getRequestDispatcher("forgotPassword.jsp?emailExists=exists");
                rd.forward(request, response);
            }
//        }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pageIdentification = request.getParameter("pageIdentification");

        try {
        if (pageIdentification.equals("sendingNewPassword")) {
            String newPassword = request.getParameter("newpassword");
            user.setUserPassword(newPassword);
            service.updatePassword(user);

            RequestDispatcher rd = request.getRequestDispatcher("forgotPassword.jsp?emailExists=passwordChangedSuccessfully");
            rd.forward(request, response);
            session.invalidate();
        }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}
