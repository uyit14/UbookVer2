package com.ubook.ubookapp.network.model;

import com.google.gson.annotations.SerializedName;

public class VerifySmsCode {
    @SerializedName("id")
    private int id;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("invitation_code")
    private String invitationCode;

    @SerializedName("language")
    private Language languageResponse;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("is_register")
    private boolean isRegister;

    public boolean isRegister() {
        return isRegister;
    }

    public String getFirstName() {
        return firstName;
    }

    public Language getLanguageResponse() {
        return languageResponse;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VerifySmsCode(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
