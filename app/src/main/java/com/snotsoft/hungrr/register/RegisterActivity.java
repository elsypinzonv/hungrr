package com.snotsoft.hungrr.register;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    private Toolbar toolbar;
    private TextInputLayout edit_username;
    private TextInputLayout edit_email;
    private TextInputLayout edit_password;
    private TextInputLayout edit_repeat_password;
    private TextInputLayout edit_gender;
    private RelativeLayout rl_go_login;
    private RegisterContract.UserActionListener mActionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
        initToolbar();
        mActionsListener = new RegisterPresenter();
        rl_go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionGoLogin();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_done) {
            actionRegister();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void actionRegister(){
        mActionsListener.doRegister(
                edit_username.getEditText().getText().toString(),
                edit_email.getEditText().getText().toString(),
                edit_password.getEditText().getText().toString(),
                edit_repeat_password.getEditText().getText().toString(),
                edit_gender.getEditText().getText().toString()
        );
    }

    private void actionGoLogin(){
        start(LoginActivity.class);
    }

    private void start(Class clas){
        Intent intent = new Intent().setClass(getApplication(), clas);
        finish();
        startActivity(intent);
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initUI(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edit_username = (TextInputLayout) findViewById(R.id.username);
        edit_email = (TextInputLayout) findViewById(R.id.email);
        edit_password = (TextInputLayout) findViewById(R.id.password);
        edit_repeat_password = (TextInputLayout) findViewById(R.id.repeat_password);
        edit_gender = (TextInputLayout) findViewById(R.id.gender);
        rl_go_login = (RelativeLayout) findViewById(R.id.go_login);
    }
}
