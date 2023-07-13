package com.inexture.userportal.userportalproject.dao;

import com.inexture.userportal.userportalproject.model.User;

import java.sql.SQLException;
import java.util.List;


public interface UserDAO {

    /**
     *
     * @param user
     * @return
     */
    boolean compareUserLogin(User user);

    /**
     *
     * @param user
     * @return
     */
    int userRegister(User user);

//    /**
//     *
//     * @param user
//     * @return
//     */
//    User showUser (User user) throws SQLException;

    /**
     *
     * @param user
     * @return
     * @throws SQLException
     */
    List<User> displayUser(User user) throws SQLException;

    /**
     *
     * @param userId
     * @throws SQLException
     */
    void deleteUser(String userId) throws SQLException;

    /**
     *
     * @param user
     * @return
     * @throws SQLException
     */
    List<User> displayAdmin(User user) throws SQLException;

    /**
     *
     * @param user
     * @throws SQLException
     */
    void updatePassword(User user) throws SQLException;

    /**
     *
     * @param user
     * @return
     * @throws SQLException
     */
    User displaySpecificUser(User user) throws SQLException;

    /**
     *
     * @param user
     * @return
     */
    int updateProfile(User user);

    /**
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    List<User> getUserDetails(String userId) throws SQLException;
}
