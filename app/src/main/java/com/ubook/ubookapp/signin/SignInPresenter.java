package com.ubook.ubookapp.signin;

import com.google.gson.JsonObject;
import com.ubook.ubookapp.base.BaseApplication;
import com.ubook.ubookapp.base.BasePresenter;
import com.ubook.ubookapp.base.BaseResponse;
import com.ubook.ubookapp.network.model.UserInfor;
import com.ubook.ubookapp.network.model.VerifyToken;
import com.ubook.ubookapp.network.service.ApiFunction;
import com.ubook.ubookapp.network.service.ApiStatus;
import com.ubook.ubookapp.utils.Constants;
import com.ubook.ubookapp.utils.InternetConnection;

public class SignInPresenter extends BasePresenter<ISignIn.View> implements ISignIn.Presenter {
    public SignInPresenter(ISignIn.View mView) {
        super(mView);
    }

    @Override
    public void requestSignIn(String phone, String areaCode, String email, int countryId, String languageCode) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phone", phone);
        jsonObject.addProperty("area_code", areaCode);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("country_id", countryId);
        jsonObject.addProperty(Constants.LANGUAGE_CODE, languageCode);

        if (!InternetConnection.getInstance().isOnline(BaseApplication.getInstance())) {
            getmView().networkConnectError();
            return;
        }
        getmView().showDialogLoading();
        mApiMethod.signUp("2.5.2", jsonObject);
    }

    @Override
    public void requestSignInPhone(String phone, String areaCode) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.PHONE, phone);
        jsonObject.addProperty(Constants.AREA_CODE, areaCode);
        if (!InternetConnection.getInstance().isOnline(BaseApplication.getInstance())) {
            getmView().networkConnectError();
            return;
        }
        getmView().showDialogLoading();
        mApiMethod.signIn("2.5.2", jsonObject);
    }

    @Override
    public void onResponseListener(ApiFunction apifunction, ApiStatus statusId, Object object) {
        if (!InternetConnection.getInstance().isOnline(BaseApplication.getInstance())) {
            getmView().networkConnectError();
            return;
        }
        switch (statusId) {
            case CALL_API_RESULT_SUCCESS:
                switch (apifunction) {
                    case SIGN_UP:
                        BaseResponse<UserInfor> dataSignUp = (BaseResponse<UserInfor>) object;
                        getmView().hideDialogLoading();
                        getmView().SignInSuccess(dataSignUp.getData());
                        break;
                    case SIGN_IN:
                        BaseResponse<VerifyToken> verifyToken = (BaseResponse<VerifyToken>) object;
                        getmView().hideDialogLoading();
                        getmView().SignInPhoneSuccess(verifyToken.getData());
                        break;
                }
                break;


            case CALL_API_RESULT_FAILURE: {
                switch (apifunction) {
                    case SIGN_UP:
                        getmView().hideDialogLoading();
                        getmView().SignInFail(object.toString());
                        break;
                    case SIGN_IN:
                        getmView().hideDialogLoading();
                        getmView().SignInPhoneFail(object.toString());
                }
                break;
            }
        }
    }
}
