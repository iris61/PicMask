package com.lovecoding.yangying.ImageDetail;

import android.os.AsyncTask;

import com.lovecoding.yangying.tools.LogUtils;
import com.lovecoding.yangying.tools.ReadProperties;
import com.lovecoding.yangying.tools.UpdateSharedPreferences;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yangying on 18/2/28.
 */

public class UpdateImageInfoTask extends AsyncTask<Integer, Integer, Boolean>{
    OnDataFinishedListener onDataFinishedListener;

    public static final int TOGGLE_LIKE = 1;
    public static final int REMOVE_IMAGE = 3;

    public void setOnDataFinishedListener(OnDataFinishedListener listener){
        this.onDataFinishedListener = listener;
    }

    @Override
    protected Boolean doInBackground(Integer... params) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建请求体
        int imageId = params[0];
        int action = params[1];
        String actionStr = null;
        switch (action) {
            case REMOVE_IMAGE:
                actionStr = "removeImage";
                break;
            case TOGGLE_LIKE:
                actionStr = "toggleLike";
                break;
            default:
                actionStr = "unknown";
        }

        RequestBody requestBody = new FormBody.Builder()
                .add("imageId", imageId + "")
                .add("action", actionStr)
                .add("username", UpdateSharedPreferences.getStringValue("username"))
                .build();

        //创建一个Request
        final Request request = new Request.Builder()
                .url(ReadProperties.getStringProperties("hostname") +
                        ReadProperties.getStringProperties("updateImageServlet"))
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = mOkHttpClient.newCall(request).execute();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        this.onDataFinishedListener.onDataSuccessfully();
    }

    public interface OnDataFinishedListener {
        public void onDataSuccessfully();
        public void onDataFailed();
    }
}
