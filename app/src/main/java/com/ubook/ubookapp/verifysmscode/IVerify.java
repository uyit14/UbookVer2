package com.ubook.ubookapp.verifysmscode;

import com.ubook.ubookapp.base.BaseView;
import com.ubook.ubookapp.network.model.VerifySmsCode;

public interface IVerify {
    interface View extends BaseView{
        void verifySuccess(VerifySmsCode verifySmsCode);
        void verifyFail(String messageError);
    }
    interface Presenter{
        void requestVerifySmsCode(String verifyToken, String smsCode);
    }
}
