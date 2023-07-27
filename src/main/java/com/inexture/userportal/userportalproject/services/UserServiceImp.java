package com.inexture.userportal.userportalproject.services;

import com.inexture.userportal.userportalproject.dao.UserDAO;
import com.inexture.userportal.userportalproject.dao.UserDAOImp;
import com.inexture.userportal.userportalproject.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImp implements UserService {

    private static Logger logger = LogManager.getLogger("UserLoginController");

    UserDAO userDAOobject = new UserDAOImp();

    @Override
    public boolean compareUserLogin(User user) {
        return userDAOobject.compareUserLogin(user);
    }

    @Override
    public int userRegister(User user) {
        /*returns the user ID*/
        return userDAOobject.userRegister(user);
    }

    @Override
    public User showUser(User user) throws SQLException {
        user = userDAOobject.showUser(user);
        return user;
    }


    @Override
    public List<User> displayUser(User user) throws SQLException {
        logger.info("inside user service imp");
        return userDAOobject.displayUser(user);
    }

    @Override
    public void deleteUser(String userId) throws SQLException {
        userDAOobject.deleteUser(userId);

    }

    @Override
    public List<User> displayAdmin(User user) throws SQLException {
        logger.info(" displayAdmin method called");
        return userDAOobject.displayAdmin(user);
    }

    @Override
    public void updatePassword(User user) throws SQLException {
        userDAOobject.updatePassword(user);
    }


    @Override
    public User displaySpecificUser(User user) throws SQLException {

        UserDAO obj = new UserDAOImp();
        logger.info("in service " + obj.displaySpecificUser(user));
        return obj.displaySpecificUser(user);

    }

    @Override
    public int updateProfile(User user) {
        /*return user ID*/
        return userDAOobject.updateProfile(user);
    }


    @Override
    public List<User> getUserDetails(String userId) throws SQLException {
        return userDAOobject.getUserDetails(userId);
    }

    /**
     * @param emailid
     * @return
     */
    @Override
    public boolean checkEmail(String emailid) {
        boolean obj = userDAOobject.checkEmail(emailid);
        return obj;
    }
}
