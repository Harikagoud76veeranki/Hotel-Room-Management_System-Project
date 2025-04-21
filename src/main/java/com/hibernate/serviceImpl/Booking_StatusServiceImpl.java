// Package declaration for Hibernate service 
package com.hibernate.serviceImpl;

//Import necessary classes and interfaces
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hibernate.entity.Booking_Status;
import com.hibernate.entity.Customer;
import com.hibernate.service.Booking_StatusService;

/*Service implementation class for Booking Status management.*/
public class Booking_StatusServiceImpl implements Booking_StatusService {
	
	 // Scanner object for reading user input
	Scanner sc = new Scanner(System.in);
	// Hibernate session and session factory objects
	Session session;
	SessionFactory sf;
	
	/*Menu-driven method for managing Booking Status.*/
	public void menuDriven(SessionFactory sf) {
		this.sf = sf;
		int choice;
		do {
			System.out.println("\n-----------------------------------------------");
			System.out.println("             Booking Status Manager            ");
			System.out.println("-----------------------------------------------");

			System.out.println("1. Create a New Booking");
			System.out.println("2. Update Existing Booking");
			System.out.println("3. Delete a Booking");
			System.out.println("4. View All Bookings");
			System.out.println("5. Search for a Booking");
			System.out.println("6. Exit Booking Status Manager");

			System.out.print("Enter your choice: ");
			 // Read user choice
			choice = sc.nextInt();
			
			 // Perform action based on user choice
			switch (choice) {
			case 1:
				insertBooking_Status(sf);
				break;
			case 2:
				updateBooking_Status(sf);
				break;
			case 3:
				deleteBooking_Status(sf);
				break;
			case 4:
				getAllBooking_Status(sf);
				break;
			case 5:
				getBooking_StatusInformation(sf);
				break;
			case 6:
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid choice. Please choose again.");
			}

		} while (choice != 6);
	}
	
	/*Method to insert a new Booking Status.*/
	public void insertBooking_Status(SessionFactory sf) {
		 // Open a new Hibernate session
		session = sf.openSession();
        // Begin a new transaction
		Transaction transaction = session.beginTransaction();
		try {
			// Create a new Booking Status object
			Booking_Status booking = new Booking_Status();

			// Read user input for Booking Status details
			System.out.println("Enter Booking Details: ");
			System.out.println("Enter Start Date(yyyy-MM-dd): ");
			String startdateString = sc.next();
			LocalDate startdate = LocalDate.parse(startdateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			booking.setStart_Date(startdate);

			System.out.println("Enter End Date(yyyy-MM-dd): ");
			String enddateString = sc.next();
			LocalDate enddate = LocalDate.parse(enddateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			booking.setEnd_Date(enddate);

			System.out.println("Enter Price Of the room: ");
			Float price = sc.nextFloat();
			booking.setPrice(price);

			System.out.println("Enter The Room Number: ");
			int roomno = sc.nextInt();
			booking.setAvailable_room(roomno);

			System.out.println("Enter Customer Id: ");
			int customerId = sc.nextInt();
			Customer customer = session.get(Customer.class, customerId);
			if (customer == null) {
				System.out.println("Customer not found.");
				return;
			}
			booking.setCustomer2(customer);
			
			// Save the Booking Status object to the database
			session.save(booking);
			// Commit the transaction
			transaction.commit();
			System.out.println("Booking Status Inserted Successfully.");

		} catch (Exception e) {
            // Roll back the transaction if an error occurs
            transaction.rollback();
            e.printStackTrace();
            System.out.println("An Error Occured while inserting Customer Details..!");
        } finally {
            // Close the Hibernate session
            session.close();
        }
    }

    /* Method to update an existing Booking Status.*/
	public void updateBooking_Status(SessionFactory sf) {
		session = sf.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			while (true) {
				// Display update options
				System.out.println(
						"Choose Option for update: \n1.Start Date \n2.End Date \n3.Price \n4.Room Number \n5.Customer Id \n6.Exit");
				int option = sc.nextInt();
				// Read user choice
				if (option == 6) {
					// Exit update process
	                System.out.println("Exiting Update Process");
	                break;
	            }

	            // Read start date for update
	            System.out.println("Enter Start Date(yyyy-MM-dd): ");
	            String startdatestring = sc.next();
	            LocalDate startdate = LocalDate.parse(startdatestring, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

	            // Retrieve Booking Status object from database
	            Booking_Status booking = session.get(Booking_Status.class, startdate);

	            if (booking == null) {
	                System.out.println("Start Date not Found. Please Try Again");
	                continue;
	            }

	            // Perform update based on user choice
	            switch (option) {
	                case 1:
	                    System.out.println("Enter Start Date(yyyy-MM-dd): ");
	                    String startdateString = sc.next();
	                    LocalDate startdate1 = LocalDate.parse(startdateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	                    booking.setStart_Date(startdate1);
	                    System.out.println("Start Date Updated Successfully!");
	                    break;
	                case 2:
	                    System.out.println("Enter End Date(yyyy-MM-dd): ");
	                    String enddateString = sc.next();
	                    LocalDate enddate1 = LocalDate.parse(enddateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	                    booking.setEnd_Date(enddate1);
	                    System.out.println("End Date Updated Successfully!");
	                    break;
	                case 3:
	                    System.out.println("Enter Price: ");
	                    booking.setPrice(sc.nextFloat());
	                    System.out.println("Price Updated Successfully!");
	                    break;
	                case 4:
	                    System.out.println("Enter Room Number: ");
	                    booking.setAvailable_room(sc.nextInt());
	                    System.out.println("Room Number Updated Successfully!");
	                    break;
	                case 5:
	                    System.out.println("Enter Customer Id: ");
	                    int Cust_ID = sc.nextInt();
	                    Customer customer = session.get(Customer.class, Cust_ID);
	                    booking.setCustomer2(customer);
	                    System.out.println("Customer Id Updated Successfully!");
	                    break;
	                default:
	                    System.out.println("Invalid Option. Please choose again");
	                    continue;
	            }

	            // Save updated Booking Status object to database
	            session.saveOrUpdate(booking);
	            // Commit transaction
	            transaction.commit();
	            System.out.println("Booking Status Updated Successfully!");
	        }
	    } catch (Exception e) {
	        // Roll back transaction if error occurs
	        transaction.rollback();
	        e.printStackTrace();
	        System.out.println("An Error Occured while inserting Booking Details..!");
	    } finally {
	        // Close Hibernate session
	        session.close();
	    }
	}

	/*Method to delete a Booking Status*/
	public void deleteBooking_Status(SessionFactory sf) {
	    // Open a new Hibernate session
	    session = sf.openSession();

	    // Begin a new transaction
	    Transaction transaction = session.beginTransaction();

	    System.out.println("Enter Start Date (yyyy-MM-dd) to delete booking status: ");
	    String startdatestring = sc.next();
	    LocalDate startdate = LocalDate.parse(startdatestring, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

	    // Retrieve Booking Status object from database
	    Booking_Status booking = session.get(Booking_Status.class, startdate);

	    if (booking != null) {
	        // Delete Booking Status object from database
	        session.delete(booking);
	        System.out.println("Booking status deleted successfully!");
	    } else {
	        System.out.println("No booking status found for the given start date. Please try again.");
	    }

	    // Commit transaction
	    transaction.commit();

	    // Close Hibernate session
	    session.close();
	}

	/*Method to retrieve all Booking Status objects*/
	public void getAllBooking_Status(SessionFactory sf) {
	    // Open a new Hibernate session
	    session = sf.openSession();

	    // Create a query to retrieve all Booking Status objects
	    Query<Booking_Status> query = session.createQuery("from Booking_Status", Booking_Status.class);

	    // Execute the query and retrieve the results
	    List<Booking_Status> resultList = query.getResultList();

	    // Print the results
	    for (Booking_Status bs : resultList) {
	        System.out.println(bs);
	    }

	    // Close Hibernate session
	    session.close();
	}

	/*Method to retrieve a Booking Status object by start date*/
	public void getBooking_StatusInformation(SessionFactory sf) {
	    // Open a new Hibernate session
	    session = sf.openSession();

	    System.out.println("Enter Start Date: ");
	    Booking_Status booking = session.get(Booking_Status.class, sc.nextInt());

	    System.out.println(booking);

	    // Close Hibernate session
	    session.close();
	}
}
