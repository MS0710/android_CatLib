package com.example.catlib_0612.data;

public class UserMyCat {
    private String name;
    private String breed;
    private String birthday;
    private String picture;
    private String key;
    private String flag;

    public UserMyCat(String _name,String _breed,String _birthday,String _picture,String _key,String _flag){
        this.name = _name;
        this.breed = _breed;
        this.birthday = _birthday;
        this.picture = _picture;
        this.key = _key;
        this.flag = _flag;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getBreed() {
        return breed;
    }

    public String getPicture() {
        return picture;
    }
    public String getKey() {
        return key;
    }
    public String getFlag() {
        return flag;
    }
}
