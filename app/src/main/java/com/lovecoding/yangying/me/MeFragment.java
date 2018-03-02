package com.lovecoding.yangying.me;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lovecoding.yangying.photos.FetchRecentPhotosTask;
import com.lovecoding.yangying.photos.PhotoCardInfo;
import com.lovecoding.yangying.photos.PhotosRecyclerAdapter;
import com.lovecoding.yangying.picmask.R;
import com.lovecoding.yangying.tools.LogUtils;
import com.lovecoding.yangying.tools.UpdateSharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangying on 18/2/9.
 */

public class MeFragment extends Fragment {
    private List<PhotoCardInfo> myPhotosCardInfoList = new ArrayList<PhotoCardInfo>();
    private RecyclerView myPhotosRecyclerView = null;
    private View view = null;
    private ProgressDialog mProgressDialog = null;
    private FetchRecentPhotosTask fetchRecentPhotosTask = null;
    private TextView textMyUsername = null;

    private void executeQueryImages(){
        fetchRecentPhotosTask = new FetchRecentPhotosTask();
        fetchRecentPhotosTask.setOnDataFinishedListener(new FetchRecentPhotosTask.OnDataFinishedListener() {
            @Override
            public void onDataSuccessfully(Object data) {
                LogUtils.v("return imagesInfo", data.toString());
                myPhotosCardInfoList = (List<PhotoCardInfo>)data;
                MyPhotosRecyclerAdapter photosRecyclerAdapter = new MyPhotosRecyclerAdapter(myPhotosCardInfoList, getActivity());
                myPhotosRecyclerView.setAdapter(photosRecyclerAdapter);
                photosRecyclerAdapter.notifyDataSetChanged();
                mProgressDialog.dismiss();
            }

            @Override
            public void onDataFailed() {
                mProgressDialog.dismiss();
                Toast.makeText(getActivity(), "加载失败！", Toast.LENGTH_SHORT).show();
            }
        });
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setProgress(ProgressDialog.STYLE_SPINNER);//圆形
            mProgressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);//水平
            //mProgressDialog.setTitle("");
            mProgressDialog.setMessage("正在读取最新数据，请稍候");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        fetchRecentPhotosTask.execute("10", UpdateSharedPreferences.getStringValue("username"),UpdateSharedPreferences.getStringValue("username"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_me,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        executeQueryImages();
    }

    private void init(){
        myPhotosRecyclerView = (RecyclerView)view.findViewById(R.id.myPhotosRecyclerView);
        myPhotosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyPhotosRecyclerAdapter photosRecyclerAdapter = new MyPhotosRecyclerAdapter(myPhotosCardInfoList, getActivity());
        myPhotosRecyclerView.setAdapter(photosRecyclerAdapter);
        textMyUsername = (TextView) view.findViewById(R.id.text_my_username);
        textMyUsername.setText(UpdateSharedPreferences.getStringValue("username"));
    }
}
