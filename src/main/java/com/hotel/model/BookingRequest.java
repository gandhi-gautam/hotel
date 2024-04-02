package com.hotel.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookingRequest {
    private Date checkInDate;
    private Date checkOutDate;
    private int rooms;
}
