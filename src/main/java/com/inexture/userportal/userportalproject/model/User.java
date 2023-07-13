package com.inexture.userportal.userportalproject.model;

import java.io.FileInputStream;
import java.io.InputStream;

public class User {

    private int userId;
    private String userRole;
    private String userFirstname;
    private String userMiddlename;
    private String userLastname;
    private String userEmailID;
    private String userUsername;
    private String userPassword;

    private int userAge;
    private String userDOB;
    private String userHobbies;

    private InputStream userProfile;

    private String base64Image;

    private FileInputStream defaultProfile;
    private Boolean userStatus;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserMiddlename() {
        return userMiddlename;
    }

    public void setUserMiddlename(String userMiddlename) {
        this.userMiddlename = userMiddlename;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getUserEmailID() {
        return userEmailID;
    }

    public void setUserEmailID(String userEmailID) {
        this.userEmailID = userEmailID;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(String userDOB) {
        this.userDOB = userDOB;
    }

    public String getUserHobbies() {
        return userHobbies;
    }

    public void setUserHobbies(String userHobbies) {
        this.userHobbies = userHobbies;
    }

    public InputStream getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(InputStream userProfile) {
        this.userProfile = userProfile;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public FileInputStream getDefaultProfile() {
        return defaultProfile;
    }

    public void setDefaultProfile(FileInputStream defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

    public Boolean getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userFirstname=" + userFirstname + ", userMiddlename=" + userMiddlename + ", userLastname=" + userLastname + ", userEmail=" + userEmailID + ", userUsername=" + userUsername +", userPassword="
                + userPassword + ", userHobby=" + userHobbies + ", userDOB=" + userDOB + ", userAge =" + userAge + ", userProfile=" + userProfile + ", defaultProfile="
                + defaultProfile + ", userStatus=" + userStatus + "]";
    }
}

