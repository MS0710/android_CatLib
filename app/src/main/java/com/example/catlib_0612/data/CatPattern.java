package com.example.catlib_0612.data;

public class CatPattern {
    private String title;
    private String content;
    private int picture;

    public CatPattern(String _title,String _content,int _picture){
        this.title = _title;
        this.content = _content;
        this.picture = _picture;
    }

    public String getTitle() {
        return title;
    }

    public int getPicture() {
        return picture;
    }

    public String getContent() {
        return content;
    }
}
