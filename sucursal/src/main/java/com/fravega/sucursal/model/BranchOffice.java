package com.fravega.sucursal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "BRANCH_OFFICE")
public class BranchOffice extends Node {

    private String address;
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

}
