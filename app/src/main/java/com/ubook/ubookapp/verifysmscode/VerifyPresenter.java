package com.ubook.ubookapp.verifysmscode;

import com.google.gson.JsonObject;
import com.ubook.ubookapp.base.BaseApplication;
import com.ubook.ubookapp.base.BasePresenter;
import com.ubook.ubookapp.base.BaseResponse;
import com.ubook.ubookapp.network.model.VerifySmsCode;
import com.ubook.ubookapp.network.service.ApiFunction;
import com.ubook.ubookapp.network.service.ApiStatus;
import com.ubook.ubookapp.utils.InternetConnection;

public class VerifyPresenter extends BasePresenter<IVerify.View> implements IVerify.Presenter {
    public VerifyPresenter(IVerify.View mView) {
        super(mView);
    }

    @Override
    public void onResponseListener(ApiFunction apifunction, ApiStatus statusId, Object object) {
        if (!InternetConnection.getInstance().isOnline(BaseApplication.getInstance())) {
            getmView().networkConnectError();
            return;
        }
        switch (statusId){
            case CALL_API_RESULT_SUCCESS:
                switch (apifunction) {
                    case VERIFY_SMS_CODE:
                        BaseResponse<VerifySmsCode> dataVerify = (BaseResponse<VerifySmsCode>) object;
                        getmView().hideDialogLoading();
                        getmView().verifySuccess(dataVerify.getData());
                        break;
                }
                break;


            case CALL_API_RESULT_FAILURE: {
                switch (apifunction) {
                    case VERIFY_SMS_CODE:
                        getmView().hideDialogLoading();
                        getmView().verifyFail(object.toString());
                        break;
                }
                break;
            }
        }
    }

    @Override
    public void requestVerifySmsCode(String verifyToken, String smsCode) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("verify_token", verifyToken);
        jsonObject.addProperty("sms_verification_code", smsCode);
        if (!InternetConnection.getInstance().isOnline(BaseApplication.getInstance())) {
            getmView().networkConnectError();
            return;
        }
        getmView().showDialogLoading();
        mApiMethod.verifySmsCode("2.5.2", jsonObject);
    }
}
