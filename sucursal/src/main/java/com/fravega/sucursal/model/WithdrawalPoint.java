package com.fravega.sucursal.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "WithdrawalPoint")
@DiscriminatorValue(value = "WITHDRAWAL_POINT")
public class WithdrawalPoint extends Node {

    @Schema(description = "Withdrawal point capacity", example = "150000")
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

    @Override
    public String toString() {
        return "WithdrawalPoint{ " +
                super.toString() +
                ", capacity=" + capacity +
                "}";
    }

}
