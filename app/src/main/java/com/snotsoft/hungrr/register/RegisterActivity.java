package com.snotsoft.hungrr.register;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Select;
import com.snotsoft.hungrr.HunGrrApplication;
import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.login.LoginActivity;
import com.snotsoft.hungrr.utils.ActivityHelper;
import com.snotsoft.hungrr.utils.Injection;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.register_coordinator) CoordinatorLayout mCoordinator;
    @Bind(R.id.usernameWrapper) TextInputLayout usernameWrapper;
    @Bind(R.id.emailWrapper) TextInputLayout emailWrapper;
    @Bind(R.id.passwordWrapper) TextInputLayout passwordWrapper;
    @Bind(R.id.confirmWrapper) TextInputLayout confirmWrapper;
    @Bind(R.id.bottom_bar_already_account) LinearLayout alreadyHaveAccount;

    @NotEmpty(message = "Ingresa un nombre de usuario")
    @Bind(R.id.et_username) EditText usernameEditText;

    @NotEmpty(message = "Ingresa un correo")
    @Email (message =  "Correo no válido")
    @Bind(R.id.et_email) EditText emailEditText;

    @Password(min = 5, scheme = Password.Scheme.ANY, message = "Mínimo 5 caracteres")
    @Bind(R.id.et_password) EditText passwordEditText;

    @ConfirmPassword
    @Bind(R.id.et_confirm_password) EditText confirmEditText;

    @Select(defaultSelection = 3, message = "Especifíca tu género")
    @Bind(R.id.spinner_gender) Spinner spinnerGender;

    private RegisterContract.UserActionListener mActionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mActionsListener = new RegisterPresenter(
                this,
                Injection.provideRegisterInteractor(),
                Injection.provideSaripaarValidator(this)
        );

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.sendTo(RegisterActivity.this, LoginActivity.class);
            }
        });

        setupSpinner();
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

    @Override
    public void onRegisterResult(Boolean result) {
        if(result){
            ActivityHelper.sendTo(this, LoginActivity.class);
        }
    }

    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void showUsernameAlreadyTakenMessage() {
        Snackbar.make(mCoordinator, getString(R.string.username_already_taken_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showEmailAlreadyInUseMessage() {
        Snackbar.make(mCoordinator, getString(R.string.email_already_in_use_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showValidationErrors(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            Log.d(HunGrrApplication.TAG, view.getClass().getName());
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else if(view instanceof Spinner){
                //showRegisterFailedMessage("Especifíca tu género");

                ((TextView)spinnerGender.getSelectedView()).setError(message);
            } else {
                showRegisterFailedMessage(message);
            }
        }
    }

    @Override
    public void showRegisterFailedMessage(String message) {
        Snackbar.make(mCoordinator, message, Snackbar.LENGTH_LONG).show();
    }

    private void setupSpinner() {
        String[] myResArray = getResources().getStringArray(R.array.genders);
        final List<String> list = Arrays.asList(myResArray);
        final int listSize = list.size() -1;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list) {
            @Override
            public int getCount() {
                return(listSize);
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(dataAdapter);
        spinnerGender.setSelection(listSize); // Hidden item to appear in the spinner
    }
}
