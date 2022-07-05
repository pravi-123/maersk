package com.maersk.bookings.service;

import com.maersk.bookings.client.MaerskWebClient;
import com.maersk.bookings.entities.Bookings;
import com.maersk.bookings.model.AvailabilityRequest;
import com.maersk.bookings.model.AvailabilityResponse;
import com.maersk.bookings.model.BookingsRequest;
import com.maersk.bookings.model.BookingsResponse;
import com.maersk.bookings.repositories.BookingsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookingsServiceTest {

    @Mock
    private BookingsRepository bookingsRepository;

    @Mock
    private MaerskWebClient maerskWebClient;

    private BookingsService bookingsService;

    @BeforeEach
    void doSetUp() {
        MockitoAnnotations.openMocks(this);
        bookingsService = new BookingsService(bookingsRepository, maerskWebClient);
        ReflectionTestUtils.setField(bookingsService, "checkAvailableUri", "/api/checkAvailable");
    }

    @Test
    public void testCheckAvailableWhenAvailable() {
        String resp = "{\"availableSpace\": 6}";
        when(maerskWebClient.getWebClient()).thenAnswer((mock) -> getWebClientMock(mock, resp));
        AvailabilityRequest availabilityRequest = new AvailabilityRequest();
        StepVerifier.create(bookingsService.checkAvailable(availabilityRequest))
                    .consumeNextWith(this::verifyAvailability)
                    .verifyComplete();

    }

    private void verifyAvailability(AvailabilityResponse availabilityResponse) {
        assertEquals(true, availabilityResponse.isAvailable());
    }

    @Test
    public void testCheckAvailableWhenNotAvailable() {
        String resp = "{\"availableSpace\": 0}";
        when(maerskWebClient.getWebClient()).thenAnswer((mock) -> getWebClientMock(mock, resp));
        AvailabilityRequest availabilityRequest = new AvailabilityRequest();
        StepVerifier.create(bookingsService.checkAvailable(availabilityRequest))
                .consumeNextWith(this::verifyNoAvailability)
                .verifyComplete();

    }
    private void verifyNoAvailability(AvailabilityResponse availabilityResponse) {
        assertEquals(false, availabilityResponse.isAvailable());
    }

    private WebClient getWebClientMock(InvocationOnMock invocationOnMock, String resp) {
        var mockWebClient = mock(WebClient.class);
        var uriSpecMock = mock(WebClient.RequestBodyUriSpec.class);
        var requestBodySpec = mock(WebClient.RequestBodySpec.class);
        var requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        var responseSpecMock = mock(WebClient.ResponseSpec.class);

        when(mockWebClient.post()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(Mono.class), eq(AvailabilityRequest.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(ArgumentMatchers.<Class<String>>notNull()))
                .thenReturn(Mono.just(resp));
        return mockWebClient;
    }

    @Test
    public void testBookContainer() {
        when(bookingsRepository.count()).thenAnswer(this::getCount);
        when(bookingsRepository.save(any(Bookings.class))).thenAnswer(this::getBooking);
        BookingsRequest bookingsRequest = new BookingsRequest();
        bookingsRequest.setTimestamp("2020-10-12T13:53:09Z");
        StepVerifier.create(bookingsService.bookContainer(bookingsRequest))
                .consumeNextWith(this::verifyBookingRef)
                .verifyComplete();
    }

    private void verifyBookingRef(BookingsResponse bookingsResponse) {
        assertEquals(123456L, bookingsResponse.getBookingRef());
    }

    private Mono<Bookings> getBooking(InvocationOnMock invocationOnMock) {
        Bookings bookings = new Bookings();
        bookings.setBooking_ref(123456L);
        return Mono.just(bookings);
    }

    private Mono<Long> getCount(InvocationOnMock invocationOnMock) {
        return Mono.just(3L);
    }
}
