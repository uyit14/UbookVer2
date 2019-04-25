package com.ubook.ubookapp;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.tlPhoneNumber)
    TextInputLayout tlPhoneNumber;
    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;
    @BindView(R.id.llNext)
    LinearLayout llNext;

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
        VerifyPhoneNumber();
    }

    private void VerifyPhoneNumber(){
        edtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edtPhoneNumber.getText().toString().length()>9){
                    llNext.setClickable(true);
                    llNext.setBackgroundColor(getResources().getColor(R.color.bgNextActive));
                }else{
                    llNext.setClickable(false);
                    llNext.setBackgroundColor(getResources().getColor(R.color.bgNextInActive));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.llNext)
    public void onNext(){
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        Intent intent = new Intent(this, VerifyActivity.class);
        intent.putExtra("PHONE_NUMBER", phoneNumber);
        startActivity(intent);
    }
}
