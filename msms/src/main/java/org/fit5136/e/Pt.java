package org.fit5136.e;

import java.util.Arrays;

/**
 * Class which is for storing the PT information.
 *
 * @version 1.0.0
 */
public class Pt {

    String ptFirstName;
    String ptLastName;
    String ptPortfolio;
    char ptGender;
    String ptPhone;

    Branch branch;

    TimeSlots[] timeSlots;
    String[] specialisations;

    public Pt() {
    }

    public Pt(String ptFirstName, String ptLastName, String ptPortfolio, char ptGender, String ptPhone, Branch branch,
            TimeSlots[] timeSlots, String[] specialisations) {
        this.ptFirstName = ptFirstName;
        this.ptLastName = ptLastName;
        this.ptPortfolio = ptPortfolio;
        this.ptGender = ptGender;
        this.ptPhone = ptPhone;
        this.branch = branch;
        this.timeSlots = timeSlots;
        this.specialisations = specialisations;
    }

    public String getPtFirstName() {
        return ptFirstName;
    }

    public void setPtFirstName(String ptFirstName) {
        this.ptFirstName = ptFirstName;
    }

    public String getPtLastName() {
        return ptLastName;
    }

    public void setPtLastName(String ptLastName) {
        this.ptLastName = ptLastName;
    }

    public String getPtPortfolio() {
        return ptPortfolio;
    }

    public void setPtPortfolio(String ptPortfolio) {
        this.ptPortfolio = ptPortfolio;
    }

    public char getPtGender() {
        return ptGender;
    }

    public void setPtGender(char ptGender) {
        this.ptGender = ptGender;
    }

    public String getPtPhone() {
        return ptPhone;
    }

    public void setPtPhone(String ptPhone) {
        this.ptPhone = ptPhone;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public TimeSlots[] getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(TimeSlots[] timeSlots) {
        this.timeSlots = timeSlots;
    }

    public String[] getSpecialisations() {
        return specialisations;
    }

    public void setSpecialisations(String[] specialisations) {
        this.specialisations = specialisations;
    }

    @Override
    public String toString() {
        return "Pt{" +
                "ptFirstName='" + ptFirstName + '\'' +
                ", ptLastName='" + ptLastName + '\'' +
                ", ptPortfolio='" + ptPortfolio + '\'' +
                ", ptGender=" + ptGender +
                ", ptPhone='" + ptPhone + '\'' +
                ", branch=" + branch +
                ", timeSlots=" + Arrays.toString(timeSlots) +
                '}';
    }

    public void displayInformation() {
        System.out.println("Pt Name: " + this.ptFirstName + " " + this.ptLastName);
        System.out.println("Gender: " + this.ptGender);

        String speicalizationString = "";

        for (int i = 0; i < this.specialisations.length; i++) {
            speicalizationString += this.specialisations[i];
            if (i != this.specialisations.length - 1)
                speicalizationString += ", ";
        }

        System.out.println("specialisation: " + speicalizationString);
        System.out.println("Branch: " + this.branch.getBranchName());
        System.out.println("Phone: " + this.ptPhone);
        System.out.println(this.ptPortfolio);
    }
}
