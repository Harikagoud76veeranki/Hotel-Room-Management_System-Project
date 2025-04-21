package com.hibernate.service;

import org.hibernate.SessionFactory;

public interface RoomsService {
	void insertRooms(SessionFactory sf);

	void updateRooms(SessionFactory sf);

	void deleteRooms(SessionFactory sf);

	void getAllRooms(SessionFactory sf);
	
	void getRoomsInformation(SessionFactory sf);
}
