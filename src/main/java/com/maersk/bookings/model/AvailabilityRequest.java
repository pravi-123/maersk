package com.maersk.bookings.model;

import com.maersk.bookings.annotations.ValidateInteger;
import com.maersk.bookings.annotations.ValidateString;

import javax.validation.constraints.*;

public class AvailabilityRequest {

    @NotNull
    @ValidateInteger(acceptedValues = {20, 40}, message = "Invalid container size!")
    private Integer containerSize;

    @NotBlank
    @ValidateString(acceptedValues={"DRY", "REEFER"}, message="Invalid container type!")
    private String containerType;

    @NotBlank
    @Size(min = 5, max = 20, message = "Origin value length must be between 5 and 20!")
    private String origin;

    @NotBlank
    @Size(min = 5, max = 20, message = "Destination value length must be between 5 and 20!")
    private String destination;

    @NotNull
    @Min(value = 1, message = "Quantity must be between 1 to 100!")
    @Max(value = 100, message = "Quantity must be between 1 to 100!")
    private Integer quantity;

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
}
