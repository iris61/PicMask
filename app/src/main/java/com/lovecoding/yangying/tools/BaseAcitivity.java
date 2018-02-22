package com.lovecoding.yangying.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by yangying on 18/2/9.
 */

public class BaseAcitivity extends AppCompatActivity {
    private static Context instance;
    //private static ArrayList<Activity> activities = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = getApplicationContext();
        //activities.add(this);
    }

    public static Context getContext(){
        return instance;
    }
}
