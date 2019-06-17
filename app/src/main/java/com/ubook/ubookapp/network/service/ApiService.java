package com.ubook.ubookapp.network.service;

import com.google.gson.JsonObject;
import com.ubook.ubookapp.base.BaseResponse;
import com.ubook.ubookapp.network.model.ListShops;
import com.ubook.ubookapp.network.model.UserInfor;
import com.ubook.ubookapp.network.model.VerifySmsCode;
import com.ubook.ubookapp.network.model.VerifyToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/v1/api/signupclient/")
    Call<BaseResponse<UserInfor>> signUp(@Header("Version") String version,
                                         @Body JsonObject object);
    @POST("v1/api/phonelogin/")
    Call<BaseResponse<VerifyToken>> signIn(@Header("Version") String version,
                                                   @Body JsonObject jsonObjectPhoneLogin);
    @POST("v1/api/verifysmscode")
    Call<BaseResponse<VerifySmsCode>> verifySmsCode(@Header("Version") String version,
                                                    @Body JsonObject jsonObject);
    @POST("/v1/api/shopsbylocation/")
    Call<BaseResponse<ListShops>> shopsByLocation(@Header("Authorization") String authorization,
                                                  @Header("Version") String version,
                                                  @Body JsonObject jsonObject);
}
