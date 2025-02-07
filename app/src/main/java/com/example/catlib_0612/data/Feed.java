package com.example.catlib_0612.data;

public class Feed {
    private String TAG;
    private String picture;
    private String brand;
    private String name;
    private String introduce;
    private String element;

    public Feed(String _TAG,String _picture, String _brand, String _name, String _introduce, String _element){
        this.TAG = _TAG;
        this.picture = _picture;
        this.brand = _brand;
        this.name = _name;
        this.introduce = _introduce;
        this.element = _element;
    }

    public String getPicture() {
        return picture;
    }
    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getElement() {
        return element;
    }

    public String getTAG() {
        return TAG;
    }
}
