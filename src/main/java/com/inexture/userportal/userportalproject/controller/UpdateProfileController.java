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
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

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
            List<Address> addressesInSession = (List<Address>) session.getAttribute("AddressList");
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
            /* user part */
            user.setUserFirstname(request.getParameter("firstname"));
            user.setUserMiddlename(request.getParameter("middlename"));
            user.setUserLastname(request.getParameter("lastname"));
            user.setUserEmailID(request.getParameter("emailid"));
            user.setUserUsername(request.getParameter("username"));
            user.setUserDOB(request.getParameter("dob"));
            user.setUserAge(AgeCalculator.calculateAge(request.getParameter("dob")));
            user.setUserHobbies(request.getParameter("hobbies"));

            int id = service.updateProfile(user);

            /* address part */
            // addressIdFromDatabase contains the addresses existing in the database
            String addressIdFromDatabase[] = new String[addressesInSession.size()];
            for (int i = 0; i < addressesInSession.size(); i++) {
                addressIdFromDatabase[i] = addressesInSession.get(i).getAddId();
            }
            // addressId contains the newly gotten addresses IDs from the frontend
            String[] addressId = request.getParameterValues("addressId[]");
            List<String> addressIdListFromFrontend = Arrays.asList(addressId);
            // takes the addresses from the frontend, and checks if it consists of the addresses present in the database,
            //if not, it removes those addresses from the database, that are not found in the frontend
            String remove = "";
            for (int i = 0; i < addressIdFromDatabase.length; i++) {
                if (!addressIdListFromFrontend.contains(addressIdFromDatabase[i])) {
                    remove += addressIdFromDatabase[i] + " ";
                }
            }

            // deleted the commented code for adding the new addresses that were added along with editing and removing addresses
            // from registration.jsp?adminEdit or userEdit, etc


            /* Add new addresses */
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

            /* getting the new addresses, and giving them the ID after the last non-empty addressID */
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

                /* update address function called */
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
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
