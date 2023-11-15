package org.fit5136.e;

/**
 * Class which is for storing the queue information.
 *
 * @version 1.0.0
 */
public class Queue {

    private String checkInDateTime;
    private String bookingDateTime;
    private Pt pt;
    private Branch branch;
    private Member member;

    /**
     * Non-Default constructor which creates the object of the class Queue.
     *
     * @param checkInDateTime The time that the booking was checked in.
     * @param bookingDateTime The scheduled time of the booking.
     * @param pt              The personal trainer of the booking.
     * @param branch          The branch where the booking takes place.
     * @param member          The member who made the booking.
     */
    public Queue(String checkInDateTime, String bookingDateTime, Pt pt, Branch branch, Member member) {
        this.checkInDateTime = checkInDateTime;
        this.bookingDateTime = bookingDateTime;
        this.pt = pt;
        this.branch = branch;
        this.member = member;
    }

    /**
     * Accessor method to get the datetime that the booking was checked in.
     *
     * @return The check-in datetime as String.
     */
    public String getCheckInDateTime() {
        return checkInDateTime;
    }

    /**
     * Mutator method to set the datetime that the booking was checked in.
     *
     * @param checkInDateTime The check-in datetime as String.
     */
    public void setCheckInDateTime(String checkInDateTime) {
        this.checkInDateTime = checkInDateTime;
    }

    /**
     * Accessor method to get the scheduled datetime of the booking.
     *
     * @return The scheduled datetime of the booking as String.
     */
    public String getBookingDateTime() {
        return bookingDateTime;
    }

    /**
     * Mutator method to set the scheduled datetime of the booking.
     *
     * @param bookingDateTime The scheduled datetime of the booking as String.
     */
    public void setBookingDateTime(String bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    /**
     * Accessor method to get the pt of the booking.
     *
     * @return The pt of the booking.
     */
    public Pt getPt() {
        return pt;
    }

    /**
     * Mutator method to set the pt of the booking.
     *
     * @param pt The pt of the booking.
     */
    public void setPt(Pt pt) {
        this.pt = pt;
    }

    /**
     * Accessor method to get the branch where the booking takes place.
     *
     * @return The branch where the booking takes place.
     */
    public Branch getBranch() {
        return branch;
    }

    /**
     * Mutator method to set the branch where the booking takes place.
     *
     * @param branch The branch where the booking takes place.
     */
    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    /**
     * Accessor method to get the member who made the booking.
     *
     * @return The member who made the booking.
     */
    public Member getMember() {
        return member;
    }

    /**
     * Mutator method to set the member who made the booking.
     *
     * @param member The member who made the booking.
     */
    public void setMember(Member member) {
        this.member = member;
    }
}
