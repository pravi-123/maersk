package com.maersk.bookings.entities;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Table("bookings")
public class Bookings {

    @PrimaryKey
    private Long booking_ref;

    @Column("container_size")
    private Integer containerSize;

    @Column("container_type")
    private String containerType;

    @Column("origin")
    private String origin;

    @Column("destination")
    private String destination;

    @Column("quantity")
    private Integer quantity;

    @Column("timestamp")
    private LocalDateTime timestamp;

    public Long getBooking_ref() {
        return booking_ref;
    }

    public void setBooking_ref(Long booking_ref) {
        this.booking_ref = booking_ref;
    }

    public Integer getContainerSize() {
        return containerSize;
    }

    public void setContainerSize(Integer containerSize) {
        this.containerSize = containerSize;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
