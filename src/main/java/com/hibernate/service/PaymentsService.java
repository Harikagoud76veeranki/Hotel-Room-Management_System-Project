package com.hibernate.service;

import org.hibernate.SessionFactory;

public interface PaymentsService {
	void insertPayments(SessionFactory sf);

	void updatePayments(SessionFactory sf);

	void deletePayments(SessionFactory sf);

	void getAllPayments(SessionFactory sf);
	
	void getPaymentsInformation(SessionFactory sf);
}
