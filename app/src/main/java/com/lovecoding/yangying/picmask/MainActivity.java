package com.lovecoding.yangying.picmask;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lovecoding.yangying.camera.CameraFragment;
import com.lovecoding.yangying.me.MeFragment;
import com.lovecoding.yangying.photos.PhotoFragment;
import com.lovecoding.yangying.tools.BaseAcitivity;

public class MainActivity extends BaseAcitivity {
    private LinearLayout layout_tabbar = null;
    private LinearLayout layout_tabbar_photos = null;
    private LinearLayout layout_tabbar_camera = null;
    private LinearLayout layout_tabbar_me = null;
    private ImageView imageView_tabbar_photos = null;
    private ImageView imageView_tabbar_camera = null;
    private ImageView imageView_tabbar_me = null;
    private PhotoFragment photoFragment = null;
    private MeFragment meFragment = null;
    private CameraFragment cameraFragment = null;

    private void init(){
        layout_tabbar = (LinearLayout) findViewById(R.id.layout_tabbar);
        layout_tabbar_photos = (LinearLayout) findViewById(R.id.layout_tabbar_photos);
        layout_tabbar_camera = (LinearLayout) findViewById(R.id.layout_tabbar_camera);
        layout_tabbar_me = (LinearLayout) findViewById(R.id.layout_tabbar_me);

        imageView_tabbar_photos = (ImageView) findViewById(R.id.image_tabbar_photos);
        imageView_tabbar_camera = (ImageView) findViewById(R.id.image_tabbar_camera);
        imageView_tabbar_me = (ImageView) findViewById(R.id.image_tabbar_me);

        photoFragment = new PhotoFragment();
        meFragment = new MeFragment();
        cameraFragment = new CameraFragment();
    }

    private void clearPressed(){
        imageView_tabbar_camera.setImageResource(R.drawable.icon_camera);
        imageView_tabbar_me.setImageResource(R.drawable.icon_user);
        imageView_tabbar_photos.setImageResource(R.drawable.icon_photo);
    }

    private void bindEvents(){
        layout_tabbar_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPressed();
                imageView_tabbar_photos.setImageResource(R.drawable.icon_photo_pressed);
                // 每次替换fragment必须用临时局部变量
                FragmentManager FMs = getFragmentManager();
                FragmentTransaction  MfragmentTransactions = FMs.beginTransaction();
                MfragmentTransactions.replace(R.id.contentPanel, photoFragment);
                MfragmentTransactions.commit();
            }
        });

        layout_tabbar_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPressed();
                imageView_tabbar_camera.setImageResource(R.drawable.icon_camera_pressed);
                FragmentManager FMs = getFragmentManager();
                FragmentTransaction  MfragmentTransactions = FMs.beginTransaction();
                MfragmentTransactions.replace(R.id.contentPanel, cameraFragment);
                MfragmentTransactions.commit();
            }
        });

        layout_tabbar_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPressed();
                imageView_tabbar_me.setImageResource(R.drawable.icon_user_pressed);
                FragmentManager FMs = getFragmentManager();
                FragmentTransaction  MfragmentTransactions = FMs.beginTransaction();
                MfragmentTransactions.replace(R.id.contentPanel, meFragment);
                MfragmentTransactions.commit();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        bindEvents();
        layout_tabbar_photos.performClick();
    }
}
