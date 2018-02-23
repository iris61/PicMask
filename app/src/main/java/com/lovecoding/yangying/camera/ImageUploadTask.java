package com.lovecoding.yangying.camera;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.lovecoding.yangying.picmask.MainActivity;
import com.lovecoding.yangying.tools.BaseAcitivity;
import com.lovecoding.yangying.tools.LogUtils;

import java.io.File;

/**
 * Created by yangying on 18/2/22.
 */

public class ImageUploadTask extends AsyncTask<String, Integer, Boolean>{

    @Override
    protected Boolean doInBackground(String... params) {
        if(ImageUploadUtils.uploadFile(new File(params[0]), params[1]) == 1) return true;
        else return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        LogUtils.d("upload image result", result?"login correct":"login failed");
        EditPicCommentActivity.dismissProgressDialog();
        if(result) {
            Toast.makeText(BaseAcitivity.getContext(), "已成功上传", Toast.LENGTH_SHORT);
        }else {
            Toast.makeText(BaseAcitivity.getContext(), "上传失败，请稍后再试", Toast.LENGTH_SHORT);
        }
        Intent mainActivityIntent = new Intent();
        mainActivityIntent.setClass(BaseAcitivity.getContext(), MainActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        BaseAcitivity.getContext().startActivity(mainActivityIntent);

    }
}
