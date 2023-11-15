package org.fit5136.e;

import java.util.Arrays;

/**
 * Class which is for storing the branch information.
 *
 * @version 1.0.0
 */
public class Branch {

    String branchName;
    String branchAddress;
    String branchPostcode;

    String branchPhone;
    TimeSlots[] openingHours = new TimeSlots[7];

    public Branch() {
    }

    public Branch(String branchName, String branchAddress, String branchPostcode, String branchPhone,
            TimeSlots[] openingHours) {
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.branchPostcode = branchPostcode;
        this.branchPhone = branchPhone;
        this.openingHours = openingHours;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getBranchPostcode() {
        return branchPostcode;
    }

    public void setBranchPostcode(String branchPostcode) {
        this.branchPostcode = branchPostcode;
    }

    public String getBranchPhone() {
        return branchPhone;
    }

    public void setBranchPhone(String branchPhone) {
        this.branchPhone = branchPhone;
    }

    public TimeSlots[] getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(TimeSlots[] openingHours) {
        this.openingHours = openingHours;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branchName='" + branchName + '\'' +
                ", branchAddress='" + branchAddress + '\'' +
                ", branchPostcode='" + branchPostcode + '\'' +
                ", branchPhone='" + branchPhone + '\'' +
                ", openingHours=" + Arrays.toString(openingHours) +
                '}';
    }

    public void printInMenu() {
        System.out.println("Branch Name: " + branchName);
        System.out.println("Address: " + branchAddress);
        System.out.println("Phone: " + branchPhone);
        System.out.println("----Opening hours----: ");
        for (int i = 0; i < openingHours.length; i++) {
            TimeSlots openingHour = this.openingHours[i];
            if (i == 0)
                System.out.println(
                        "[" + openingHour.day + " " + openingHour.openingTime + " - " + openingHour.closeTime + "] ");
            else
                System.out.println(openingHour.day + " " + openingHour.openingTime + " - " + openingHour.closeTime);
        }
    }

}
