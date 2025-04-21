package com.hibernate.main;

import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.hibernate.serviceImpl.Booking_StatusServiceImpl;
import com.hibernate.serviceImpl.CustomerServiceImpl;
import com.hibernate.serviceImpl.EmployeeServiceImpl;
import com.hibernate.serviceImpl.HotelServiceImpl;
import com.hibernate.serviceImpl.PaymentsServiceImpl;
import com.hibernate.serviceImpl.RoomsServiceImpl;
import com.hibernate.utility.ConfigurationUtility;

/*
 * This class provides the main entry point for the Hotel Room Management application.
 * It provides a menu-driven interface for users to interact with the application.
 */
public class HotelRoomManagementOperations {

    public static void main(String[] args) {
        // Create a SessionFactory instance to manage sessions
        SessionFactory getsFactory = ConfigurationUtility.getsFactory();
        
        // Open a new session
        Session session = getsFactory.openSession();
        
        // Begin a new transaction
        Transaction transaction = session.beginTransaction();
        
        // Create a Scanner instance to read user input
        Scanner sc = new Scanner(System.in);
        
        // Create instances of service implementation classes
        CustomerServiceImpl customerservice1 = new CustomerServiceImpl();
        HotelServiceImpl hotelservice1 = new HotelServiceImpl();
        EmployeeServiceImpl employeeservice1 = new EmployeeServiceImpl();
        RoomsServiceImpl roomservice1 = new RoomsServiceImpl();
        Booking_StatusServiceImpl bookingservice1 = new Booking_StatusServiceImpl();
        PaymentsServiceImpl paymentservice1 = new PaymentsServiceImpl();
        
        // Display the welcome message and menu options
        System.out.println("------------ Welcome to Minerva Resort ------------");
        System.out.println("-------------------------------------------------------");
        System.out.println("Please Select Your Option");
        System.out.println("-------------------------------------------------------");
        
        try {
            // Loop indefinitely until the user chooses to exit
            while (true) {
                // Display the menu options
                System.out.println("1. View Hotel Details");
                System.out.println("2. View Employee Information");
                System.out.println("3. Manage Customer Information");
                System.out.println("4. View Booking Information");
                System.out.println("5. Manage Room Information");
                System.out.println("6. View Payments Information");
                System.out.println("7. Exit Application");
                
                // Read the user's choice
                int option = sc.nextInt();
                
                // Handle the user's choice
                switch (option) {
                    case 1:
                        // View hotel details
                        hotelservice1.menuDriven(getsFactory);
                        break;
                    case 2:
                        // View employee information
                        employeeservice1.menuDriven(getsFactory);
                        break;
                    case 3:
                        // Manage customer information
                        customerservice1.menuDriven(getsFactory);
                        break;
                    case 4:
                        // View booking information
                        bookingservice1.menuDriven(getsFactory);
                        break;
                    case 5:
                        // Manage room information
                        roomservice1.menuDriven(getsFactory);
                        break;
                    case 6:
                        // View payments information
                        paymentservice1.menuDriven(getsFactory);
                        break;
                    case 7:
                        // Exit the application
                        System.out.println("Exiting application. Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        // Handle invalid options
                        System.out.println("Invalid Option. Please choose again");
                        break;
                }
            }
        } finally {
            // Close the SessionFactory instance
            getsFactory.close();
        }
    }
}
