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
        boolean obj = userDAOobject.compareUserLogin(user);
        return obj;
    }

    @Override
    public int userRegister(User user) {
        int id = userDAOobject.userRegister(user);
        return id;
    }

//    @Override
//    public User showUser(User user) throws SQLException {
//        user = userDAOobject.showUser(user);
//        return user;
//    }


    @Override
    public List<User> displayUser(User user) throws SQLException {
        logger.info("inside user service imp");

        List<User> list = userDAOobject.displayUser(user);
        return list;
    }

    @Override
    public void deleteUser(String userId) throws SQLException {
        userDAOobject.deleteUser(userId);

    }

    @Override
    public List<User> displayAdmin(User user) throws SQLException {
        logger.info(" displayAdmin method called");
        List<User> list = userDAOobject.displayAdmin(user);
        return list;
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
        int id = userDAOobject.updateProfile(user);
        return id;
    }


    @Override
    public List<User> getUserDetails(String userId) throws SQLException {
        List<User> list = userDAOobject.getUserDetails(userId);
        return list;
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
