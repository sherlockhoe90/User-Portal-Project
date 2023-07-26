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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.inexture.userportal.userportalproject.utility.PasswordEncryption;
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
import sun.security.krb5.EncryptedData;

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
        List<String> validationErrors = new ArrayList<>(); // to be shown on registration.jsp
        String emailRegex;
        String dobRegex;
        Pattern pattern;
        Matcher matcher;



        /* getting the user details */
        /*each field is first received using req.getParam, and the validated in the next line*/
        /*firstname*/
        user.setUserFirstname(request.getParameter("firstname"));
        if (request.getParameter("firstname") == null || request.getParameter("firstname").trim().isEmpty()) {
            validationErrors.add("Firstname is required.");
        } else if (request.getParameter("firstname").length() > 20) {
            validationErrors.add("Firstname cannot be longer than 20 characters.");
        }
        /*middle name*/
        user.setUserMiddlename(request.getParameter("middlename"));
        if (request.getParameter("middlename") == null || request.getParameter("middlename").trim().isEmpty()) {
            validationErrors.add("Middle name is required.");
        } else if (request.getParameter("middlename").length() > 20) {
            validationErrors.add("Middlename cannot be longer than 20 characters.");
        }
        /*lastname*/
        user.setUserLastname(request.getParameter("lastname"));
        if (request.getParameter("lastname") == null || request.getParameter("lastname").trim().isEmpty()) {
            validationErrors.add("Lastname is required.");
        } else if (request.getParameter("lastname").length() > 20) {
            validationErrors.add("Lastname cannot be longer than 20 characters.");
        }
        /*email id*/
        user.setUserEmailID(request.getParameter("emailid"));
        if (request.getParameter("emailid") == null || request.getParameter("emailid").trim().isEmpty()) {
            validationErrors.add("Email ID is required.");
        } else if (request.getParameter("emailid").length() > 40) {
            validationErrors.add("Email-ID cannot be longer than 40 characters.");
        } else {
            // checking email pattern using regular expression
            emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            pattern = Pattern.compile(emailRegex);
            matcher = pattern.matcher(request.getParameter("emailid"));
            if (!matcher.matches()) {
                validationErrors.add("Invalid email format.");
            }
        }
        /*username*/
        user.setUserUsername(request.getParameter("username"));
        if (request.getParameter("username") == null || request.getParameter("username").trim().isEmpty()) {
            validationErrors.add("Username is required.");
        } else if (request.getParameter("username").length() > 20) {
            validationErrors.add("Username cannot be longer than 20 characters.");
        }
        /*password and confirm-password*/
        // encrpyting the password before setting it and saving it into the database
        String EncryptedPassword = PasswordEncryption.encrypt(request.getParameter("pwd"));
        if (request.getParameter("pwd") == null || request.getParameter("pwd").trim().isEmpty()) {
            validationErrors.add("Password is required.");
        } else if (request.getParameter("pwd").length() > 30) {
            validationErrors.add("Password cannot be longer than 30 characters.");
        }
        if (request.getParameter("cpwd") == null || request.getParameter("cpwd").trim().isEmpty() || !request.getParameter("cpwd").equals(request.getParameter("cpwd"))) {
            validationErrors.add("Confirm password is either empty, or the passwords do not match.");
        }
        user.setUserPassword(EncryptedPassword);
        /*date of birth, and age*/
        user.setUserDOB(request.getParameter("dob"));
        if (request.getParameter("dob") == null || request.getParameter("dob").trim().isEmpty()) {
            validationErrors.add("Date of Birth is required.");
        } else {
            /* each different regex with different formats is separated by a '|' character in the regex.
             * the date format is yyyy-mm-dd, but it also accepts yyyy.mm.dd, and yyyy/mm/dd */
            /* ((18|19|20)[0-9]{2})-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]) signifies the months 1,3,5,7,8,10, and 12 with 31 days.
             * The year should start with 18, 19, or 20 */
            /* ((18|19|20)[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30) signifies the months 4,6,9 and 11 with 30 days*/
            /* ((18|19|20)[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]) signifies the month 2 February with upto 28 days*/
            /* ((18|19|20)(04|08|[2468][048]|[13579][26]))-02-29 signifies the part that matches leap years in February,
             * allowing February 29 on years that are divisible by 4 but not divisible by 100, except for years divisible by 400 (leap year rule).*/
            dobRegex = "^((18|19|20)[0-9]{2})-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01])|((18|19|20)[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30)|((18|19|20)[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8])|((18|19|20)(04|08|[2468][048]|[13579][26]))-02-29$";
            pattern = Pattern.compile(dobRegex);
            matcher = pattern.matcher(request.getParameter("dob"));
            String[] dobParts = request.getParameter("dob").split("-");
            if (!matcher.matches()) {
                validationErrors.add("Invalid Date format.");
            } else if (Integer.parseInt(dobParts[0]) < 1900) {
                validationErrors.add("The Date cannot be older than 1900.");
            } else if (Integer.parseInt(dobParts[0]) > 2024) {
                validationErrors.add("The Date cannot be after 2024.");
            }
        }
        user.setUserAge(AgeCalculator.calculateAge(user.getUserDOB()));
        /*hobbies*/
        user.setUserHobbies(request.getParameter("hobbies"));
        if (request.getParameter("hobbies") == null || request.getParameter("hobbies").trim().isEmpty()) {
            validationErrors.add("Hobbies are required.");
        } else if (request.getParameter("hobbies").length() > 50) {
            validationErrors.add("Hobbies cannot be longer than 50 characters.");
        }


        //printing the user details that i've got
        user.toString();

        // Check if there are validation errors
        if (validationErrors.isEmpty()) {
            // If there are NO errors, save them in the database
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
            if (houseno[count] == null || houseno[count].trim().isEmpty()) {
                validationErrors.add("House No. is required.");
            } else if (houseno[count].length() > 10) {
                validationErrors.add("House No cannot be longer than 10 characters.");
            } else {
                addobj.setAddHouseNo(houseno[count]);
            }
            /*street*/
            if (street[count] == null || street[count].trim().isEmpty()) {
                validationErrors.add("Street is required.");
            } else if (street[count].length() > 20) {
                validationErrors.add("Street cannot be longer than 20 characters.");
            } else {
                addobj.setAddStreet(street[count]);
            }
            /*landmark*/
            if (landmark[count] == null || landmark[count].trim().isEmpty()) {
                validationErrors.add("Landmark is required.");
            } else if (landmark[count].length() > 50) {
                validationErrors.add("Street cannot be longer than 20 characters.");
            } else {
                addobj.setAddLandmark(landmark[count]);
            }
            /*city*/
            if (city[count] == null || city[count].trim().isEmpty()) {
                validationErrors.add("City is required.");
            } else if (city[count].length() > 20) {
                validationErrors.add("City name cannot be longer than 20 characters.");
            } else {
                addobj.setAddCity(city[count]);
            }
            /*state*/
            if (state[count] == null || state[count].trim().isEmpty()) {
                validationErrors.add("State is required.");
            } else if (state[count].length() > 20) {
                validationErrors.add("State name cannot be longer than 20 characters.");
            } else {
                addobj.setAddState(state[count]);
            }
            /*Zipcode*/
            if (zipcode[count] == null || zipcode[count].trim().isEmpty()) {
                validationErrors.add("Zipcode is required.");
            } else if (zipcode[count].length() > 20) {
                validationErrors.add("Zipcode cannot be longer than 20 characters.");
            } else {
                addobj.setAddZipcode(zipcode[count]);
            }
            /*Country*/
            if (country[count] == null || country[count].trim().isEmpty()) {
                validationErrors.add("Country is required.");
            } else if (country[count].length() > 20) {
                validationErrors.add("Country name cannot be longer than 57 characters.");
            } else {
                addobj.setAddCountry(country[count]);
            }
            /*postal address*/
            if (postaladdress[count] == null || postaladdress[count].trim().isEmpty()) {
                validationErrors.add("Postal Address is required.");
            } else if (postaladdress[count].length() > 250) {
                validationErrors.add("Postal Address cannot be longer than 250 characters.");
            } else {
                addobj.setAddPostalAdd(postaladdress[count]);
            }

            // Check if there are validation errors
            if (!validationErrors.isEmpty()) {
                // If there are errors, send them back to the registration page
                request.setAttribute("errors", validationErrors);
                request.getRequestDispatcher("/registration.jsp").forward(request, response);
                return;
            } else {
                addservice.addAddress(id, addobj);
                /*I AM YET TO ADD THE ROLLBACK FEATURE FOR IF 5 OUTTA 10 ADDRS ARE GOOD, AND THE 6TH IS BAD, DONT SAVE THE FIVE*/
            }

            // save the address in the database
            count++;
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