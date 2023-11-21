package com.example.project_mobile.models;

public class HikeModel {
    private int hike_id;
    private String hike_name;
    private String location_hike;
    private String date_hike;
    private String parking_available;
    private String hike_length;
    private String hike_level;
    private String hike_description;

    public HikeModel() {
    }

    public HikeModel(int hike_id, String hike_name, String location_hike, String date_hike, String parking_available, String hike_length, String hike_level, String hike_description) {
        this.hike_id = hike_id;
        this.hike_name = hike_name;
        this.location_hike = location_hike;
        this.date_hike = date_hike;
        this.parking_available = parking_available;
        this.hike_length = hike_length;
        this.hike_level = hike_level;
        this.hike_description = hike_description;
    }

    public int getHike_id() {
        return hike_id;
    }

    public void setHike_id(int hike_id) {
        this.hike_id = hike_id;
    }

    public String getHike_name() {
        return hike_name;
    }

    public void setHike_name(String hike_name) {
        this.hike_name = hike_name;
    }

    public String getDate_hike() {
        return date_hike;
    }

    public void setDate_hike(String date_hike) {
        this.date_hike = date_hike;
    }

    public String getParking_available() {
        return parking_available;
    }

    public void setParking_available(String parking_available) {
        this.parking_available = parking_available;
    }

    public String getHike_length() {
        return hike_length;
    }

    public void setHike_length(String hike_length) {
        this.hike_length = hike_length;
    }

    public String getHike_level() {
        return hike_level;
    }

    public void setHike_level(String hike_level) {
        this.hike_level = hike_level;
    }

    public String getHike_description() {
        return hike_description;
    }

    public void setHike_description(String hike_description) {
        this.hike_description = hike_description;
    }

    public String getLocation_hike() {
        return location_hike;
    }

    public void setLocation_hike(String location_hike) {
        this.location_hike = location_hike;
    }
}
