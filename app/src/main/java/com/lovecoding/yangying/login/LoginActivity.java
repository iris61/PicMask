package com.lovecoding.yangying.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lovecoding.yangying.picmask.MainActivity;
import com.lovecoding.yangying.picmask.R;
import com.lovecoding.yangying.tools.BaseAcitivity;
import com.lovecoding.yangying.tools.LogUtils;
import com.lovecoding.yangying.tools.UpdateSharedPreferences;


public class LoginActivity extends BaseAcitivity {
    private Button loginButton = null;
    private Button signupButton = null;
    private String username = null;
    private String password = null;
    private EditText userEdit = null;
    private EditText pwdEdit = null;

    private void init(){
        loginButton = (Button) findViewById(R.id.btn_login);
        signupButton = (Button) findViewById(R.id.btn_signup);
        userEdit = (EditText) findViewById(R.id.edit_username);
        pwdEdit = (EditText) findViewById(R.id.edit_password);

        if(UpdateSharedPreferences.getStringValue("username").length() != 0) {
            userEdit.setText(UpdateSharedPreferences.getStringValue("username"));
            pwdEdit.setText(UpdateSharedPreferences.getStringValue("password"));
        }
    }

    public static void redirectToMainActivity(){
        LogUtils.d("loginActivity", "redirect");
        Context loginContext = BaseAcitivity.getContext();
        Intent mainIntent = new Intent(loginContext, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        loginContext.startActivity(mainIntent);
    }

    private void bindEvents(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d("LoginActivity", "click login");
                username = userEdit.getText().toString();
                password = pwdEdit.getText().toString();
                new LoginTask().execute(username, password);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        bindEvents();
    }
}
