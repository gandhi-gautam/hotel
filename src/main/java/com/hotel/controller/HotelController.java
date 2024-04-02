package com.hotel.controller;

import com.hotel.model.Booking;
import com.hotel.model.BookingRequest;
import com.hotel.model.Hotel;
import com.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    /**
     * This method is used to get hotel by id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable int id) {
        Hotel hotel = hotelService.getHotelById(id);
        if (hotel != null)
            return ResponseEntity.ok(hotel);
        else
            return ResponseEntity.notFound().build();
    }

    /**
     * This method is used to get all the hotels from the database
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    /**
     * this method is used to book a room based on the request id
     *
     * @param id
     * @param request
     * @param userId
     * @return
     */
    @PostMapping("/{id}/user/{userId}/book")
    public ResponseEntity<String> bookRoom(@PathVariable int id, @RequestBody BookingRequest request,
                                           @PathVariable long userId) {
        Date checkInDate = request.getCheckInDate();
        Date checkOutDate = request.getCheckOutDate();
        int rooms = request.getRooms();

        if (hotelService.bookRoom(id, userId, checkInDate, checkOutDate, rooms)) {
            return ResponseEntity.ok("Room(s) booked successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failed to book room(s).");
        }
    }

    /**
     * this method is used to get the booking done by the user
     *
     * @param userId
     * @return
     */
    @GetMapping("/bookings/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable long userId) {
        List<Booking> bookings = hotelService.getUserBookings(userId);
        if (bookings != null && !bookings.isEmpty()) {
            return ResponseEntity.ok(bookings);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
