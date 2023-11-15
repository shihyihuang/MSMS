package org.fit5136.e;

/**
 * Class which is for storing the booking information.
 *
 * @version 1.0.0
 */
public class Booking {
    Member member;
    Branch branch;
    Pt pt;
    String bookingReason;
    String memberStatus;
    String bookingDate;
    String bookingStartTime;
    String bookingDuration;
    boolean checkInStatus;

    /**
     * Non-Default constructor which creates the object of the class Booking.
     *
     * @param member           The logged in member.
     * @param branch           The branch where the booking takes place.
     * @param pt               The personal trainer of the booking.
     * @param bookingReason    The reason for seeing PT.
     * @param memberStatus     The status indicates whether the member is an
     *                         existing or new member.
     * @param bookingDate      The date of the booking.
     * @param bookingStartTime The start time of the booking.
     * @param bookingDuration  The duration of the booking.
     * @param checkInStatus    The check-in status of the booking.
     */
    public Booking(Member member, Branch branch, Pt pt, String bookingReason, String memberStatus,
            String bookingDate, String bookingStartTime, String bookingDuration, boolean checkInStatus) {
        this.member = member;
        this.branch = branch;
        this.pt = pt;
        this.bookingReason = bookingReason;
        this.memberStatus = memberStatus;
        this.bookingDate = bookingDate;
        this.bookingStartTime = bookingStartTime;
        this.bookingDuration = bookingDuration;
        this.checkInStatus = checkInStatus;
    }

    /**
     * Accessor method to get the member associated with the booking.
     *
     * @return The member making the booking.
     */
    public Member getMember() {
        return member;
    }

    /**
     * Mutator method to set the member associated with the booking.
     *
     * @param member The member making the booking.
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * Accessor method to get the branch associated with the booking.
     *
     * @return The branch where the booking takes place.
     */
    public Branch getBranch() {
        return branch;
    }

    /**
     * Mutator method to set the branch associated with the booking.
     *
     * @param branch The branch where the booking takes place.
     */
    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    /**
     * Accessor method to get the pt associated with the booking.
     *
     * @return The pt of the booking.
     */
    public Pt getPt() {
        return pt;
    }

    /**
     * Mutator method to set the pt associated with the booking.
     *
     * @param pt The pt of the booking.
     */
    public void setPt(Pt pt) {
        this.pt = pt;
    }

    /**
     * Accessor method to get the reason for seeing PT.
     *
     * @return The reason for seeing PT.
     */
    public String getBookingReason() {
        return bookingReason;
    }

    /**
     * Mutator method to set the reason for seeing PT.
     *
     * @param bookingReason The reason for seeing PT.
     */
    public void setBookingReason(String bookingReason) {
        this.bookingReason = bookingReason;
    }

    /**
     * Accessor method to get the member status associated with the member making
     * booking.
     *
     * @return The member status indicating is an existing or new member.
     */
    public String getMemberStatus() {
        return memberStatus;
    }

    /**
     * Mutator method to set the member status associated with the member making
     * booking.
     *
     * @param memberStatus The member status indicating is an existing or new
     *                     member.
     */
    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    /**
     * Accessor method to get the date of the booking.
     *
     * @return The date of the booking as String.
     */
    public String getBookingDate() {
        return bookingDate;
    }

    /**
     * Mutator method to set the date of the booking.
     *
     * @param bookingDate The date of the booking as String.
     */
    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Accessor method to get the start time of the booking.
     *
     * @return The start time of the booking as String.
     */
    public String getBookingStartTime() {
        return bookingStartTime;
    }

    /**
     * Mutator method to set the start time of the booking.
     *
     * @param bookingStartTime The start time of the booking as String.
     */
    public void setBookingStartTime(String bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
    }

    /**
     * Accessor method to get the duration of the booking.
     *
     * @return The duration of the booking as String.
     */
    public String getBookingDuration() {
        return bookingDuration;
    }

    /**
     * Mutator method to set the duration of the booking.
     *
     * @param bookingDuration The duration of the booking as String.
     */
    public void setBookingDuration(String bookingDuration) {
        this.bookingDuration = bookingDuration;
    }

    /**
     * Accessor method to get the check-in status of the booking.
     *
     * @return The boolean indicates whether the booking is checked in or not.
     */
    public boolean getCheckInStatus() {
        return checkInStatus;
    }

    /**
     * Mutator method to set the check-in status of the booking.
     *
     * @param checkInStatus The boolean indicates whether the booking is checked in
     *                      or not.
     */
    public void setCheckInStatus(boolean checkInStatus) {
        this.checkInStatus = checkInStatus;
    }

    /**
     * Print the booking summary including the required info.
     *
     */
    public void displayBookingSummary() {
        System.out.println("\n\nHere's your booking list");
        System.out.println("\nDate Time: " + this.getBookingDate() + " " + this.getBookingStartTime());
        System.out.println("PT: " + this.pt.getPtFirstName() + " " + this.pt.getPtLastName());
        System.out.println("Branch: " + this.branch.getBranchName());
        String displayedCheckInStatus = this.getCheckInStatus() ? "Checked in" : "Not Checked in yet";
        System.out.println("Check-in Status: " + displayedCheckInStatus);
    }

}