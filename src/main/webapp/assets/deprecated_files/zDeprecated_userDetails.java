package com.inexture.userportal.userportalproject.services;

import com.inexture.userportal.userportalproject.utility.DatabaseManager;
import com.inexture.userportal.userportalproject.utility.AgeCalculator;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@Deprecated //IN ORDER TO DEPRECATE IT AND SHOW IT WITH A Strikethrough
@WebServlet(name = "zDeprecated_userDetails", value = "/zDeprecated_userDetails")
public class zDeprecated_userDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstname = (String) request.getAttribute("firstnameFromController");
        String middlename = (String) request.getAttribute("middlenameFromController");
        String lastname = (String) request.getAttribute("lastnameFromController");
        String dob = (String) request.getAttribute("dobFromController");
        String emailid = (String) request.getAttribute("emailidFromController");
        String hobbies = (String) request.getAttribute("hobbiesFromController");
        String password = (String) request.getAttribute("passwordFromController");
        String username = (String) request.getAttribute("usernameFromController");

        //calculating the age
        int age =  AgeCalculator.calculateAge(dob);

        System.out.println("from zDeprecated_userDetails :\n" + firstname+ " " + middlename + " " + lastname + " " + dob + " " + age + " " + emailid + " " + hobbies + " " + username + " " + password);
            //commenting this for now because it was making the static pages slow
//        Connection connection = (Connection) DatabaseManager.getConnection();
        Statement statement;
        try {
            statement = (Statement) DatabaseManager.getStatement();

            String query = "insert into userportal_users(firstname, middlename, lastname, emailid, username, password, age, dob, hobbies) values('"+firstname+"', '"+middlename+"', '"+lastname+"', '"+emailid+"', '"+username+"', '"+password+"', '"+age+"', '"+dob+"', '"+hobbies+"');";

            statement.executeUpdate(query);
            System.out.println("everything added, go check in the database");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("registration.jsp");
    }
}
