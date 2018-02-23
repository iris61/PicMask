package com.lovecoding.yangying.photos;

/**
 * Created by yangying on 18/2/19.
 */

public class PhotoCardInfo {
    private String imageName = null;
    private String createUser = null;
    private String createTime = null;
    private String comment = null;
    private int image_id = 0;

    public PhotoCardInfo(String URL, String createdTime, String createdUser, String memo){
        this.createTime = createdTime;
        this.createUser = createdUser;
        this.comment = memo;
        this.imageName = URL;
    }

    public PhotoCardInfo(String URL){
        this.createTime = new String(" ");
        this.createUser = new String(" ");
        this.comment = new String(" ");
        this.imageName = URL;
    }

    public String getImageName(){
        return this.imageName;
    }

    public String getCreatedUser(){
        return this.createUser;
    }

    public String getMemo(){
        return this.comment;
    }

    public String getCreatedTime(){
        return this.createTime;
    }

    public void setImageName(String URL){
        this.imageName = URL;
    }

    public void setCreatedUser(String createdUser){
        this.createUser = createdUser;
    }

    public void setCreatedTime(String createdTime){
        this.createTime = createdTime;
    }

    public void setMemo(String memo){
        this.comment = memo;
    }
}
