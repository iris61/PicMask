package com.lovecoding.yangying.photos;

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
import com.lovecoding.yangying.picmask.R;
import com.lovecoding.yangying.tools.BaseAcitivity;
import com.lovecoding.yangying.tools.ReadProperties;
import com.lovecoding.yangying.tools.Tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangying on 18/2/19.
 */


public class PhotosRecyclerAdapter extends RecyclerView.Adapter<PhotosRecyclerAdapter.photosRecyclerViewHolder>{

    private Context context = null;
    private List<PhotoCardInfo> photos = new ArrayList<PhotoCardInfo>();

    public PhotosRecyclerAdapter(List<PhotoCardInfo> photos, Context context){
        this.photos = photos;
        this.context = context;
    }

    @Override
    public photosRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photos
                ,parent,false);
        return new photosRecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(photosRecyclerViewHolder holder, int position) {
        holder.image_name = photos.get(position).getImageName();
        holder.likes = photos.get(position).getLikes();
        holder.image_id = photos.get(position).getImageId();
        if(Tools.judgeLocalImages(holder.image_name)){
            Glide.with(context).load(new File(BaseAcitivity.getContext().getExternalFilesDir(Environment.DIRECTORY_DCIM) + "", holder.image_name)).into(holder.photosImageView);
        }else {
            Glide.with(context).load(ReadProperties.getStringProperties("hostname") +
                    ReadProperties.getStringProperties("downloadRecentImages") + "/" +
                    holder.image_name).into(holder.photosImageView);
        }
        holder.photosCreatedTimeTextView.setText(photos.get(position).getCreatedTime());
        holder.photosCreatedUserTextView.setText(photos.get(position).getCreatedUser());
        holder.photosMemoTextView.setText(photos.get(position).getMemo());
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
        ImageView photosImageView;
        TextView photosMemoTextView;
        TextView photosCreatedUserTextView;
        TextView photosCreatedTimeTextView;
        CardView photosCardView;
        String image_name = null;
        public photosRecyclerViewHolder(View itemView) {
            super(itemView);
            photosImageView = (ImageView) itemView.findViewById(R.id.photosCardImageView);
            photosCreatedTimeTextView = (TextView) itemView.findViewById(R.id.photo_created_time);
            photosCreatedUserTextView = (TextView) itemView.findViewById(R.id.photo_created_user);
            photosMemoTextView = (TextView) itemView.findViewById(R.id.text_photo_memo);
            photosCardView = (CardView) itemView.findViewById(R.id.card_photos_item);
        }

        public int getImageId(){
            return this.image_id;
        }
        public int getLikes(){
            return this.likes;
        }

        public String getCreateUser() {
            return this.photosCreatedUserTextView.getText().toString();
        }

        public String getCreateTime() {
            return this.photosCreatedTimeTextView.getText().toString();
        }

        public String getImageName() {
            return this.image_name;
        }

        public String getMemo() {
            return this.photosMemoTextView.getText().toString();
        }

    }
}
