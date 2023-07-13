package com.inexture.userportal.userportalproject.controller;

import com.inexture.userportal.userportalproject.dao.UserDAOImp;
import com.inexture.userportal.userportalproject.model.Address;
import com.inexture.userportal.userportalproject.model.User;
import com.inexture.userportal.userportalproject.services.AddressService;
import com.inexture.userportal.userportalproject.services.AddressServiceImp;
import com.inexture.userportal.userportalproject.services.UserService;
import com.inexture.userportal.userportalproject.services.UserServiceImp;
import com.inexture.userportal.userportalproject.utility.AgeCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.xml.stream.events.Comment;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

//@WebServlet(name = "UpdateProfileController", value = "/UpdateProfileController")
@MultipartConfig
public class UpdateProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static Logger logger = LogManager.getLogger(UserDAOImp.class.getName());

    public UpdateProfileController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            // to get address list
            List<Address> addList = (List<Address>) session.getAttribute("AddressList");
            UserService service = new UserServiceImp();
            AddressService addservice = new AddressServiceImp();
            // User user = new User();
            User user = (User) session.getAttribute("CurrentUser");
            Address address = new Address();

//            Part file = request.getPart("img");
//            if (file.getSize() == 0) {
//
//                String base64Image = request.getParameter("oldImage");
//                InputStream targetStream = new ByteArrayInputStream(base64Image.getBytes());
//                InputStream is = Base64.getDecoder().wrap(targetStream);
//                user.setUserProfile(is);
//            } else {
//                InputStream imgContent = file.getInputStream();
//                user.setUserProfile(imgContent);
//            }

            /* to set the updated values */
            // user part
            user.setUserFirstname(request.getParameter("firstname"));
            user.setUserMiddlename(request.getParameter("middlename"));
            user.setUserLastname(request.getParameter("lastname"));
            user.setUserEmailID(request.getParameter("emailid"));
            user.setUserUsername(request.getParameter("username"));
            user.setUserDOB(request.getParameter("dob"));
            user.setUserAge(AgeCalculator.calculateAge(request.getParameter("dob")));
            user.setUserHobbies(request.getParameter("hobbies"));

            int id = service.updateProfile(user);

            // address part
            String addrId[] = new String[addList.size()];
            for (int i = 0; i < addList.size(); i++) {
                addrId[i] = addList.get(i).getAddId();
            }
            String[] addressId = request.getParameterValues("addressId[]");
            List<String> addressIdList = Arrays.asList(addressId);
            String remove = "";
            for (int i = 0; i < addrId.length; i++) {
                if (!addressIdList.contains(addrId[i])) {
                    remove += addrId[i] + " ";
                }
            }

            // adding the new addresses that were added along with editing and removing addresses
            // from registration.jsp?adminEdit or userEdit, etc
//            int addingNewAddCount = addrId.length + 1;
//            for (int i = 0; i < addressId.length; i++) {
//                if (Objects.equals(addressId[i], "")) {
//                    String[] houseno = request.getParameterValues("houseno["+i+"]");
//                    String[] street = request.getParameterValues("address["+i+"]");
//                    String[] landmark = request.getParameterValues("landmark["+i+"]");
//                    String[] zipcode = request.getParameterValues("zipcode["+i+"]");
//                    String[] city = request.getParameterValues("city["+i+"]");
//                    String[] state = request.getParameterValues("state["+i+"]");
//                    String[] country = request.getParameterValues("country["+i+"]");
//                    String[] postaladdress = request.getParameterValues("postaladdress["+i+"]");
//
//                    address.setAddId(addressId[addingNewAddCount]);
//                    address.setAddHouseNo(houseno[addingNewAddCount]);
//                    address.setAddStreet(street[addingNewAddCount]);
//                    address.setAddLandmark(landmark[addingNewAddCount]);
//                    address.setAddCity(city[addingNewAddCount]);
//                    address.setAddState(state[addingNewAddCount]);
//                    address.setAddZipcode(zipcode[addingNewAddCount]);
//                    address.setAddCountry(country[addingNewAddCount]);
//                    address.setAddPostalAdd(postaladdress[addingNewAddCount]);
//
//                    addservice.addAddress(user.getUserId(), address);
//                    addingNewAddCount++;
//                }
//
//            }


// Add new addresses
            String[] newHouseno = request.getParameterValues("houseno[]");
            String[] newStreet = request.getParameterValues("address[]");
            String[] newLandmark = request.getParameterValues("landmark[]");
            String[] newZipcode = request.getParameterValues("zipcode[]");
            String[] newCity = request.getParameterValues("city[]");
            String[] newState = request.getParameterValues("state[]");
            String[] newCountry = request.getParameterValues("country[]");
            String[] newPostaladdress = request.getParameterValues("postaladdress[]");

            int lastNonEmptyId = 0;
//            for (int i = 0; i < existingAddressIds.length; i++) {
            for (int i = 0; i < addressId.length; i++) {
                if (!addressId[i].equals("")) {
                    int newAddressId = Integer.parseInt(addressId[i]);
                    if (newAddressId > lastNonEmptyId) {
                        lastNonEmptyId = newAddressId;
                    }
                }
            }

            for (int i = 0; i < newHouseno.length; i++) {
                if (addressId[i].equals("")) {
                    Address newAddress = new Address();
                    lastNonEmptyId++;
                    newAddress.setAddId(String.valueOf(lastNonEmptyId));
                    newAddress.setAddHouseNo(newHouseno[i]);
                    newAddress.setAddStreet(newStreet[i]);
                    newAddress.setAddLandmark(newLandmark[i]);
                    newAddress.setAddCity(newCity[i]);
                    newAddress.setAddState(newState[i]);
                    newAddress.setAddZipcode(newZipcode[i]);
                    newAddress.setAddCountry(newCountry[i]);
                    newAddress.setAddPostalAdd(newPostaladdress[i]);

                    addservice.addAddress(id, newAddress);
                }
            }


            // to get the updated values
            String[] houseno = request.getParameterValues("houseno[]");
            String[] street = request.getParameterValues("address[]");
            String[] landmark = request.getParameterValues("landmark[]");
            String[] zipcode = request.getParameterValues("zipcode[]");
            String[] city = request.getParameterValues("city[]");
            String[] state = request.getParameterValues("state[]");
            String[] country = request.getParameterValues("country[]");
            String[] postaladdress = request.getParameterValues("postaladdress[]");

            int count = 0;
            while (count < street.length) {

                // to set the updated values
                address.setAddId(addressId[count]);
                address.setAddHouseNo(houseno[count]);
                address.setAddStreet(street[count]);
                address.setAddLandmark(landmark[count]);
                address.setAddCity(city[count]);
                address.setAddState(state[count]);
                address.setAddZipcode(zipcode[count]);
                address.setAddCountry(country[count]);
                address.setAddPostalAdd(postaladdress[count]);
                address.setRemoveAddressId(remove);

                // update address function called
                addservice.updateAddress(address, id);
                logger.info("address values inside update servlet" + address);
                count++;
            }

            String uName = (String) session.getAttribute("userName"); //gets this from the JS code on top of registration.jsp
            if (uName.equals("adminEdit")) {
                response.sendRedirect("adminHomePage.jsp");
            } else if (uName.equals("userEdit")) {
                User userList = service.displaySpecificUser(user);
                session.setAttribute("specificUserData", userList);
                List<Address> listAddress = addservice.getAllAddress(id);
                session.setAttribute("AddressList", listAddress);
                // session.setAttribute("CurrentUser", user);
                // response.sendRedirect("UserHomePage.jsp");
                RequestDispatcher req = request.getRequestDispatcher("userHomePage.jsp");
                req.include(request, response);
            } else if (uName.equals("admin")) {
                List<User> adminList = service.displayAdmin(user);
                session.setAttribute("adminList", adminList);
                List<Address> listAddress = addservice.getAllAddress(id);
                session.setAttribute("AddressList", listAddress);
                response.sendRedirect("adminHomePage.jsp");

            }


    } catch(
    SQLException e)

    {
        e.printStackTrace();
    }

}
}
