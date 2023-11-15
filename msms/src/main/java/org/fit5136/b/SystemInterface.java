package org.fit5136.b;

import org.fit5136.e.*;

import java.util.ArrayList;

/**
 * Class which is for displaying information to the user.
 *
 * @version 1.0.0
 */
public class SystemInterface {

    public SystemInterface() {
    }

    public static void display(String content) {
        System.out.println(content);
    }

    public static void showErrorMsg(String errorMessage) {
        SystemInterface.clearIDEConsole();
        System.out.println(errorMessage);
        SystemInterface.display("Press enter to continue...");
        Input.acceptStringInput();

    }

    public static void displayWelcomeMessage() {
        SystemInterface.display("----Welcome to MSMS----");
        SystemInterface.display("""
                Welcome to the Monash Sports Management System (MSMS).
                Commissioned by Monash Local Government, this system allows for effective member
                management and appointment handling, shifting away from 3rd party providers.
                """);
        SystemInterface.display("Press enter to get started!");
    }

    public static void displayLogoutMessage() {
        SystemInterface.display("----Logout----");
        SystemInterface.display("You have successfully logged out");
    }

    public static void displayBackMenu() {
        SystemInterface.display("Enter 0  ( back to previous page )");
        SystemInterface.display("Enter M  ( back to main page )");
        SystemInterface.display("Please enter:");
    }

    public static void displayMainMenu() {
        SystemInterface.clearIDEConsole();
        SystemInterface.display("----Main Menu----");
        SystemInterface.display("1. Make an Appointment");
        SystemInterface.display("2. My Favourite List");
        SystemInterface.display("3. My Booking Details");
        SystemInterface.display("4. Log out");
        SystemInterface.display("Please enter the number of option(e.g. \"1\") :");
    }

    public static void displayBranchMenu() {
        SystemInterface.clearIDEConsole();
        SystemInterface.display("Which method would you like to use for finding a branch?");
        SystemInterface.display("1. Suburb");
        SystemInterface.display("2. Postcode");
        SystemInterface.display("Enter M ( go to main page )");
    }

    public static void displayArrayString(ArrayList<String> items) {
        int index = 1;
        for (String item : items) {
            System.out.println(index + ". " + item);
            index++;
        }
    }

    public static void displaySuburbMenu(ArrayList<String> suburbs) {
        SystemInterface.clearIDEConsole();
        SystemInterface.display("Please enter the number of the suburb you'd like to select");
        SystemInterface.displayArrayString(suburbs);
        SystemInterface.displayBackMenu();
    }

    public static void displayPostcodeMenu(ArrayList<String> postcodes) {
        SystemInterface.clearIDEConsole();
        SystemInterface.display("Please enter the number of the postcode you'd like to select\n");
        SystemInterface.displayArrayString(postcodes);
        SystemInterface.displayBackMenu();
    }

    public static void displayBranchesInformation(ArrayList<Branch> branches, int index) {
        SystemInterface.clearIDEConsole();
        branches.get(index).printInMenu();
        int currentItem = index + 1;
        SystemInterface.display("\nCurrent page: " + currentItem + "/" + branches.size());

        if (branches.size() > 1 && index != branches.size() - 1)
            SystemInterface.display("Enter N ( view next branch )");

        if (branches.size() > 1 && index > 0)
            SystemInterface.display("Enter P ( view previous branch )");

        SystemInterface.display("\nEnter Y ( proceed with booking )");
        SystemInterface.display("Enter F ( add to favorite list )");
        SystemInterface.displayBackMenu();
    }

    public static void displayPtsInformation(ArrayList<Pt> pts, int index) {
        SystemInterface.clearIDEConsole();
        pts.get(index).displayInformation();
        int currentItem = index + 1;
        SystemInterface.display("\nCurrent page: " + currentItem + "/" + pts.size());

        if (pts.size() > 1 && index != pts.size() - 1)
            SystemInterface.display("Enter N ( view next PT )");

        if (pts.size() > 1 && index > 0)
            SystemInterface.display("Enter P ( view previous PT )");

        SystemInterface.display("\nEnter Y ( proceed with booking )");
        SystemInterface.display("Enter F  ( add to favorite list )");
        SystemInterface.displayBackMenu();
    }

    public static void displayReasonMenu(ArrayList<String> reasons) {
        SystemInterface.display("\nWhat's your reason for seeing PT (Choose only one):");
        SystemInterface.displayArrayString(reasons);
    }

    public static void displayMemberStatusMenu() {
        SystemInterface.clearIDEConsole();

        SystemInterface.display("Please answer the following question to confirm booking ");

        SystemInterface.display("\nWhat's your member status:");
        SystemInterface.display("1. Existing Member");
        SystemInterface.display("2. New Member");
    }

    public static void displayLoginMenu() {
        SystemInterface.display("Please login to start to use MSMS");
    }

    public static void displayDateMenu() {
        SystemInterface.clearIDEConsole();
        SystemInterface.display("Please enter the date for your booking");
        SystemInterface.display("in the following format: DD/MM/YYYY e.g. 13/08/2023");
        SystemInterface.displayBackMenu();
    }

    public static void displayTimeSlotMenu(ArrayList<String> timeSlots) {
        SystemInterface.clearIDEConsole();
        SystemInterface.display("Please enter an available timeslot from the options below \n" + //
                "in the following format: HH:MM e.g. 09:00");
        SystemInterface.displayArrayString(timeSlots);
        SystemInterface.displayBackMenu();
    }

    public static void displayBookingSuccessMessage() {
        SystemInterface.clearIDEConsole();
        SystemInterface.display("Thank you for scheduling your appointment !\n" +
                "You can now review it in 'My Booking Details");
        SystemInterface.display("Press enter to go to main page");
        Input.acceptStringInput();
    }

    public static void displayCancelBookingSuccessMessage() {
        SystemInterface.clearIDEConsole();
        SystemInterface.display("Your appointment has been successfully canceled");
        SystemInterface.display("Press enter to go to main page");
    }

    public static void displayRemoveFavSuccessMessage(String type) {
        SystemInterface.clearIDEConsole();
        SystemInterface.display("The " + type + " has been successfully removed from the favorite list !\n");
        SystemInterface.display("Press enter to proceed");
    }

    public static void displayAddFavSuccessMessage(String type) {
        SystemInterface.clearIDEConsole();
        SystemInterface.display("The " + type + " has been successfully added to your favorites list!\n");
        SystemInterface.display("Press enter to proceed");
    }

    public static void displayConfirmBooking(Booking booking) {
        SystemInterface.clearIDEConsole();
        SystemInterface.display("Your Booking Details");
        display("Date Time: " + booking.getBookingDate() + " " + booking.getBookingStartTime());
        SystemInterface.display("PT:" + booking.getPt().getPtFirstName() + " " + booking.getPt().getPtLastName());
        SystemInterface.display("Reason: " + booking.getBookingReason());
        SystemInterface.display("Branch Name: " + booking.getBranch().getBranchName());
        SystemInterface.display("\nPlease enter one of the following options:\n" +
                "1. Confirm and continue\n" +
                "2. Change branch\n" +
                "3. Modify date and time");
    }

    /**
     * Displays info or booking summary and proper options based on the bookings
     * ArrayList and the current index.
     *
     * @param bookings The ArrayList of bookings to be displayed.
     * @param index    The index of the currently displayed booking in the list.
     */
    public static void displayBookingsList(ArrayList<Booking> bookings, int index) {
        try {
            // if the member hasn't booked or all the bookings are passed
            if (bookings.isEmpty()) {
                SystemInterface.display("You don't have any upcoming booking");
                SystemInterface.display("Enter M ( go to main page )");
            }
            if (!bookings.isEmpty()) {
                // display the summary of the selected booking based on the index that the
                // member control
                // and display the proper action options according to the situation
                bookings.get(index).displayBookingSummary();
                int currentItem = index + 1;
                SystemInterface.display("\nCurrent page: " + currentItem + "/" + bookings.size());
                if (bookings.size() > 1)
                    SystemInterface.display("Enter N ( view next Booking )");

                if (bookings.size() > 1 && index > 0)
                    SystemInterface.display("Enter P ( view previous Booking )");

                SystemInterface.display("Enter Y ( proceed to edit or see the detail )");
                SystemInterface.display("Enter M ( go to main page )");

            }
            SystemInterface.display("Please enter:");

        } catch (Exception e) {
            System.out.println("displayBookingsList Exception: " + e);
        }

    }

    public static void displayBranchFavorite(BranchFav branchFav) {
        SystemInterface.clearIDEConsole();
        System.out.println("Branches in your favorite list: ");
        // print out the branch list with index
        int index = 1;
        for (Branch branch : branchFav.getBranchList()) {
            System.out.println(index + ". " + branch.getBranchName());
            index++;
        }
        System.out.println("\nEnter branch's number to continue");
        System.out.println("Enter 0 ( back to previous page )");
    }

    public static void displayBranchFavoriteDetail(Branch branch) {
        SystemInterface.clearIDEConsole();
        branch.printInMenu();
        System.out.println("\nEnter Y ( proceed with booking)");
        System.out.println("Enter R ( remove this branch from your favorite list)");
        SystemInterface.displayBackMenu();
        // System.out.println("Enter 0 ( Go back to previous page)");
        // System.out.println("Enter M ( Go back to main menu )");
    }

    public static void displayPtFavoriteDetail(Pt pt) {
        SystemInterface.clearIDEConsole();
        pt.displayInformation();
        System.out.println("\nEnter Y ( proceed with booking)");
        System.out.println("Enter R ( remove this PT from your favorite list)");
        SystemInterface.displayBackMenu();
    }

    public static void displayPtFavorite(PtFav ptFav) {
        SystemInterface.clearIDEConsole();
        System.out.println("PTs in your favorite list: ");
        // print out the pt list with index
        int index = 1;
        for (Pt pt : ptFav.getPtList()) {
            System.out.println(index + ". " + pt.getPtFirstName() + " " + pt.getPtLastName());
            index++;
        }
        System.out.println("\nEnter Pt's number to continue");
        System.out.println("Enter 0 ( back to previous page )");
    }

    public static void displayFavoriteMenu() {
        SystemInterface.clearIDEConsole();
        System.out.println("Please choose the following option to \nview specific favorite list:\n");
        System.out.println("1. Branch\n2. PT\n");
        System.out.println("Enter 1 ( view branch list )");
        System.out.println("Enter 2 ( view PT list )");
        System.out.println("Enter M ( go to main page )");
    }

    /**
     * Clears the IDE console screen.
     */
    public static void clearIDEConsole() {
        for (int i = 0; i < 35; i++) {
            System.out.println();
        }
    }
}
