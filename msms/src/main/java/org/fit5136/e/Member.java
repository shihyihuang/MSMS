package org.fit5136.e;

/**
 * Class which is for storing the member information.
 *
 * @version 1.0.0
 */
public class Member extends User {

    private String MemberFirstName;
    private String MemberLastName;
    private String address;
    private char gender;
    private String dob;

    public Member(String email, String password) {
        super(email, password);
    }

    public Member(String email, String password, String memberFirstName, String memberLastName, String address,
            char gender, String dob) {
        super(email, password);
        MemberFirstName = memberFirstName;
        MemberLastName = memberLastName;
        this.address = address;
        this.gender = gender;
        this.dob = dob;
    }

    public String getMemberFirstName() {
        return MemberFirstName;
    }

    public void setMemberFirstName(String memberFirstName) {
        MemberFirstName = memberFirstName;
    }

    public String getMemberLastName() {
        return MemberLastName;
    }

    public void setMemberLastName(String memberLastName) {
        MemberLastName = memberLastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Member{" +
                "Email='" + getEmail() + '\'' +
                "password='" + getPassword() + '\'' +
                "MemberFirstName='" + MemberFirstName + '\'' +
                ", MemberLastName='" + MemberLastName + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", dob='" + dob + '\'' +
                '}';
    }

}
