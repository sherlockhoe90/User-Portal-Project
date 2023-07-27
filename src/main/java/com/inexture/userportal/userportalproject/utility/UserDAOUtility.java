package com.inexture.userportal.userportalproject.utility;

import com.inexture.userportal.userportalproject.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/*this class's methods are used in UserDAO*/
public class UserDAOUtility {

    public static void SetResultSetWithinUserObject(User user, ResultSet rs) throws SQLException {
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
    }
}

