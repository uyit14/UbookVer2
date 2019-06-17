package com.ubook.ubookapp.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ubook.ubookapp.R;
import com.ubook.ubookapp.components.dialog.DialogResultItem;
import com.ubook.ubookapp.components.dialog.MessageDialogHelper;
import com.ubook.ubookapp.components.dialog.MessageDialogManger;
import com.ubook.ubookapp.components.dialog.OnClickDialogListener;
import com.ubook.ubookapp.components.dialog.TypeMessageDialog;
import com.ubook.ubookapp.utils.InternetConnection;

public class BaseActivity extends AppCompatActivity implements OnClickDialogListener, BaseView {
    protected final int DIALOG_LOADING = 540;
    protected final int DIALOG_ERROR = 541;
    protected final int DIALOG_SUCCESS = 542;
    protected MessageDialogManger mMessageDialogManger;
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessageDialogManger = new MessageDialogManger();
    }

    protected void showDialogLoading(Integer dialogId, String content) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGEDIALOG_TYPE_LOADING)
                .setDialogId(dialogId)
                .setTitleDialog(getResources().getString(R.string.dialog_title))
                .setContentDialog(content)
                .setOnClickDialogListener(this)
                .build(this));
        mMessageDialogManger.onShow();
    }

    protected void showDialogError(Integer dialogId, String content) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGEDIALOG_TYPE_ERROR)
                .setDialogId(dialogId)
                .setContentDialog(content)
                .setOnClickDialogListener(this)
                .build(this));
        mMessageDialogManger.onShow();
    }

    protected void showDialogSuccess(Integer dialogId, String content) {
        mMessageDialogManger.onCreate(new MessageDialogHelper.MessageDialogBuilder()
                .setTypeMessageDialog(TypeMessageDialog.MESSAGEDIALOG_TYPE_SUCCESS)
                .setDialogId(dialogId)
                .setTitleDialog(getResources().getString(R.string.dialog_title))
                .setContentDialog(content)
                .setOnClickDialogListener(this)
                .build(this));
        mMessageDialogManger.onShow();
    }


    @Override
    public void showDialogError(int resId) {
        showDialogError(DIALOG_ERROR, getResources().getString(resId));
    }

    @Override
    public void showDialogLoading() {
        showDialogLoading(DIALOG_LOADING, "Loading");
    }

    @Override
    public void hideDialogLoading() {
        mMessageDialogManger.onDimiss();
    }

    @Override
    public void showProgressDialog() {
        showProgress();
    }

    @Override
    public void hideProgressDialog() {
        hideProgress();
    }

    @Override
    public boolean isNetworkConnect() {
        if (InternetConnection.getInstance().isOnline(this)) {
            return true;
        }
        return false;
    }

    @Override
    public void networkConnectError() {
        showDialogError(DIALOG_ERROR, getResources().getString(R.string.error_internet));
    }

    protected void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onClickDialog(DialogResultItem dialogResultItem) {
        switch (dialogResultItem.getDialogId()) {
            case DIALOG_ERROR:
                mMessageDialogManger.onDimiss();
                break;
            case DIALOG_SUCCESS:
                mMessageDialogManger.onDimiss();
                break;
        }
    }
}
