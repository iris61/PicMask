package com.lovecoding.yangying.photos;

/**
 * Created by yangying on 18/2/19.
 */

public class PhotoCardInfo {
    private String image_name = null;
    private String create_user = null;
    private String create_time = null;
    private String comment = null;
    private int image_id = 0;

    public PhotoCardInfo(String URL, String createdTime, String createdUser, String memo){
        this.create_time = createdTime;
        this.create_user = createdUser;
        this.comment = memo;
        this.image_name = URL;
    }

    public PhotoCardInfo(String URL){
        this.create_time = new String(" ");
        this.create_user = new String(" ");
        this.comment = new String(" ");
        this.image_name = URL;
    }

    public String getImageName(){
        return this.image_name;
    }

    public String getCreatedUser(){
        return this.create_user;
    }

    public String getMemo(){
        return this.comment;
    }

    public String getCreatedTime(){
        return this.create_time;
    }

    public void setImageName(String URL){
        this.image_name = URL;
    }

    public void setCreatedUser(String createdUser){
        this.create_user = createdUser;
    }

    public void setCreatedTime(String createdTime){
        this.create_time = createdTime;
    }

    public void setMemo(String memo){
        this.comment = memo;
    }
}
