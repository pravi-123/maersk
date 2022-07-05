package com.maersk.bookings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.maersk.bookings"}, exclude = {DataSourceAutoConfiguration.class } )
public class MaerskApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaerskApplication.class, args);
    }

}
