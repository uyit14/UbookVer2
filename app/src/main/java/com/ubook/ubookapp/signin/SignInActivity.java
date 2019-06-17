package com.ubook.ubookapp.signin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.ubook.ubookapp.R;
import com.ubook.ubookapp.verifysmscode.VerifyActivity;
import com.ubook.ubookapp.base.BaseActivity;
import com.ubook.ubookapp.network.model.UserInfor;
import com.ubook.ubookapp.network.model.VerifyToken;
import com.ubook.ubookapp.utils.Constants;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity implements View.OnClickListener, ISignIn.View {

    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;
    @BindView(R.id.btNext)
    Button btNext;

    private SignInPresenter mSignInPresenter;
    String phoneNumber;

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        mSignInPresenter = new SignInPresenter(this);
        VerifyPhoneNumber();
    }

    private void VerifyPhoneNumber() {
        edtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtPhoneNumber.getText().toString().length() > 9) {
                    btNext.setEnabled(true);
                    btNext.setBackgroundColor(getResources().getColor(R.color.bgNextActive));
                } else {
                    btNext.setEnabled(false);
                    btNext.setBackgroundColor(getResources().getColor(R.color.bgNextInActive));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.btNext)
    public void onNext() {
        phoneNumber = edtPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            edtPhoneNumber.setError(getString(R.string.phone_number_must_not_be_blank));
            return;
        }
        if (phoneNumber.charAt(0) == '0') {
            phoneNumber = phoneNumber.substring(1);
        }
        String uniqueId = UUID.randomUUID().toString();
        String email = uniqueId + "uy@gmail.com";
        mSignInPresenter.requestSignIn(phoneNumber, "84", email, 1, "vn");


    }

    @Override
    public void SignInSuccess(UserInfor userInfo) {
        if(userInfo!=null){
            Intent intent = new Intent(this, VerifyActivity.class);
            intent.putExtra("PHONE_NUMBER", phoneNumber);
            startActivity(intent);
        }
    }

    @Override
    public void SignInFail(String messageError) {
        if(messageError.equals(Constants.CHANGE_TO_LOGIN)){
            mSignInPresenter.requestSignInPhone(phoneNumber, "84");
        }else{
            showDialogError(541, messageError);
        }
    }

    @Override
    public void SignInPhoneSuccess(VerifyToken verifyToken) {
        if(verifyToken!=null){
            Intent intent = new Intent(this, VerifyActivity.class);
            intent.putExtra("PHONE_NUMBER", phoneNumber);
            intent.putExtra("VERIFY_TOKEN", verifyToken.getVerifyToken());
            startActivity(intent);
        }
    }

    @Override
    public void SignInPhoneFail(String messageError) {
        showDialogError(541, messageError);
    }

    @Override
    public void onClick(View v) {

    }
}
