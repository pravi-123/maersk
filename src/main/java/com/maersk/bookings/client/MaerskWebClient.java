package com.maersk.bookings.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MaerskWebClient<T> {

    @Value("${maersk.bookings.api}")
    private String basePath;

    private final WebClient webClient;

    public MaerskWebClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl(basePath).build();
    }

    public WebClient getWebClient() {
        return this.webClient;
    }

}
