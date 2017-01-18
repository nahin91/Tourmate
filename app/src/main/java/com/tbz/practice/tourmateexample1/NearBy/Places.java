package com.tbz.practice.tourmateexample1.NearBy;

/**
 * Created by TYCOHANX on 1/15/2017.
 */

public class Places {

    private String name;
    private String address;
    private boolean openingHour;
    private Double rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(boolean openingHour) {
        this.openingHour = openingHour;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
