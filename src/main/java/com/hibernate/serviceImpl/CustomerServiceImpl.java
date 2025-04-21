package com.hibernate.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.hibernate.entity.Customer;
import com.hibernate.entity.Rooms;
import com.hibernate.service.CustomerService;
import com.hibernate.utility.RoomAlreadyAssignedException;
import com.hibernate.utility.RoomNotFoundException;

public class CustomerServiceImpl implements CustomerService {

    // Scanner object for taking user input
    Scanner sc = new Scanner(System.in);
    Session session;
    SessionFactory sf;

    // Menu-driven method to allow user to choose different operations
    public void menuDriven(SessionFactory sf) {
        this.sf = sf;
        int choice;
        do {
            // Displaying the menu for user interaction
            System.out.println("\n-----------------------------------------------");
            System.out.println("          Customer Information Manager          ");
            System.out.println("-----------------------------------------------");

            System.out.println("1. Create a New Customer Account");
            System.out.println("2. Update Existing Customer Details");
            System.out.println("3. Delete a Customer");
            System.out.println("4. View All Customers");
            System.out.println("5. Search for a Customer");
            System.out.println("6. Exit Customer Information Manager");

            // Taking user input for menu choice
            System.out.print("\nChoose an option: ");
            choice = sc.nextInt();

            // Switching to corresponding methods based on the user's choice
            switch (choice) {
            case 1:
                insertCustomer(sf);  // Call method to insert a new customer
                break;
            case 2:
                updateCustomer(sf);  // Call method to update customer details
                break;
            case 3:
                deleteCustomer(sf);  // Call method to delete a customer
                break;
            case 4:
                getAllCustomers(sf);  // Call method to view all customers
                break;
            case 5:
                getCustomerInformation(sf);  // Call method to search a specific customer
                break;
            case 6:
                System.out.println("Exiting...");  // Exit the loop
                break;
            default:
                System.out.println("Invalid choice. Please choose again.");  // In case of invalid choice
            }
        } while (choice != 6);  // Loop continues until user selects option 6 (Exit)
    }

    // Method to insert a new customer into the database
    public void insertCustomer(SessionFactory sf) {

        session = sf.openSession();  // Open a new session
        Transaction transaction = session.beginTransaction();  // Start a transaction

        try {
            // Creating a new Customer object to hold user input
            Customer customer = new Customer();

            System.out.println("Enter Customer Details: ");
            System.out.println("Enter Customer ID: ");
            int customerId = sc.nextInt();  // Taking customer ID as input
            customer.setCust_ID(customerId);

            System.out.println("Enter First Name: ");
            String first = sc.next();  // Taking first name
            customer.setFname(first);

            System.out.println("Enter Last Name: ");
            String last = sc.next();  // Taking last name
            customer.setLname(last);

            System.out.println("Enter Date of Birth(yyyy-MM-dd): ");
            String dateString = sc.next();  // Taking date of birth
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            customer.setD_O_B(date);

            System.out.println("Enter Customer Age: ");
            int age = sc.nextInt();  // Taking customer age
            customer.setAge(age);

            System.out.println("City: ");
            String city = sc.next();  // Taking customer city
            sc.nextLine();
            customer.setCity(city);

            System.out.println("State: ");
            String state = sc.next();  // Taking customer state
            sc.nextLine();
            customer.setState(state);

            System.out.println("Street: ");
            String street = sc.next();  // Taking customer street address
            sc.nextLine();
            customer.setStreet(street);

            System.out.println("Pincode: ");
            int pincode = sc.nextInt();  // Taking pincode
            customer.setPincode(pincode);

            System.out.println("Mobile Number: ");
            long phno = sc.nextLong();  // Taking customer mobile number
            customer.setMobile_no(phno);

            // Asking for room number assignment
            System.out.println("Enter Room No: ");
            int roomno = sc.nextInt();
            Rooms room = session.get(Rooms.class, roomno);  // Fetching room based on room number
            room.setCustomer(customer);

            // Checking if the room is already assigned to another customer
            if (room.getCustomer() != null) {
                throw new RoomAlreadyAssignedException("Room number" + room + "is already assigned to another Customer"
                        + room.getCustomer().getCust_ID());
            } else {
                customer.setRoom1(room);  // Assigning the room to the customer
                System.out.println("Room Updated Successfully!");
            }

            session.persist(customer);  // Persisting the customer object to the database
            transaction.commit();  // Committing the transaction
            System.out.println("Customer Details Inserted Successfully.");

        } catch (Exception e) {
            transaction.rollback();  // In case of error, rollback the transaction
            e.printStackTrace();
            System.out.println("An Error Occured while inserting Customer Details..!");

        } finally {
            session.close();  // Closing session after operation
        }
    }

    // Method to update existing customer details
    public void updateCustomer(SessionFactory sf) {

        session = sf.openSession();  // Open a session
        Transaction transaction = session.beginTransaction();  // Begin transaction

        try {
            while (true) {
                // Providing options for updating different customer details
                System.out.println(
                        "Choose Option for update: \n1.Customer ID \n2.Customer First Name \n3.Customer Last Name \n4."
                                + "Date Of birth \n5.Age \n6.City \n7.State \n8.Street \n9.Pincode \n10.Mobile Number \n11.Room No \n12.Exit");
                int option = sc.nextInt();
                if (option == 12) {
                    System.out.println("Exiting Update Process");  // Exiting update process
                    break;
                }
                System.out.println("Enter Customer Id: ");
                int CustomerId = sc.nextInt();  // Taking customer ID to identify the customer to be updated
                Customer customer = session.get(Customer.class, CustomerId);

                if (customer == null) {
                    System.out.println("Customer " + CustomerId + "not Found. Please Try Again");
                    continue;
                }
                // Switch statement for updating different fields of customer
                switch (option) {
                case 1:
                    System.out.println("Enter new ID: ");
                    customer.setCust_ID(sc.nextInt());
                    System.out.println("Customer ID Updated Successfully!");
                    break;
                case 2:
                    System.out.println("Enter First Name: ");
                    customer.setFname(sc.next());
                    System.out.println("Customer Name Updated Successfully!");
                    break;
                case 3:
                    System.out.println("Enter Last Name: ");
                    customer.setLname(sc.next());
                    System.out.println("Customer Name Updated Successfully!");
                    break;
                case 4:
                    System.out.println("Enter Date of Birth(yyyy-MM-dd): ");
                    String dateString1 = sc.next();
                    LocalDate date = LocalDate.parse(dateString1, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    customer.setD_O_B(date);
                    System.out.println("Customer Date Of Birth Updated Successfully!");
                    break;
                case 5:
                    System.out.println("Enter Age: ");
                    customer.setAge(sc.nextInt());
                    System.out.println("Customer Age Updated Successfully!");
                    break;
                case 6:
                    System.out.println("Enter City: ");
                    customer.setCity(sc.next());
                    System.out.println("Customer City Name Updated Successfully!");
                    break;
                case 7:
                    System.out.println("Enter State: ");
                    customer.setState(sc.next());
                    System.out.println("Customer State Name Updated Successfully!");
                    break;
                case 8:
                    System.out.println("Enter Street: ");
                    customer.setStreet(sc.next());
                    System.out.println("Customer Street Name Updated Successfully!");
                    break;
                case 9:
                    System.out.println("Enter Pin Code: ");
                    customer.setPincode(sc.nextInt());
                    System.out.println("Customer Pincode Updated Successfully!");
                    break;
                case 10:
                    System.out.println("Enter Mobile Number: ");
                    customer.setMobile_no(sc.nextLong());
                    System.out.println("Customer Mobile number Updated Successfully!");
                    break;
                case 11:
                    System.out.println("Enter Room Number: ");
                    int Room_no = sc.nextInt();
                    Rooms room = session.get(Rooms.class, Room_no);

                    if (room != null) {
                        if (room.getCustomer() != null) {
                            throw new RoomAlreadyAssignedException("Room number" + Room_no
                                    + "is already assigned to another Customer" + room.getCustomer().getCust_ID());
                        } else {
                            customer.setRoom1(room);  // Assigning new room to the customer
                            System.out.println("Room Updated Successfully!");
                        }
                    } else {
                        throw new RoomNotFoundException("Room Not Found");
                    }
                    break;
                default:
                    System.out.println("Invalid Option. Please choose again");
                    continue;
                }
                session.saveOrUpdate(customer);  // Save or update the customer details in the database
                System.out.println("Customer Details Updated Successfully!");
                transaction.commit();  // Commit the transaction after updates
            }

        } catch (Exception e) {
            transaction.rollback();  // Rollback the transaction in case of an error
            e.printStackTrace();
            System.out.println("An Error Occured while Updating Customer Details..!");

        } finally {
            session.close();  // Close session
        }
    }

    // Method to delete a customer from the database
    public void deleteCustomer(SessionFactory sf) {
        session = sf.openSession();  // Open a session
        Transaction transaction = session.beginTransaction();  // Begin transaction

        System.out.println("Enter Customer Id: ");
        // Fetch customer using provided ID
        Customer customer = session.get(Customer.class, sc.nextInt());
        if (customer != null) {
            session.delete(customer);  // Delete the customer if found
            System.out.println("Customer record deleted successfully......!!");
        } else {
            System.out.println("Enter valid Customer Id: ");  // If customer ID doesn't exist
        }
        transaction.commit();  // Commit the transaction
        session.close();  // Close session
    }

    // Method to get all customers from the database
    public void getAllCustomers(SessionFactory sf) {
        session = sf.openSession();  // Open session

        Query<Customer> query = session.createQuery("from Customer",Customer.class);  // Create query to fetch all customers
        List<Customer> resultList = query.getResultList();  // Get result list

        for (Customer c : resultList)
            System.out.println(c);  // Display all customers

        session.close();  // Close session
    }

    // Method to search and view a specific customer's information
    public void getCustomerInformation(SessionFactory sf) {
        session = sf.openSession();  // Open session

        System.out.println("Enter Customer ID");
        // Fetch and display customer details based on provided ID
        Customer customer = session.get(Customer.class, sc.nextInt());
        System.out.println(customer);
        session.close();  // Close session
    }

}
