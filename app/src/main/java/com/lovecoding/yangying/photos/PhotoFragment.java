package com.lovecoding.yangying.photos;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lovecoding.yangying.camera.EditPicCommentActivity;
import com.lovecoding.yangying.picmask.R;
import com.lovecoding.yangying.tools.BaseAcitivity;
import com.lovecoding.yangying.tools.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangying on 18/2/9.
 */

public class PhotoFragment extends Fragment{
    private List<PhotoCardInfo> photosCardInfoList = new ArrayList<PhotoCardInfo>();
    private RecyclerView photosRecyclerView = null;
    private View view = null;
    private ProgressDialog mProgressDialog = null;
    private SwipeRefreshLayout mSwipeLayout = null;
    private FetchRecentPhotosTask fetchRecentPhotosTask = null;

    private void executeQueryImages(){
        fetchRecentPhotosTask = new FetchRecentPhotosTask();
        fetchRecentPhotosTask.setOnDataFinishedListener(new FetchRecentPhotosTask.OnDataFinishedListener() {
            @Override
            public void onDataSuccessfully(Object data) {
                LogUtils.v("return imagesInfo", data.toString());
                photosCardInfoList = (List<PhotoCardInfo>)data;
                PhotosRecyclerAdapter photosRecyclerAdapter = new PhotosRecyclerAdapter(photosCardInfoList, getActivity());
                photosRecyclerView.setAdapter(photosRecyclerAdapter);
                photosRecyclerAdapter.notifyDataSetChanged();
                if(mSwipeLayout.isRefreshing()) mSwipeLayout.setRefreshing(false);
                else mProgressDialog.dismiss();
            }

            @Override
            public void onDataFailed() {
                mProgressDialog.dismiss();
                Toast.makeText(getActivity(), "加载失败！", Toast.LENGTH_SHORT).show();
            }
        });
        if(!mSwipeLayout.isRefreshing()) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setProgress(ProgressDialog.STYLE_SPINNER);//圆形
            mProgressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);//水平
            //mProgressDialog.setTitle("");
            mProgressDialog.setMessage("正在读取最新数据，请稍候");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
        fetchRecentPhotosTask.execute("10", "");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_photos,container,false);
        initRecyclerView();
        executeQueryImages();
        return view;
    }

    private void initRecyclerView(){
        photosRecyclerView = (RecyclerView)view.findViewById(R.id.photosRecyclerView);
        photosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        PhotosRecyclerAdapter photosRecyclerAdapter = new PhotosRecyclerAdapter(photosCardInfoList, getActivity());
        photosRecyclerView.setAdapter(photosRecyclerAdapter);

        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout_photos_fragment);
        mSwipeLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);
        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        mSwipeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);
        //设置下拉刷新的监听
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeQueryImages();
            }
        });
    }

//    private void initPhotosArray(){
//        for(int i = 0; i < 10; i++) {
//            photosCardInfoList.add(new PhotoCardInfo("http://imgsrc.baidu.com/imgad/pic/item/34fae6cd7b899e51fab3e9c048a7d933c8950d21.jpg", "2018/01/01", "iris61", "今天天气好赞啊！"));
//        }
//    }
}
