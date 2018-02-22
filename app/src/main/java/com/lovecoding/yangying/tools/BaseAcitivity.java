package com.lovecoding.yangying.tools;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by yangying on 18/2/9.
 */

public class BaseAcitivity extends AppCompatActivity {
    private static Context instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = getApplicationContext();
    }

    public static Context getContext(){
        return instance;
    }
}
