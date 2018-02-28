package com.lovecoding.yangying.login;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.lovecoding.yangying.tools.LogUtils;
import com.lovecoding.yangying.tools.UpdateSharedPreferences;
import com.lovecoding.yangying.tools.ReadProperties;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yangying on 18/2/13.
 */

public class LoginTask extends AsyncTask <String, Integer, Boolean>{

    private String username = null;
    private String password = null;
    @Override
    protected Boolean doInBackground(String... params) {
        //创建okHttpClient对象
        LogUtils.d("LoginTask", "create http client");
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建请求体
        username = params[0];
        password = params[1];
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("pwd", password)
                .build();
        LogUtils.d("LoginTask", "create request");
        //创建一个Request
        final Request request = new Request.Builder()
                .url(ReadProperties.getStringProperties("hostname") +
                        ReadProperties.getStringProperties("loginServerlet"))
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
            UpdateSharedPreferences.addStringKeyValuePair("username", username);
            UpdateSharedPreferences.addStringKeyValuePair("password", password);
            LoginActivity.redirectToMainActivity();
        }

    }
}
