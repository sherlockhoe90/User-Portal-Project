package com.inexture.userportal.userportalproject.controller;

import com.inexture.userportal.userportalproject.model.Address;
import com.inexture.userportal.userportalproject.model.User;
import com.inexture.userportal.userportalproject.services.AddressService;
import com.inexture.userportal.userportalproject.services.AddressServiceImp;
import com.inexture.userportal.userportalproject.services.UserService;
import com.inexture.userportal.userportalproject.services.UserServiceImp;
import com.inexture.userportal.userportalproject.utility.PasswordEncryption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.security.krb5.EncryptedData;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//@WebServlet(name = "UserLoginController", value = "/UserLoginController")
public class UserLoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LogManager.getLogger("UserLoginController");
//    private static final Logger logger = LogManager.getLogger(UserLoginController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("hello from UserLoginController doGet");
        doPost(request,response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        HttpSession session = request.getSession();
        UserService service = new UserServiceImp();
        AddressService addservice = new AddressServiceImp();
        // User user = (User) session.getAttribute("CurrentUser");
        User user = new User();
        response.setContentType("text/html");

        user.setUserEmailID(request.getParameter("emailid_from_login"));
        String EncryptedPassword = PasswordEncryption.encrypt(request.getParameter("password_from_login"));
        user.setUserPassword(EncryptedPassword);
        // checking if the encrypted password matches with the encrypted password in the databsse
        boolean isValid = service.compareUserLogin(user);

        List<User> list1; /*for admin*/
        // List<User> list2; /*for user*/
        List<Address> listAddress;

        if (isValid) { // if TRUE i.e. the account exists in db
            if (user.getUserStatus()) { // if TRUE i.e. ADMIN
                try {
                    //setting the type of user, so that they'll be redirected to the appropriate homepage when they go bak to login after logging in
                    session.setAttribute("userRole", "admin");

                    list1 = service.displayAdmin(user);
//                    int id = list1.get(0).getUserId();
                    int id = user.getUserId();
                    listAddress = addservice.getAllAddress(id);
                    session.setAttribute("adminList", list1);
                    session.setAttribute("CurrentUser", user);

                    session.setAttribute("AddressList", listAddress);

                    RequestDispatcher req = request.getRequestDispatcher("/adminHomePage.jsp");
                    req.include(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }

                logger.info("UserLoginController: ADMIN has logged in");
            } else { // if FALSE i.e. NORMAL USER
                try {
                    //setting the type of user, so that they'll be redirected to the appropriate homepage when they go bak to login after logging in
                    session.setAttribute("userRole", "user");

                    User list2 = service.displaySpecificUser(user);
                    System.out.println("UserLoginController: user value GOT......" + list2.toString());
                    // int id = list2.get(0).getUserId();
                    int id = list2.getUserId();
                    session.setAttribute("CurrentUser", user);
                    session.setAttribute("specificUserData", list2);

                    listAddress = addservice.getAllAddress(id);
                    logger.info("UserLoginController: listAddress value " + listAddress.toString());
                    session.setAttribute("AddressList", listAddress);
                    // session.setAttribute("user", user);
                    RequestDispatcher req = request.getRequestDispatcher("/userHomePage.jsp");
                    req.include(request, response);
                } catch (SQLException e) {
                     e.printStackTrace();
                    logger.error(e.toString());
                }
            }
        } else {
            RequestDispatcher req = request.getRequestDispatcher("login.jsp");
            req.forward(request, response);
        }





















//        HttpSession session = request.getSession();
//        UserService service = new UserServiceImp();
//        AddressService addservice = new AddressServiceImp();
//        // User user = (User) session.getAttribute("CurrentUser");
//        User user = new User();
//        response.setContentType("text/html");
//
//
//        user.setUserEmailID(request.getParameter("emailid_from_login"));
//        user.setUserPassword(request.getParameter("password_from_login"));
//
//        boolean isValidUser = service.compareUserLogin(user);
//
//        List<User> list1;
//        // List<User> list2;
//        List<Address> listAddress;
//
//        if (isValidUser) {
//            /*admin*/
//            if ((user.getUserRole()).equals("admin")) {
//                System.out.println("UserLoginController: show something for admin");
//                /*user*/
//            } else if ((user.getUserRole()).equals("user")) {
//                System.out.println("UserLoginController: show something for user");
//                try {
//                    service.showUser(user);
//                    request.setAttribute("userObject",user);
//
//                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/z_Delete_userHomePage.jsp");
//                    rd.forward(request,response);
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        } else {
//            RequestDispatcher req = request.getRequestDispatcher("login.jsp");
//            req.forward(request, response);
//            System.out.println("UserLoginController: wrong emailid or password");
//        }
    }


}
