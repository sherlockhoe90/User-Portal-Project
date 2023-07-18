package com.inexture.userportal.userportalproject.controller;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
//import java.util.logging.Logger;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.inexture.userportal.userportalproject.dao.UserDAOImp;
import com.inexture.userportal.userportalproject.model.Address;
import com.inexture.userportal.userportalproject.model.User;
import com.inexture.userportal.userportalproject.services.AddressService;
import com.inexture.userportal.userportalproject.services.AddressServiceImp;
import com.inexture.userportal.userportalproject.services.UserService;
import com.inexture.userportal.userportalproject.services.UserServiceImp;
import com.inexture.userportal.userportalproject.utility.AgeCalculator;
import com.inexture.userportal.userportalproject.utility.DatabaseManager;

//@WebServlet(name = "UserRegisterController", value = "/UserRegisterController")
@MultipartConfig
public class UserRegisterController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LogManager.getLogger("UserRegisterController");

    public UserRegisterController() {
        super();
    }

    //    DatabaseManager dm = new DatabaseManager();
    Connection c;

    public void init(ServletConfig config) throws ServletException {
        // create connection if one hasn't already been made before
        if (c == null) {
            c = DatabaseManager.getConnection();
            logger.info("UserRegisterController: Value of the getConnection object in init: " + c);
        }

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession sessionInvalidate = request.getSession();
        sessionInvalidate.invalidate();

        int id = 0;

        UserService service = new UserServiceImp();
        AddressService addservice = new AddressServiceImp();
        User user = new User();
        Map<String, String> messages = new HashMap<String, String>();
        request.setAttribute("messages", messages);


//        Part file = request.getPart("img");
//        logger.info("UserRegisterController: this is what i've got in the name of an image: " + file);
//        if (file.getSize() == 0) {
//
////            InputStream fis = Files.newInputStream(Paths.get("./../webapp/assets/images/default_profile.jpg"));
////            InputStream fis = new FileInputStream("D:/Java/InextureSolutionsTraining_Images/default_profile.jpg");
//            user.setUserProfile(fis);
//        } else {
//            InputStream imgContent = file.getInputStream();
//            user.setUserProfile(imgContent);
//        }

        user.setUserFirstname(request.getParameter("firstname"));
        user.setUserMiddlename(request.getParameter("middlename"));
        user.setUserLastname(request.getParameter("lastname"));
        user.setUserEmailID(request.getParameter("emailid"));
        user.setUserUsername(request.getParameter("username"));
        user.setUserPassword(request.getParameter("pwd"));
        user.setUserDOB(request.getParameter("dob"));
        user.setUserAge(AgeCalculator.calculateAge(user.getUserDOB()));
        user.setUserHobbies(request.getParameter("hobbies"));

        //printing the user details that i've got
        user.toString();

        id = service.userRegister(user); /*this line submits the code into the database, and returns the userID*/
        String[] houseno = request.getParameterValues("houseno[]");
        String[] street = request.getParameterValues("address[]");
        String[] landmark = request.getParameterValues("landmark[]");
        String[] zipcode = request.getParameterValues("zipcode[]");
        String[] city = request.getParameterValues("city[]");
        String[] state = request.getParameterValues("state[]");
        String[] country = request.getParameterValues("country[]");
        String[] postaladdress = request.getParameterValues("postaladdress[]");


        int count = 0;
        int initialaddID = 0;
        while (count < street.length) {
            Address addobj = new Address();
            addobj.setAddId(String.valueOf(++initialaddID));
            addobj.setAddHouseNo(houseno[count]);
            addobj.setAddStreet(street[count]);
            addobj.setAddLandmark(landmark[count]);
            addobj.setAddCity(city[count]);
            addobj.setAddState(state[count]);
            addobj.setAddZipcode(zipcode[count]);
            addobj.setAddCountry(country[count]);
            addobj.setAddPostalAdd(postaladdress[count]);

            addservice.addAddress(id, addobj);
            count++;
        }

        //printing the address that i've got
        Address addModel = new Address();
        addModel.toString();

        HttpSession session = request.getSession();
        String uName = (String) session.getAttribute("userFirstname");
        logger.info("uName" + uName);


//        if (uName != null) {
//            response.sendRedirect("login.jsp");
//        } else {
//            // UserService service = new UserServiceImpl();
//            List<User> adminList;
//            try {
//                adminList = service.displayAdmin(user);
//                session.setAttribute("adminList", adminList);
//                List<Address> listAddress = addservice.getAllAddress(id);
//                session.setAttribute("AddressList", listAddress);
//                response.sendRedirect("AdminHomePage.jsp");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }

        //after registration is done, redirect back to the same page
        response.sendRedirect("registration.jsp");














//        HttpSession sessionInvalidate = request.getSession();
//        sessionInvalidate.invalidate();
//
//        int id = 0;
//
//        UserService service = new UserServiceImp();
//        AddressService addservice = new AddressServiceImp();
//        User user = new User();
//        Map<String, String> messages = new HashMap<String, String>();
//        request.setAttribute("messages", messages);
//
//
//        user.setUserFirstname(request.getParameter("firstname_from_registration"));
//        user.setUserMiddlename(request.getParameter("middlename_from_registration"));
//        user.setUserLastname(request.getParameter("lastname_from_registration"));
//        user.setUserEmailID(request.getParameter("emailid_from_registration"));
//        user.setUserUsername(request.getParameter("username_from_registration"));
////        user.setUserPassword(request.getParameter("password"));
//        user.setUserDOB(request.getParameter("dob_from_registration"));
//        user.setUserAge(AgeCalculator.calculateAge(user.getUserDOB()));
//        user.setUserHobbies(request.getParameter("hobbies_from_registration"));
//
//        //        System.out.println("birthday: " + request.getParameter("dob_from_registration"));
////        user.setUserAge(AgeCalculator.calculateAge(request.getParameter("dob_from_registration")));
//
////        String hobbies = "";
////        String[] hobby = request.getParameterValues("options");
////        for (int i = 0; i < hobby.length; i++) {
////            hobbies += hobby[i] + ",";
////        }
////        user.setUserHobbies(hobbies.substring(0, hobbies.length() - 1));
//
//        user.setUserPassword(request.getParameter("password_from_registration"));
//
//        id = service.userRegister(user);
//
//        //done with the user registration
//                //----------------------------------------------------------------
//        //now starting with the address
//        String houseno = request.getParameter("houseno");
//        String street = request.getParameter("address");
//        String city = request.getParameter("city");
//        String state = request.getParameter("landmark");
//        String country = request.getParameter("country");
//        String landmark = request.getParameter("landmark");
//        String zipcode = request.getParameter("zipcode");
//        String postaladdress = request.getParameter("postaladdress");
//
//        System.out.println("stuff received in userRegister controller: " + houseno + street + city + state + country + landmark + zipcode + postaladdress);
//
//        Address addobj = new Address();
//        addobj.setAddHouseNo(houseno);
//        addobj.setAddStreet(street);
//        addobj.setAddCity(city);
//        addobj.setAddState(state);
//        addobj.setAddCountry(country);
//        addobj.setAddZipcode(zipcode);
//        addobj.setAddLandmark(landmark);
//        addobj.setAddPostalAdd(postaladdress);
//
//
//        addservice.addAddress(id, addobj);
//
//
//        HttpSession session = request.getSession();
//        String uName = (String) session.getAttribute("userFirstname");
//        System.out.println("uName (from UserRegisterController) :- " + uName);    /*just to check who it is*/
//        response.sendRedirect("login.jsp");

    }
}