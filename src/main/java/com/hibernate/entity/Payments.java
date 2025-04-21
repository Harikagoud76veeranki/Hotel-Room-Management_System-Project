package com.hibernate.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/*This entity represents a payment made by a customer in the hotel management system. It contains information about the payment, such as its ID, method, amount, and date*/
@Entity
public class Payments {

	// The unique identifier for the payment.
	@Id
	private int Payment_Id;
	// The method used to make the payment (e.g. credit card, cash, etc.).
	private String Payment_Method;
	// The amount of the payment.
	private float Payment_Amount;
	// The date the payment was made.
	private LocalDate Payment_Date;

	// Default constructor for the entity.
	public Payments() {
		super();
	}

	/*Many-to-one relationship with the Customer entity. This field represents the customer who made the payment*/
	@ManyToOne
	@JoinColumn(name = "Cust_Id")
	private Customer customer1;

	// Getters and setters for the fields
	public int getPayment_Id() {
		return Payment_Id;
	}

	public void setPayment_Id(int payment_Id) {
		Payment_Id = payment_Id;
	}

	public String getPayment_Method() {
		return Payment_Method;
	}

	public void setPayment_Method(String payment_Method) {
		Payment_Method = payment_Method;
	}

	public float getPayment_Amount() {
		return Payment_Amount;
	}

	public void setPayment_Amount(float payment_Amount) {
		Payment_Amount = payment_Amount;
	}

	public LocalDate getPayment_Date() {
		return Payment_Date;
	}

	public void setPayment_Date(LocalDate payment_Date) {
		Payment_Date = payment_Date;
	}

	public Customer getCustomer1() {
		return customer1;
	}

	public void setCustomer1(Customer customer1) {
		this.customer1 = customer1;
	}

	@Override
	public String toString() {
		return "Payments [Payment_Id=" + Payment_Id + ", Payment_Method=" + Payment_Method + ", Payment_Amount="
				+ Payment_Amount + ", Payment_Date=" + Payment_Date + ", customer1=" + customer1 + "]";
	}
}
