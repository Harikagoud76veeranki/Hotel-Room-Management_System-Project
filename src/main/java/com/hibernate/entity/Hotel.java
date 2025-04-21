package com.hibernate.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/*This entity represents a hotel in the hotel management system. It contains information about the hotel, such as its ID, name, and location.*/
@Entity
public class Hotel {

    //The unique identifier for the hotel.
    @Id
    private int Hotel_Id;
    //The name of the hotel.
    private String Hotel_Name;
    //The location of the hotel.
    private String Location;
    //Default constructor for the entity.
    public Hotel() {
        super();
    }

    /*One-to-many relationship with the Employee entity. This field represents the employees who work at the hotel.*/
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    List<Employee> employee = new ArrayList<Employee>();

    /*One-to-many relationship with the Rooms entity. This field represents the rooms available at the hotel.*/
    @OneToMany(mappedBy = "hotel1", cascade = CascadeType.ALL)
    List<Rooms> room = new ArrayList<Rooms>();

    // Getters and setters for the fields
    public int getHotel_Id() {
        return Hotel_Id;
    }

    public void setHotel_Id(int hotel_Id) {
        Hotel_Id = hotel_Id;
    }

    public String getHotel_Name() {
        return Hotel_Name;
    }

    public void setHotel_Name(String hotel_Name) {
        Hotel_Name = hotel_Name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public List<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(List<Employee> employee) {
        this.employee = employee;
    }

    public List<Rooms> getRoom() {
        return room;
    }

    public void setRoom(List<Rooms> room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Hotel [Hotel_Id=" + Hotel_Id + ", Hotel_Name=" + Hotel_Name + ", Location=" + Location + "]";
    }
}
