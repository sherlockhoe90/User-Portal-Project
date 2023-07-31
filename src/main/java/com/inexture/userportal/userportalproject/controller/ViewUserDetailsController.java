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

    // object of User model created
    User user = new User();

    // object of UserService created
    UserService serviceObject = new UserServiceImp();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("doPOST method called, but it shouldn't be.\nThe doGET should've been called using the new ajax setting.");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        List<User> list;
        int currentPage;
        int recordsPerPage;

        currentPage = Integer.parseInt(request.getParameter("currentPage"));
        recordsPerPage = Integer.parseInt(request.getParameter("recordsPerPage"));

        try {
            list = serviceObject.displayUser(currentPage, recordsPerPage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("userList", list);

        int rows = serviceObject.getNumberOfRows();

        int nOfPages = rows / recordsPerPage;

        if (nOfPages % recordsPerPage > 0) {

            nOfPages++;
        }

        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);

        RequestDispatcher dispatcher = request.getRequestDispatcher("viewUsers.jsp");
        dispatcher.forward(request, response);
    }
}
