package com.lovecoding.yangying.tools;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yangying on 18/2/22.
 */

public class readProperties {
    public readProperties(){}
    public static Properties loadProperties(Context context) {
        Properties properties = new Properties();
        try {
            InputStream in = context.getAssets().open("Server.properties");
            properties.load(in);
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return properties;
    }

    public static String getStringProperties(String key) {
        Properties properties = loadProperties(BaseAcitivity.getContext());
        return properties.getProperty(key);
    }
}
