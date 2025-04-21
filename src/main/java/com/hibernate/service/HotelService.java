package com.hibernate.service;

import org.hibernate.SessionFactory;

public interface HotelService {
	void insertHotel(SessionFactory sf);

	void updateHotel(SessionFactory sf);

	void deleteHotel(SessionFactory sf);
	
	void getAllHotel(SessionFactory sf);
	
	void getHotelInformation(SessionFactory sf);
}
