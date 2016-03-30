package com.snotsoft.hungrr.register;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.login.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.usernameWrapper) TextInputLayout usernameWrapper;
    @Bind(R.id.et_username) EditText usernameEditText;
    @Bind(R.id.emailWrapper) TextInputLayout emailWrapper;
    @Bind(R.id.et_email) EditText emailEditText;
    @Bind(R.id.passwordWrapper) TextInputLayout passwordWrapper;
    @Bind(R.id.et_password) EditText passwordEditText;
    @Bind(R.id.confirmWrapper) TextInputLayout confirmWrapper;
    @Bind(R.id.et_confirm_password) EditText confirmEditText;
    @Bind(R.id.spinner_gender) Spinner spinnerGender;
    @Bind(R.id.bottom_bar_already_account)
    LinearLayout rl_go_login;

    private RegisterContract.UserActionListener mActionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    private void actionRegister() {
        mActionsListener.doRegister(
                usernameWrapper.getEditText().getText().toString(),
                emailWrapper.getEditText().getText().toString(),
                passwordWrapper.getEditText().getText().toString(),
                spinnerGender.getSelectedItem().toString()
        );
    }

    private void actionGoLogin(){
        sendTo(LoginActivity.class);
    }

    private void sendTo(Class clas){
        Intent intent = new Intent().setClass(getApplication(), clas);
        finish();
        startActivity(intent);
    }
}
