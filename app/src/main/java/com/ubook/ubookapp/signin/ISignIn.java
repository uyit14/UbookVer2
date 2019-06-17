package com.ubook.ubookapp.signin;

import com.ubook.ubookapp.base.BaseView;
import com.ubook.ubookapp.network.model.UserInfor;
import com.ubook.ubookapp.network.model.VerifyToken;

public interface ISignIn {
    interface View extends BaseView{
        void SignInSuccess(UserInfor userInfo);
        void SignInFail(String messageError);
        void SignInPhoneSuccess(VerifyToken verifyToken);
        void SignInPhoneFail(String messageError);
    }

    interface Presenter{
        void requestSignIn(String phone, String areaCode, String email, int countryId, String languageCode);
        void requestSignInPhone(String phone, String areaCode);
    }
}
