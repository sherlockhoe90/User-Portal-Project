package com.inexture.userportal.userportalproject.dao;

import com.inexture.userportal.userportalproject.model.User;
import com.inexture.userportal.userportalproject.utility.DatabaseManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UserDAOImp implements UserDAO {

    private static Logger logger = LogManager.getLogger("UserDAOImp");

    Connection c;

    public UserDAOImp() {
        if(c == null) {
            c = DatabaseManager.getConnection();
        }
    }

    // implementation of user login
    @Override
    public boolean compareUserLogin(User user) {
        // logger.info("User Data" + user.toString());
        try {
            PreparedStatement pstmt = c.prepareStatement("select * from userportal_users where emailid=? and password = ?");
            pstmt.setString(1, user.getUserEmailID());
            pstmt.setString(2, user.getUserPassword());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Integer role = rs.getInt(12); // getting the value of the 'isAdmin' column

                // unrelated to login procedure
                // determining the role of the person
                user.setUserRole(String.valueOf(role));
                if (user.getUserRole().equals("1")){ //setting it to true : ADMIN
                    user.setUserStatus(true);
                } else if (user.getUserRole().equals("0")) { // setting it to false NORMAL USER
                    user.setUserStatus(false);
                }
                logger.info("UserDAOImp: Setting the userStatus =  " + user.getUserRole() + " and : " + user.getUserStatus());
                //commenting the unnecessary stuff
//                user.setUserId(rs.getInt("id"));
//                user.setUserFirstname(rs.getString("firstname"));
//                user.setUserMiddlename(rs.getString("middlename"));
//                user.setUserLastname(rs.getString("lastname"));
//                user.setUserPassword(rs.getString("password"));
//                user.setUserHobbies(rs.getString("hobbies"));
//                user.setUserDOB(rs.getString("dob"));
//                user.setUserAge(rs.getInt("age"));

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return false;
    }

    // implementation of of user Registration
    @Override
    public int userRegister(User user) {
        int id = 0;
        try {
            PreparedStatement pstmt = c.prepareStatement(
                    "insert into userportal_users(role, firstname, middlename, lastname, emailid, username, password, age, dob, hobbies) values(?,?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, "user");
            pstmt.setString(2, user.getUserFirstname());
            pstmt.setString(3, user.getUserMiddlename());
            pstmt.setString(4, user.getUserLastname());
            pstmt.setString(5, user.getUserEmailID());
            pstmt.setString(6, user.getUserUsername());
            pstmt.setString(7, user.getUserPassword());
            pstmt.setInt(8, user.getUserAge());
            pstmt.setString(9, user.getUserDOB());
            pstmt.setString(10, user.getUserHobbies());
//            pstmt.setBlob(11, user.getUserProfile()); /*dont forget to add the field name back to the pstmt statement above*/

            logger.info("some of the values received in userDAOImp are : " + user.getUserFirstname() + user.getUserLastname() + user.getUserEmailID() + " " + user.getUserAge() );

            pstmt.executeUpdate();
            PreparedStatement stmt = c.prepareStatement("select id from userportal_users");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;

    }

//    @Override
//    public User showUser(User user) throws SQLException {
//
//        PreparedStatement pstmt = c.prepareStatement(
//                "select * from userportal_users where emailid=?");
//        pstmt.setString(1, user.getUserEmailID());
//        ResultSet rs = pstmt.executeQuery();
//        if(rs.next()) {
//            user.setUserId(rs.getInt("id"));
//            user.setUserFirstname(rs.getString("firstname"));
//            user.setUserMiddlename(rs.getString("middlename"));
//            user.setUserLastname(rs.getString("lastname"));
//            user.setUserHobbies(rs.getString("hobbies"));
//            user.setUserAge(rs.getInt("age"));
//            user.setUserDOB(rs.getString("dob"));
//        }
//
//        return user;
//    }


    // display user details on admin side
    @Override
    public List<User> displayUser(User user) throws SQLException {

        List<User> list = new ArrayList<>();
        PreparedStatement pstmt = c.prepareStatement("select * from userportal_users where isAdmin=0");
        logger.info("User Data" + user.toString());
//        System.out.println("User Data" + user.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            user = new User();
            user.setUserId(rs.getInt("id"));
            user.setUserFirstname(rs.getString("firstname"));
            user.setUserMiddlename(rs.getString("middlename"));
            user.setUserLastname(rs.getString("lastname"));
            user.setUserEmailID(rs.getString("emailid"));
            user.setUserUsername(rs.getString("username"));
            user.setUserPassword(rs.getString("password"));
            user.setUserDOB(rs.getString("dob"));
            user.setUserAge(rs.getInt("age"));
            user.setUserHobbies(rs.getString("hobbies"));
//            Blob blob = rs.getBlob("profile_img");
//
//            byte[] photo = blob.getBytes(1, (int) blob.length());
//            String base64Image = Base64.getEncoder().encodeToString(photo);
//            user.setBase64Image(base64Image);
            System.out.println("User Data" + user); //printing the user to check all of the information that we've received

            list.add(user);
        }

        return list;
    }

    @Override
    public void deleteUser(String userId) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("delete from userportal_users where id=?");
        pstmt.setString(1, userId);
        pstmt.execute();
    }


    @Override
    public List<User> displayAdmin(User user) throws SQLException {
        List<User> list = new ArrayList<>();
        PreparedStatement pstmt = c.prepareStatement("select * from userportal_users where isAdmin=1 and emailid = ?");
        pstmt.setString(1, user.getUserEmailID());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
//            user = new User();
            user.setUserId(rs.getInt("id"));
            user.setUserFirstname(rs.getString("firstname"));
            user.setUserMiddlename(rs.getString("middlename"));
            user.setUserLastname(rs.getString("lastname"));
            user.setUserEmailID(rs.getString("emailid"));
            user.setUserUsername(rs.getString("username"));
            user.setUserPassword(rs.getString("password"));
            user.setUserDOB(rs.getString("dob"));
            user.setUserAge(rs.getInt("age"));
            user.setUserHobbies(rs.getString("hobbies"));
//            Blob blob = rs.getBlob("profile_img");
//
//            byte[] photo = blob.getBytes(1, (int) blob.length());
//            String base64Image = Base64.getEncoder().encodeToString(photo);
//            user.setBase64Image(base64Image);

            list.add(user);
        }

        return list;
    }

    @Override
    public void updatePassword(User user) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement("UPDATE userportal_users SET password = ? WHERE emailid = ?");
        pstmt.setString(1, user.getUserPassword());
        pstmt.setString(2, user.getUserEmailID());
        pstmt.executeUpdate();
    }

    @Override
    public User displaySpecificUser(User user) throws SQLException {

//		List<User> list = new ArrayList<User>();

        PreparedStatement pstmt = c.prepareStatement("select * from userportal_users where emailid = ? and password = ?");
        pstmt.setString(1, user.getUserEmailID());
        pstmt.setString(2, user.getUserPassword());
        ResultSet rs = pstmt.executeQuery();
        User us = new User();
        while (rs.next()) {
            // user = new User();
            us.setUserId(rs.getInt("id"));
            us.setUserFirstname(rs.getString("firstname"));
            us.setUserMiddlename(rs.getString("middlename"));
            us.setUserLastname(rs.getString("lastname"));
            us.setUserEmailID(rs.getString("emailid"));
            us.setUserUsername(rs.getString("username"));
            us.setUserPassword(rs.getString("password"));
            us.setUserDOB(rs.getString("dob"));
            us.setUserAge(rs.getInt("age"));
            us.setUserHobbies(rs.getString("hobbies"));
//            Blob blob = rs.getBlob("profile_img");

//            byte[] photo = blob.getBytes(1, (int) blob.length());
//            String base64Image = Base64.getEncoder().encodeToString(photo);
//            us.setBase64Image(base64Image);
//			list.add(us);
        }
        return us;
    }

    @Override
    public int updateProfile(User user) {
        int id = 0;
        try {
            PreparedStatement pstmt = c.prepareStatement(
                    "UPDATE userportal_users SET firstname = ?, middlename = ?, lastname = ?, hobbies = ?, username = ?, dob = ?, age = ? WHERE emailid = ?");

            pstmt.setString(1, user.getUserFirstname());
            pstmt.setString(2, user.getUserMiddlename());
            pstmt.setString(3, user.getUserLastname());
            pstmt.setString(4, user.getUserHobbies());
            pstmt.setString(5, user.getUserUsername());
            pstmt.setString(6, user.getUserDOB());
            pstmt.setInt(7, user.getUserAge());
//            pstmt.setBlob(7, user.getUserProfile());
            pstmt.setString(8, user.getUserEmailID());
            logger.debug("The UserID of the current user is : " + user.getUserId());

            pstmt.executeUpdate();
            PreparedStatement stmt = c.prepareStatement("select id from userportal_users where emailid=?");
            stmt.setString(1, user.getUserEmailID());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    @Override
    public List<User> getUserDetails(String userId) throws SQLException {
        List<User> list = new ArrayList<User>();
        PreparedStatement pstmt = c.prepareStatement("select * from userportal_users where id = ?");
        pstmt.setString(1, userId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("id"));
            user.setUserFirstname(rs.getString("firstname"));
            user.setUserMiddlename(rs.getString("middlename"));
            user.setUserLastname(rs.getString("lastname"));
            user.setUserEmailID(rs.getString("emailid"));
            user.setUserDOB(rs.getString("dob"));
            user.setUserAge(rs.getInt("age"));
            user.setUserUsername(rs.getString("username"));
            user.setUserPassword(rs.getString("password"));
//            Blob blob = rs.getBlob("profile_img");
//
//            byte[] photo = blob.getBytes(1, (int) blob.length());
//            String base64Image = Base64.getEncoder().encodeToString(photo);
//            user.setBase64Image(base64Image);
//            user.setUserHobbies(rs.getString("hobbies"));
            list.add(user);
        }
        return list;
    }

}
