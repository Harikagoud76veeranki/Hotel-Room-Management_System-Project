package com.hibernate.entity;

// Import necessary annotations and classes
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/*Entity class representing a hotel room*/
@Entity
public class Rooms {

    // Unique identifier for the room
    @Id
    private int Room_no;

    // Category of the room (e.g. single, double, suite)
    private String Room_Category;

    // Current status of the room (e.g. available, occupied, maintenance)
    private String Room_status;

    // Many-to-one relationship with Booking_Status entity (start date)
    @ManyToOne
    @JoinColumn(name = "Start_Date")
    private Booking_Status booking1;

    // Many-to-one relationship with Booking_Status entity (end date)
    @ManyToOne
    @JoinColumn(name = "End_Date")
    private Booking_Status booking2;

    // Many-to-one relationship with Hotel entity
    @ManyToOne
    @JoinColumn(name = "Hotel_Id")
    private Hotel hotel1;

    // One-to-one relationship with Customer entity (cascade all operations)
    @OneToOne(targetEntity = Customer.class, cascade = CascadeType.ALL)
    private Customer customer;

    // No-arg constructor for entity instantiation
    public Rooms() {
        super();
    }

    // Getters and setters for room attributes
    public int getRoom_no() {
        return Room_no;
    }

    public void setRoom_no(int room_no) {
        Room_no = room_no;
    }

    public String getRoom_Category() {
        return Room_Category;
    }

    public void setRoom_Category(String room_Category) {
        Room_Category = room_Category;
    }

    public String getRoom_status() {
        return Room_status;
    }

    public void setRoom_status(String room_status) {
        Room_status = room_status;
    }

    // Getters and setters for relationships
    public Booking_Status getBooking1() {
        return booking1;
    }

    public void setBooking1(Booking_Status booking1) {
        this.booking1 = booking1;
    }

    public Hotel getHotel1() {
        return hotel1;
    }

    public void setHotel1(Hotel hotel1) {
        this.hotel1 = hotel1;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Booking_Status getBooking2() {
        return booking2;
    }

    public void setBooking2(Booking_Status booking2) {
        this.booking2 = booking2;
    }

    // Override toString method for debugging purposes
    @Override
    public String toString() {
        return "Rooms [Room_no=" + Room_no + ", Room_Category=" + Room_Category + ", Room_status=" + Room_status + ", booking1=" + booking1 + ", booking2=" + booking2 + ", hotel1=" + hotel1 + ", customer=" + customer + "]";
    }
}
