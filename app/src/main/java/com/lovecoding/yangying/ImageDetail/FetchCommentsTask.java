package com.lovecoding.yangying.ImageDetail;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lovecoding.yangying.tools.LogUtils;
import com.lovecoding.yangying.tools.ReadProperties;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yangying on 18/2/26.
 */

public class FetchCommentsTask extends AsyncTask <Integer, Integer, List<CommentsInfo>> {
    OnDataFinishedListener onDataFinishedListener;

    private static final int TIME_OUT = 10 * 10000000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    @Override
    protected List<CommentsInfo> doInBackground(Integer... params) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建请求体
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=utf-8"),
//                "{\"imageId\":\"" + params[0] + "\"}");
        //创建一个Request
        RequestBody requestBody = new FormBody.Builder()
                .add("imageId", params[0] + "")
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
            final String htmlStr = response.body().string();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<CommentsInfo>>(){}.getType();
            List<CommentsInfo> comments=gson.fromJson(htmlStr, listType);//result就是从servlet端传过来的字符串
            if (comments != null) {
                return comments;
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<CommentsInfo>();
    }

    @Override
    protected void onPostExecute(List<CommentsInfo> commentsInfos) {
        if(commentsInfos != null) {
            onDataFinishedListener.onDataSuccessfully(commentsInfos);
        }else {
            onDataFinishedListener.onDataFailed();
        }
    }

    public void setOnDataFinishedListener(OnDataFinishedListener listener){
        this.onDataFinishedListener = listener;
    }

    public interface OnDataFinishedListener {
        public void onDataSuccessfully(Object data);
        public void onDataFailed();
    }
}
