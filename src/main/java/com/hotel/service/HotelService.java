package com.hotel.service;

import com.hotel.model.Booking;
import com.hotel.model.Hotel;

import java.util.Date;
import java.util.List;

public interface HotelService {
    Hotel getHotelById(long id);

    List<Hotel> getAllHotels();

    boolean bookRoom(long hotelId, long userId, Date checkInDate, Date checkOutDate, int rooms);

    List<Booking> getUserBookings(long userId);
}
