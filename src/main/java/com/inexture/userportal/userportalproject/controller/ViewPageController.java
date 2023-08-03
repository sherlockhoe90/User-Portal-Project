package com.inexture.userportal.userportalproject.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ViewPageController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LogManager.getLogger("ViewPageController");


    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        RequestDispatcher rd = req.getRequestDispatcher("/viewUsers.jsp");
        rd.forward(req, res);
        logger.info("Forwarded the flow to the 'viewUsers.jsp' page.");
    }
}
