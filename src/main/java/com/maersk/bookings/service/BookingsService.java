package com.maersk.bookings.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maersk.bookings.client.MaerskWebClient;
import com.maersk.bookings.entities.Bookings;
import com.maersk.bookings.model.AvailabilityRequest;
import com.maersk.bookings.model.AvailabilityResponse;
import com.maersk.bookings.model.BookingsRequest;
import com.maersk.bookings.model.BookingsResponse;
import com.maersk.bookings.repositories.BookingsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

@Service
public class BookingsService {

    private final BookingsRepository bookingsRepository;
    private final MaerskWebClient maerskWebClient;

    public BookingsService(BookingsRepository bookingsRepository, MaerskWebClient maerskWebClient) {
        this.bookingsRepository = bookingsRepository;
        this.maerskWebClient = maerskWebClient;
    }

    @Value("${maersk.bookings.check.available}")
    private String checkAvailableUri;

    public Mono<AvailabilityResponse> checkAvailable(AvailabilityRequest availabilityRequest) {
        return maerskWebClient.getWebClient()
                     .post()
                     .uri(checkAvailableUri)
                     .body(Mono.just(availabilityRequest), AvailabilityRequest.class)
                     .retrieve()
                     .bodyToMono(String.class)
                     .flatMap(this::prepareResponse);
    }

    private Mono<AvailabilityResponse> prepareResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        AvailabilityResponse availabilityResponse = new AvailabilityResponse();
        try {
            JsonNode jsonNode = objectMapper.readValue(response, JsonNode.class);
            Integer availableSpace = jsonNode.get("availableSpace").asInt();
            if(availableSpace > 0) {
                availabilityResponse.setAvailable(true);
            } else {
                availabilityResponse.setAvailable(false);
            }
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }
        return Mono.just(availabilityResponse);
    }

    public Mono<BookingsResponse> bookContainer(BookingsRequest bookingsRequest) {
        return bookingsRepository.count().flatMap(count -> getRecord(bookingsRequest, count));
    }

    private Mono<BookingsResponse> getRecord(BookingsRequest bookingsRequest, Long count) {
        Bookings bookings = new Bookings();
        bookings.setContainerSize(bookingsRequest.getContainerSize());
        bookings.setContainerType(bookingsRequest.getContainerType());
        bookings.setDestination(bookingsRequest.getDestination());
        bookings.setOrigin(bookingsRequest.getOrigin());
        bookings.setQuantity(bookingsRequest.getQuantity());
        TemporalAccessor accessor = DateTimeFormatter.ISO_INSTANT.parse(bookingsRequest.getTimestamp());
        Instant instant = Instant.from(accessor);
        Date date = Date.from(instant);
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        bookings.setTimestamp(ldt);
        bookings.setBooking_ref(957000001L + count);
        return bookingsRepository.save(bookings)
                .flatMap(this::prepareBookingsResponse);
    }

    private Mono<BookingsResponse> prepareBookingsResponse(Bookings bookings) {
        BookingsResponse bookingsResponse = new BookingsResponse();
        bookingsResponse.setBookingRef(bookings.getBooking_ref());
        return Mono.just(bookingsResponse);
    }
}
