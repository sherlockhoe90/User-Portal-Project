package com.inexture.userportal.userportalproject.controller;

import com.inexture.userportal.userportalproject.model.User;
import com.inexture.userportal.userportalproject.services.UserService;
import com.inexture.userportal.userportalproject.services.UserServiceImp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
            /*
             * using sendRedirect instead of RequestDispatcher, as the RD was enabling
             * the user to go back to the viewUser Servlet instead of the viewUsers JSP,
             * causing the session to get picked up again and get validated as an admin
             */
            response.sendRedirect("viewUsers.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
