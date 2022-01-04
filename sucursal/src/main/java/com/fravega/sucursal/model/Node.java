package com.fravega.sucursal.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Entity(name = "Node")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "NODE_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Node {

    @Schema(description = "Unique identifier of the node.", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Schema(description = "Latitude coordinate of the node (Range: -90.00 .. 90.00)", example = "45.567", required = true)
    private double latitude;
    @Schema(description = "Longitude coordinate of the node (Range: -180.00 .. 180.00)", example = "120.567", required = true)
    private double longitude;

    public Node() {
    }

    public Node(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return  "id = " + id +
                ", latitude = " + latitude +
                ", longitude = " + longitude;
    }

}
