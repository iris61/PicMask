package com.lovecoding.yangying.login;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.lovecoding.yangying.tools.LogUtils;
import com.lovecoding.yangying.tools.readProperties;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by yangying on 18/2/13.
 */

public class LoginTask extends AsyncTask <String, Integer, Boolean>{

    @Override
    protected Boolean doInBackground(String... params) {
        //创建okHttpClient对象
        LogUtils.d("LoginTask", "create http client");
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建请求体
        RequestBody requestBody = new FormEncodingBuilder()
                .add("username", params[0])
                .add("pwd", params[1])
                .build();
        LogUtils.d("LoginTask", "create request");
        //创建一个Request
        final Request request = new Request.Builder()
                .url(readProperties.getStringProperties("loginServerlet"))
                .post(requestBody)
                .build();

        Response response = null;
        try {
            LogUtils.d("LoginTask", "start");
            response = mOkHttpClient.newCall(request).execute();
            LogUtils.d("LoginTask", "response");
            final String htmlStr = response.body().string();
            LogUtils.d("LoginTask", "return");
            Gson gson = new Gson();
            Userinfo userinfoBack = gson.fromJson(htmlStr,Userinfo.class);
            if (response.isSuccessful()) {
                LogUtils.d("LoginTask", "Success");
                return userinfoBack.getFound();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        LogUtils.d("login result", result?"login correct":"login failed");
        if(result) {
            LoginActivity.redirectToMainActivity();
        }

    }
}
