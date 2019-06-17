package com.ubook.ubookapp.base;

import com.ubook.ubookapp.network.service.ApiListener;
import com.ubook.ubookapp.network.service.ApiMethod;

public abstract class BasePresenter<T extends BaseView> implements ApiListener {
    protected ApiMethod mApiMethod;
    private T mView;

    public BasePresenter(T mView) {
        this.mView = mView;
        mApiMethod = new ApiMethod();
        mApiMethod.setApiListener(this);
    }

    public T getmView() {
        return mView;
    }

}
