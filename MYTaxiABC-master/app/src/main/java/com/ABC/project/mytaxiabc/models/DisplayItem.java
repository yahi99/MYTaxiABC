package com.ABC.project.mytaxiabc.models;

public class DisplayItem {
    public AddressModel addressModel;
    public Double latitude;
    public Double longitude;

    public DisplayItem(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
