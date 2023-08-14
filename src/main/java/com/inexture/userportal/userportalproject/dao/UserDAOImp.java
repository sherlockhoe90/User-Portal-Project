package com.inexture.userportal.userportalproject.dao;

import com.inexture.userportal.userportalproject.model.User;
import com.inexture.userportal.userportalproject.utility.DatabaseManager;
import com.inexture.userportal.userportalproject.utility.PasswordEncryption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImp implements UserDAO {

    private static Logger logger = LogManager.getLogger("UserDAOImp");

    Connection c;

    public UserDAOImp() throws SQLException {
        if (c == null || c.isClosed()) {
            c = DatabaseManager.getConnection();
        }
    }

    // implementation of user login
    @Override
    public boolean compareUserLogin(User user) {
        // logger.info("User Data" + user.toString());
        try {
            try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement("select * from userportal_users where emailid=? and password = ?")) {
                pstmt.setString(1, user.getUserEmailID());
                pstmt.setString(2, user.getUserPassword());
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Integer role = rs.getInt(12); // getting the value of the 'isAdmin' column

                        // unrelated to login procedure
                        // determining the role of the person
                        user.setUserRole(String.valueOf(role));
                        if (user.getUserRole().equals("1")) { // setting it to true : ADMIN
                            user.setUserStatus(true);
                        } else if (user.getUserRole().equals("0")) { // setting it to false NORMAL USER
                            user.setUserStatus(false);
                        }
                        logger.info("UserDAOImp: Setting the userStatus =  " + user.getUserRole() + " and : " + user.getUserStatus());
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return false;
    }

    // implementation of user Registration
    @Override
    public int userRegister(User user) {
        int id = 0;
        try {
            try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(
                    "insert into userportal_users(role, firstname, middlename, lastname, emailid, username, password, age, dob, hobbies) values(?,?,?,?,?,?,?,?,?,?)")) {
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

                logger.info("some of the values received in userDAOImp are : " + user.getUserFirstname() + user.getUserLastname() + user.getUserEmailID() + " " + user.getUserAge());

                pstmt.executeUpdate();
                try (PreparedStatement stmt = c.prepareStatement("select id from userportal_users")) {
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            id = rs.getInt("id");
                        }
                    }
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public User showUser(User user) throws SQLException {
        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement("select * from userportal_users where emailid=?")) {
            pstmt.setString(1, user.getUserEmailID());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt("id"));
                    user.setUserFirstname(rs.getString("firstname"));
                    user.setUserMiddlename(rs.getString("middlename"));
                    user.setUserLastname(rs.getString("lastname"));
                    user.setUserHobbies(rs.getString("hobbies"));
                    user.setUserAge(rs.getInt("age"));
                    user.setUserDOB(rs.getString("dob"));
                }
            }
        }
        return user;
    }

    // display All users' details on admin side
    @Override
    public List<User> displayUser(User user) throws SQLException {
        List<User> list = new ArrayList<>();
        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement("select * from userportal_users where isAdmin=0")) {
            logger.info("User Data" + user.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    user = new User();
                    /*setting the database data inside the 'user' object*/
                    user = setResultSetWithinUserObject(user, rs);
                    logger.info("User Data" + user); // printing the user to check all of the information that we've received
                    list.add(user);
                }
            }
        }
        return list;
    }

    /**
     * @param currentPage
     * @param recordsPerPage
     * @return
     * @throws SQLException
     */
    @Override
    public List<User> displayUser(int currentPage, int recordsPerPage) throws SQLException {
        List<User> list = new ArrayList<User>();
        User user = null;
        int start = currentPage * recordsPerPage - recordsPerPage;
        try {
            try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement("SELECT * FROM userportal_users LIMIT ?, ?")) {
                pstmt.setInt(1, start);
                pstmt.setInt(2, recordsPerPage);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        user = new User();
                        user = setResultSetWithinUserObject(user, rs);
                        list.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteUser(String userId) throws SQLException {
        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement("delete from userportal_users where id=?")) {
            pstmt.setInt(1, Integer.parseInt(userId));
            pstmt.execute();
        }
    }

    @Override
    public List<User> displayAdmin(User user) throws SQLException {
        List<User> list = new ArrayList<>();
        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement("select * from userportal_users where emailid = ?")) {
            pstmt.setString(1, user.getUserEmailID());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    user = setResultSetWithinUserObject(user, rs);
                    list.add(user);
                }
            }
        }
        return list;
    }

    @Override
    public void updatePassword(User user) throws SQLException {
        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement("UPDATE userportal_users SET password = ? WHERE emailid = ?")) {
            user.setUserPassword(PasswordEncryption.encrypt(user.getUserPassword())); // encrypt the password before storing in the db
            pstmt.setString(1, user.getUserPassword());
            pstmt.setString(2, user.getUserEmailID());
            pstmt.executeUpdate();
        }
    }

    @Override
    public User displaySpecificUser(User user) throws SQLException {
        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement("select * from userportal_users where emailid = ?")) {
            pstmt.setString(1, user.getUserEmailID());
            try (ResultSet rs = pstmt.executeQuery()) {
                User us = null;
                while (rs.next()) {
                    us = setResultSetWithinUserObject(new User(), rs);
                }
                return us;
            }
        }
    }

    @Override
    public int updateProfile(User user) {
        int id = 0;
        try {
            try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(
                    "UPDATE userportal_users SET firstname = ?, middlename = ?, lastname = ?, hobbies = ?, username = ?, dob = ?, age = ? WHERE emailid = ?")) {
                pstmt.setString(1, user.getUserFirstname());
                pstmt.setString(2, user.getUserMiddlename());
                pstmt.setString(3, user.getUserLastname());
                pstmt.setString(4, user.getUserHobbies());
                pstmt.setString(5, user.getUserUsername());
                pstmt.setString(6, user.getUserDOB());
                pstmt.setInt(7, user.getUserAge());
                pstmt.setString(8, user.getUserEmailID());
                logger.debug("The UserID of the current user is : " + user.getUserId());
                pstmt.executeUpdate();
                try (PreparedStatement stmt = c.prepareStatement("select id from userportal_users where emailid=?")) {
                    stmt.setString(1, user.getUserEmailID());
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            id = rs.getInt("id");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public List<User> getUserDetails(String userId) throws SQLException {
        List<User> list = new ArrayList<User>();
        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement("select * from userportal_users where id = ?")) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    User user = setResultSetWithinUserObject(new User(), rs);
                    list.add(user);
                }
            }
        }
        return list;
    }

    /**
     * @param emailid
     * @return
     */
    @Override
    public boolean checkEmail(String emailid) {
        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement("select emailid from userportal_users where emailid=?")) {
            pstmt.setString(1, emailid);
            try (ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param user
     * @return
     */
    /* utility method to reduce code duplication */
    public User setResultSetWithinUserObject(User user, ResultSet rs) throws SQLException {
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
        return user;
    }

    /**
     * @return
     */
    @Override
    public Integer getNumberOfRows() {
        Integer numOfRows = 0;
        try (Statement stmt = DatabaseManager.getConnection().createStatement()) {
            String sql = "SELECT COUNT(id) FROM userportal_users";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    numOfRows = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        return numOfRows;
    }
}
