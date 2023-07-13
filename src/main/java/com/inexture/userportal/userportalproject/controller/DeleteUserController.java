package com.inexture.userportal.userportalproject.controller;

import com.inexture.userportal.userportalproject.services.UserService;
import com.inexture.userportal.userportalproject.services.UserServiceImp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

//@WebServlet(name = "DeleteUserController", value = "/DeleteUserController")
public class DeleteUserController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public DeleteUserController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
//        String userIdReceived = request.getParameter("userId");
//        request.setAttribute("userId",userIdReceived);
//        doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("userId");
//        doGet(request, response);
        UserService serviceobj = new UserServiceImp();
        try {
            // to delete the user
            serviceobj.deleteUser(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
