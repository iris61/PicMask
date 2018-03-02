package com.lovecoding.yangying.ImageDetail;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lovecoding.yangying.photos.PhotosRecyclerAdapter;
import com.lovecoding.yangying.picmask.R;
import com.lovecoding.yangying.tools.BaseAcitivity;
import com.lovecoding.yangying.tools.LogUtils;
import com.lovecoding.yangying.tools.ReadProperties;
import com.lovecoding.yangying.tools.Tools;
import com.lovecoding.yangying.tools.UpdateSharedPreferences;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageDetailActivity extends AppCompatActivity {

    private ImageView imageView =  null;
    private TextView textMemoImage = null;
    private TextView textCreateTime = null;
    private Button btnDeleteThisImage = null;
    private Button btnLikeAction = null;
    private EditTextAddHostReply editMemo = null;
    private Button sendMemo = null;
    private RecyclerView recyclerCommentsView = null;
    private TextView textCreateUser = null;
    private String imageName = null;
    private String createUser = null;
    private String createTime = null;
    private String comment = null;
    private int selfLike = 0;
    private int likes = 0;
    private int imageId = 0;
    private List<CommentsInfo> commentsInfoList = new ArrayList<CommentsInfo>();
    private FetchCommentsTask fetchCommentsTask = null;

    private void fetchComments() {
        fetchCommentsTask = new FetchCommentsTask();
        fetchCommentsTask.setOnDataFinishedListener(new FetchCommentsTask.OnDataFinishedListener() {
            @Override
            public void onDataSuccessfully(Object data) {
                LogUtils.v("return comments info", data.toString());
                commentsInfoList = (List<CommentsInfo>)data;
                CommentsRecyclerAdapter commentsRecyclerAdapter = new CommentsRecyclerAdapter(commentsInfoList, getApplicationContext());
                commentsRecyclerAdapter.setOnItemClickedListener(new CommentsRecyclerAdapter.OnItemClickedListener() {
                    @Override
                    public void OnItemClicked(String replyToUsername, int reply_comment_id, int host_comment) {
                        editMemo.setHint(UpdateSharedPreferences.getStringValue("username") + " 回复：" + replyToUsername);
                        editMemo.setHostComment(host_comment);
                        editMemo.setReplyToComment(reply_comment_id);
                    }
                });
                recyclerCommentsView.setAdapter(commentsRecyclerAdapter);
                commentsRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDataFailed() {
                Toast.makeText(getApplicationContext(), "加载失败！", Toast.LENGTH_SHORT).show();
            }
        });
        fetchCommentsTask.execute(imageId);
    }

    void init() {
        Intent imageDetailActivityIntent = getIntent();
        createUser = imageDetailActivityIntent.getStringExtra("createUser");
        createTime = imageDetailActivityIntent.getStringExtra("createTime");
        comment = imageDetailActivityIntent.getStringExtra("memo");
        imageId = imageDetailActivityIntent.getIntExtra("imageId", 0);
        likes = imageDetailActivityIntent.getIntExtra("likes", 0);
        selfLike = imageDetailActivityIntent.getIntExtra("selfLike", 0);
        imageName = imageDetailActivityIntent.getStringExtra("imageName");

        imageView = (ImageView) findViewById(R.id.image_detail_image_info);
        if(Tools.judgeLocalImages(imageName)){
            Glide.with(getApplication()).load(new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DCIM) + "", imageName)).into(imageView);
        }else {
            Glide.with(getApplication()).load(ReadProperties.getStringProperties("hostname") +
                    ReadProperties.getStringProperties("downloadRecentImages") + "/" +
                    imageName).into(imageView);
        }

        textMemoImage = (TextView) findViewById(R.id.text_comment_image_detail);
        textMemoImage.setText(comment);

        textCreateTime = (TextView) findViewById(R.id.text_create_time_image_detail);
        textCreateTime.setText(createTime);

        btnDeleteThisImage = (Button) findViewById(R.id.btn_delete_image_detail);
        if(!createUser.equals(UpdateSharedPreferences.getStringValue("username"))) btnDeleteThisImage.setVisibility(View.GONE);

        btnLikeAction = (Button) findViewById(R.id.btn_like_image_detail);
        btnLikeAction.setText(likes + " Likes");
        if(selfLike == 1) {
            Drawable leftDrawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.btn_liked);
            leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
            btnLikeAction.setCompoundDrawables(leftDrawable, null, null, null );
        }

        editMemo = (EditTextAddHostReply) findViewById(R.id.edit_image_comments);
        sendMemo = (Button) findViewById(R.id.btn_send_image_comment);
        recyclerCommentsView = (RecyclerView) findViewById(R.id.recycler_image_comments);

        textCreateUser = (TextView) findViewById(R.id.text_create_user_image_detail);
        textCreateUser.setText(createUser);

        recyclerCommentsView = (RecyclerView)findViewById(R.id.recycler_image_comments);
        RecyclerView.LayoutManager layoutManager = new AutoLinearLayout(getApplicationContext());
        //layoutManager.setAutoMeasureEnabled(true);
        recyclerCommentsView.setLayoutManager(layoutManager);
        CommentsRecyclerAdapter commentsRecyclerAdapter = new CommentsRecyclerAdapter(commentsInfoList, getApplicationContext());
        recyclerCommentsView.setAdapter(commentsRecyclerAdapter);
        recyclerCommentsView.setNestedScrollingEnabled(false);
    }

    private void bindEvent() {
        sendMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String memo = editMemo.getText().toString();
            //String userId = UpdateSharedPreferences.getStringValue("username");
            Date curDate =  new Date(System.currentTimeMillis());
            SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss");
            String strDate = formatter.format(curDate);
            CommentsInfo commentsInfo= new CommentsInfo(UpdateSharedPreferences.getStringValue("username"), imageId, editMemo.getReplyToComment(), memo, strDate,
                    editMemo.getHostComment() == 0 ? editMemo.getReplyToComment() : editMemo.getHostComment());
            SubmitCommentsTask submitCommentsTask = new SubmitCommentsTask();
            submitCommentsTask.setOnDataFinishedListener(new SubmitCommentsTask.OnDataFinishedListener() {
                @Override
                public void onDataSuccessfully() {
                    editMemo.setText("");
                    fetchComments();
                }

                @Override
                public void onDataFailed() {

                }
            });
            submitCommentsTask.execute(commentsInfo);
            }
        });

        btnDeleteThisImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateImageInfoTask deleteThisImage = new UpdateImageInfoTask();
                deleteThisImage.setOnDataFinishedListener(new UpdateImageInfoTask.OnDataFinishedListener() {
                    @Override
                    public void onDataSuccessfully() {
                        finish();
                    }

                    @Override
                    public void onDataFailed() {

                    }
                });
                deleteThisImage.execute(imageId, UpdateImageInfoTask.REMOVE_IMAGE);
            }
        });

        btnLikeAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selfLike == 1) {
                    UpdateImageInfoTask removeLikeTask = new UpdateImageInfoTask();
                    removeLikeTask.setOnDataFinishedListener(new UpdateImageInfoTask.OnDataFinishedListener() {
                        @Override
                        public void onDataSuccessfully() {
                            Drawable leftDrawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.btn_like);
                            leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
                            btnLikeAction.setCompoundDrawables(leftDrawable, null, null, null );
                            btnLikeAction.setText( (likes - 1) + " Likes");
                            selfLike = 0;
                            likes = likes - 1;
                        }

                        @Override
                        public void onDataFailed() {

                        }
                    });
                    removeLikeTask.execute(imageId, UpdateImageInfoTask.TOGGLE_LIKE);
                }else {
                    UpdateImageInfoTask addLikeTask = new UpdateImageInfoTask();
                    addLikeTask.setOnDataFinishedListener(new UpdateImageInfoTask.OnDataFinishedListener() {
                        @Override
                        public void onDataSuccessfully() {
                            Drawable leftDrawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.btn_liked);
                            leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
                            btnLikeAction.setCompoundDrawables(leftDrawable, null, null, null );
                            btnLikeAction.setText( (likes + 1) + " Likes");
                            selfLike = 1;
                            likes = likes + 1;
                        }

                        @Override
                        public void onDataFailed() {

                        }
                    });
                    addLikeTask.execute(imageId, UpdateImageInfoTask.TOGGLE_LIKE);
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        init();
        fetchComments();
        bindEvent();
    }

}
