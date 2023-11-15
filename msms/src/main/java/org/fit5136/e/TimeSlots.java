package org.fit5136.e;

/**
 * Class which is for storing the opening hours information.
 *
 * @version 1.0.0
 */
public class TimeSlots {

    String day;
    String openingTime;
    String closeTime;

    public TimeSlots(String day, String openingTime, String closeTime) {
        this.day = day;
        this.openingTime = openingTime;
        this.closeTime = closeTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    @Override
    public String toString() {
        return "OpeningHours{==" +
                "day='" + day + '\'' +
                ", openingTime='" + openingTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                '}';
    }

}
