package com.snotsoft.hungrr.view.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.snotsoft.hungrr.R;

public class LogoutDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private OnConfirmationLogout mListener;
    private Dialog mDialog;
    private Button btnConfirmation;
    private Button btnCancel;

    public LogoutDialog(Context context, OnConfirmationLogout listener) {
        super(context);
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_logout);
        btnConfirmation = (Button) findViewById(R.id.btn_confirmation);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        btnConfirmation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_confirmation){
            mListener.onConfirmation();
        }else if (v.getId() == R.id.btn_cancel){
            this.dismiss();
            mListener.onCancel();
        }
    }

    public interface OnConfirmationLogout {
        void onConfirmation();
        void onCancel();
    }

}