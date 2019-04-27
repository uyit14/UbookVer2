package com.ubook.ubookapp.model;

public class UserLocation {
    private String name;
    private Double lat;
    private Double lng;
    private int avatar;

    public UserLocation(String name, Double lat, Double lng, int avatar) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
