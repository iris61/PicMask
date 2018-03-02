package com.lovecoding.yangying.photos;

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
 * Created by yangying on 18/2/23.
 */

public class FetchRecentPhotosTask extends AsyncTask<String, Integer, List<PhotoCardInfo>> {
    OnDataFinishedListener onDataFinishedListener;

    @Override
    protected List<PhotoCardInfo> doInBackground(String... params) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建请求体
        RequestBody requestBody = new FormBody.Builder()
                .add("num", Integer.parseInt(params[0]) + "")
                .add("username", params[1])
                .add("me", params[2])
                .build();
        //创建一个Request
        final Request request = new Request.Builder()
                .url(ReadProperties.getStringProperties("hostname")
                        + ReadProperties.getStringProperties("fetchRecentImages"))
                .post(requestBody)
                .build();

        Response response = null;
        try {
            response = mOkHttpClient.newCall(request).execute();
            final String htmlStr = response.body().string();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<PhotoCardInfo>>(){}.getType();
            List<PhotoCardInfo> images=gson.fromJson(htmlStr, listType);//result就是从servlet端传过来的字符串
            if (response.isSuccessful()) {
                return images;
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<PhotoCardInfo>();
    }

    public void setOnDataFinishedListener(
            OnDataFinishedListener onDataFinishedListener) {
        this.onDataFinishedListener = onDataFinishedListener;
    }

    public interface OnDataFinishedListener {
        public void onDataSuccessfully(Object data);
        public void onDataFailed();
    }

    @Override
    protected void onPostExecute(List<PhotoCardInfo> photoCardInfos) {
        if(photoCardInfos!=null){
            onDataFinishedListener.onDataSuccessfully(photoCardInfos);
        }else{
            onDataFinishedListener.onDataFailed();
        }
    }
}
