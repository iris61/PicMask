package com.lovecoding.yangying.photos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_photos,container,false);
        //initPhotosArray();
        initRecyclerView();
        return view;
    }

    private void initRecyclerView(){
        photosRecyclerView = (RecyclerView)view.findViewById(R.id.photosRecyclerView);
        photosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FetchRecentPhotosTask fetchRecentPhotosTask = new FetchRecentPhotosTask();
        fetchRecentPhotosTask.setOnDataFinishedListener(new FetchRecentPhotosTask.OnDataFinishedListener() {
            @Override
            public void onDataSuccessfully(Object data) {
                LogUtils.v("return imagesInfo", data.toString());
                photosCardInfoList = (List<PhotoCardInfo>)data;
                PhotosRecyclerAdapter photosRecyclerAdapter = new PhotosRecyclerAdapter(photosCardInfoList, getActivity());
                photosRecyclerView.setAdapter(photosRecyclerAdapter);
                photosRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDataFailed() {
                Toast.makeText(BaseAcitivity.getContext(), "加载失败！", Toast.LENGTH_SHORT).show();
            }
        });
        fetchRecentPhotosTask.execute(10);
        PhotosRecyclerAdapter photosRecyclerAdapter = new PhotosRecyclerAdapter(photosCardInfoList, getActivity());
        photosRecyclerView.setAdapter(photosRecyclerAdapter);
    }

//    private void initPhotosArray(){
//        for(int i = 0; i < 10; i++) {
//            photosCardInfoList.add(new PhotoCardInfo("http://imgsrc.baidu.com/imgad/pic/item/34fae6cd7b899e51fab3e9c048a7d933c8950d21.jpg", "2018/01/01", "iris61", "今天天气好赞啊！"));
//        }
//    }
}
