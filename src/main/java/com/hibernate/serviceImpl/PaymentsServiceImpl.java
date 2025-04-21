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
import com.hibernate.entity.Payments;
import com.hibernate.service.PaymentsService;

public class PaymentsServiceImpl implements PaymentsService {
    // Scanner for user input
    Scanner sc = new Scanner(System.in);
    Session session;
    SessionFactory sf;

    // Method to display menu options and perform the chosen actions
    public void menuDriven(SessionFactory sf) {
        this.sf = sf;
        int choice;
        do {
            // Displaying the Payment Services Management Menu
            System.out.println("\n-----------------------------------------------");
            System.out.println("          Payment Services Management          ");
            System.out.println("-----------------------------------------------");

            System.out.println("1. Process a New Payment");
            System.out.println("2. Update Existing Payment Details");
            System.out.println("3. Cancel a Payment");
            System.out.println("4. View All Payment Records");
            System.out.println("5. Search for a Payment");
            System.out.println("6. Exit Payment Services Management");

            // Taking input from the user for the choice
            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();

            // Handling the user's choice with a switch statement
            switch (choice) {
            case 1:
                insertPayments(sf);  // Call to insert a new payment
                break;
            case 2:
                updatePayments(sf);  // Call to update an existing payment
                break;
            case 3:
                deletePayments(sf);  // Call to delete a payment
                break;
            case 4:
                getAllPayments(sf);  // Call to display all payment records
                break;
            case 5:
                getPaymentsInformation(sf);  // Call to search for a specific payment
                break;
            case 6:
                System.out.println("Exiting...");  // Exit the payment services menu
                break;
            default:
                System.out.println("Invalid choice. Please choose again.");
            }

        } while (choice != 6);  // Repeat the menu until the user selects Exit
    }

    // Method to insert a new payment
    public void insertPayments(SessionFactory sf) {
        session = sf.openSession();  // Opening a Hibernate session
        Transaction transaction = session.beginTransaction();  // Starting a transaction
        try {
            Payments payment = new Payments();  // Creating a new Payments object

            // Taking input for the payment details
            System.out.println("Enter Payment Details: ");
            System.out.println("Enter Payment ID: ");
            int paymentId = sc.nextInt();
            payment.setPayment_Id(paymentId);

            System.out.println("Payment Method: ");
            String method = sc.next();
            payment.setPayment_Method(method);

            System.out.println("Enter Payment Amount: ");
            Float amount = sc.nextFloat();
            payment.setPayment_Amount(amount);

            // Taking input for the payment date and converting to LocalDate
            System.out.println("Enter Payment Date(yyyy-MM-dd): ");
            String paydate = sc.next();
            LocalDate date = LocalDate.parse(paydate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            payment.setPayment_Date(date);

            // Taking input for Customer Id and fetching the customer from the database
            System.out.println("Enter Customer Id: ");
            int customerId = sc.nextInt();
            Customer customer = session.get(Customer.class, customerId);
            if (customer == null) {
                System.out.println("Customer not found.");
                return;
            }
            payment.setCustomer1(customer);  // Associating the customer with the payment

            // Persisting the payment object in the database
            session.persist(payment);
            transaction.commit();  // Committing the transaction
            System.out.println("Payment Details Inserted Successfully.");

        } catch (Exception e) {
            transaction.rollback();  // Rolling back the transaction in case of an error
            e.printStackTrace();
            System.out.println("An Error Occurred while inserting Payment Details..!");

        } finally {
            session.close();  // Closing the session
        }

    }

    // Method to update existing payment details
    public void updatePayments(SessionFactory sf) {
        session = sf.openSession();  // Opening a Hibernate session
        Transaction transaction = session.beginTransaction();  // Starting a transaction

        try {
            while (true) {
                // Displaying options for updating payment details
                System.out.println("Choose Option for update: \n1.Payment ID \n2.Payment Method \n3.Payment Amount \n4."
                        + "Payment Date \n5.Customer Id \n6.Exit");
                int option = sc.nextInt();
                if (option == 6) {
                    System.out.println("Exiting Update Process");
                    break;
                }
                
                // Asking for Payment ID to fetch the corresponding payment record
                System.out.println("Enter Payment Id: ");
                Payments payment = session.get(Payments.class, sc.nextInt());

                if (payment == null) {
                    System.out.println("Payment Id not Found. Please Try Again");
                    continue;
                }

                // Handling the selected update option
                switch (option) {
                case 1:
                    System.out.println("Enter new Payment ID: ");
                    payment.setPayment_Id(sc.nextInt());
                    System.out.println("Payment ID Updated Successfully!");
                    break;
                case 2:
                    System.out.println("Enter Payment Method: ");
                    payment.setPayment_Method(sc.next());
                    System.out.println("Payment Method Updated Successfully!");
                    break;
                case 3:
                    System.out.println("Enter Payment Amount: ");
                    payment.setPayment_Amount(sc.nextFloat());
                    System.out.println("Payment Amount Updated Successfully!");
                    break;
                case 4:
                    System.out.println("Enter Payment Date(yyyy-MM-dd): ");
                    String paymentdate = sc.next();
                    LocalDate date = LocalDate.parse(paymentdate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    payment.setPayment_Date(date);
                    System.out.println("Payment Date Updated Successfully!");
                    break;
                case 5:
                    System.out.println("Enter Customer Id: ");
                    int Cust_ID = sc.nextInt();
                    Customer customer = session.get(Customer.class, Cust_ID);
                    payment.setCustomer1(customer);  // Updating the customer associated with the payment
                    System.out.println("Customer Id Updated Successfully!");
                    break;
                default:
                    System.out.println("Invalid Option. Please choose again");
                    continue;
                }
                
                // Saving or updating the payment record in the database
                session.saveOrUpdate(payment);
                System.out.println("Payment Details Updated Successfully!");
                transaction.commit();  // Committing the transaction
            }

        } catch (Exception e) {
            transaction.rollback();  // Rolling back in case of an error
            e.printStackTrace();
            System.out.println("An Error Occured while updating Payment Details..!");

        } finally {
            session.close();  // Closing the session
        }
    }

    // Method to delete a payment based on Payment ID
    public void deletePayments(SessionFactory sf) {
        session = sf.openSession();  // Opening a Hibernate session
        Transaction transaction = session.beginTransaction();  // Starting a transaction

        System.out.println("Please Enter Your Payment ID: ");
        Payments payment = session.get(Payments.class, sc.nextInt());  // Fetching the payment using Payment ID
        if (payment != null) {
            session.delete(payment);  // Deleting the payment record
            System.out.println("Payment record deleted successfully......!!");
        } else {
            System.out.println("Enter valid Payment ID: ");
        }
        transaction.commit();  // Committing the transaction
        session.close();  // Closing the session
    }

    // Method to display all payment records
    public void getAllPayments(SessionFactory sf) {
        session = sf.openSession();  // Opening a Hibernate session

        // Creating a query to fetch all payments from the database
        Query<Payments> query = session.createQuery("from Payments", Payments.class);
        List<Payments> resultList = query.getResultList();

        // Displaying the fetched payment records
        for (Payments p : resultList)
            System.out.println(p);

        session.close();  // Closing the session
    }

    // Method to search and display a specific payment by Payment ID
    public void getPaymentsInformation(SessionFactory sf) {
        session = sf.openSession();  // Opening a Hibernate session

        System.out.println("Please Enter Your Payment ID: ");
        Payments payment = session.get(Payments.class, sc.nextInt());  // Fetching the payment using Payment ID
        System.out.println(payment);  // Displaying the payment record
        session.close();  // Closing the session
    }
}
