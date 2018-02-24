package com.lovecoding.yangying.login;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lovecoding.yangying.tools.BaseAcitivity;
import com.lovecoding.yangying.tools.LogUtils;
import com.lovecoding.yangying.tools.readProperties;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by yangying on 18/2/24.
 */

public class SignUpTask extends AsyncTask<String, Integer, Integer> {
    public static int SUCCESS_CREATE_ACCOUNT = 0;
    public static int FAILED_CREATE_ACCOUNT_REPEATE = 1;
    public static int UNKNOWN_ERROR = 99;
    private SignUpFinishListener signUpFinishListener = null;

    @Override
    protected Integer doInBackground(String... params) {
        LogUtils.d("SignUpTask", "create http client");
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建请求体
        RequestBody requestBody = new FormEncodingBuilder()
                .add("username", params[0])
                .add("pwd", params[1])
                .build();
        LogUtils.d("SignUpTask", "create request");
        //创建一个Request
        final Request request = new Request.Builder()
                .url(readProperties.getStringProperties("signUpServerlet"))
                .post(requestBody)
                .build();

        Response response = null;
        try {
            response = mOkHttpClient.newCall(request).execute();
            final String htmlStr = response.body().string();
            int result = Integer.parseInt(htmlStr);
            if (response.isSuccessful()) {
                return result;
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UNKNOWN_ERROR;
    }

    @Override
    protected void onPostExecute(Integer signUpBack) {
        super.onPostExecute(signUpBack);
        switch (signUpBack) {
            case 0:
                signUpFinishListener.OnSignUpSuccess();
                Toast.makeText(BaseAcitivity.getContext(), "Successfully sign up", Toast.LENGTH_SHORT).show();
                break;
            default:
                signUpFinishListener.OnSignUpFailed();
                Toast.makeText(BaseAcitivity.getContext(), "Sign up failed, please try again", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void setSignUpFinishListener(SignUpFinishListener signUpFinishListener){
        this.signUpFinishListener = signUpFinishListener;
    }

    public interface SignUpFinishListener{
        public void OnSignUpSuccess();
        public void OnSignUpFailed();
    }
}
