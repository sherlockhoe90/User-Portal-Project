package com.inexture.userportal.userportalproject.controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//@WebServlet(name = "LogoutController", value = "/logout")
public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LogManager.getLogger("LogoutController");


    int callSequence = 0;
    public LogoutController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
        logger.info("LogoutController.java : doGet was called in Sequence " + ++callSequence);
    }

//    @Override
//    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
//        doPost((HttpServletRequest) req, (HttpServletResponse) res);
//        System.out.println("LogoutController.java : Service was called.");
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("LogoutController.java : doPost was called in Sequence " + ++callSequence);

        // to invalidate the session
        HttpSession session = request.getSession(false);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
        session.setAttribute("userRole", null); //login.jsp checks for this
        logger.info("value of userRole : " + session.getAttribute("userRole"));
        session.invalidate(); //removes session from the registry
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher req = request.getRequestDispatcher("/login.jsp");
        req.forward(request, response);
        response.sendRedirect("/login.jsp");
    }

}

