package com.hotel.repository;

import com.hotel.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * This method is used to get the hotel using the id and also is user can book the hotel
     *
     * @param hotelId
     * @param checkInDate
     * @param checkOutDate
     * @return
     */
    @Query("SELECT b FROM Booking b WHERE b.hotelId = :hotelId AND ((b.checkInDate >= :checkInDate AND " +
            "b.checkInDate < :checkOutDate) OR (b.checkOutDate > :checkInDate AND b.checkOutDate <= :checkOutDate))")
    List<Booking> findByHotelIdAndDates(@Param("hotelId") Long hotelId, @Param("checkInDate") Date checkInDate,
                                        @Param("checkOutDate") Date checkOutDate);

    /**
     * This method is used to get the user using the user id
     *
     * @param userId
     * @return
     */
    List<Booking> findByUserId(long userId);
}
