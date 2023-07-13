package com.inexture.userportal.userportalproject.utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class AgeCalculator {
    private static Logger logger = LogManager.getLogger("UserLoginController");

    public static void main(String[] args) {}

    public static int calculateAge(String ageFromExternalClass) {
        String birthday = ageFromExternalClass; // Replace with the actual birthday in dd-mm-yyyy format

        if (birthday.equals("")){
            return 0;
        } else {

            // Parse the birthday string into a LocalDate object
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthDate = LocalDate.parse(birthday, formatter);

            // Calculate the age based on the current date
            LocalDate currentDate = LocalDate.now();
            Period age = birthDate.until(currentDate);

            // Retrieve the years, months, and days from the Period object
            int years = age.getYears();
            int months = age.getMonths();
            int days = age.getDays();

            // Print the age
            logger.info("Age: " + years + " years, " + months + " months, " + days + " days");

            //upper ceiling - rounding off the age
            if (months > 6) {
                years++;
            }

            return years;
        }
    }
}
