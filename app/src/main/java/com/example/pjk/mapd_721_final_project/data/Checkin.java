package com.example.pjk.mapd_721_final_project.data;

public class Checkin {

    public String date;
    public String time;
    public String longitude;
    public String latitude;
    public String desc;
    public String remarks;

    public Checkin() {
    }

    public Checkin(String date, String time, String longitude, String latitude, String desc, String remarks) {
        this.date = date;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.desc = desc;
        this.remarks = remarks;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
    public String getLongitude() {
        return longitude;
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
