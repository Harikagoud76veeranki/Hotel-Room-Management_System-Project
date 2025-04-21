package com.hibernate.service;

import org.hibernate.SessionFactory;

public interface CustomerService {
	void insertCustomer(SessionFactory sf);

	void updateCustomer(SessionFactory sf);

	void deleteCustomer(SessionFactory sf);

	void getAllCustomers(SessionFactory sf);
	
	void getCustomerInformation(SessionFactory sf);
}
