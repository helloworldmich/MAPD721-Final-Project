package com.example.pjk.mapd_721_final_project.data;

import java.util.Map;

public class Checkin {

    public String checkinId;
    public String date;
    public String time;
    public String longitude;
    public String latitude;
    public String city;
    public String country;
    public String desc;
    public String title;
    public String isFavorite;
    public String remarks;
    public String postal;

    public long timestamp;

    public Checkin() {
    }

    public Checkin(String checkinId, String title,String date, String time, String longitude, String latitude, String city, String country, String desc, String postal, String isFavorite,String remarks, long timestamp) {
        this.checkinId = checkinId;
        this.title = title;
        this.date = date;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.country = country;
        this.desc = desc;
        this.postal = postal;
        this.remarks = remarks;
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getCheckinId() {
        return checkinId;
    }
    public String getTime() {
        return time;
    }
    public String getLongitude() {
        return longitude;
    }
    public String getCity() {
        return city;
    }
    public String getCountry() {
        return country;
    }

    public String getLatitude() {
        return latitude;
    }
    public String getDesc() {
        return desc;
    }
    public String getPostal() {
        return postal;
    }
    public String getRemarks() {
        return remarks;
    }
    public long getTimestamp() {
        return timestamp;
    }

}
