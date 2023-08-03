package com.inexture.userportal.userportalproject.controller;

import com.inexture.userportal.userportalproject.model.Address;
import com.inexture.userportal.userportalproject.model.User;
import com.inexture.userportal.userportalproject.services.AddressService;
import com.inexture.userportal.userportalproject.services.AddressServiceImp;
import com.inexture.userportal.userportalproject.services.UserService;
import com.inexture.userportal.userportalproject.services.UserServiceImp;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminEditController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminEditController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        // String uName = (String) session.getAttribute("userName");
        UserService service = new UserServiceImp();
        AddressService addService = new AddressServiceImp();

        String userId = request.getParameter("userId");

        try {
            // to get details of a particular user
            List<User> userData = service.getUserDetails(userId);
            User user = userData.get(0); //getting the details of the user in the object
            session.setAttribute("CurrentUser", user); // setting the object into the session
            List<Address> listAddress = addService.getAllAddress(Integer.parseInt(userId));
            session.setAttribute("AddressList", listAddress);
            RequestDispatcher req = request.getRequestDispatcher("registration.jsp?user=adminEdit");
            req.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

