package com.lovecoding.yangying.ImageDetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lovecoding.yangying.picmask.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangying on 18/2/26.
 */

public class CommentsRecyclerAdapter  extends RecyclerView.Adapter<CommentsRecyclerAdapter.CommentsRecyclerViewHolder>{

    private Context context = null;
    private List<CommentsInfo> comments = new ArrayList<CommentsInfo>();

    public CommentsRecyclerAdapter(List<CommentsInfo> comments, Context context){
        this.comments = comments;
        this.context = context;
    }

    @Override
    public CommentsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_comments
                ,parent,false);
        return new CommentsRecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(CommentsRecyclerViewHolder holder, int position) {
        holder.comment_id = comments.get(position).getCommentId();
        holder.host_comment = comments.get(position).getHostComment();
        if(holder.host_comment == 0) {
            holder.textHostCommentsItem.setText(comments.get(position).getUserId() + ": " +
                    comments.get(position).getContent());
            holder.textReplyCommentsItem.setVisibility(View.GONE);
        }else {
            holder.textReplyCommentsItem.setText(comments.get(position).getUserId() + " 回复: " +
                    comments.get(position).getReplyToComment() +
                    comments.get(position).getContent());
            holder.textHostCommentsItem.setVisibility(View.GONE);
        }
        holder.layoutItemComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    public class CommentsRecyclerViewHolder extends RecyclerView.ViewHolder{

        int comment_id = 0;
        int host_comment = 0;
        TextView textHostCommentsItem;
        TextView textReplyCommentsItem;
        LinearLayout layoutItemComment;
        public CommentsRecyclerViewHolder(View itemView) {
            super(itemView);
            textHostCommentsItem = (TextView) itemView.findViewById(R.id.text_host_comments_item);
            textReplyCommentsItem = (TextView) itemView.findViewById(R.id.text_reply_comments_item);
            layoutItemComment = (LinearLayout) itemView.findViewById(R.id.layout_item_comment);
        }

        public int getCommentId(){
            return this.comment_id;
        }
        public int getHostComment(){
            return this.host_comment;
        }
    }
}