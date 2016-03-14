package com.snotsoft.hungrr;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.facebook.FacebookSdk;
import com.snotsoft.hungrr.login.LoginActivity;
import com.snotsoft.hungrr.register.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn_login;
    private Button btn_register;
    private TextView tx_appname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initUI();
        setTypeface();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionLogin();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionRegister();
            }
        });

    }

    private  void actionLogin(){
        start(LoginActivity.class);
    }

    private void actionRegister(){
        start(RegisterActivity.class);
    }

    private void setTypeface(){
        Typeface robotoBoldCondensedItalic = Typeface.createFromAsset(getAssets(), "fonts/lobster.otf");
        if(tx_appname != null){
            tx_appname.setTypeface(robotoBoldCondensedItalic);
        }
    }

    private void start(Class clas){
        Intent intent = new Intent().setClass(getApplication(), clas);
        startActivity(intent);
    }


    private void initUI(){
        btn_login = (Button) findViewById(R.id.login);
        btn_register = (Button) findViewById(R.id.register);
        tx_appname = (TextView) findViewById(R.id.appname);
    }
}
