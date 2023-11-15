package org.fit5136.c;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Calendar;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Class which is for checking data validation
 *
 * @author Ng Ka Ho Thomas
 * @version ver1.0.0
 */
public class Validation {
    /**
     * Default constructor which creates the object of the class Validation.
     *
     */
    public Validation() {

    }

    /**
     * Method to calculae the score and update into the player object
     *
     * @param text text for checking as string.
     * @return the checking result as boolean.
     */
    public static boolean checkBlank(String text) {
        boolean isBlank = text.trim() == "";
        if (isBlank)
            System.out.println("The input cannot be empty!!!\n");
        return isBlank;
    }

    /**
     * Method to calculae the score and update into the player object
     *
     * @param text   text for checking as string.
     * @param length length for checking as int.
     * @return the checking result as boolean.
     */
    public static boolean checkLength(String text, int length) {
        boolean fulfillMinLength = text.length() >= length ? true : false;
        if (!fulfillMinLength)
            System.out.println("Cannot input less than" + length + " characters!!!\n");
        return fulfillMinLength;
    }

    public static boolean checkIsInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkDateAfterToday(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        try {
            Date date = dateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            Date yesterday = calendar.getTime();

            return !date.before(yesterday);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Method to check if a given integer is within a specified range (inclusive).
     *
     * @param number the integer to be checked.
     * @param min    the minimum value of the range (inclusive).
     * @param max    the maximum value of the range (inclusive).
     * @return true if the integer is within the specified range, false otherwise.
     */
    public static boolean checkRange(int number, int min, int max) {
        return (number >= min && number <= max);
    }

    public static boolean isValidDateFormat(String dateString) {
        // Define the expected date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false); // Disable lenient parsing
        try {
            // Attempt to parse the input string as a date
            Date parsedDate = dateFormat.parse(dateString);

            // Check if the parsed date matches the original input
            return dateFormat.format(parsedDate).equals(dateString);
        } catch (ParseException e) {
            // Parsing failed, indicating an invalid date format
            return false;
        }
    }

    /**
     * Method to check if a given string is a valid email address.
     *
     * @param email the email address to be checked.
     * @return true if the email is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        // Define a regular expression for a valid email address.
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Compile the regex pattern.
        Pattern pattern = Pattern.compile(regex);

        // Match the input email against the pattern.
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches())
            System.out.println("Email format is not correct, please check it");

        // Return true if it's a valid email, otherwise false.
        return matcher.matches();
    }

    /**
     * Check if a string is a valid number or not.
     *
     * @param text the text to be checked.
     * @return true if the text is a valid number, false is not a number.
     */
    public static boolean isNumber(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e1) {
            try {
                Double.parseDouble(text);
                return true;
            } catch (NumberFormatException e2) {
                return false;
            }
        }
    }

    /**
     * Checks if a given datetime has already passed.
     *
     * @param inputDatetimeStr The datetime string in the format "dd/MM/yyyy HH:mm"
     *                         to be
     *                         checked.
     * @return True if the datetime has passed; false otherwise or if the input is
     *         invalid.
     */
    public static boolean isPassed(String inputDatetimeStr) {

        boolean result = false;
        if (!inputDatetimeStr.trim().isEmpty()) {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                // Parse the input date and time
                LocalDateTime inputDateTime = LocalDateTime.parse(inputDatetimeStr, dtf);
                // Get the current date and time
                LocalDateTime currentDateTime = LocalDateTime.now();
                // check if the current time is after the input time
                result = currentDateTime.isAfter(inputDateTime);

            } catch (Exception e) {
                System.out.println("isPassed Exception: " + e);
            }
        }
        return result;
    }

    /**
     * Compare two input datetime, checks if the first input datetime is before the
     * second input datetime.
     *
     * @param datetimeStr       The datetime string to be checked, in the format
     *                          "dd/MM/yyyy HH:mm".
     * @param targetDatetimeStr The target datetime string to compare against, in
     *                          the format "dd/MM/yyyy HH:mm".
     * @return True if the input datetime is before the target datetime; false
     *         otherwise or if the input is invalid.
     */
    public static boolean isBefore(String datetimeStr, String targetDatetimeStr) {
        boolean result = false;

        if (!datetimeStr.trim().isEmpty() && !targetDatetimeStr.trim().isEmpty()) {
            try {
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime date = LocalDateTime.parse(datetimeStr, dateFormat);
                LocalDateTime targetDatetime = LocalDateTime.parse(targetDatetimeStr, dateFormat);
                result = date.isBefore(targetDatetime);

            } catch (Exception e) {
                System.out.println("isBefore Exception: " + e);
            }
        }
        return result;
    }

    /**
     * Checks if a given datetime is at least 24 hours before the current datetime.
     *
     * @param inputDatetimeStr The datetime string in the format "dd/MM/yyyy HH:mm"
     *                         to be
     *                         checked.
     * @return True if the input datetime is at least 24 hours before the current
     *         datetime; false otherwise or if the input is invalid.
     */
    public static boolean isTwentyFourHoursBefore(String inputDatetimeStr) {
        try {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime inputDateTime = LocalDateTime.parse(inputDatetimeStr, dtf);
            LocalDateTime currentDateTime = LocalDateTime.now();
            Duration duration = Duration.between(currentDateTime, inputDateTime);
            boolean isPassed = Validation.isPassed(inputDatetimeStr);
            // return true if the duration is more than 24 hours and the current time is
            // earlier than the input time
            return !isPassed && duration.toMinutes() > 1440;

        } catch (Exception e) {
            System.out.println("isTwentyFourHoursBefore Exception: " + e);
            return false;
        }
    }

    /**
     * Checks if a given datetime is within ten minutes before the current datetime.
     *
     * @param inputDatetimeStr The datetime string in the format "dd/MM/yyyy HH:mm"
     *                         to be
     *                         checked.
     * @return True if the input datetime is within ten minutes before the current
     *         datetime; false otherwise or if the input is invalid.
     */
    public static boolean isWithinTenMinutesBefore(String inputDatetimeStr) {

        try {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime inputDateTime = LocalDateTime.parse(inputDatetimeStr, dtf);
            LocalDateTime currentDateTime = LocalDateTime.now();
            Duration duration = Duration.between(currentDateTime, inputDateTime);
            boolean isPassed = Validation.isPassed(inputDatetimeStr);
            // return true if the duration is within 10 minutes and the current time is
            // earlier than the input time
            return !isPassed && duration.toMinutes() <= 10;

        } catch (Exception e) {
            System.out.println("isWithinTenMinutesBefore Exception: " + e);
            return false;
        }

    }

    /**
     * Checks if the current time is at least 45 minutes after the check-in time.
     *
     * @param checkInDateTimeStr The check-in datetime string in the format
     *                           "dd/MM/yyyy HH:mm" to be checked.
     * @return True if the current time is at least 45 minutes after the input
     *         check-in time; false otherwise or if the input is invalid.
     */
    public static boolean isFortyFiveMinutesAfter(String checkInDateTimeStr) {

        boolean result = false;
        if (!checkInDateTimeStr.trim().isEmpty()) {
            try {

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime checkInDateTime = LocalDateTime.parse(checkInDateTimeStr, dtf);
                LocalDateTime currentDateTime = LocalDateTime.now();
                Duration duration = Duration.between(checkInDateTime, currentDateTime);
                boolean isPassed = Validation.isPassed(checkInDateTimeStr);
                // return true if the duration is over 45 minutes and the current time is later
                // than the input time
                result = isPassed && duration.toMinutes() > 45;

            } catch (Exception e) {
                System.out.println("isFortyFiveMinutesAfter Exception: " + e);
            }
        }
        return result;
    }

    public static boolean isTimeBeforeCurrent(String checkInTimeStr) {
        try {
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime checkInTime = LocalTime.parse(checkInTimeStr, timeFormat);
            LocalTime currentTime = LocalTime.now();
            return checkInTime.isBefore(currentTime);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isToday(String inputDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        LocalDate date = LocalDate.parse(inputDate, dtf);
        return today.equals(date);
    }

}