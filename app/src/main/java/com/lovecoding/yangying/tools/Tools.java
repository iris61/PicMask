package com.lovecoding.yangying.tools;

import android.os.Environment;

import java.io.File;

/**
 * Created by yangying on 18/2/26.
 */

public class Tools {
    public Tools(){}

    public static boolean judgeLocalImages(String imageName) {
        String saveProcessedPic = BaseAcitivity.getContext().getExternalFilesDir(Environment.DIRECTORY_DCIM) + "";//+"/picMask";
        LogUtils.d("judgeImagesLocal", saveProcessedPic);
        File processImageDir=new File(saveProcessedPic, imageName);
        if(processImageDir.exists()) return true;
        else return false;
    }
}
