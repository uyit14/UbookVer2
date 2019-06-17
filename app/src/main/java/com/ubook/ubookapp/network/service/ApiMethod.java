package com.ubook.ubookapp.network.service;

import com.google.gson.JsonObject;
import com.ubook.ubookapp.R;
import com.ubook.ubookapp.base.BaseResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiMethod {
    private ApiListener mApiListener;
    int status = 500;
    String messageError = "";

    public void setApiListener(ApiListener mApiListener) {
        this.mApiListener = mApiListener;
    }

    /*
    Danh sách các hàm gọi api
     */
    public void signUp(String version, JsonObject object) {
        ApiService apiInterfaces = ApiClient.getClient().create(ApiService.class);
        handleResponseObject(apiInterfaces.signUp(version, object), ApiFunction.SIGN_UP);
    }

    public void signIn(String version, JsonObject object){
        ApiService apiInterfaces = ApiClient.getClient().create(ApiService.class);
        handleResponseObject(apiInterfaces.signIn(version, object), ApiFunction.SIGN_IN);
    }

    public void verifySmsCode(String version, JsonObject object){
        ApiService apiInterfaces = ApiClient.getClient().create(ApiService.class);
        handleResponseObject(apiInterfaces.verifySmsCode(version, object), ApiFunction.VERIFY_SMS_CODE);
    }

    public void shopsByLocation(String authorization, String version, JsonObject object ){
        ApiService apiInterfaces = ApiClient.getClient().create(ApiService.class);
        handleResponseObject(apiInterfaces.shopsByLocation(authorization,version, object), ApiFunction.SHOP_BY_LOCATION);
    }

    /*
    Kết thúc danh sách hàm
     */

    /*
    response trả về 1 object, thường áp dụng cho response khi login, signup...
     */
    private <T> void handleResponseObject(Call<BaseResponse<T>> responseCall, final ApiFunction apiFunction) {
        responseCall.enqueue(new Callback<BaseResponse<T>>() {
            @Override
            public void onResponse(Call<BaseResponse<T>> call, Response<BaseResponse<T>> response) {
                if (response != null) {
                    switch (response.raw().code()) {
                        case 200:
                            mApiListener.onResponseListener(apiFunction, ApiStatus.CALL_API_RESULT_SUCCESS, response.body());
                            break;
                        default:
                            try {
                                String errorObject = response.errorBody().string().toString();
                                JSONObject jsonObject = new JSONObject(errorObject);
                                status = jsonObject.getInt("code");
                                messageError = jsonObject.getString("message");
                                JSONObject dataJson = new JSONObject(jsonObject.getJSONObject("data").toString());

                                if (dataJson.length() != 0) {
                                    Iterator<String> keys = dataJson.keys();
                                    if (keys.hasNext()) {
                                        String key = (String) keys.next();
                                        JSONArray jsonArray = new JSONArray(dataJson.getString(key).toString());// First key in your json object
                                        messageError = jsonArray.get(0).toString();
                                    }
                                } else {
                                    messageError = jsonObject.getString("message");
                                }
                                if (messageError.isEmpty())
                                    messageError = response.raw().message();
                                mApiListener.onResponseListener(apiFunction, ApiStatus.CALL_API_RESULT_FAILURE, messageError);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<T>> call, Throwable t) {
                mApiListener.onResponseListener(apiFunction, ApiStatus.CALL_API_RESULT_TIMEOUT, t.getMessage());
            }
        });
    }

    /*
    xử lý lỗi từ server trả về
     */
    private void notifyErrorFromServer(ApiFunction apiFunction, ApiStatus apiStatus, int errorCode) {
        switch (errorCode) {
            case 401:
                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_401);
                break;
            default:
                mApiListener.onResponseListener(apiFunction, apiStatus, R.string.error_404);
        }
    }
}
