package com.hibernate.serviceImpl;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.hibernate.entity.Hotel;
import com.hibernate.service.HotelService;

public class HotelServiceImpl implements HotelService {

    // Scanner instance for taking user inputs
    Scanner sc = new Scanner(System.in);
    Session session;
    SessionFactory sf;

    // Menu-driven interface for managing hotel information
    public void menuDriven(SessionFactory sf) {
        this.sf = sf;
        int choice;
        do {
            // Displaying the menu options
            System.out.println("\n-----------------------------------------------");
            System.out.println("          Hotel Information Management          ");
            System.out.println("-----------------------------------------------");

            System.out.println("1. Register a New Hotel");
            System.out.println("2. Update Existing Hotel Details");
            System.out.println("3. Remove a Hotel Listing");
            System.out.println("4. View All Hotels");
            System.out.println("5. Search for a Hotel");
            System.out.println("6. Exit Hotel Information Management");

            // Taking user input for the option
            System.out.print("\nChoose an option: ");
            choice = sc.nextInt();

            // Handling user choice using switch case
            switch (choice) {
            case 1:
                insertHotel(sf);
                break;
            case 2:
                updateHotel(sf);
                break;
            case 3:
                deleteHotel(sf);
                break;
            case 4:
                getAllHotel(sf);
                break;
            case 5:
                getHotelInformation(sf);
                break;
            case 6:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice. Please choose again.");
            }
        } while (choice != 6); // Loop until the user chooses to exit
    }

    // Method to register a new hotel in the system
    public void insertHotel(SessionFactory sf) {
        session = sf.openSession();  // Opening a new session
        Transaction transaction = session.beginTransaction(); // Starting a transaction
        try {
            Hotel hotel = new Hotel();  // Creating a new hotel object

            // Taking user input for hotel details
            System.out.println("Enter Hotel Details: ");
            System.out.println("Enter Hotel ID: ");
            int hotelid = sc.nextInt();
            sc.nextLine();  // Consume the newline character after the integer input
            hotel.setHotel_Id(hotelid);

            System.out.println("Enter Hotel Name: ");
            String name = sc.next();
            hotel.setHotel_Name(name);

            System.out.println("Enter Location: ");
            String location = sc.next();
            hotel.setLocation(location);

            // Saving the hotel object into the database
            session.save(hotel);

            transaction.commit();  // Committing the transaction
            System.out.println("Hotel Inserted Successfully");

        } catch (Exception e) {
            transaction.rollback();  // Rolling back the transaction in case of an error
            e.printStackTrace();
            System.out.println("An Error Occured while inserting Customer Details..!");

        } finally {
            session.close();  // Closing the session
        }
    }

    // Method to update hotel details based on user input
    public void updateHotel(SessionFactory sf) {
        session = sf.openSession();  // Opening a new session
        Transaction transaction = session.beginTransaction(); // Starting a transaction
        
        try {
            while (true) {
                // Displaying the options to update hotel details
                System.out.println("Choose Option for update: \n1.Hotel Id \n2.Hotel Name \n3.Hotel Location \n4.Exit");
                int option = sc.nextInt();
                if (option == 4) {
                    System.out.println("Exiting Update Process");
                    break;
                }
                
                // Asking for the hotel ID to update the details
                System.out.println("Enter Hotel Id: ");
                Hotel hotel = session.get(Hotel.class, sc.nextInt());  // Fetching hotel from the database

                if (hotel == null) {
                    System.out.println("Hotel not Found. Please Try Again");
                    continue;
                }

                // Performing the update based on the selected option
                switch (option) {
                case 1:
                    System.out.println("Enter new Hotel ID: ");
                    int newHotelId = sc.nextInt();

                    // Create a new hotel entity with the new ID
                    Hotel newHotel = new Hotel();
                    newHotel.setHotel_Id(newHotelId);
                    newHotel.setHotel_Name(hotel.getHotel_Name());
                    newHotel.setLocation(hotel.getLocation());

                    // Saving the new hotel and deleting the old one
                    session.save(newHotel);
                    session.delete(hotel);
                    System.out.println("Hotel ID Updated Successfully!");
                    break;
                case 2:
                    System.out.println("Enter Hotel Name: ");
                    hotel.setHotel_Name(sc.next());
                    System.out.println("Hotel Name Updated Successfully!");
                    break;
                case 3:
                    System.out.println("Enter Location: ");
                    hotel.setLocation(sc.next());
                    System.out.println("Hotel Location Updated Successfully!");
                    break;
                default:
                    System.out.println("Invalid Option. Please choose again");
                    continue;
                }
                
                // Saving the updated hotel object into the database
                session.saveOrUpdate(hotel);
                System.out.println("Hotel Details Updated Successfully!");
                transaction.commit();  // Committing the transaction
            }

        } catch (Exception e) {
            transaction.rollback();  // Rolling back the transaction in case of an error
            e.printStackTrace();
            System.out.println("An Error Occured while updating Hotel Details..!");

        } finally {
            session.close();  // Closing the session
        }

    }

    // Method to delete a hotel record based on the hotel ID
    public void deleteHotel(SessionFactory sf) {
        session = sf.openSession();  // Opening a new session
        Transaction transaction = session.beginTransaction(); // Starting a transaction

        System.out.println("Enter Hotel Id: ");
        Hotel hotel = session.get(Hotel.class, sc.nextInt());  // Fetching the hotel by ID

        if (hotel != null) {
            // Deleting the hotel record from the database
            session.delete(hotel);
            System.out.println("Hotel record deleted successfully......!!");
        } else {
            System.out.println("Enter valid Hotel Id: ");
        }
        
        transaction.commit();  // Committing the transaction
        session.close();  // Closing the session
    }

    // Method to fetch and display all hotels in the system
    public void getAllHotel(SessionFactory sf) {
        session = sf.openSession();  // Opening a new session

        // Creating a query to fetch all hotels
        Query<Hotel> query1 = session.createQuery("from Hotel", Hotel.class);
        List<Hotel> resultList = query1.getResultList();

        // Displaying the hotel details
        for (Hotel h : resultList)
            System.out.println(h);

        session.close();  // Closing the session
    }

    // Method to fetch and display a specific hotel based on the hotel ID
    public void getHotelInformation(SessionFactory sf) {
        session = sf.openSession();  // Opening a new session

        System.out.println("Enter Hotel ID");
        Hotel hotel = session.get(Hotel.class, sc.nextInt());  // Fetching the hotel by ID
        System.out.println(hotel);  // Displaying the hotel details
        session.close();  // Closing the session
    }

}
