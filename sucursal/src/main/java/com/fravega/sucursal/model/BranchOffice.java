package com.fravega.sucursal.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "BranchOffice")
@DiscriminatorValue(value = "BRANCH_OFFICE")
public class BranchOffice extends Node {

    @Schema(description = "Branch office address", example = "Av. Monroe 2465")
    private String address;
    @Schema(description = "Branch office business hours", example = "8:00 - 18:00")
    private String businessHours;

    public BranchOffice() {
    }

    public BranchOffice(double latitude, double longitude, String address, String businessHours) {
        super(latitude, longitude);
        this.address = address;
        this.businessHours = businessHours;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    @Override
    public String toString() {
        return "BranchOffice{ " +
                super.toString() +
                ", address='" + address + '\'' +
                ", businessHours='" + businessHours + '\'' +
                "}";
    }

}
