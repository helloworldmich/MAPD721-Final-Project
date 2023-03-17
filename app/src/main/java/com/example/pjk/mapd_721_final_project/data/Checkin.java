package com.example.pjk.mapd_721_final_project.data;

public class Checkin {

    public String checkinID;
    public String date;
    public String time;
    public String longitude;
    public String latitude;
    public String city;
    public String country;
    public String desc;
    public String title;
    public String remarks;

    public Checkin() {
    }

    public Checkin(String checkinID, String title,String date, String time, String longitude, String latitude, String city, String country, String desc, String remarks) {
        this.checkinID = checkinID;
        this.title = title;
        this.date = date;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.country = country;
        this.desc = desc;
        this.remarks = remarks;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getCheckinID() {
        return checkinID;
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
    public String getRemarks() {
        return remarks;
    }

}
