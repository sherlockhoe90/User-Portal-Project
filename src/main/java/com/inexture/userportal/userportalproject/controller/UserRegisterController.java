package com.inexture.userportal.userportalproject.controller;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.inexture.userportal.userportalproject.utility.PasswordEncryption;
import com.inexture.userportal.userportalproject.utility.ValidateOnServerSide;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.inexture.userportal.userportalproject.model.Address;
import com.inexture.userportal.userportalproject.model.User;
import com.inexture.userportal.userportalproject.services.AddressService;
import com.inexture.userportal.userportalproject.services.AddressServiceImp;
import com.inexture.userportal.userportalproject.services.UserService;
import com.inexture.userportal.userportalproject.services.UserServiceImp;
import com.inexture.userportal.userportalproject.utility.AgeCalculator;
import com.inexture.userportal.userportalproject.utility.DatabaseManager;

@MultipartConfig
public class UserRegisterController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LogManager.getLogger("UserRegisterController");

    public UserRegisterController() {
        super();
    }

    Connection c;

    public void init(ServletConfig config) throws ServletException {
        // create connection if one hasn't already been made before
        if (c == null) {
            c = DatabaseManager.getConnection();
            logger.info("UserRegisterController: Value of the getConnection object in init: " + c);
        }

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
        List<String> validationErrorsArrayList = new ArrayList<>(); // to be shown on registration.jsp

        user.setUserFirstname(request.getParameter("firstname"));
        user.setUserMiddlename(request.getParameter("middlename"));
        user.setUserLastname(request.getParameter("lastname"));
        user.setUserEmailID(request.getParameter("emailid"));
        user.setUserUsername(request.getParameter("username"));
        user.setUserDOB(request.getParameter("dob"));
        user.setUserAge(AgeCalculator.calculateAge(request.getParameter("dob")));
        String EncryptedPassword = PasswordEncryption.encrypt(request.getParameter("pwd"));
        user.setUserPassword(EncryptedPassword);
        String EncryptedConfirmPassword = PasswordEncryption.encrypt(request.getParameter("cpwd"));
        user.setUserConfirmPassword(EncryptedConfirmPassword);
        user.setUserHobbies(request.getParameter("hobbies"));


        //call the validation method here
        validationErrorsArrayList = ValidateOnServerSide.validateUser(user);
        //Checking if the Email-ID is unique, it's a part of the Validation On Server Side
        if (service.checkEmail(user.getUserEmailID())) {
            validationErrorsArrayList.add("Email ID already exists. Use a different Email-ID.");
        }

        //printing the user details that i've got
        user.toString();

        // Check if there are validation errors
        if (validationErrorsArrayList.isEmpty()) {
            // If there are NO errors, save the user-details in the database
            id = service.userRegister(user); /*this line submits the code into the database, and returns the userID*/
        }

        /* getting the user's Addresses */
        String[] houseno = request.getParameterValues("houseno[]");
        String[] street = request.getParameterValues("address[]");
        String[] landmark = request.getParameterValues("landmark[]");
        String[] zipcode = request.getParameterValues("zipcode[]");
        String[] city = request.getParameterValues("city[]");
        String[] state = request.getParameterValues("state[]");
        String[] country = request.getParameterValues("country[]");
        String[] postaladdress = request.getParameterValues("postaladdress[]");


        /* saving every single address into the database */
        int count = 0;
        int initialaddID = 0;
        while (count < street.length) {
            Address addobj = new Address();
            /*validating while setting values*/
            /*house no*/
            addobj.setAddId(String.valueOf(++initialaddID));
            addobj.setAddHouseNo(houseno[count]);
            addobj.setAddStreet(street[count]);
            addobj.setAddLandmark(landmark[count]);
            addobj.setAddCity(city[count]);
            addobj.setAddState(state[count]);
            addobj.setAddZipcode(zipcode[count]);
            addobj.setAddCountry(country[count]);
            addobj.setAddPostalAdd(postaladdress[count]);
            logger.info("validationErrorsArrayList BEFORE going to validateAddress : " + validationErrorsArrayList);

            //adding the address ERRORS in the List
            validationErrorsArrayList.addAll(ValidateOnServerSide.validateAddress(addobj));

            // Check if there are validation errors
            if (!validationErrorsArrayList.isEmpty()) {
                // If there are errors, send them back to the registration page
                request.setAttribute("errors", validationErrorsArrayList);
                logger.info("validationErrorsArrayList AFTER going to validateAddress : " + validationErrorsArrayList);
                request.getRequestDispatcher("/registration.jsp").forward(request, response);
                validationErrorsArrayList.clear();
                return;
            } else {
                addservice.addAddress(id, addobj);
                // save the address in the database
                count++;
                /*I AM YET TO ADD THE ROLLBACK FEATURE FOR IF 5 OUTTA 10 ADDRS ARE GOOD, AND THE 6TH IS BAD, DONT SAVE THE FIVE*/
            }

        }

        //printing the address(es) that i've got
        Address addModel = new Address();
        addModel.toString();

        HttpSession session = request.getSession();
        String uName = (String) session.getAttribute("userFirstname");
        logger.info("uName" + uName);
        //after registration is done, redirect back to the login page
        response.sendRedirect("login.jsp");
    }
}