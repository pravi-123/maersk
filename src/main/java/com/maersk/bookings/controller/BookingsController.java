package com.maersk.bookings.controller;

import com.maersk.bookings.model.AvailabilityRequest;
import com.maersk.bookings.model.AvailabilityResponse;
import com.maersk.bookings.model.BookingsRequest;
import com.maersk.bookings.model.BookingsResponse;
import com.maersk.bookings.service.BookingsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("bookings")
public class BookingsController {

    private final BookingsService bookingsService;

    public BookingsController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @PostMapping("checkAvailable")
    public Mono<AvailabilityResponse> checkAvailable(@Valid @RequestBody AvailabilityRequest availabilityRequest) {
        return bookingsService.checkAvailable(availabilityRequest);
    }

    @PostMapping
    public Mono<BookingsResponse> bookContainer(@Valid @RequestBody BookingsRequest bookingsRequest) {
        return bookingsService.bookContainer(bookingsRequest);
    }

}
