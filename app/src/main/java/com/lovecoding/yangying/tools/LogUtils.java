package com.lovecoding.yangying.tools;

import android.util.Log;

/**
 * Created by yangying on 18/2/9.
 */

public class LogUtils {
    private static int debugLevel = 0;
    public static final int LOG_LEVEL_ALL = 0;
    public static final int LOG_LEVEL_VERBOSE = 1;
    public static final int LOG_LEVEL_DEBUG = 2;
    public static final int LOG_LEVEL_INFO = 3;
    public static final int LOG_LEVEL_WARN = 4;
    public static final int LOG_LEVEL_ERROR = 5;
    public static final int LOG_LEVEL_ASSERT = 6;

    private LogUtils(){
        debugLevel = 0;
    }

    public void setDebugLevel(int debugLevel){
        this.debugLevel = debugLevel;
    }

    public int getDebugLevel(){
        return this.debugLevel;
    }

    public static void v(String Tag, String log){
        if(debugLevel <= LOG_LEVEL_VERBOSE) Log.v(Tag, log);
    }

    public static void d(String Tag, String log){
        if(debugLevel <= LOG_LEVEL_VERBOSE) Log.d(Tag, log);
    }

    public static void e(String Tag, String log){
        if(debugLevel <= LOG_LEVEL_VERBOSE) Log.e(Tag, log);
    }

    public static void i(String Tag, String log){
        if(debugLevel <= LOG_LEVEL_VERBOSE) Log.i(Tag, log);
    }

    public static void w(String Tag, String log){
        if(debugLevel <= LOG_LEVEL_VERBOSE) Log.w(Tag, log);
    }
}
