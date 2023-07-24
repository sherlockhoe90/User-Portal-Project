package com.inexture.userportal.userportalproject.controller;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

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


        /* getting the user details */
        user.setUserFirstname(request.getParameter("firstname"));
        user.setUserMiddlename(request.getParameter("middlename"));
        user.setUserLastname(request.getParameter("lastname"));
        user.setUserEmailID(request.getParameter("emailid"));
        user.setUserUsername(request.getParameter("username"));
        // encrpyting the password before setting it and saving it into the database
        String EncryptedPassword = PasswordEncryption.encrypt(request.getParameter("pwd"));
        user.setUserPassword(EncryptedPassword);
        user.setUserDOB(request.getParameter("dob"));
        user.setUserAge(AgeCalculator.calculateAge(user.getUserDOB()));
        user.setUserHobbies(request.getParameter("hobbies"));

        //printing the user details that i've got
        user.toString();

        id = service.userRegister(user); /*this line submits the code into the database, and returns the userID*/

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

        //printing the address(es) that i've got
        Address addModel = new Address();
        addModel.toString();

        HttpSession session = request.getSession();
        String uName = (String) session.getAttribute("userFirstname");
        logger.info("uName" + uName);
        //after registration is done, redirect back to the same page
        response.sendRedirect("registration.jsp");
    }
}