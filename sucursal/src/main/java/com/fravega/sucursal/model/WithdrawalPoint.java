package com.fravega.sucursal.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "WITHDRAWAL_POINT")
public class WithdrawalPoint extends Node {

    private long capacity;

    public WithdrawalPoint() {
    }

    public WithdrawalPoint(double latitude, double longitude, long capacity) {
        super(latitude, longitude);
        this.capacity = capacity;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

}
