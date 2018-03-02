package com.lovecoding.yangying.ImageDetail;

import android.os.AsyncTask;

import com.lovecoding.yangying.tools.LogUtils;
import com.lovecoding.yangying.tools.ReadProperties;
import com.lovecoding.yangying.tools.UpdateSharedPreferences;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yangying on 18/2/26.
 */

public class SubmitCommentsTask extends AsyncTask<CommentsInfo, Integer, Boolean>{

    OnDataFinishedListener onDataFinishedListener;
    private static final int TIME_OUT = 10 * 10000000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码

    @Override
    protected Boolean doInBackground(CommentsInfo... params) {
        try {
            OkHttpClient mOkHttpClient = new OkHttpClient();
            //创建请求体
            RequestBody requestBody = new FormBody.Builder()
                    .add("userName", params[0].getUserName() + "")
                    .add("imageId", params[0].getImageId() + "")
                    .add("replyToComment", params[0].getReplyToComment() + "")
                    .add("content", params[0].getContent() + "")
                    .add("time", params[0].getTime() + "")
                    .add("hostComment", params[0].getHostComment() + "")
                    .build();

            final Request request = new Request.Builder()
                    .url(ReadProperties.getStringProperties("hostname") +
                            ReadProperties.getStringProperties("commentsServlet"))
                    .addHeader("content-type", "text/html;charset:utf-8")
                    .post(requestBody)
                    .build();

            Response response = null;
            response = mOkHttpClient.newCall(request).execute();
            //final String htmlStr = response.body().string();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void setOnDataFinishedListener(OnDataFinishedListener listener){
        this.onDataFinishedListener = listener;
    }

    public interface OnDataFinishedListener {
        public void onDataSuccessfully();
        public void onDataFailed();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(aBoolean) {
            this.onDataFinishedListener.onDataSuccessfully();
        }else this.onDataFinishedListener.onDataFailed();
    }
}
