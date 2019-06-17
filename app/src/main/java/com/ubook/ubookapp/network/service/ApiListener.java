package com.ubook.ubookapp.network.service;

public interface ApiListener {
    void onResponseListener(ApiFunction apifunction, ApiStatus statusId, Object object);
}
