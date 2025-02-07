package com.example.catlib_0612.data;

public class CatBreed {
    String title;
    int picture;
    String introduce1;
    String introduce2;
    public CatBreed(String _title,int _picture,String _introduce1,String _introduce2){
        this.title = _title;
        this.picture = _picture;
        this.introduce1 = _introduce1;
        this.introduce2 = _introduce2;
    }

    public int getPicture() {
        return picture;
    }

    public String getTitle() {
        return title;
    }

    public String getIntroduce1() {
        return introduce1;
    }

    public String getIntroduce2() {
        return introduce2;
    }
}
