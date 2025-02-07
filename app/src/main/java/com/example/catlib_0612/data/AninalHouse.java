package com.example.catlib_0612.data;

public class AninalHouse {
    private int picture;
    private String title;
    private String phone;
    private String address;
    private String openTime;
    private String note;
    private double lat,lng;

    public AninalHouse(int _picture,String _title,String _phone,String _address,String _openTime,String _note,
                       double _lat,double _lng){
        this.picture = _picture;
        this.title = _title;
        this.phone = _phone;
        this.address = _address;
        this.openTime = _openTime;
        this.note = _note;
        this.lat = _lat;
        this.lng = _lng;
    }

    public int getPicture() {
        return picture;
    }

    public String getTitle() {
        return title;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getNote() {
        return note;
    }

    public String getOpenTime() {
        return openTime;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
