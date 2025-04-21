package com.hibernate.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hibernate.entity.Booking_Status;
import com.hibernate.entity.Customer;
import com.hibernate.entity.Hotel;
import com.hibernate.entity.Rooms;
import com.hibernate.service.RoomsService;

public class RoomsServiceImpl implements RoomsService {
	Scanner sc = new Scanner(System.in);  // Scanner object to take user input
	Session session;  // Hibernate session object
	SessionFactory sf;  // Hibernate SessionFactory

	// Menu-driven interface for Room Management System
	public void menuDriven(SessionFactory sf) {
		this.sf = sf;  // Initializing SessionFactory
		int choice;
		do {
			// Displaying options to the user
			System.out.println("\n-----------------------------------------------");
			System.out.println("          Room Management System          ");
			System.out.println("-----------------------------------------------");

			System.out.println("1. Register a New Room");
			System.out.println("2. Update Existing Room Details");
			System.out.println("3. Remove a Room from Inventory");
			System.out.println("4. View All Available Rooms");
			System.out.println("5. Search for a Specific Room");
			System.out.println("6. Exit Room Management System");

			System.out.print("\nEnter your choice: ");
			choice = sc.nextInt();  // Taking user choice

			// Switch case to handle user choice
			switch (choice) {
			case 1:
				insertRooms(sf);  // Call method to insert new room
				break;
			case 2:
				updateRooms(sf);  // Call method to update room details
				break;
			case 3:
				deleteRooms(sf);  // Call method to delete room
				break;
			case 4:
				getAllRooms(sf);  // Call method to view all rooms
				break;
			case 5:
				getRoomsInformation(sf);  // Call method to search specific room
				break;
			case 6:
				System.out.println("Exiting...");  // Exit the menu
				break;
			default:
				System.out.println("Invalid choice. Please choose again.");  // Handle invalid choice
			}
		} while (choice != 6);  // Repeat until user chooses to exit
	}

	// Method to register a new room
	public void insertRooms(SessionFactory sf) {
		Session session = sf.openSession();  // Open a new session
		Transaction transaction = session.beginTransaction();  // Begin transaction

		try {
			Rooms room = new Rooms();  // Create new Room object

			// Prompt user for room details
			System.out.println("Enter Room Details: ");
			System.out.println("Enter Room Number: ");
			int roomNo = sc.nextInt();
			sc.nextLine();  // Consume newline left-over
			room.setRoom_no(roomNo);

			System.out.println("Enter Room Category(AC, NON-AC, Deluxe, Classic, Luxury, Normal): ");
			String roomCategory = sc.nextLine();
			room.setRoom_Category(roomCategory);

			System.out.println("Enter Room Status(Available, Occupied): ");
			String roomStatus = sc.nextLine();
			room.setRoom_status(roomStatus);

			// Assign hotel to room
			System.out.println("Enter Hotel Id: ");
			int hotelId = sc.nextInt();
			Hotel hotel = session.get(Hotel.class, hotelId);
			if (hotel == null) {
				System.out.println("Hotel not found.");
				return;
			}
			room.setHotel1(hotel);

			// Parse start date of booking
			System.out.println("Enter Start Date Of Booking (yyyy-MM-dd): ");
			String Start_Date = sc.next();
			try {
				LocalDate startDate = LocalDate.parse(Start_Date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				Booking_Status booking = session.get(Booking_Status.class, startDate);
				if (booking == null) {
					System.out.println("Booking status not found.");
					return;
				}
				room.setBooking1(booking);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please use yyyy-MM-dd.");
				return;
			}

			// Parse end date of booking
			System.out.println("Enter End Date (yyyy-MM-dd): ");
			String End_Date = sc.next();
			try {
				LocalDate endDate = LocalDate.parse(End_Date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				Booking_Status booking = session.get(Booking_Status.class, endDate);
				if (booking == null) {
					System.out.println("Booking status not found.");
					return;
				}
				room.setBooking2(booking);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please use yyyy-MM-dd.");
				return;
			}

			// Assign customer to room
			System.out.println("Enter Customer Id: ");
			int customerId = sc.nextInt();
			Customer customer = session.get(Customer.class, customerId);
			if (customer == null) {
				System.out.println("Customer not found.");
				return;
			}
			room.setCustomer(customer);

			session.persist(room);  // Persist room object in database
			transaction.commit();  // Commit transaction
			System.out.println("Room Details Inserted Successfully.");

		} catch (Exception e) {
			transaction.rollback();  // Rollback transaction in case of an error
			e.printStackTrace();
			System.out.println("An Error Occured while inserting Customer Details..!");
		} finally {
			session.close();  // Close session
		}
	}

	// Method to update room details
	public void updateRooms(SessionFactory sf) {
		session = sf.openSession();  // Open a session
		Transaction transaction = session.beginTransaction();  // Begin transaction
		try {
			while (true) {
				// Display update options
				System.out.println("Choose Option for update: \n1.Room Number \n2.Room Category \n3.Room Status \n4."
						+ "Hotel Id \n5.Start Date \n6. End Date \n7.Customer Id \n8.Exit");
				int option = sc.nextInt();
				if (option == 8) {
					System.out.println("Exiting Update Process");
					break;  // Exit the update loop
				}

				// Get room object based on room number
				System.out.println("Enter Room Number: ");
				Rooms room = session.get(Rooms.class, sc.nextInt());
				if (room == null) {
					System.out.println("Room Number Not Found. Please Try Another Room Number");
					continue;
				}

				// Switch case for updating different room details
				switch (option) {
				case 1:
					System.out.println("Enter new Room Number: ");
					room.setRoom_no(sc.nextInt());
					System.out.println("Room Number Updated Successfully!");
					break;
				case 2:
					System.out.println("Enter Room Category(AC, NON-AC, Deluxe, Classic, Luxury, Normal): ");
					room.setRoom_Category(sc.next());
					System.out.println("Room Category Updated Successfully!");
					break;
				case 3:
					System.out.println("Enter Room Status(Available, Occupied): ");
					room.setRoom_status(sc.next());
					System.out.println("Room Status Updated Successfully!");
					break;
				case 4:
					System.out.println("Enter Hotel Id: ");
					int Hotel_Id = sc.nextInt();
					Hotel hotel = session.get(Hotel.class, Hotel_Id);
					room.setHotel1(hotel);
					System.out.println("Hotel Id Updated Successfully!");
					break;
				case 5:
					System.out.println("Enter Start Date(yyyy-MM-dd): ");
					String StartDateString = sc.next();
					LocalDate startdate = LocalDate.parse(StartDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					System.out.println("Start Date Updated Successfully!");
					break;
				case 6:
					System.out.println("Enter End Date(yyyy-MM-dd): ");
					String endDateString = sc.next();
					LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					System.out.println("End Date Updated Successfully!");
					break;
				case 7:
					System.out.println("Enter Customer Id: ");
					int Cust_ID = sc.nextInt();
					Customer customer = session.get(Customer.class, Cust_ID);
					room.setCustomer(customer);
					System.out.println("Customer Id Updated Successfully!");
					break;
				default:
					System.out.println("Invalid Option. Please choose again");
					continue;
				}

				session.saveOrUpdate(room);  // Save or update the room in database
				System.out.println("Rooms Updated Successfully!");
				transaction.commit();  // Commit transaction
			}
		} catch (Exception e) {
			transaction.rollback();  // Rollback transaction in case of an error
			e.printStackTrace();
			System.out.println("An Error Occured while updating Rooms Details..!");
		} finally {
			session.close();  // Close session
		}
	}

	// Method to delete a room based on room number
	public void deleteRooms(SessionFactory sf) {
		session = sf.openSession();  // Open a session
		Transaction transaction = session.beginTransaction();  // Begin transaction

		// Get room to delete
		System.out.println("Enter Room Number: ");
		Rooms room = session.get(Rooms.class, sc.nextInt());
		if (room != null) {
			session.delete(room);  // Delete the room record
			System.out.println("Rooms record deleted successfully......!!");
		} else {
			System.out.println("Enter valid Room Number: ");
		}
		transaction.commit();  // Commit transaction
		session.close();  // Close session
	}

	// Method to get and display all rooms
	public void getAllRooms(SessionFactory sf) {
		session = sf.openSession();  // Open a session

		// Query to fetch all rooms from the database
		Query<Rooms> query = session.createQuery("from Rooms", Rooms.class);
		List<Rooms> resultList = query.getResultList();

		// Print details of each room
		for (Rooms r : resultList)
			System.out.println(r);

		session.close();  // Close session
	}

	// Method to search and display a specific room based on room number
	public void getRoomsInformation(SessionFactory sf) {
		session = sf.openSession();  // Open a session

		// Get room based on room number
		System.out.println("Enter Room Number");
		Rooms room = session.get(Rooms.class, sc.nextInt());
		System.out.println(room);  // Print room details
		session.close();  // Close session
	}

}
