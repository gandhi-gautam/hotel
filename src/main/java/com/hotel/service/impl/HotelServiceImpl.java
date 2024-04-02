package com.hotel.service.impl;

import com.hotel.model.Booking;
import com.hotel.model.Hotel;
import com.hotel.repository.BookingRepository;
import com.hotel.repository.HotelRepository;
import com.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * This method is used to get the hotel by id
     *
     * @param id
     * @return
     */
    @Override
    public Hotel getHotelById(long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    /**
     * This method is used to get all the hotels from the database
     *
     * @return
     */
    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    /**
     * This method is used to book the hotel room
     *
     * @param hotelId
     * @param userId
     * @param checkInDate
     * @param checkOutDate
     * @param rooms
     * @return
     */
    @Override
    public boolean bookRoom(long hotelId, long userId, Date checkInDate, Date checkOutDate, int rooms) {
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
        if (hotel == null || hotel.getAvailableRooms() < rooms)
            return false;

        // Check if rooms are available on the given dates
        List<Booking> bookings = bookingRepository.findByHotelIdAndDates(hotelId, checkInDate, checkOutDate);
        int totalBookedRooms = bookings.stream().mapToInt(Booking::getRoomsBooked).sum();
        if (hotel.getAvailableRooms() - totalBookedRooms < rooms)
            return false;

        // Create booking
        Booking booking = new Booking();
        booking.setHotelId(hotelId);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setRoomsBooked(rooms);
        bookingRepository.save(booking);

        hotel.setAvailableRooms(hotel.getAvailableRooms() - rooms);
        hotelRepository.save(hotel);

        return true;
    }

    /**
     * This method is used to get the user bookings
     *
     * @param userId
     * @return
     */
    @Override
    public List<Booking> getUserBookings(long userId) {
        return bookingRepository.findByUserId(userId);
    }
}
