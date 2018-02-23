package com.lovecoding.yangying.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.lovecoding.yangying.imageprocessing.ProcessImageView;
import com.lovecoding.yangying.picmask.R;
import com.lovecoding.yangying.tools.BaseAcitivity;
import com.lovecoding.yangying.tools.LogUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by yangying on 18/2/19.
 */

public class ImageProcessActivity extends BaseAcitivity {
    private static String imagePath = null;
    private ProcessImageView capturePicImageView= null;
    private SeekBar seekBarRpercent = null;
    private SeekBar seekBarGpercent = null;
    private SeekBar seekBarBpercent = null;
    private SeekBar seekBarApercent = null;
    private Button backToOriginBtn = null;
    private Toolbar buttonToolBar = null;
    private Button buttonToolBarBack = null;
    private Button buttonToolBarNext = null;

    private class seekbarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(seekBar == seekBarRpercent) capturePicImageView.setRpercent(progress);
            else if(seekBar == seekBarGpercent) capturePicImageView.setGpercent(progress);
            else if(seekBar == seekBarBpercent) capturePicImageView.setBpercent(progress);
            else if(seekBar == seekBarApercent) capturePicImageView.setApercent(progress);
            else ;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private String saveBitmapFile(Bitmap bitmap) {
        String saveProcessedPic = getExternalFilesDir(Environment.DIRECTORY_DCIM) + "";//+"/picMask";
        LogUtils.d("processedImageSavePath", saveProcessedPic);
        File processImageDir=new File(saveProcessedPic, "iris61" + System.currentTimeMillis()+"P.png");
        if(!processImageDir.exists()){
            try {
                //根据一个 文件地址生成一个新的文件用来存照片
                processImageDir.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ////重复保存时，覆盖原同名图片
        try {
            BufferedOutputStream bos= new BufferedOutputStream(new FileOutputStream(processImageDir));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return processImageDir.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void init(){
        capturePicImageView = (ProcessImageView) findViewById(R.id.image_capture_pic);
        seekBarRpercent = (SeekBar) findViewById(R.id.seekbar_R_Percent);
        seekBarGpercent = (SeekBar) findViewById(R.id.seekbar_G_Percent);
        seekBarBpercent = (SeekBar) findViewById(R.id.seekbar_B_Percent);
        seekBarApercent = (SeekBar) findViewById(R.id.seekbar_A_Percent);
        backToOriginBtn = (Button) findViewById(R.id.btn_back_to_origin);
        buttonToolBarBack = (Button) findViewById(R.id.btn_toolbar_back);
        buttonToolBarNext = (Button) findViewById(R.id.btn_toolbar_next);
        buttonToolBar = (Toolbar) findViewById(R.id.btn_tool_bar);
        setSupportActionBar(buttonToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void bindEvent(){
        seekBarRpercent.setOnSeekBarChangeListener(new seekbarChangeListener());
        seekBarGpercent.setOnSeekBarChangeListener(new seekbarChangeListener());
        seekBarBpercent.setOnSeekBarChangeListener(new seekbarChangeListener());
        seekBarApercent.setOnSeekBarChangeListener(new seekbarChangeListener());

        backToOriginBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        capturePicImageView.backToOriginImage();
                        break;
                    case MotionEvent.ACTION_UP:
                        capturePicImageView.backToCurrentImage();
                        break;
                    default:;
                }
                return true;
            }
        });

        buttonToolBarNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePicImageView.buildDrawingCache(true);
                capturePicImageView.buildDrawingCache();
                Bitmap processedBitmap = capturePicImageView.getDrawingCache();
                String savedFilterImagePath = saveBitmapFile(processedBitmap);
                capturePicImageView.setDrawingCacheEnabled(false);

                Intent editPicCommentIntent = new Intent();
                editPicCommentIntent.putExtra("filterImagePath", savedFilterImagePath);
                editPicCommentIntent.setClass(ImageProcessActivity.this, EditPicCommentActivity.class);
                startActivity(editPicCommentIntent);
            }
        });

        buttonToolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_process);
        init();
        bindEvent();
        imagePath = getIntent().getStringExtra("imagePath");
        Bitmap bm= BitmapFactory.decodeFile(imagePath);
        capturePicImageView.setImageBitmap(bm);
    }
}
