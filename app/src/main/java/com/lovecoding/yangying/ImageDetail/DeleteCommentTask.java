package com.lovecoding.yangying.ImageDetail;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lovecoding.yangying.tools.ReadProperties;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yangying on 18/2/26.
 */

public class DeleteCommentTask extends AsyncTask <Integer, Integer, Boolean> {
    OnDataFinishedListener onDataFinishedListener;

    private static final int TIME_OUT = 10 * 10000000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    @Override
    protected Boolean doInBackground(Integer... params) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建请求体
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=utf-8"),
//                "{\"imageId\":\"" + params[0] + "\"}");
        //创建一个Request
        RequestBody requestBody = new FormBody.Builder()
                .add("delComment", params[0] + "")
                .build();

        final Request request = new Request.Builder()
                .url(ReadProperties.getStringProperties("hostname") +
                        ReadProperties.getStringProperties("commentsServlet"))
                .addHeader("content-type", "text/html;charset:utf-8")
                .post(requestBody)
                .build();

        Response response = null;

        try {
            response = mOkHttpClient.newCall(request).execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result) {
            onDataFinishedListener.onDataSuccessfully();
        }else {
            onDataFinishedListener.onDataFailed();
        }
    }

    public void setOnDataFinishedListener(OnDataFinishedListener listener){
        this.onDataFinishedListener = listener;
    }

    public interface OnDataFinishedListener {
        public void onDataSuccessfully();
        public void onDataFailed();
    }
}
