package com.example.catlib_0612.data;

public class CatPaw {
    private String mainTitle;
    private String title;
    private String content;
    private int picture;

    public CatPaw(String _mainTitle,String _title,String _content,int _picture){
        this.mainTitle = _mainTitle;
        this.title = _title;
        this.content = _content;
        this.picture = _picture;
    }

    public String getMainTitle() {
        return mainTitle;
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
