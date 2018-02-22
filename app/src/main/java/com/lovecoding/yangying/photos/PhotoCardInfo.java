package com.lovecoding.yangying.photos;

/**
 * Created by yangying on 18/2/19.
 */

public class PhotoCardInfo {
    private String URL = null;
    private String createdUser = null;
    private String createdTime = null;
    private String memo = null;

    public PhotoCardInfo(String URL, String createdTime, String createdUser, String memo){
        this.createdTime = createdTime;
        this.createdUser = createdUser;
        this.memo = memo;
        this.URL = URL;
    }

    public PhotoCardInfo(String URL){
        this.createdTime = new String(" ");
        this.createdUser = new String(" ");
        this.memo = new String(" ");
        this.URL = URL;
    }

    public String getURL(){
        return this.URL;
    }

    public String getCreatedUser(){
        return this.createdUser;
    }

    public String getMemo(){
        return this.memo;
    }

    public String getCreatedTime(){
        return this.createdTime;
    }

    public void setURL(String URL){
        this.URL = URL;
    }

    public void setCreatedUser(String createdUser){
        this.createdUser = createdUser;
    }

    public void setCreatedTime(String createdTime){
        this.createdTime = createdTime;
    }

    public void setMemo(String memo){
        this.memo = memo;
    }
}
