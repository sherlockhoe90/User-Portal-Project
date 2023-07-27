package com.inexture.userportal.userportalproject.utility;

import com.inexture.userportal.userportalproject.model.Address;
import com.inexture.userportal.userportalproject.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateOnServerSide {

    static List<String> validationErrorsArrayListForUser = new ArrayList<>(); // to be shown on registration.jsp
    static List<String> validationErrorsArrayListForAddress = new ArrayList<>();

    static String emailRegex;
    static String dobRegex;
    static Pattern pattern;
    static Matcher matcher;
    public static List<String> validateUser(User user) {

        /* getting the user details */

        /*each field is first received using req.getParam, and the validated in the next line*/
        /*firstname*/
        if (user.getUserFirstname() == null || user.getUserFirstname().trim().isEmpty()) {
            validationErrorsArrayListForUser.add("Firstname is required.");
        } else if (user.getUserFirstname().length() > 20) {
            validationErrorsArrayListForUser.add("Firstname cannot be longer than 20 characters.");
        }
        /*middle name*/
        if (user.getUserMiddlename() == null || user.getUserMiddlename().trim().isEmpty()) {
            validationErrorsArrayListForUser.add("Middle name is required.");
        } else if (user.getUserMiddlename().length() > 20) {
            validationErrorsArrayListForUser.add("Middlename cannot be longer than 20 characters.");
        }
        /*lastname*/
        if (user.getUserLastname() == null || user.getUserLastname().trim().isEmpty()) {
            validationErrorsArrayListForUser.add("Lastname is required.");
        } else if (user.getUserLastname().length() > 20) {
            validationErrorsArrayListForUser.add("Lastname cannot be longer than 20 characters.");
        }
        /*email id*/
        if (user.getUserEmailID() == null || user.getUserEmailID().trim().isEmpty()) {
            validationErrorsArrayListForUser.add("Email ID is required.");
        } else if (user.getUserEmailID().length() > 40) {
            validationErrorsArrayListForUser.add("Email-ID cannot be longer than 40 characters.");
        } else {
            // checking email pattern using regular expression
            emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            pattern = Pattern.compile(emailRegex);
            matcher = pattern.matcher(user.getUserEmailID());
            if (!matcher.matches()) {
                validationErrorsArrayListForUser.add("Invalid email format.");
            }
        }
        /*username*/
        if (user.getUserUsername() == null || user.getUserUsername().trim().isEmpty()) {
            validationErrorsArrayListForUser.add("Username is required.");
        } else if (user.getUserUsername().length() > 20) {
            validationErrorsArrayListForUser.add("Username cannot be longer than 20 characters.");
        }
        /*password and confirm-password*/
        // encrypting the password before setting it and saving it into the database
        if (user.getUserPassword() == null || user.getUserPassword().trim().isEmpty()) {
            validationErrorsArrayListForUser.add("Password is required.");
        } else if (user.getUserPassword().length() > 30) {
            validationErrorsArrayListForUser.add("Password cannot be longer than 30 characters.");
        }
        if (user.getUserConfirmPassword() == null || user.getUserConfirmPassword().trim().isEmpty() || !user.getUserConfirmPassword().equals(user.getUserPassword())) {
            validationErrorsArrayListForUser.add("Confirm password is either empty, or the passwords do not match.");
        }
        /*date of birth, and age*/
        if (user.getUserDOB() == null || user.getUserDOB().trim().isEmpty()) {
            validationErrorsArrayListForUser.add("Date of Birth is required.");
        } else {
            /* each different regex with different formats is separated by a '|' character in the regex.
             * the date format is yyyy-mm-dd, but it also accepts yyyy.mm.dd, and yyyy/mm/dd */
            /* ((18|19|20)[0-9]{2})-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]) signifies the months 1,3,5,7,8,10, and 12 with 31 days.
             * The year should start with 18, 19, or 20 */
            /* ((18|19|20)[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30) signifies the months 4,6,9 and 11 with 30 days*/
            /* ((18|19|20)[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]) signifies the month 2 February with upto 28 days*/
            /* ((18|19|20)(04|08|[2468][048]|[13579][26]))-02-29 signifies the part that matches leap years in February,
             * allowing February 29 on years that are divisible by 4 but not divisible by 100, except for years divisible by 400 (leap year rule).*/
            dobRegex = "^((18|19|20)[0-9]{2})-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01])|((18|19|20)[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30)|((18|19|20)[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8])|((18|19|20)(04|08|[2468][048]|[13579][26]))-02-29$";
            pattern = Pattern.compile(dobRegex);
            matcher = pattern.matcher(user.getUserDOB());
            String[] dobParts = user.getUserDOB().split("-");
            // Todo: change date of birth validation
            if (!matcher.matches()) {
                validationErrorsArrayListForUser.add("Invalid Date format.");
            } else if (Integer.parseInt(dobParts[0]) < 1900) {
                validationErrorsArrayListForUser.add("The Date cannot be older than 1900.");
            } else if (Integer.parseInt(dobParts[0]) > 2024) {
                validationErrorsArrayListForUser.add("The Date cannot be after 2024.");
            }
        }
        /*hobbies*/
        if (user.getUserHobbies() == null || user.getUserHobbies().trim().isEmpty()) {
            validationErrorsArrayListForUser.add("Hobbies are required.");
        } else if (user.getUserHobbies().length() > 50) {
            validationErrorsArrayListForUser.add("Hobbies cannot be longer than 50 characters.");
        }

        return validationErrorsArrayListForUser;
    }

    public static List<String> validateAddress(Address address) {
        /* creating a new ArrayList as it was causing duplicate references after this validation arraylist is added to the
         * main (and same) validation arraylist. this line to be exact- UserRegisterController:validationErrorsArrayList.addAll(ValidateOnServerSide.validateAddress(addobj));
         * and hence displaying the errors twice on the frontend */

        if (address.getAddHouseNo() == null || address.getAddHouseNo().trim().isEmpty()) {
            validationErrorsArrayListForAddress.add("House No. is required.");
        } else if (address.getAddHouseNo().length() > 10) {
            validationErrorsArrayListForAddress.add("House No cannot be longer than 10 characters.");
        }
        /*street*/
        if (address.getAddStreet() == null || address.getAddStreet().trim().isEmpty()) {
            validationErrorsArrayListForAddress.add("Street is required.");
        } else if (address.getAddStreet().length() > 20) {
            validationErrorsArrayListForAddress.add("Street cannot be longer than 20 characters.");
        }
        /*landmark*/
        if (address.getAddLandmark() == null || address.getAddLandmark().trim().isEmpty()) {
            validationErrorsArrayListForAddress.add("Landmark is required.");
        } else if (address.getAddLandmark().length() > 50) {
            validationErrorsArrayListForAddress.add("Street cannot be longer than 20 characters.");
        }
        /*city*/
        if (address.getAddCity() == null || address.getAddCity().trim().isEmpty() || address.getAddCity().equals("0")) {
            validationErrorsArrayListForAddress.add("City is required.");
        } else if (address.getAddCity().length() > 20) {
            validationErrorsArrayListForAddress.add("City name cannot be longer than 20 characters.");
        }
        /*state*/
        if (address.getAddState() == null || address.getAddState().trim().isEmpty() || address.getAddState().equals("0")) {
            validationErrorsArrayListForAddress.add("State is required.");
        } else if (address.getAddState().length() > 20) {
            validationErrorsArrayListForAddress.add("State name cannot be longer than 20 characters.");
        }
        /*Zipcode*/
        if (address.getAddZipcode() == null || address.getAddZipcode().trim().isEmpty()) {
            validationErrorsArrayListForAddress.add("Zipcode is required.");
        } else if (address.getAddZipcode().length() > 20) {
            validationErrorsArrayListForAddress.add("Zipcode cannot be longer than 20 characters.");
        }
        /*Country*/
        if (address.getAddCountry() == null || address.getAddCountry().trim().isEmpty()) {
            validationErrorsArrayListForAddress.add("Country is required.");
        } else if (address.getAddCountry().length() > 20) {
            validationErrorsArrayListForAddress.add("Country name cannot be longer than 57 characters.");
        }
        /*postal address*/
        if (address.getAddPostalAdd() == null || address.getAddPostalAdd().trim().isEmpty()) {
            validationErrorsArrayListForAddress.add("Postal Address is required.");
        } else if (address.getAddPostalAdd().length() > 250) {
            validationErrorsArrayListForAddress.add("Postal Address cannot be longer than 250 characters.");
        }

        return validationErrorsArrayListForAddress;

    }
}
