package com.hibernate.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/* This entity represents the booking status of a hotel room. It contains information about the booking, such as the start and end dates, The price, and the available rooms.*/
@Entity
public class Booking_Status {

	// The start date of the booking. This field is used as the primary key for the entity.
	@Id
	private LocalDate Start_Date;
	// The end date of the booking.
	private LocalDate End_Date;
	// The price of the booking.
	private float Price;
	// The number of available rooms for the booking.
	private int Available_room;

	// Default constructor for the entity.
	public Booking_Status() {
		super();
	}

	/*One-to-many relationship with the Rooms entity. This field represents the rooms that are booked.*/
	@OneToMany(mappedBy = "booking1", cascade = CascadeType.ALL)
	List<Rooms> room2 = new ArrayList<Rooms>();

	/*One-to-many relationship with the Rooms entity. This field represents the
	 rooms that are available. */
	@OneToMany(mappedBy = "booking2", cascade = CascadeType.ALL)
	List<Rooms> room3 = new ArrayList<Rooms>();

	/*Many-to-one relationship with the Customer entity. This field represents the customer who made the booking.*/
	@ManyToOne
	@JoinColumn(name = "Cust_Id")
	private Customer Customer2;

	// Getters and setters for the fields
	public LocalDate getStart_Date() {
		return Start_Date;
	}

	public void setStart_Date(LocalDate start_Date) {
		Start_Date = start_Date;
	}

	public LocalDate getEnd_Date() {
		return End_Date;
	}

	public void setEnd_Date(LocalDate end_Date) {
		End_Date = end_Date;
	}

	public float getPrice() {
		return Price;
	}

	public void setPrice(float price) {
		Price = price;
	}

	public int getAvailable_room() {
		return Available_room;
	}

	public void setAvailable_room(int available_room) {
		Available_room = available_room;
	}

	public Customer getCustomer2() {
		return Customer2;
	}

	public void setCustomer2(Customer customer2) {
		Customer2 = customer2;
	}

	public List<Rooms> getRoom2() {
		return room2;
	}

	public void setRoom2(List<Rooms> room2) {
		this.room2 = room2;
	}

	@Override
	public String toString() {
		return "Booking_Status [Start_Date=" + Start_Date + ", End_Date=" + End_Date + ", Price=" + Price
				+ ", Available_room=" + Available_room + ", Customer2=" + Customer2 + "]";
	}
}
