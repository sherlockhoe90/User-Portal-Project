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
import java.sql.SQLException;
import java.util.List;

//@WebServlet(name = "ViewUserDetailsController", value = "/ViewUserDetailsController")
public class ViewUserDetailsController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LogManager.getLogger("ViewUserDetailsController");


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // object of User model created
        User user = new User();

        // object of UserService created
        UserService serviceObject = new UserServiceImp();

        try {
            List<User> list = serviceObject.displayUser(user);
            HttpSession session = request.getSession();
            session.setAttribute("userList", list);
            RequestDispatcher req = request.getRequestDispatcher("viewUsers.jsp");
            req.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
