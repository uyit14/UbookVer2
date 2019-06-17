package com.ubook.ubookapp.network.model;

import com.google.gson.annotations.SerializedName;

public class VerifyToken {
    @SerializedName("verify_token")
    private String verifyToken;

    public VerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }
}
