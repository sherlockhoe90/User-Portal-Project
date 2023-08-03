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

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserLoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LogManager.getLogger("UserLoginController");
//    private static final Logger logger = LogManager.getLogger(UserLoginController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("hello from UserLoginController doGet");
        doPost(request, response);
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

        /* The error you're encountering, "Store of non serializable object into HttpSession," is related to storing
        non-serializable objects in an HttpSession. This can potentially cause issues when the session needs to be
        serialized and persisted, such as when the server restarts or when sessions are migrated between different instances.
        Let's break down the issue and discuss how to address it: CurrentUser, and AddressList

         * In your UserLoginController servlet, you are storing the User and List<Address> objects in the session:

         * By implementing the Serializable interface, you are telling Java that instances of these classes can be
         safely serialized and deserialized, which prevents potential issues when storing them in the session.

         * Additionally, make sure that any other classes you use in your servlet that are stored in the session also
         implement Serializable if they are part of the session attributes.

        */

        List<User> list1; /*for admin*/
        // List<User> list2; /*for user*/ declared
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
                    /*
                     * using sendRedirect instead of RequestDispatcher, as the RD was enabling
                     * the user to go back to the Login Servlet instead of the login JSP,
                     * causing the session to get picked up again and get validated as an admin
                     */
                    response.sendRedirect("adminHomePage.jsp");
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
                    logger.info("UserLoginController: user value GOT......" + list2.toString());
                    // int id = list2.get(0).getUserId();
                    int id = list2.getUserId();
                    session.setAttribute("CurrentUser", user);
                    session.setAttribute("specificUserData", list2);

                    listAddress = addservice.getAllAddress(id);
                    logger.info("UserLoginController: listAddress value " + listAddress.toString());
                    session.setAttribute("AddressList", listAddress);
                    // session.setAttribute("user", user);
                    /*
                     * using sendRedirect instead of RequestDispatcher, as the RD was enabling
                     * the user to go back to the Login Servlet instead of the login JSP,
                     * causing the session to get picked up again and get validated as a valid User
                     */
                    response.sendRedirect("userHomePage.jsp");
                } catch (SQLException e) {
                    e.printStackTrace();
                    logger.error(e.toString());
                }
            }
        } else {
            RequestDispatcher req = request.getRequestDispatcher("login.jsp?authenticationStatus=wrong");
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
