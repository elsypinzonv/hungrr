package com.snotsoft.hungrr.view.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.login.LoginActivity;
import com.snotsoft.hungrr.register.RegisterActivity;
import com.snotsoft.hungrr.utils.TextViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DispatchActivity extends AppCompatActivity {

    @Bind(R.id.btn_login) Button btnLogin;
    @Bind(R.id.btn_register) Button btnRegister;
    @Bind(R.id.tv_app_name) TextView appNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
        ButterKnife.bind(this);
        TextViewUtils.setLobsterTypeface(this, appNameTextView);
    }

    @OnClick(R.id.btn_login) public void actionLogin(){
        sendTo(LoginActivity.class);
    }

    @OnClick(R.id.btn_register) public void actionRegister(){
        sendTo(RegisterActivity.class);
    }

    private void sendTo(Class classTo){
        Intent intent = new Intent().setClass(getApplication(), classTo);
        startActivity(intent);
    }

}
