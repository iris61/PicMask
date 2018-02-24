package com.lovecoding.yangying.login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lovecoding.yangying.picmask.R;
import com.lovecoding.yangying.tools.BaseAcitivity;

public class SignUpActivity extends BaseAcitivity {

    private EditText editUsername = null;
    private EditText editPassword = null;
    private EditText editPassAgain = null;
    private Button btnSignUpSure = null;
    private Button btnSignUpCancel = null;
    private String username = null;
    private String password = null;
    private String passagain = null;

    private void init(){
        editUsername = (EditText) findViewById(R.id.edit_signup_username);
        editPassAgain = (EditText) findViewById(R.id.edit_signup_password_again);
        editPassword = (EditText) findViewById(R.id.edit_signup_password);
        btnSignUpSure = (Button) findViewById(R.id.btn_signup_ok);
        btnSignUpCancel = (Button) findViewById(R.id.btn_signup_cancel);
    }

    private void bindEvent() {
        btnSignUpSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editUsername.getText().toString();
                password = editPassword.getText().toString();
                passagain = editPassAgain.getText().toString();

                if(password.equals(passagain)) {
                    SignUpTask signUpTask = new SignUpTask();
                    signUpTask.setSignUpFinishListener(new SignUpTask.SignUpFinishListener() {
                        @Override
                        public void OnSignUpSuccess() {
                            finish();
                        }

                        @Override
                        public void OnSignUpFailed() {

                        }
                    });
                    signUpTask.execute(username, password);
                }else {
                    Toast.makeText(getApplicationContext(), "您输入的密码不一致，请再次输入", Toast.LENGTH_SHORT ).show();
                }

            }
        });

        btnSignUpCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editPassAgain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    if(editPassword.getText().toString().length() == 0) {
                        editPassword.setFocusable(true);
                        editPassword.setFocusableInTouchMode(true);
                        editPassword.requestFocus();
                        Toast.makeText(getApplicationContext(), "请先输入密码", Toast.LENGTH_SHORT ).show();
                    }
                } else {
                    // 此处为失去焦点时的处理内容
                    if(editPassword.getText().toString().equals(editPassAgain.getText().toString())){}
                    else {
                        Toast.makeText(getApplicationContext(), "您输入的密码不一致，请再次输入", Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        bindEvent();
    }


}
