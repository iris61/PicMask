package com.lovecoding.yangying.camera;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lovecoding.yangying.picmask.MainActivity;
import com.lovecoding.yangying.picmask.R;
import com.lovecoding.yangying.tools.BaseAcitivity;

import java.io.File;

public class EditPicCommentActivity extends BaseAcitivity {

    private EditText editComment = null;
    private TextView textCountComment = null;
    private final static int MAX_COMMENT_COUNT = 200;
    private Button btnCancelComment = null;
    private Button btnFinishComment = null;
    private String filterImagePath = null;
    private static ProgressDialog mProgressDialog = null;

    private void init(){
        editComment = (EditText) findViewById(R.id.edit_pic_comment);
        textCountComment = (TextView) findViewById(R.id.text_pic_comment_count);
        btnCancelComment = (Button) findViewById(R.id.btn_comment_cancel);
        btnFinishComment = (Button) findViewById(R.id.btn_comment_finish);

        filterImagePath = getIntent().getStringExtra("filterImagePath");
        editComment.setFocusable(true);
        editComment.setFocusableInTouchMode(true);
        editComment.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void bindEvent(){
        editComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String comment = editComment.getText().toString();
                textCountComment.setText(MAX_COMMENT_COUNT - comment.length() + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnCancelComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File imageFile = new File(filterImagePath);
                if(imageFile.exists()) imageFile.delete();
                finish();
            }
        });

        btnFinishComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = new ProgressDialog(EditPicCommentActivity.this);
                // 设置mProgressDialog风格
                mProgressDialog.setProgress(ProgressDialog.STYLE_SPINNER);//圆形
                mProgressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);//水平
                // 设置mProgressDialog标题
                mProgressDialog.setTitle("上传中");
                // 设置mProgressDialog提示
                mProgressDialog.setMessage("图片上传中，请稍候");
                // 设置mProgressDialog进度条的图标
                //mProgressDialog.setIcon(R.drawable.flag);
                // 设置mProgressDialog的进度条是否不明确
                //不滚动时，当前值在最小和最大值之间移动，一般在进行一些无法确定操作时间的任务时作为提示，明确时就是根据你的进度可以设置现在的进度值
                mProgressDialog.setIndeterminate(false);
                //mProgressDialog.setProgress(m_count++);
                // 是否可以按回退键取消
                mProgressDialog.setCancelable(false);
                // 设置mProgressDialog的一个Button
//                mProgressDialog.setButton(ProgressDialog.BUTTON_POSITIVE,"确定", new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        dialog.cancel();
//                    }
//                });
                // 显示
                mProgressDialog.show();
                new ImageUploadTask().execute(filterImagePath, editComment.getText().toString());
            }
        });
    }

    public static void dismissProgressDialog(){
        mProgressDialog.dismiss();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pic_comment);

        init();
        bindEvent();
    }

}
