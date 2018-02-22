package com.lovecoding.yangying.camera;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.lovecoding.yangying.picmask.MainActivity;
import com.lovecoding.yangying.picmask.R;
import com.lovecoding.yangying.tools.LogUtils;

import java.io.File;
import java.io.IOException;


/**
 * Created by yangying on 18/2/19.
 */

public class CameraFragment extends Fragment {
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int CHOOSE_PIC_REQUEST_CODE = 200;
    public static String SAVED_CAPTURE_IMAGE_PATH = null;
    private Button selectPicButton = null;
    private Button takePicButton = null;
    private File imageDir;

    private void init(View v){
        selectPicButton = (Button) v.findViewById(R.id.btn_select_pic);
        takePicButton = (Button) v.findViewById(R.id.btn_take_pic);
    }

    private void bindEvent(){
        selectPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent choosePicIntent = null;
                boolean isKitKatO = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
                if (isKitKatO) {
                    choosePicIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                } else {
                    choosePicIntent = new Intent(Intent.ACTION_GET_CONTENT);
                }
                choosePicIntent.setType("image/*");//相片类型
                startActivityForResult(choosePicIntent, CHOOSE_PIC_REQUEST_CODE);
            }
        });

        takePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state= Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)){

                    //设置图片保存路径
                    SAVED_CAPTURE_IMAGE_PATH = getActivity().getExternalCacheDir() + "";//+"/picMask";
                    LogUtils.d("imageSavePath", SAVED_CAPTURE_IMAGE_PATH);
                    imageDir=new File(SAVED_CAPTURE_IMAGE_PATH, System.currentTimeMillis()+".png");
                    if(!imageDir.exists()){
                        try {
                            //根据一个 文件地址生成一个新的文件用来存照片
                            imageDir.createNewFile();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageDir));
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                }else {
                    Toast.makeText(getActivity(),"SD卡未插入",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cameraResult(int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK) {
            if (imageDir.exists()){
                //通过图片地址将图片加载到bitmap里面
                Intent imageProcessIntent = new Intent(getActivity(), ImageProcessActivity.class);
                imageProcessIntent.putExtra("imagePath", imageDir.getAbsolutePath());
                startActivity(imageProcessIntent);
            }else {
                Toast.makeText(getActivity(),"图片文件不存在",Toast.LENGTH_LONG).show();
            }
        }else Toast.makeText(getActivity(),"图片获取失败",Toast.LENGTH_LONG).show();
    }

    private void getChoosePic(int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK) {
            Uri choosePicUri = data.getData(); // 获得图片的uri
            String[] proj = { MediaStore.Images.Media.DATA };

            // 好像是android多媒体数据库的封装接口，具体的看Android文档
            Cursor cursor = getActivity().getContentResolver().query(choosePicUri, proj, null, null, null);
            // 按我个人理解 这个是获得用户选择的图片的索引值
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            // 将光标移至开头 ，这个很重要，不小心很容易引起越界
            cursor.moveToFirst();
            // 最后根据索引值获取图片路径
            String path = cursor.getString(column_index);
            Intent imageProcessIntent = new Intent(getActivity(), ImageProcessActivity.class);
            imageProcessIntent.putExtra("imagePath", path);
            startActivity(imageProcessIntent);
        }else Toast.makeText(getActivity(),"图片获取失败",Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera,container,false);
        init(view);
        bindEvent();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                cameraResult(resultCode, data);
                break;
            case CHOOSE_PIC_REQUEST_CODE:
                getChoosePic(resultCode, data);
            default:;
        }
    }
}
