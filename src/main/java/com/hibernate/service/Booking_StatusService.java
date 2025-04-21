package com.hibernate.service;

import org.hibernate.SessionFactory;

public interface Booking_StatusService {
	void insertBooking_Status(SessionFactory sf);

	void updateBooking_Status(SessionFactory sf);

	void deleteBooking_Status(SessionFactory sf);

	void getAllBooking_Status(SessionFactory sf);
	
	void getBooking_StatusInformation(SessionFactory sf);
}
