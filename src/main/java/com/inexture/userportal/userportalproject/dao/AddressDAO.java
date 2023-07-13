package com.inexture.userportal.userportalproject.dao;

import com.inexture.userportal.userportalproject.model.Address;

import java.sql.SQLException;
import java.util.List;

public interface AddressDAO {

    // these JavaDocs docs specify the parameters given to the below method
    // and the return type
    /**
     *
     * @param userId
     * @param address
     * @return
     *
     */
    void addAddress(int userId, Address address);

    /**
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    List<Address> getAllAddress(int userId) throws SQLException;

    /**
     *
     * @param address
     * @param id
     * @throws SQLException
     */
    void updateAddress(Address address, int id) throws SQLException;

}
