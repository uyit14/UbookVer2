package com.ubook.ubookapp.base;

public interface BaseView {
    boolean isNetworkConnect();
    void networkConnectError();
    void showDialogLoading();
    void hideDialogLoading();
    void showProgressDialog();
    void hideProgressDialog();
    void showDialogError(int resId);
}
