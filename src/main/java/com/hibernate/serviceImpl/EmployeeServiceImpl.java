package com.hibernate.serviceImpl;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hibernate.entity.Employee;
import com.hibernate.entity.Hotel;
import com.hibernate.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {
    
    // Scanner instance for taking user inputs
    Scanner sc = new Scanner(System.in);
    Session session;
    SessionFactory sf;

    // Menu-driven interface for managing employee information
    public void menuDriven(SessionFactory sf) {
        this.sf = sf;
        int choice;
        do {
            // Displaying the menu options
            System.out.println("\n-----------------------------------------------");
            System.out.println("          Employee Information Manager          ");
            System.out.println("-----------------------------------------------");

            System.out.println("1. Create a New Employee Account");
            System.out.println("2. Update Existing Employee Details");
            System.out.println("3. Delete an Employee Account");
            System.out.println("4. View All Employee Accounts");
            System.out.println("5. Search for an Employee");
            System.out.println("6. Exit Employee Information Manager");

            // Taking user input for the option
            System.out.print("\nChoose an option: ");
            choice = sc.nextInt();

            // Handling user choice using switch case
            switch (choice) {
            case 1:
                insertEmployee(sf);
                break;
            case 2:
                updateEmployee(sf);
                break;
            case 3:
                deleteEmployee(sf);
                break;
            case 4:
                getAllEmployees(sf);
                break;
            case 5:
                getEmployeeInformation(sf);
                break;
            case 6:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice. Please choose again.");
            }
        } while (choice != 6); // Loop until the user chooses to exit
    }

    // Method to insert a new employee record into the database
    public void insertEmployee(SessionFactory sf) {
        session = sf.openSession();  // Opening a new session
        Transaction transaction = session.beginTransaction(); // Starting a transaction
        try {
            Employee employee = new Employee();  // Creating a new employee object

            // Taking user input for employee details
            System.out.println("Enter Employee Details: ");
            System.out.println("Enter Employee ID: ");
            int employeeId = sc.nextInt();
            employee.setEmp_Id(employeeId);

            System.out.println("Enter Employee Name: ");
            String name = sc.next();
            employee.setEmp_Name(name);

            System.out.println("Enter Mobile Number: ");
            Long phno = sc.nextLong();
            employee.setMobile_No(phno);

            System.out.println("Enter Job Department(Admin,Receptionist,RoomService)");
            String jobdepartment = sc.next();
            employee.setJob_department(jobdepartment);

            System.out.println("Enter Address: ");
            String address = sc.next();
            employee.setAddress(address);

            System.out.println("Enter Hotel Id: ");
            int Hotel_Id = sc.nextInt();
            Hotel hotel = session.get(Hotel.class, Hotel_Id); // Fetching hotel object based on hotel ID
            employee.setHotel(hotel);

            // Saving the employee record into the database
            session.persist(employee);

            transaction.commit();  // Committing the transaction
            System.out.println("Employee Details Inserted Successfully.");

        } catch (Exception e) {
            transaction.rollback();  // Rolling back the transaction in case of an error
            e.printStackTrace();
            System.out.println("An Error Occured while inserting Employee Details..!");

        } finally {
            session.close();  // Closing the session
        }
    }

    // Method to update employee details
    public void updateEmployee(SessionFactory sf) {
        session = sf.openSession();  // Opening a new session
        Transaction transaction = session.beginTransaction(); // Starting a transaction
        try {
            while (true) {
                // Displaying the options to update the employee details
                System.out.println("Choose Option for update: \n1.Employee ID \n2.Employee Name \n3.Mobile Number \n4."
                        + "Job Department \n5.Address \n6.Hotel Id \n7.Exit");
                int option = sc.nextInt();
                if (option == 7) {
                    System.out.println("Exiting Update Process");
                    break;
                }

                // Asking for the employee ID to update the details
                System.out.println("Enter Employee Id: ");
                Employee employee = session.get(Employee.class, sc.nextInt()); // Fetching employee from database

                if (employee == null) {
                    System.out.println("Employee not Found. Please Try Again");
                    continue;
                }

                // Performing the update based on the selected option
                switch (option) {
                case 1:
                    System.out.println("Enter new ID: ");
                    employee.setEmp_Id(sc.nextInt());
                    System.out.println("Employee ID Updated Successfully!");
                    break;
                case 2:
                    System.out.println("Enter Name: ");
                    employee.setEmp_Name(sc.next());
                    System.out.println("Employee Name Updated Successfully!");
                    break;
                case 3:
                    System.out.println("Enter Mobile Number: ");
                    employee.setMobile_No(sc.nextLong());
                    System.out.println("Employee Mobile Number Updated Successfully!");
                    break;
                case 4:
                    System.out.println("Enter Job Department(Admin,Receptionist,RoomService)");
                    employee.setJob_department(sc.next());
                    System.out.println("Employee Job Department Updated Successfully!");
                    break;
                case 5:
                    System.out.println("Enter Address: ");
                    employee.setAddress(sc.next());
                    System.out.println("Employee Address Updated Successfully!");
                    break;
                case 6:
                    System.out.println("Enter Hotel Id: ");
                    int Hotel_Id = sc.nextInt();
                    Hotel hotel = session.get(Hotel.class, Hotel_Id);
                    System.out.println("Hotel ID Updated Successfully!");
                    break;
                default:
                    System.out.println("Invalid Option. Please choose again");
                    continue;
                }
                
                // Saving the updated employee record
                session.saveOrUpdate(employee);
                System.out.println("Employee Details Updated Successfully!");
                transaction.commit();  // Committing the transaction
            }

        } catch (Exception e) {
            transaction.rollback();  // Rolling back the transaction in case of an error
            e.printStackTrace();
            System.out.println("An Error Occured while updating Employee Details..!");

        } finally {
            session.close();  // Closing the session
        }
    }

    // Method to delete an employee record
    public void deleteEmployee(SessionFactory sf) {
        session = sf.openSession();  // Opening a new session
        Transaction transaction = session.beginTransaction(); // Starting a transaction

        System.out.println("Enter Employee Id: ");
        Employee employee = session.get(Employee.class, sc.nextInt()); // Fetching employee by ID

        if (employee != null) {
            // Deleting the employee record
            session.delete(employee);
            System.out.println("Employee record deleted successfully......!!");
        } else {
            System.out.println("Enter valid Employee Id: ");
        }
        transaction.commit();  // Committing the transaction
        session.close();  // Closing the session
    }

    // Method to fetch and display all employees
    public void getAllEmployees(SessionFactory sf) {
        session = sf.openSession();  // Opening a new session

        // Fetching all employee records from the database
        Query<Employee> query = session.createQuery("from Employee", Employee.class);
        List<Employee> resultList = query.getResultList();

        // Displaying the employee details
        for (Employee e : resultList)
            System.out.println(e);

        session.close();  // Closing the session
    }

    // Method to fetch and display information of a specific employee
    public void getEmployeeInformation(SessionFactory sf) {
        session = sf.openSession();  // Opening a new session

        System.out.println("Enter Employee ID");
        Employee employee = session.get(Employee.class, sc.nextInt());  // Fetching employee by ID
        System.out.println(employee);  // Displaying the employee details
        session.close();  // Closing the session
    }
}
