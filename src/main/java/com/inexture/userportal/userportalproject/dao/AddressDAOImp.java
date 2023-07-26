package com.inexture.userportal.userportalproject.dao;

import com.inexture.userportal.userportalproject.model.Address;
import com.inexture.userportal.userportalproject.utility.DatabaseManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class AddressDAOImp implements AddressDAO {

    private static Logger logger = LogManager.getLogger("AddressDAOImp");

    Connection c;

    public AddressDAOImp() {
        c = DatabaseManager.getConnection();
    }

    PreparedStatement pstmt;

    /**
     * @param userId
     * @param address
     * @return
     */
    @Override
    public void addAddress(int userId, Address address) {

//        System.out.println("yet to make the AddressDAOImp addAddress method function");
        try {
            logger.info("printing the value of the current user id in AddressDAOImp : " + userId);
            pstmt = c.prepareStatement(
                    "insert into userportal_addresses(addressid, userid, houseno, street, city, state, country, zipcode, landmark, postaladdress) values(?,?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, Integer.parseInt(address.getAddId())); //only one address for each user as of now... im yet to add the multiple address feature
            pstmt.setInt(2, userId);
            pstmt.setString(3, address.getAddHouseNo());
            pstmt.setString(4, address.getAddStreet()); //this is basically the value of 'STREET' from the registration .jsp
            pstmt.setString(5, address.getAddCity());
            pstmt.setString(6, address.getAddState());
            pstmt.setString(7, address.getAddCountry());
            pstmt.setString(8, address.getAddZipcode());
            pstmt.setString(9, address.getAddLandmark());
            pstmt.setString(10, address.getAddPostalAdd());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

    }

    public List<Address> getAllAddress(int userId) throws SQLException {
        List<Address> list = new ArrayList<>();

        pstmt = c.prepareStatement("select * from userportal_addresses where userid = ?");

        pstmt.setInt(1, userId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Address address = new Address();
            address.setAddUserID(rs.getInt("userid"));
            address.setAddId(rs.getString("addressid"));
            address.setAddHouseNo(rs.getString("houseno"));
            address.setAddStreet(rs.getString("street"));
            address.setAddLandmark(rs.getString("landmark"));
            address.setAddZipcode(rs.getString("zipcode"));
            address.setAddCity(rs.getString("city"));
            address.setAddState(rs.getString("state"));
            address.setAddCountry(rs.getString("country"));
            address.setAddPostalAdd(rs.getString("postaladdress"));

            list.add(address);
        }

        return list;

    }

    // To update address
    @Override
    public void updateAddress(Address address, int id) throws SQLException {

        // to add address
        /* commenting this code for now, as I'm adding new addresses from the Controller itself
        * if (address.getAddId().isEmpty()) {
        *    addAddress(id, address);
        } */
        // to delete address
        String remove = address.getRemoveAddressId();
        String[] removeId = remove.split(" ");

        if (!remove.isEmpty()) {
            // delete address called
            deleteAddress(removeId);
        }

        pstmt = c.prepareStatement(
                "UPDATE userportal_addresses SET houseno = ?, street = ?, city = ?, state = ?, country = ?, zipcode = ?, landmark = ?, postaladdress = ? WHERE userid = ? and addressid=?");
        pstmt.setString(1, address.getAddHouseNo());
        pstmt.setString(2, address.getAddStreet());
        pstmt.setString(3, address.getAddCity());
        pstmt.setString(4, address.getAddState());
        pstmt.setString(5, address.getAddCountry());
        pstmt.setString(6, address.getAddZipcode());
        pstmt.setString(7, address.getAddLandmark());
        pstmt.setString(8, address.getAddPostalAdd());
        pstmt.setInt(9, id);
        pstmt.setString(10, address.getAddId());

        pstmt.executeUpdate();

    }

    // To delete address
    public void deleteAddress(String addressId[]) {
        try {
            for (int counter = 0; counter < addressId.length; counter++) {
                pstmt = c.prepareStatement("delete from userportal_addresses where addressid=?");
                pstmt.setString(1, addressId[counter]);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }

    }

}
