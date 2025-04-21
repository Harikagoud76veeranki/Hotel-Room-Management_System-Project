package com.hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/*This entity represents an employee in the hotel management system. It contains information about the employee, such as their ID, name, mobile number, job department, and address.*/
@Entity
public class Employee {

	//The unique identifier for the employee.
	@Id
	private int Emp_Id;
	//The employee's name.
	private String Emp_Name;
	//The employee's mobile phone number.
	private Long Mobile_No;
	//The employee's job department.
	private String Job_department;
	//The employee's address.
	private String Address;
	//Default constructor for the entity.
	public Employee() {
		super();
	}

	/* Many-to-one relationship with the Hotel entity. This field represents the hotel where the employee works.*/
	@ManyToOne
	@JoinColumn(name = "Hotel_Id")
	private Hotel hotel;

	// Getters and setters for the fields
	public int getEmp_Id() {
		return Emp_Id;
	}

	public void setEmp_Id(int emp_Id) {
		Emp_Id = emp_Id;
	}

	public String getEmp_Name() {
		return Emp_Name;
	}

	public void setEmp_Name(String emp_Name) {
		Emp_Name = emp_Name;
	}

	public Long getMobile_No() {
		return Mobile_No;
	}

	public void setMobile_No(Long mobile_No) {
		Mobile_No = mobile_No;
	}

	public String getJob_department() {
		return Job_department;
	}

	public void setJob_department(String job_department) {
		Job_department = job_department;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	@Override
	public String toString() {
		return "Employee [Emp_Id=" + Emp_Id + ", Emp_Name=" + Emp_Name + ", Mobile_No=" + Mobile_No
				+ ", Job_department=" + Job_department + ", Address=" + Address + ", hotel=" + hotel + "]";
	}
}
