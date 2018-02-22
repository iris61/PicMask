package com.lovecoding.yangying.photos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lovecoding.yangying.picmask.R;

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
        Glide.with(context).load(photos.get(position).getURL()).into(holder.photosImageView);
        holder.photosCreatedTimeTextView.setText(photos.get(position).getCreatedTime());
        holder.photosCreatedUserTextView.setText(photos.get(position).getCreatedUser());
        holder.photosMemoTextView.setText(photos.get(position).getMemo());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }


    public class photosRecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView photosImageView;
        TextView photosMemoTextView;
        TextView photosCreatedUserTextView;
        TextView photosCreatedTimeTextView;
        public photosRecyclerViewHolder(View itemView) {
            super(itemView);
            photosImageView = (ImageView) itemView.findViewById(R.id.photosCardImageView);
            photosCreatedTimeTextView = (TextView) itemView.findViewById(R.id.photo_created_time);
            photosCreatedUserTextView = (TextView) itemView.findViewById(R.id.photo_created_user);
            photosMemoTextView = (TextView) itemView.findViewById(R.id.text_photo_memo);
        }
    }
}
