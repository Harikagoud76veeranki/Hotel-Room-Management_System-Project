package com.hibernate.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/*This entity represents a customer in the hotel management system. It contains information about the customer, such as their name, date of birth, address, and contact details.*/
@Entity
public class Customer {

    //The unique identifier for the customer.
    @Id
    private int Cust_ID;
    //The customer's first name.
    private String Fname;
    //The customer's last name.
    private String Lname;
    //The customer's date of birth.
    private LocalDate D_O_B;
    //The customer's age.
    private int Age;
    //The customer's city of residence.
    private String City;
    //The customer's state of residence.
    private String State;
    //The customer's street address.
    private String Street;
    //The customer's postal code.
    private int Pincode;
    //The customer's mobile phone number.
    private Long Mobile_no;
    //Default constructor for the entity.
    public Customer() {
        super();
    }

    /*One-to-one relationship with the Rooms entity. This field represents the room assigned to the customer*/
    @OneToOne(targetEntity = Rooms.class)
    @JoinColumn(name = "Room_no")
    private Rooms room1;

    /*One-to-many relationship with the Payments entity. This field represents the payments made by the customer*/
    @OneToMany(mappedBy = "customer1", cascade = CascadeType.ALL)
    List<Payments> payment = new ArrayList<Payments>();

    /*One-to-many relationship with the Booking_Status entity. This field represents the booking status of the customer*/
    @OneToMany(mappedBy = "Customer2", cascade = CascadeType.ALL)
    List<Booking_Status> booking = new ArrayList<Booking_Status>();

    // Getters and setters for the fields
    public int getCust_ID() {
        return Cust_ID;
    }

    public void setCust_ID(int cust_ID) {
        Cust_ID = cust_ID;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public LocalDate getD_O_B() {
        return D_O_B;
    }

    public void setD_O_B(LocalDate d_O_B) {
        D_O_B = d_O_B;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public int getPincode() {
        return Pincode;
    }

    public void setPincode(int pincode) {
        Pincode = pincode;
    }

    public Long getMobile_no() {
        return Mobile_no;
    }

    public void setMobile_no(Long mobile_no) {
        Mobile_no = mobile_no;
    }

    public Rooms getRoom1() {
        return room1;
    }

    public void setRoom1(Rooms room1) {
        this.room1 = room1;
    }

    @Override
    public String toString() {
        return "Customer [Cust_ID=" + Cust_ID + ", Fname=" + Fname + ", Lname=" + Lname + ", D_O_B=" + D_O_B + ", Age=" + Age + ", City=" + City + ", State=" + State + ", Street=" + Street + ", Pincode=" + Pincode + ", Mobile_no=" + Mobile_no + ", room1=" + room1 + "]";
    }
}
