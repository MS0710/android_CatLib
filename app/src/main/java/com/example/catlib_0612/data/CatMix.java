package com.example.catlib_0612.data;

public class CatMix {
    String title;
    int picture;
    String color;
    String introduce;
    public CatMix(String _title,int _picture,String _color,String _introduce){
        this.title = _title;
        this.picture = _picture;
        this.color = _color;
        this.introduce = _introduce;
    }

    public String getTitle() {
        return title;
    }

    public int getPicture() {
        return picture;
    }

    public String getColor() {
        return color;
    }

    public String getIntroduce() {
        return introduce;
    }
}
