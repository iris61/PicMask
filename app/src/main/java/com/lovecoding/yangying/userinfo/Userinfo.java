package com.lovecoding.yangying.userinfo;

/**
 * Created by yangying on 18/2/13.
 */

public class Userinfo {
    private String username;
    private String password;
    private boolean found;

    public Userinfo(String username, String password, boolean found){
        this.username = username;
        this.password = password;
        this.found = found;
    }

    public Userinfo(String username, String password){
        this.username = username;
        this.password = password;
        this.found = false;
    }

    public boolean getFound(){
        return this.found;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
