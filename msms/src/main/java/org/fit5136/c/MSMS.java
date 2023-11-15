package org.fit5136.c;

import org.fit5136.b.Database;
import org.fit5136.b.Input;
import org.fit5136.b.SystemInterface;
import org.fit5136.e.*;
import org.fit5136.e.Queue;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * This class is the main class of the system. It contains all the methods that
 * are used to interact with the user.
 */
public class MSMS {

    Member currentMember = null;
    ArrayList<Member> members = new ArrayList<Member>();
    ArrayList<String> suburbs = new ArrayList<String>();
    ArrayList<String> postcodes = new ArrayList<String>();
    ArrayList<Branch> branches = new ArrayList<Branch>();
    ArrayList<Pt> pts = new ArrayList<Pt>();
    ArrayList<String> reasons = new ArrayList<String>();
    ArrayList<Queue> checkInQueue = new ArrayList<org.fit5136.e.Queue>();
    ArrayList<Booking> bookings = new ArrayList<Booking>();
    String[] weekdays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

    /**
     * This method is for asking user to enter data using keyboard
     * 
     * @param prompt              The prompt to be displayed to the user
     *                            before accepting input.
     * @param validationPredicate The predicate to be used to validate the input.
     * @return The input String as String
     */
    private String askUserInput(String prompt, Predicate<String> validationPredicate) {
        String input = "";
        boolean isValid = false;

        while (!isValid) {
            SystemInterface.display(prompt);
            input = Input.acceptStringInput();

            // Check if the input is valid based on the provided validation predicate
            if (!Validation.checkBlank(input) && validationPredicate.test(input)) {
                isValid = true;
            }
        }

        return input;
    }

    // reference:
    // https://ganeshkumarm1.medium.com/predicate-and-function-in-java-837b46c6dcf7
    /**
     * This method is for searching the item by name
     * 
     * @param list      The list of items to be searched.
     * @param name      The name of the item to be searched.
     * @param predicate The predicate to be used to search the item.
     * @return The item that is found.
     */
    private <T> T searchByName(List<T> list, String name, BiPredicate<T, String> predicate) {
        T targetItem = null;

        for (T item : list) {
            if (predicate.test(item, name)) {
                targetItem = item;
                break;
            }
        }

        return targetItem;
    }

    /**
     * This method is for searching the branch by name
     * 
     * @param name The name of the branch to be searched.
     * @return The branch that is found.
     */
    private Branch searchForBranchByName(String name) {
        return searchByName(this.branches, name, (branch, branchName) -> branch.getBranchName().equals(branchName));
    }

    /**
     * This method is for searching the pt by name
     * 
     * @param firstName The first name of the pt to be searched.
     * @param lastName  The last name of the pt to be searched.
     * @return The branch that is found.
     */
    private Pt searchForPtByName(String firstName, String lastName) {
        return searchByName(this.pts, firstName + " " + lastName,
                (pt, fullName) -> pt.getPtFirstName().equals(firstName) && pt.getPtLastName().equals(lastName));
    }

    /**
     * This method is for search the member by email
     * 
     * @param email The email of the member to be searched.
     * @return The member that is found.
     */
    private Member searchForMemberByEmail(String email) {
        return searchByName(this.members, email, (member, userEmail) -> member.getEmail().equals(userEmail));
    }

    /**
     * This method is for searching the branch by suburb or postcode
     * 
     * @param query The query to be searched.
     * @param type  The type of the query.
     * @return The list of branches that are found.
     */
    private ArrayList<Branch> searchBranchBySuburbOrPostcode(String query, String type) {
        ArrayList<Branch> targetBranches = new ArrayList<Branch>();
        for (Branch branch : this.branches) {
            if ((type == "suburb" && branch.getBranchAddress().toLowerCase().contains(query.toLowerCase()))
                    || (type == "postcode" && branch.getBranchPostcode().equals(query))) {
                targetBranches.add(branch);
            }
        }
        return targetBranches;
    }

    /**
     * This method is for searching the pt by branch name
     * 
     * @param branchName The name of the branch to be searched.
     * @return The list of pts that are found.
     */
    private ArrayList<Pt> searchPtByBranchName(String branchName) {
        ArrayList<Pt> resultPts = new ArrayList<Pt>();

        for (Pt pt : this.pts) {
            if (pt.getBranch().getBranchName().equals(branchName)) {
                resultPts.add(pt);
            }
        }
        return resultPts;
    }

    /**
     * This method is for checking the login
     * 
     * @param email    The email of the member to be checked.
     * @param password The password of the member to be checked.
     * @return True if the login is correct, otherwise false.
     */
    private boolean checkLogin(String email, String password) {
        boolean correct = false;
        User user = new User(email, password);
        // check if the user is in the member list
        for (Member member : this.members) {
            if (member.equals(user)) {
                correct = true;
                this.currentMember = member;
                break;
            }
        }
        return correct;
    }

    /**
     * This method is for selecting the user choice
     * 
     * @param selectType The type of the selection.
     *                   1. "suburb" for selecting suburb
     *                   2. "postcode" for selecting postcode
     * @return The selected item.
     */
    private int selectItem(String selectType) {
        String selection = "";
        int choice = 0;
        ArrayList<String> items = new ArrayList<>();
        while (selection.equals("")) {
            switch (selectType) {
                case "suburb":
                    SystemInterface.displaySuburbMenu(this.suburbs);
                    items = this.suburbs;
                    break;

                case "postcode":
                    SystemInterface.displayPostcodeMenu(this.postcodes);
                    items = this.postcodes;
                    break;
            }

            selection = Input.acceptStringInput();
            if (selection.toLowerCase().equals("m")) {
                return -1;
            }

            if (!Validation.checkIsInt(selection)) {
                SystemInterface.showErrorMsg("Please enter m or the number within the range");
                continue;
            }

            choice = Integer.parseInt(selection);

            if (choice < 0 || choice > items.size()) {
                SystemInterface.showErrorMsg("The selection is out of the valid range!\n");
                selection = "";
            }
        }
        return choice;
    }

    /**
     * This method is for selecting the pt
     * 
     * @param ptList The list of pts to be selected.
     * @return The selected index of the pt.
     */
    private int selectPt(ArrayList<Pt> ptList) {
        int currentIndex = 0;
        String selection = "";

        while (selection.isEmpty()) {
            SystemInterface.displayPtsInformation(ptList, currentIndex);
            selection = Input.acceptStringInput();

            switch (selection.toLowerCase()) {
                case "y":
                    break;
                case "m":
                    // go back to home page
                    return -1;
                case "0":
                    return -2;
                case "f":
                    // add to favorite list
                    try {
                        ArrayList<String> lines = new ArrayList<String>();
                        ArrayList<String> ptRawData = Database.read("Pt.txt");
                        ArrayList<String> ptNameList = new ArrayList<String>();
                        ArrayList<String> ptFavRawData = Database.read("PtFav.txt");

                        for (String ptName : ptFavRawData) {
                            String[] ptNameData = ptName.split("&");
                            ptNameList.add(ptNameData[1] + "&" + ptNameData[2]);

                        }

                        for (String ptRawDatum : ptRawData) {
                            String[] ptData = ptRawDatum.split("&");

                            if (ptData[0].equals(ptList.get(currentIndex).getPtFirstName())
                                    && ptData[1].equals(ptList.get(currentIndex).getPtLastName())) {
                                if (ptNameList.contains(ptList.get(currentIndex).getPtFirstName() + "&"
                                        + ptList.get(currentIndex).getPtLastName())) {
                                    SystemInterface
                                            .showErrorMsg("You have already added this PT to your favorite list");
                                    break;
                                }
                                lines.add(currentMember.getEmail() + "&" + ptRawDatum);
                                Database.writeAppend(lines, "PtFav.txt");
                                SystemInterface.displayAddFavSuccessMessage("PT");
                                Input.acceptStringInput();
                            }
                        }
                    } catch (Exception exception) {
                        SystemInterface.showErrorMsg("Exception:" + exception);
                    }
                    selection = "";
                    break;

                case "n":
                    selection = "";
                    // next page
                    if (currentIndex != ptList.size() - 1)
                        currentIndex = currentIndex + 1;
                    else
                        SystemInterface.showErrorMsg("It is the last one already!!!");
                    break;
                case "p":
                    selection = "";
                    // previous page
                    if (currentIndex != 0)
                        currentIndex = currentIndex - 1;
                    else
                        SystemInterface.showErrorMsg("it is the first one already!!!");
                    break;
                default:
                    selection = "";
                    SystemInterface.showErrorMsg("Please input the valid input!!!");
                    break;
            }
        }

        return currentIndex;
    }

    /**
     * This method is for selecting the branch
     * 
     * @return The selected branch.
     */
    private Branch selectBranch() {
        char selection = 'n';
        Branch targetBranch = null;
        ArrayList<Branch> targetBranches = new ArrayList<>();
        int pageNumber = -1;

        while (Character.toLowerCase(selection) != 'y') {
            // check if the targetBranches is empty
            if (targetBranches.isEmpty()) {
                switch (selection) {
                    case 'n':
                        SystemInterface.displayBranchMenu();
                        selection = Input.acceptCharInput();
                        break;
                    case '1':
                    case '2':
                        String itemType = (selection == '1') ? "suburb" : "postcode";
                        int itemSelection = selectItem(itemType);

                        if (itemSelection == 0) {
                            selection = 'n';
                            continue;
                        }

                        if (itemSelection == -1) {
                            selection = 'y';
                            continue;
                        }

                        String itemValue = (selection == '1') ? suburbs.get(itemSelection - 1)
                                : postcodes.get(itemSelection - 1);
                        targetBranches = searchBranchBySuburbOrPostcode(itemValue, itemType);
                        pageNumber = 0;
                        break;
                    case 'm':
                    case '0':
                        selection = 'y';
                        break;
                    default:
                        SystemInterface.showErrorMsg("You have to enter 1 / 2 / m to perform further actions");
                        selection = 'n';
                        break;
                }
            }

            // Found branches, ask the user to select a branch
            if (targetBranches.size() > 0) {
                SystemInterface.displayBranchesInformation(targetBranches, pageNumber);
                char action = Input.acceptCharInput();
                targetBranch = targetBranches.get(pageNumber);
                switch (Character.toLowerCase(action)) {
                    case 'y':
                        // Save the branch
                        selection = 'y';
                        break;
                    case 'f':
                        // Add to favorites
                        try {
                            ArrayList<String> lines = new ArrayList<String>();
                            ArrayList<String> branchRawData = Database.read("Branches.txt");
                            ArrayList<String> branchNameList = new ArrayList<String>();
                            ArrayList<String> branchFavRawData = Database.read("BranchFav.txt");
                            for (String branchName : branchFavRawData) {
                                String[] branchNameData = branchName.split("&");
                                branchNameList.add(branchNameData[1]);
                            }
                            SystemInterface.display("branchNameList: " + branchNameList);

                            for (String branchRawDatum : branchRawData) {
                                String[] branchData = branchRawDatum.split("&");
                                SystemInterface.display("branchData: " + branchData[0]);

                                // Check if the branch name is the same as the selected branch
                                if (branchData[0].equals(targetBranch.getBranchName())) {
                                    // Check if the branch is already in the favorite list
                                    if (branchNameList.contains(branchData[0])) {
                                        SystemInterface.showErrorMsg(
                                                "You have already added this branch to your favorite list");
                                        break;
                                    }
                                    lines.add(currentMember.getEmail() + "&" + branchRawDatum);
                                    Database.writeAppend(lines, "BranchFav.txt");
                                    SystemInterface.displayAddFavSuccessMessage("branch");
                                    Input.acceptStringInput();
                                }
                            }
                        } catch (Exception exception) {
                            SystemInterface.showErrorMsg("Exception: " + exception);
                        }
                        break;
                    case 'm':
                        // Go back to the home page
                        selection = 'y';
                        break;
                    case 'n':
                        // Go to the next page
                        if (pageNumber != targetBranches.size() - 1) {
                            pageNumber++;
                        } else {
                            SystemInterface.showErrorMsg("Exceed the page number!!!");
                        }
                        break;
                    case 'p':
                        // Go to the previous page
                        if (pageNumber != 0) {
                            pageNumber--;
                        } else {
                            SystemInterface.showErrorMsg("It is the first page already!!!");
                        }
                        break;
                    case '0':
                        targetBranches.clear();
                        break;
                    default:
                        SystemInterface.showErrorMsg("Invalid Input!!!");
                        break;
                }
            } else if (pageNumber == 0 && targetBranches.isEmpty()) {
                SystemInterface.showErrorMsg("No branches could be found, please select others");
            }
        }
        return targetBranch;
    }

    /**
     * This method is for selecting the date
     * 
     * @return The selected date.
     */
    private String selectDate() {
        String date = "";

        while (date.equals("")) {
            SystemInterface.displayDateMenu();
            date = Input.acceptStringInput();
            String errorMsg = "";

            // Check if the user wants to go back to the home page
            if (date.equals("0") || date.toLowerCase().equals("m"))
                continue;

            // validate the date format
            if (!Validation.isValidDateFormat(date)) {
                errorMsg = "Invalid date format!";
            }

            // validate the date is after today
            if (!Validation.checkDateAfterToday(date)) {
                errorMsg = "Cannot input a date earlier than today!";
            }

            // show the error message if exists
            if (errorMsg != "") {
                SystemInterface.showErrorMsg(errorMsg);
                date = "";
                continue;
            }
        }

        return date;
    }

    /**
     * This method is for selecting the TimeSlot
     * 
     * @param timeSlots The list of time slots to be selected.
     * @param date      The date to be selected.
     * @return The index of the selected time slot.
     */
    private int selectTimeSlot(ArrayList<String> timeSlots, String date) {
        String selection = "";
        int choice = 0;

        while (selection.equals("")) {

            SystemInterface.displayTimeSlotMenu(timeSlots);
            selection = Input.acceptStringInput();

            if (selection.toLowerCase().equals("m"))
                return -1;

            if (selection.toLowerCase().equals("0"))
                return 0;

            if (!Validation.checkIsInt(selection)) {
                SystemInterface.showErrorMsg("Please enter a valid input");
                selection = "";
                continue;
            }

            choice = Integer.parseInt(selection);

            if (!Validation.checkRange(choice, 1, timeSlots.size())) {
                SystemInterface.showErrorMsg("The selection is out of the range (1 - " + timeSlots.size() + " )");
                selection = "";
                continue;
            }
            if (choice < timeSlots.size()) {
                String dateStr = timeSlots.get(choice - 1);
                boolean timeBeforeCurrent = Validation.isTimeBeforeCurrent(dateStr);
                boolean isToday = Validation.isToday(date);

                if (timeBeforeCurrent && isToday) {
                    SystemInterface.showErrorMsg("The selected time slot is before the current time!");
                    selection = "";
                    continue;
                }
            }
        }
        return choice;
    }

    /**
     * Display all bookings in paginated format with the proper action options.
     *
     * @param bookings The ArrayList which includes all bookings.
     * @return The selected Booking object.
     */
    private Booking selectBooking(ArrayList<Booking> bookings) {
        Booking targetBooking = null;
        int currentIndex = 0;
        char selection = '1';

        while (selection != 'y' && selection != 'm') {

            SystemInterface.clearIDEConsole();
            this.removePassedBooking();
            SystemInterface.displayBookingsList(bookings, currentIndex);
            selection = Input.acceptCharInput();

            switch (Character.toLowerCase(selection)) {
                // select the specific booking
                case 'y':
                    targetBooking = bookings.get(currentIndex);
                    break;
                // go back to home page
                case 'm':
                    selection = 'm';
                    break;
                // display next booking
                case 'n':
                    if (currentIndex != bookings.size() - 1)
                        currentIndex = currentIndex + 1;
                    else
                        SystemInterface.display("Invalid Input!");
                    break;
                // display previous booking
                case 'p':
                    if (currentIndex != 0)
                        currentIndex = currentIndex - 1;
                    else
                        SystemInterface.display("Invalid Input!");
                    break;
                // invalid input
                default:
                    SystemInterface.showErrorMsg("Please enter the provided option only!");
                    break;
            }
        }
        return targetBooking;
    }

    /**
     * Displays booking options and number of queue based on the selected Booking.
     * Checking if it's 24 hours before the appointment, if it's within 10 minutes
     * before the appointment, and whether the appointment has already been checked
     * in.
     *
     * @param selectedBooking The booking for which booking options are displayed.
     */
    private void displayBookingOption(Booking selectedBooking) {
        if (!Objects.isNull(selectedBooking)) {
            String bookingDateTime = selectedBooking.getBookingDate() + " " + selectedBooking.getBookingStartTime();
            boolean isTwentyFourHoursBefore = Validation.isTwentyFourHoursBefore(bookingDateTime);
            boolean isTenMinutesBefore = Validation.isWithinTenMinutesBefore(bookingDateTime);
            boolean isCheckedIn = selectedBooking.getCheckInStatus();

            SystemInterface.clearIDEConsole();
            // display CANCEL option if it's 24 hours before the appointment and not checked
            // in
            if (isTwentyFourHoursBefore && !isCheckedIn) {
                SystemInterface.display("Enter \"cancel\" to cancel this appointment");
            }
            // display CHECK-IN option if it's 10 minutes before the appointment and not
            // checked in
            if (isTenMinutesBefore && !isCheckedIn) {
                SystemInterface.display("Enter \"Y\" to check in this appointment");
            }
            // display num of ppl in the queue if the booking is already check-in
            if (isCheckedIn) {
                this.displayQueue(selectedBooking);
            }
            // inform users that they are not available to cancel or check-in currently
            if (!isTenMinutesBefore && !isTwentyFourHoursBefore) {
                SystemInterface.display("Sorry you can't cancel your booking now \n" +
                        "please check-in 10 minutes before your appointment time");
            }
            SystemInterface.display("Enter M ( go to main page)");
            SystemInterface.display("Enter 0 ( go to previous page)");
        }
    }

    /**
     * Displays the queue info.
     *
     * @param targetBooking The booking for which queue info are displayed.
     */
    private void displayQueue(Booking targetBooking) {

        this.removeOvertimeQueue();
        int numberOfPeople = this.handleQueue(targetBooking);
        if (numberOfPeople == 0) {
            SystemInterface.display("You're at the front of the queue with no one ahead of you");
        }
        if (numberOfPeople > 0) {
            SystemInterface.display("Total " + numberOfPeople + " people are in front of the queue");
        }
    }

    /**
     * Removes the selected Booking from both bookingDatabase and bookings
     * ArrayList.
     *
     * @param canceledBooking The booking selected to cancel.
     * @return True if the booking was successfully removed from both the database
     *         and the ArrayList, otherwise false.
     */
    private boolean removeCanceledBooking(Booking canceledBooking) {
        boolean successfulRemoveFromDb = false;
        boolean successfulRemoveFromBookings = false;
        String line = canceledBooking.getMember().getEmail() + "&" + canceledBooking.getMemberStatus() + "&"
                + canceledBooking.getBookingDate() + "&" + canceledBooking.getBookingStartTime() + "&"
                + canceledBooking.getBookingReason() + "&" + canceledBooking.getBranch().getBranchName() + "&"
                + canceledBooking.getPt().getPtFirstName() + "&" + canceledBooking.getPt().getPtLastName() + "&"
                + canceledBooking.getBookingDuration() + "&" + canceledBooking.getCheckInStatus();

        try {
            // remove the canceledBooking from the Booking database
            successfulRemoveFromDb = Database.remove(line, "Booking.txt");
            // remove the canceledBooking from the bookings ArrayList
            successfulRemoveFromBookings = this.bookings.removeIf(
                    currentBooking -> currentBooking.equals(canceledBooking));
        } catch (IOException e) {
            SystemInterface.showErrorMsg("removeCanceledBooking exception" + e);
        }

        return successfulRemoveFromDb && successfulRemoveFromBookings;
    }

    /**
     * Removes entries that are 45 minutes after check-in from both queueDatabase
     * and checkInQueue ArrayList.
     *
     */
    private void removeOvertimeQueue() {
        try {
            ArrayList<String> queueDb = Database.read("Queue.txt");
            ArrayList<org.fit5136.e.Queue> removeList = new ArrayList<>();

            for (String checkInData : queueDb) {
                if (checkInData.trim().isEmpty()) {
                    // remove empty line
                    Database.remove(checkInData, "Queue.txt");
                }
                // check if now is over 45 minutes after checkInTime
                if (!checkInData.trim().isEmpty()) {
                    String[] value = checkInData.split("&");
                    String dbCheckInTime = value[0];
                    boolean afterFortyFiveMinutes = Validation.isFortyFiveMinutesAfter(dbCheckInTime);

                    // Auto remove 45 minutes after check-in
                    if (afterFortyFiveMinutes) {
                        Database.remove(checkInData, "Queue.txt");

                        // Find and remove the corresponding Queue object by Member from checkInQueue
                        // ArrayList
                        for (org.fit5136.e.Queue queue : checkInQueue) {
                            if (queue.getCheckInDateTime().equals(dbCheckInTime)) {
                                removeList.add(queue);
                                break;
                            }
                        }
                    }
                }
            }
            this.checkInQueue.removeAll(removeList);

        } catch (Exception exception) {
            SystemInterface.showErrorMsg("removeOvertimeQueue exception" + exception);
        }
    }

    /**
     * Removes the expired bookings from both bookingDatabase and bookings
     * ArrayList.
     *
     */
    private void removePassedBooking() {

        try {
            ArrayList<String> bookingsDb = Database.read("Booking.txt");
            ArrayList<Booking> bookingsToRemove = new ArrayList<>();

            for (String booking : bookingsDb) {
                if (!booking.trim().isEmpty()) {
                    String[] bookingsDbSplit = booking.split("&");
                    String bookingsDbDateTime = bookingsDbSplit[2] + " " + bookingsDbSplit[3];
                    boolean isPassed = Validation.isPassed(bookingsDbDateTime);

                    if (isPassed) {

                        // Remove from the database
                        Database.remove(booking, "Booking.txt");

                        // Find and add to remove list
                        for (Booking foundBooking : this.bookings) {
                            String bookingsDateTime = foundBooking.getBookingDate() + " "
                                    + foundBooking.getBookingStartTime();
                            if (bookingsDateTime.equals(bookingsDbDateTime)) {
                                bookingsToRemove.add(foundBooking);
                            }
                        }
                    }
                }
            }
            // remove all elements of bookingsToRemove from bookings
            this.bookings.removeAll(bookingsToRemove);

        } catch (Exception exception) {
            SystemInterface.showErrorMsg("removePassedBooking exception" + exception);
        }
    }

    /**
     * Update bookingDatabase to change the booking status to true once the booking
     * is checked-in.
     *
     * @param updatedBooking The booking that is checking in.
     */
    private boolean updateBookingStatusInDatabase(Booking updatedBooking) {
        boolean successUpdateBookingDb = false;
        String line = updatedBooking.getMember().getEmail() + "&" + updatedBooking.getMemberStatus() + "&"
                + updatedBooking.getBookingDate() + "&" + updatedBooking.getBookingStartTime() + "&"
                + updatedBooking.getBookingReason() + "&" + updatedBooking.getBranch().getBranchName() + "&"
                + updatedBooking.getPt().getPtFirstName() + "&" + updatedBooking.getPt().getPtLastName() + "&"
                + updatedBooking.getBookingDuration() + "&" + updatedBooking.getCheckInStatus();
        try {
            successUpdateBookingDb = Database.updateStatus(line, "Booking.txt");
        } catch (IOException e) {
            SystemInterface.showErrorMsg(e.toString());
        }
        return successUpdateBookingDb;
    }

    /**
     * Manages the interaction with a selected booking, allowing the user to perform
     * actions
     * such as canceling or checking in for the appointment,
     * displays available options based on the selected booking's status and user
     * Input.
     */
    private void manageBooking() {
        Booking selectedBooking = null;
        String selection = "";

        while (selection != "m") {
            // display the action options based on condition of the selected Booking
            if (Objects.isNull(selectedBooking)) {
                selectedBooking = this.selectBooking(this.bookings);
                this.displayBookingOption(selectedBooking);
            }
            // redirect to home if no Booking is selected
            if (Objects.isNull(selectedBooking)) {
                selection = "m";
                break;
            }

            selection = Input.acceptStringInput();

            if (selection.equals("0")) {
                // User wants to go back to the previous page
                selectedBooking = null;
            } else {

                switch (selection.toLowerCase()) {
                    // cancel selected Booking
                    case "cancel":
                        boolean successRemove = this.removeCanceledBooking(selectedBooking);
                        if (successRemove) {
                            SystemInterface.displayCancelBookingSuccessMessage();
                            Input.acceptStringInput();
                        } else {
                            SystemInterface.showErrorMsg("Sorry! Failed to cancel the appointment!");
                        }
                        selection = "m";
                        break;
                    // check-in selected Booking
                    case "y":
                        this.handleCheckin(selectedBooking);
                        Input.acceptStringInput();
                        selection = "m";
                        break;
                    // go back to home page
                    case "m":
                        selection = "m";
                        break;
                    // invalid input
                    default:
                        SystemInterface.display("Invalid Input!!!");
                        break;
                }
            }
        }
        SystemInterface.clearIDEConsole();
    }

    private void insertBookingIntoDatabase(Booking booking) {

        ArrayList<String> lines = new ArrayList<String>();

        bookings.add(booking);

        for (Booking item : bookings) {
            String bookingData = item.getMember().getEmail() + "&" + item.getMemberStatus() + "&"
                    + item.getBookingDate() + "&" + item.getBookingStartTime() + "&"
                    + item.getBookingReason() + "&" + item.getBranch().getBranchName() + "&"
                    + item.getPt().getPtFirstName() + "&" + item.getPt().getPtLastName() + "&"
                    + item.getBookingDuration() + "&" + item.getCheckInStatus();
            lines.add(bookingData);
        }

        try {
            Database.write(lines, "Booking.txt");
        } catch (IOException e) {
            SystemInterface.display(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Insert the booking info including check-in time to both queueDatabase and
     * checkInQueue ArrayList.
     *
     * @param checkinBooking The booking which is checking in.
     */
    private void insertQueueIntoDatabase(Booking checkinBooking) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String checkInDateTimeStr = currentDateTime.format(formatter);
        String bookingDateTimeStr = checkinBooking.getBookingDate() + " " + checkinBooking.getBookingStartTime();

        // collect the required info for database
        String checkInData = checkInDateTimeStr + "&" + bookingDateTimeStr + "&" +
                checkinBooking.getPt().getPtFirstName() + checkinBooking.getPt().getPtLastName() + "&"
                + checkinBooking.getBranch().getBranchName() + "&" + checkinBooking.getMember();
        ArrayList<String> lines = new ArrayList<String>();
        lines.add(checkInData);

        // collect the required info for Queue obj and add the Queue obj to the
        // ArrayList
        org.fit5136.e.Queue queue = new org.fit5136.e.Queue(checkInDateTimeStr, bookingDateTimeStr,
                checkinBooking.getPt(), checkinBooking.getBranch(), checkinBooking.getMember());
        this.checkInQueue.add(queue);

        try {
            // insert new check-in data to database
            Database.writeAppend(lines, "Queue.txt");
        } catch (IOException e) {
            SystemInterface.display(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Handle the login process.
     *
     */
    private void handleLogin() {
        boolean start = true;
        String email = "";
        String password = "";

        while (start) {
            SystemInterface.displayLoginMenu();
            email = askUserInput("Email:", Validation::isValidEmail);
            password = askUserInput("Password:", pwd -> Validation.checkLength(pwd, 8));
            // doing login here
            if (this.checkLogin(email, password)) {
                SystemInterface.display("Logged in successfully");
                start = false;
            } else {
                SystemInterface.showErrorMsg("Logged in failed");
            }
        }
    }

    /**
     * Handle the opening hours.
     * 
     * @param openingHoursStrings The opening hours in string format.
     * @return The opening hours in TimeSlots format.
     */
    private TimeSlots[] handleOpeningHours(String[] openingHoursStrings) {
        TimeSlots[] timeSlotsObjects = new TimeSlots[openingHoursStrings.length];

        for (int i = 0; i < openingHoursStrings.length; i++) {
            String openingHoursString = openingHoursStrings[i];

            String[] parts = openingHoursString.split(" ");
            String day = parts[0];
            String openingTime = parts[1];
            String closingTime = parts[2];

            TimeSlots timeSlots = new TimeSlots(day, openingTime, closingTime);
            timeSlotsObjects[i] = timeSlots;
        }

        return timeSlotsObjects;
    }

    /**
     * Handle the main menu.
     * 
     * @return The selection by user.
     */
    private int handleMainMenu() {
        SystemInterface.displayMainMenu();
        int selection = Input.acceptNumberInput();
        return selection;
    }

    /**
     * Handle the member status and reason selection.
     *
     * @return The answers by user.
     */
    private String[] handleMemberStatusAndReason() {
        String[] answers = new String[2];

        while (true) {
            SystemInterface.displayMemberStatusMenu();
            SystemInterface.displayReasonMenu(reasons);
            SystemInterface.display("\nPlease separate your answers with a comma. \n" +
                    "e.g. If you're an existing member seeing the PT for recovery,  enter: 1,3");
            SystemInterface.displayBackMenu();
            String answer = Input.acceptStringInput();

            // go back to main menu
            if (answer.equals("0") || answer.toLowerCase().equals("m")) {
                answers[0] = answer;
                break;
            }

            String[] splitAnswers = answer.split(",");

            // check if the input is valid
            if (splitAnswers.length != 2 || !Validation.isNumber(splitAnswers[0])
                    || !Validation.isNumber(splitAnswers[1])) {
                SystemInterface.showErrorMsg("You have to follow the format (e.g. 1,3) and input integers.");
                continue;
            }

            int memberStatus = Integer.parseInt(splitAnswers[0]);
            int reason = Integer.parseInt(splitAnswers[1]);

            // check if the input is within the range
            if (!Validation.checkRange(memberStatus, 1, 2) || !Validation.checkRange(reason, 1, reasons.size())) {
                SystemInterface.showErrorMsg(
                        "Invalid selection. Member status should be between 1 - 2, and reason between 1 - "
                                + reasons.size());
                continue;
            }

            answers[0] = String.valueOf(memberStatus);
            answers[1] = String.valueOf(reason);
            break;
        }

        return answers;
    }

    /**
     * Handle the whole booking process including update Booking database,
     * display the success message while informing the member about how many people
     * are in front of the queue.
     *
     * @param booking The booking that is checking in.
     * @return The selection by user.
     */
    private int handleConfirmBooking(Booking booking) {
        int selection = 0;

        while (selection == 0) {
            SystemInterface.displayConfirmBooking(booking);
            selection = Input.acceptNumberInput();

            // check if the input is valid
            if (!Validation.checkRange(selection, 1, 3)) {
                SystemInterface.showErrorMsg("The selection is out of the range (1 - 3)");
                selection = 0;
                continue;
            }
        }

        return selection;
    }

    /**
     * Handle the whole check-in process including update Booking and Queue
     * database,
     * display the success message while informing the member about how many people
     * are in front of the queue.
     *
     * @param checkinBooking The booking that is checking in.
     */
    private void handleCheckin(Booking checkinBooking) {
        this.updateBookingStatusInDatabase(checkinBooking);
        checkinBooking.setCheckInStatus(true);
        this.insertQueueIntoDatabase(checkinBooking);
        SystemInterface.clearIDEConsole();
        SystemInterface.display("Your appointment has been successfully checked in");
        this.displayQueue(checkinBooking);
        SystemInterface.display("Press enter to go to main page");
    }

    /**
     * Counts how many people are in front of the queue,
     * namely to count how many bookings have the same PT and check-in earlier.
     *
     * @param targetBooking The booking for which queue info are displayed.
     * @return The number of bookings with the same PT that have checked in earlier
     *         than the target booking.
     *
     */
    private int handleQueue(Booking targetBooking) {
        // retrieve the Pt and Member of the Booking
        Pt targetPt = targetBooking.getPt();
        Member targetMember = targetBooking.getMember();
        String targetBookingDateTime = targetBooking.getBookingDate() + " " + targetBooking.getBookingStartTime();

        int count = 0;
        try {
            String targetCheckInTime = "";

            // retrieve the checkInTime of the selected booking searching by Member and
            // booking time
            // as it would be removed 45 later, same member might have 2 records in the
            // queue if having consecutive bookings
            if (!checkInQueue.isEmpty()) {
                for (org.fit5136.e.Queue queue : this.checkInQueue) {
                    if (queue.getMember().equals(targetMember) &&
                            queue.getBookingDateTime().equals(targetBookingDateTime)) {
                        targetCheckInTime = queue.getCheckInDateTime();
                        break;
                    }
                }
            }
            // iterate through the check-in queue to count sessions with the same Pt that
            // are earlier
            for (org.fit5136.e.Queue queue : this.checkInQueue) {
                Pt checkInPt = queue.getPt();
                String checkInTime = queue.getCheckInDateTime();
                // check if the booking has the same Pt and if it is earlier
                boolean samePt = checkInPt.equals(targetPt);
                boolean isEarlier = Validation.isBefore(checkInTime, targetCheckInTime);
                if (samePt && isEarlier) {
                    count++;
                }
            }
        } catch (Exception exception) {
            System.out.print("handleQueue exception" + exception);
        }

        return count;
    }

    private void handleBooking(Branch branch, Pt pt) {

        Branch targetBranch = branch;
        Pt targetPt = pt;
        boolean finishedBooking = false;
        String memberStatus = "";
        String reason = "";
        String date = "";
        String targetTimeSlot = "";
        while (!finishedBooking) {
            if (Objects.isNull(targetBranch)) {
                targetBranch = this.selectBranch();

                // if null == go to main page
                if (Objects.isNull(targetBranch)) {
                    finishedBooking = true;
                    break;
                }
            }

            if (Objects.isNull(targetPt)) {

                ArrayList<Pt> ptArrayList = this.searchPtByBranchName(targetBranch.getBranchName());
                // check if the pt list is empty
                if (ptArrayList.size() == 0) {
                    SystemInterface.display("No Pts can be selected, please choose another branch");
                    continue;
                }

                // show pt list
                SystemInterface.display(
                        "The Pt List of " + targetBranch.getBranchName() + " Branch");
                int selectionPt = this.selectPt(ptArrayList);
                // back to previous page
                if (selectionPt == -2) {
                    targetBranch = null;
                    continue;
                }
                // go to main page
                if (selectionPt == -1) {
                    finishedBooking = true;
                    break;
                }
                targetPt = ptArrayList.get(selectionPt);
            }

            if (Objects.equals(reason, "") || Objects.equals(memberStatus, "")) {
                // handle reason and member status
                String[] answers = this.handleMemberStatusAndReason();
                if (answers[0].equals("0")) {
                    targetPt = null;
                    continue;
                }

                if (answers[0].equals("m")) {
                    finishedBooking = true;
                    break;
                }

                memberStatus = Objects.equals(answers[0], "1") ? "Existing Member" : "New Member";
                reason = this.reasons.get(Integer.parseInt(answers[0]) - 1);
            }

            // handle date
            if (Objects.equals(date, "")) {
                date = this.selectDate();
                if (date.equals("0")) {
                    SystemInterface.display("going back to pervious page");
                    reason = "";
                    date = "";
                    continue;
                }

                // back to main page
                if (date.toLowerCase().equals("m")) {
                    finishedBooking = true;
                    break;
                }
            }

            if (targetTimeSlot.equals("")) {
                Calendar calendar = Calendar.getInstance();
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                String weekday = weekdays[dayOfWeek - 1];
                String startTime = "";
                String endTime = "";

                for (TimeSlots timeSlots : targetBranch.getOpeningHours()) {
                    if (timeSlots.getDay().equals(weekday)) {
                        startTime = timeSlots.getOpeningTime();
                        endTime = timeSlots.getCloseTime();
                        break;
                    }
                }

                ArrayList<String> timeSlots = new ArrayList<String>();

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                try {
                    Date start = timeFormat.parse(startTime);
                    Date close = timeFormat.parse(endTime);

                    calendar.setTime(start);

                    // add time slot to the list
                    while (calendar.getTime().before(close)) {
                        timeSlots.add(timeFormat.format(calendar.getTime()));
                        calendar.add(Calendar.MINUTE, 30);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // handle timeslot
                int timeSlotSelection = this.selectTimeSlot(timeSlots, date);

                // back to previous page
                if (timeSlotSelection == 0) {
                    date = "";
                    targetTimeSlot = "";
                    continue;
                }

                // back to main page
                if (timeSlotSelection == -1) {
                    finishedBooking = true;
                    break;
                }

                targetTimeSlot = timeSlots.get(timeSlotSelection - 1);
            }

            // check the duplicate booking here
            Boolean duplicated = false;
            String errorMsg = "";
            for (Booking existBooking : this.bookings) {
                // check if the pt is occupied in the same date and time
                if (existBooking.getPt().equals(targetPt)
                        && existBooking.getBookingDate().equals(date) &&
                        existBooking.getBookingStartTime().equals(targetTimeSlot)) {

                    duplicated = true;
                    errorMsg = "The pt was occupied in this date and time, please choose a different date and time";
                    break;
                }

                // check if the member is occupied in the same date and time
                if (existBooking.getBookingDate().equals(date) &&
                        existBooking.getBookingStartTime().equals(targetTimeSlot)) {
                    errorMsg = "You already have another booking in the same time slot, please choose a different date and time";
                    duplicated = true;
                    break;
                }
            }

            if (duplicated) {
                date = "";
                targetTimeSlot = "";
                SystemInterface.showErrorMsg(errorMsg);
                continue;
            }

            // confirm booking
            Booking booking = new Booking(currentMember, targetBranch, targetPt, reason, memberStatus, date,
                    targetTimeSlot, "30", false);

            int bookingSelection = this.handleConfirmBooking(booking);

            switch (bookingSelection) {
                case 1:
                    this.insertBookingIntoDatabase(booking);
                    // show booking confirmation
                    SystemInterface.displayBookingSuccessMessage();

                    finishedBooking = true;
                    break;
                case 2:
                    targetBranch = null;
                    targetPt = null;
                    reason = "";
                    date = "";
                    targetTimeSlot = "";
                    break;

                case 3:
                    date = "";
                    targetTimeSlot = "";
                    break;
            }

        }
    }

    /**
     * Displays the favorite list of branches and PTs, and allows the user to
     * select a branch or PT to view the details, remove the branch or PT from
     * the favorite list, or go back to the main menu.
     */
    private void handleFavorite() {
        BranchFav branchFav = new BranchFav();
        PtFav ptFav = new PtFav();

        // read the favorite list from the database
        try {
            ArrayList<Branch> tempBranch = new ArrayList<>();
            ArrayList<String> branchFavRawData = Database.read("BranchFav.txt");
            for (String data : branchFavRawData) {
                String[] values = data.split("&");
                // check if the email is the same as the current member
                if (values[0].trim().equals(currentMember.getEmail())) {
                    Branch branch = this.searchForBranchByName(values[1]);
                    tempBranch.add(branch);
                }
            }
            branchFav.setBranchList(tempBranch);
        } catch (Exception exception) {
            System.out.print("exception" + exception);
        }

        // read the favorite list from the database
        try {
            ArrayList<Pt> tempPt = new ArrayList<Pt>();
            ArrayList<String> ptFavRawData = Database.read("PtFav.txt");
            for (String data : ptFavRawData) {
                String[] values = data.split("&");
                if (values[0].trim().equals(currentMember.getEmail())) {
                    Pt pt = this.searchForPtByName(values[1], values[2]);
                    tempPt.add(pt);
                }
            }
            ptFav.setPtList(tempPt);
        } catch (Exception exception) {
            System.out.print("exception" + exception);
        }

        boolean flag = true;
        while (flag) {
            SystemInterface.displayFavoriteMenu();
            char choice = Input.acceptCharInput();
            switch (Character.toUpperCase(choice)) {
                // display the favorite list of branches
                case '1':
                    boolean flag1 = true;
                    while (flag1){
                        SystemInterface.displayBranchFavorite(branchFav);
                        int selection = Input.acceptNumberInput();
                        int numberOfBranchFav = branchFav.getBranchList().size();
                        if (selection == 0) {
                            flag1 = false;

                        }
                        // check if the selection is out of the range
                        else if (selection > numberOfBranchFav || selection < 0) {
                            SystemInterface.showErrorMsg("Out of the range, select between 1 - " +
                                    numberOfBranchFav);
                        } else {
                            Branch branch = branchFav.getBranchList().get(selection - 1);
                            boolean flag2 = true;
                            while (flag2){
                                SystemInterface.displayBranchFavoriteDetail(branch);
                                char choice2 = Input.acceptCharInput();
                                switch (Character.toUpperCase(choice2)) {
                                    case 'Y':
                                        // process booking
                                        this.handleBooking(branch, null);
                                        flag = false;
                                        flag2 = false;
                                        flag1 = false;
                                        break;
                                    case 'M':
                                        flag = false;
                                        flag1 = false;
                                        flag2 = false;
                                        break;
                                    case 'R':
                                        branchFav.removeBranch(branch);
                                        ArrayList<String> lines = new ArrayList<String>();
                                        for (Branch item : branchFav.getBranchList()) {
                                            String branchData = currentMember.getEmail() + "&" + item.getBranchName() + "&"
                                                    + item.getBranchAddress() + "&" +
                                                    item.getBranchPostcode() + "&" + item.getBranchPhone() + "&"
                                                    + Arrays.toString(item.getOpeningHours());
                                            lines.add(branchData);
                                        }
                                        // write the updated favorite list to the database
                                        try {
                                            Database.write(lines, "BranchFav.txt");
                                            SystemInterface.displayRemoveFavSuccessMessage("branch");
                                            Input.acceptStringInput();
                                        } catch (IOException e) {
                                            SystemInterface.display(e.toString());
                                            e.printStackTrace();
                                        }
                                        break;
                                    case '0':
                                        flag2 = false;
                                        break;
                                    default:
                                        SystemInterface.showErrorMsg("Invalid Input!!!");
                                        break;
                                }
                            }
                        }
                    }
                    break;

                // display the favorite list of PTs
                case '2':
                    boolean flag2 = true;
                    while (flag2) {
                        SystemInterface.displayPtFavorite(ptFav);
                        int selection2 = Input.acceptNumberInput();
                        int numberOfPtFav = ptFav.getPtList().size();
                        if (selection2 == 0) {
                            flag2 = false;
                        }
                        // check if the selection is out of the range
                        else if (selection2 > numberOfPtFav || selection2 < 0){
                            SystemInterface.showErrorMsg("Out of the range, select between 1 - " +
                                    numberOfPtFav);

                        } else {
                            Pt pt = ptFav.getPtList().get(selection2 - 1);
                            boolean flag3 = true;
                            while (flag3) {
                                SystemInterface.displayPtFavoriteDetail(pt);
                                char choice3 = Input.acceptCharInput();
                                switch (Character.toUpperCase(choice3)) {
                                    case 'Y':
                                        // process booking
                                        this.handleBooking(null, pt);
                                        flag3 = false;
                                        flag2 = false;
                                        flag = false;
                                        break;
                                    case 'M':
                                        flag = false;
                                        flag2 = false;
                                        flag3 = false;
                                        break;
                                    case '0':
                                        flag3 = false;
                                        break;
                                    case 'R':
                                        ptFav.removePt(pt);
                                        ArrayList<String> lines = new ArrayList<String>();
                                        for (Pt item : ptFav.getPtList()) {
                                            String ptData = currentMember.getEmail() + "&" + item.getPtFirstName() + "&"
                                                    + item.getPtLastName() + "&" +
                                                    item.getPtGender() + "&" + item.getPtPhone() + "&"
                                                    + Arrays.toString(item.getSpecialisations()) + "&" +
                                                    item.getBranch().getBranchName() + "&" + item.getPtPortfolio() + "&"
                                                    + Arrays.toString(item.getTimeSlots());
                                            lines.add(ptData);
                                        }
                                        // write the updated favorite list to the database
                                        try {
                                            Database.write(lines, "PtFav.txt");
                                            SystemInterface.displayRemoveFavSuccessMessage("PT");
                                            Input.acceptStringInput();
                                        } catch (IOException e) {
                                            SystemInterface.display(e.toString());
                                            e.printStackTrace();
                                        }
                                        break;
                                    default:
                                        SystemInterface.showErrorMsg("Invalid Input!!!");
                                        break;
                                }
                            }
                        }
                    }
                    break;
                case 'M':
                    flag = false;
                    break;
                default:
                    SystemInterface.showErrorMsg("Invalid Input!!!");
                    break;
            }
        }

    }


    // reference: https://www.javatpoint.com/how-to-sort-arraylist-in-java
    /**
     * Sort the Pt list by first name and last name.
     *
     * @param ptList The Pt list to be sorted.
     */
    public static void sortPtList(ArrayList<Pt> ptList) {
        Collections.sort(ptList, new Comparator<Pt>() {
            public int compare(Pt pt1, Pt pt2) {
                int firstNameComparison = pt1.getPtFirstName().compareTo(pt2.getPtFirstName());
                if (firstNameComparison == 0) {
                    return pt1.getPtLastName().compareTo(pt2.getPtLastName());
                }
                return firstNameComparison;
            }
        });
    }

    /**
     * Loading information from data files as the initialization of the system.
     */
    private void initData() {
        // Load postcode data from PostCode.txt
        this.postcodes = Database.loadData("PostCode.txt", (dataStr, data) -> {
            data.add(dataStr.trim());
        });
        // Load suburb data from Suburb.txt
        this.suburbs = Database.loadData("Suburb.txt", (dataStr, data) -> {
            data.add(dataStr.trim());
        });
        // Load checked in booking from Queue.txt
        this.checkInQueue = Database.loadData("Queue.txt", (dataStr, data) -> {
            String[] values = dataStr.split("&");
            if (values.length >= 6) {
                Member targetMember = this.searchForMemberByEmail(values[6]);
                Branch targetBranch = this.searchForBranchByName(values[5]);
                Pt targetPt = this.searchForPtByName(values[3], values[4]);
                org.fit5136.e.Queue queue = new Queue(values[0], values[1], targetPt, targetBranch, targetMember);
                data.add(queue);
            } else {
                System.out.println("Invalid queue data: " + dataStr);
            }
        });
        // Load reasons for seeing pt from Reason.txt
        this.reasons = Database.loadData("Reason.txt", (dataStr, data) -> {
            data.add(dataStr.trim());
        });
        // Load member data from User.txt
        this.members = Database.loadData("User.txt", (dataStr, data) -> {
            String[] values = dataStr.split(" ");
            if (values[0].trim().equals("Member")) {
                Member member = new Member(values[1], values[2]);
                data.add(member);
            }
        });
        // Load branch data from Branches.txt
        this.branches = Database.loadData("Branches.txt", (dataStr, data) -> {
            String[] values = dataStr.split("&");
            if (values.length >= 4) {
                TimeSlots[] openingHours = this.handleOpeningHours(values[4].split(","));
                Branch branch = new Branch(values[0], values[1], values[2], values[3], openingHours);
                data.add(branch);
            } else {
                System.out.println("Invalid branch data: " + dataStr);
            }
        });
        // Load pt data from PT.txt
        this.pts = Database.loadData("PT.txt", (dataStr, data) -> {
            String[] values = dataStr.split("&");
            if (values.length >= 4) {
                TimeSlots[] openingHours = this.handleOpeningHours(values[7].split(","));
                Branch targetBranch = this.searchForBranchByName(values[5]);
                String[] specialisations = values[4].split(",");
                Pt pt = new Pt(values[0], values[1], values[6], values[2].charAt(0), values[3], targetBranch,
                        openingHours, specialisations);
                data.add(pt);
                this.sortPtList(data);
            } else {
                System.out.println("Invalid pt data: " + dataStr);
            }
        });
        // Load booking data from Booking.txt
        this.bookings = Database.loadData("Booking.txt", (dataStr, data) -> {
            String[] values = dataStr.split("&");
            if (values.length >= 10) {
                Member targetMember = this.searchForMemberByEmail(values[0]);
                Branch targetBranch = this.searchForBranchByName(values[5]);
                Pt targetPt = this.searchForPtByName(values[6], values[7]);
                Booking booking = new Booking(targetMember, targetBranch, targetPt, values[4], values[1],
                        values[2], values[3], values[8], Boolean.parseBoolean(values[9]));
                data.add(booking);
            } else {
                System.out.println("Invalid booking data: " + dataStr);
            }
        });
    }

    protected void run() {
        this.initData();
        SystemInterface.clearIDEConsole();
        SystemInterface.displayWelcomeMessage();
        Input.acceptStringInput();
        SystemInterface.clearIDEConsole();
        this.handleLogin();

        if (currentMember != null) {
            int menuSelection = 0;
            while (currentMember != null) {
                switch (menuSelection) {
                    // make an appointment
                    case 0:
                        menuSelection = this.handleMainMenu();
                        break;
                    case 1:
                        this.handleBooking(null, null);
                        menuSelection = 0;
                        break;
                    // my favourite list
                    case 2:
                        this.handleFavorite();
                        menuSelection = 0;

                        break;
                    // my booking details
                    case 3:
                        this.manageBooking();
                        menuSelection = 0;
                        break;
                    case 4:
                        SystemInterface.displayLogoutMessage();
                        currentMember = null;
                        break;

                    default:
                        menuSelection = 0;
                        SystemInterface.showErrorMsg("Invalid option, please try again\n");
                }
            }
        }
    }
}