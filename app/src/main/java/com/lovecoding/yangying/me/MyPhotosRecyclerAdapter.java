package com.lovecoding.yangying.me;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lovecoding.yangying.ImageDetail.ImageDetailActivity;
import com.lovecoding.yangying.photos.PhotoCardInfo;
import com.lovecoding.yangying.picmask.R;
import com.lovecoding.yangying.tools.BaseAcitivity;
import com.lovecoding.yangying.tools.ReadProperties;
import com.lovecoding.yangying.tools.Tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangying on 18/2/25.
 */

public class MyPhotosRecyclerAdapter  extends RecyclerView.Adapter<MyPhotosRecyclerAdapter.photosRecyclerViewHolder>{

    private Context context = null;
    private List<PhotoCardInfo> photos = new ArrayList<PhotoCardInfo>();

    public MyPhotosRecyclerAdapter(List<PhotoCardInfo> photos, Context context){
        this.photos = photos;
        this.context = context;
    }

    @Override
    public MyPhotosRecyclerAdapter.photosRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_photos
                ,parent,false);
        return new MyPhotosRecyclerAdapter.photosRecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyPhotosRecyclerAdapter.photosRecyclerViewHolder holder, int position) {
        holder.image_name = photos.get(position).getImageName();
        holder.likes = photos.get(position).getLikes();
        holder.image_id = photos.get(position).getImageId();
        if(Tools.judgeLocalImages(holder.image_name)){
            Glide.with(context).load(new File(BaseAcitivity.getContext().getExternalFilesDir(Environment.DIRECTORY_DCIM) + "", holder.image_name)).into(holder.myPhotosImageView);
        }else {
            Glide.with(context).load(ReadProperties.getStringProperties("hostname") +
                    ReadProperties.getStringProperties("downloadRecentImages") + "/" +
                    holder.image_name).into(holder.myPhotosImageView);
        }
        holder.myPhotosCreatedTimeTextView.setText(photos.get(position).getCreatedTime());
        holder.myPhotosMemoTextView.setText(photos.get(position).getMemo());
        holder.photosCardView.setTag(photos.get(position));
        holder.photosCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageDetailActivityIntent = new Intent();
                imageDetailActivityIntent.setClass(BaseAcitivity.getContext(), ImageDetailActivity.class);
                imageDetailActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                imageDetailActivityIntent.putExtra("createUser", ((PhotoCardInfo)v.getTag()).getCreatedUser());
                imageDetailActivityIntent.putExtra("createTime", ((PhotoCardInfo)v.getTag()).getCreatedTime());
                imageDetailActivityIntent.putExtra("memo", ((PhotoCardInfo)v.getTag()).getMemo());
                imageDetailActivityIntent.putExtra("imageId", ((PhotoCardInfo)v.getTag()).getImageId());
                imageDetailActivityIntent.putExtra("likes", ((PhotoCardInfo)v.getTag()).getLikes());
                imageDetailActivityIntent.putExtra("imageName", ((PhotoCardInfo)v.getTag()).getImageName());
                imageDetailActivityIntent.putExtra("selfLike", ((PhotoCardInfo)v.getTag()).getSelfLike());
                BaseAcitivity.getContext().startActivity(imageDetailActivityIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }


    public class photosRecyclerViewHolder extends RecyclerView.ViewHolder{

        int image_id = 0;
        int likes = 0;
        ImageView myPhotosImageView;
        TextView myPhotosMemoTextView;
        TextView myPhotosCreatedTimeTextView;
        CardView photosCardView;
        String image_name = null;
        public photosRecyclerViewHolder(View itemView) {
            super(itemView);
            myPhotosImageView = (ImageView) itemView.findViewById(R.id.myPhotosCardImageView);
            myPhotosCreatedTimeTextView = (TextView) itemView.findViewById(R.id.my_photo_created_time);
            myPhotosMemoTextView = (TextView) itemView.findViewById(R.id.text_my_photo_memo);
            photosCardView = (CardView) itemView.findViewById(R.id.card_my_photos_item);
        }
        public int getImageId(){
            return this.image_id;
        }

        public int getLikes(){
            return this.likes;
        }

        public String getCreateTime() {
            return this.myPhotosCreatedTimeTextView.getText().toString();
        }

        public String getImageName() {
            return this.image_name;
        }

        public String getMemo() {
            return this.myPhotosMemoTextView.getText().toString();
        }
    }
}
