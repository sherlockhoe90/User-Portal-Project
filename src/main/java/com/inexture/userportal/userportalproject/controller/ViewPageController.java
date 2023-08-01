package com.inexture.userportal.userportalproject.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

public class ViewPageController extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/viewUsers.jsp");
        rd.forward(req, res);
    }
}
