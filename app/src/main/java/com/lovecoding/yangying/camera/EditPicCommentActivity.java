package com.lovecoding.yangying.camera;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                finish();
            }
        });

        btnFinishComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ImageUploadUtils.uploadFile(new File(filterImagePath));
                    }
                }).start();
                //finish();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pic_comment);

        init();
        bindEvent();
    }

}
