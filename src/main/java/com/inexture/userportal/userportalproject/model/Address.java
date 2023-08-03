package com.inexture.userportal.userportalproject.model;

import java.io.Serializable;


public class Address implements Serializable {
    /* By implementing the Serializable interface, you are telling Java that instances of these classes can be
     safely serialized and deserialized, which prevents potential issues when storing them in the session.

     * Additionally, make sure that any other classes you use in your servlet that are stored in the session also
     implement Serializable if they are part of the session attributes.
     */

    private static final long serialVersionUID = 1L;
    private String addId;
    private int addUserID;
    private String addHouseNo;
    private String addStreet;
    private String addCity;
    private String addState;
    private String addCountry;
    private String addZipcode;
    private String addLandmark;
    private String addPostalAdd;
    private String removeAddressId;

//ask chatgpt, copy this files code located in the root project, and paste it on gpt,
    // ask it if the addUserID is simply for identification purposes, or is it the actual name of the table columns?
    // Ans: we can use the addUSerID as a FOREIGN KEY in another table
    //also check the other files in order to confirm the sql queries

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public int getAddUserID() {
        return addUserID;
    }

    public void setAddUserID(int addUserID) {
        this.addUserID = addUserID;
    }

    public String getAddHouseNo() {
        return addHouseNo;
    }

    public void setAddHouseNo(String addHouseNo) {
        this.addHouseNo = addHouseNo;
    }

    public String getAddStreet() {
        return addStreet;
    }

    public void setAddStreet(String addStreet) {
        this.addStreet = addStreet;
    }

    public String getAddCity() {
        return addCity;
    }

    public void setAddCity(String addCity) {
        this.addCity = addCity;
    }

    public String getAddState() {
        return addState;
    }

    public void setAddState(String addState) {
        this.addState = addState;
    }

    public String getAddCountry() {
        return addCountry;
    }

    public void setAddCountry(String addCountry) {
        this.addCountry = addCountry;
    }

    public String getAddZipcode() {
        return addZipcode;
    }

    public void setAddZipcode(String addZipcode) {
        this.addZipcode = addZipcode;
    }

    public String getAddLandmark() {
        return addLandmark;
    }

    public void setAddLandmark(String addLandmark) {
        this.addLandmark = addLandmark;
    }

    public String getAddPostalAdd() {
        return addPostalAdd;
    }

    public void setAddPostalAdd(String addPostalAdd) {
        this.addPostalAdd = addPostalAdd;
    }

    public String getRemoveAddressId() {
        return removeAddressId;
    }

    public void setRemoveAddressId(String removeAddressId) {
        this.removeAddressId = removeAddressId;
    }

    @Override
    public String toString() {
        return "Address [addId=" + addId + ", addUserID=" + addUserID + ", addZipcode=" + addZipcode + ", addHouseNo=" + addHouseNo + ", addStreet="
                + addStreet + ", addLandmark=" + addLandmark + ", addCity=" + addCity + ", addState=" + addState + ", addCountry= " + addCountry + "" +
                ", addPostalAddress= " + addPostalAdd + " ]";
    }
}
