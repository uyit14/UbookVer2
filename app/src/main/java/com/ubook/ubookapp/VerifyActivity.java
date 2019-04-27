package com.ubook.ubookapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyActivity extends AppCompatActivity {
    @BindView(R.id.edt01)
    EditText edt01;
    @BindView(R.id.edt02)
    EditText edt02;
    @BindView(R.id.edt03)
    EditText edt03;
    @BindView(R.id.edt04)
    EditText edt04;
    @BindView(R.id.btSubmit)
    Button btSubmit;
    @BindView(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;

    //
    private EditText[] editTexts;


    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        ButterKnife.bind(this);
        //
        String phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
        if (phoneNumber != null) {
            tvPhoneNumber.setText(phoneNumber);
        }
        HandleEnterCode();
    }

    //
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void updateNextValidation() {
        boolean isEnable = !TextUtils.isEmpty(edt01.getEditableText().toString()) && !TextUtils.isEmpty(edt02.getEditableText().toString()) &&
                !TextUtils.isEmpty(edt03.getEditableText().toString()) && !TextUtils.isEmpty(edt04.getEditableText().toString());
        if (isEnable) {
            //Enable btn Submit
            btSubmit.setBackground(getResources().getDrawable(R.drawable.custom_submit_button_active));
            btSubmit.setTextColor(getResources().getColor(R.color.white));
            btSubmit.setEnabled(isEnable);
        } else {
            //Enable btn Submit
            btSubmit.setBackground(getResources().getDrawable(R.drawable.custom_submit_button));
            btSubmit.setTextColor(getResources().getColor(R.color.gray));
            btSubmit.setEnabled(isEnable);
        }
    }

    //set up event enter sms code
    private void HandleEnterCode() {
        //
        editTexts = new EditText[]{edt01, edt02, edt03, edt04};

        edt01.addTextChangedListener(new PinTextWatcher(0));
        edt02.addTextChangedListener(new PinTextWatcher(1));
        edt03.addTextChangedListener(new PinTextWatcher(2));
        edt04.addTextChangedListener(new PinTextWatcher(3));

        edt01.setOnKeyListener(new PinOnKeyListener(0));
        edt02.setOnKeyListener(new PinOnKeyListener(1));
        edt03.setOnKeyListener(new PinOnKeyListener(2));
        edt04.setOnKeyListener(new PinOnKeyListener(3));

        edt01.setOnFocusChangeListener(new CustomFocusChangeListener(edt01, this));
        edt02.setOnFocusChangeListener(new CustomFocusChangeListener(edt02, this));
        edt03.setOnFocusChangeListener(new CustomFocusChangeListener(edt03, this));
        edt04.setOnFocusChangeListener(new CustomFocusChangeListener(edt04, this));
    }

    //
    public class PinOnKeyListener implements View.OnKeyListener {

        private int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }
    }

    //
    public class PinTextWatcher implements TextWatcher {

        private int currentIndex;
        private boolean isFirst, isLast;
        private String newTypedString;

        public PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;
            if (currentIndex == 0) {
                isFirst = true;
            } else if (currentIndex == editTexts.length - 1) {
                isLast = true;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            newTypedString = charSequence.subSequence(start, start + count).toString().trim();
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void afterTextChanged(Editable editable) {
            String text = newTypedString;
            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0));
            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1) {
                moveToNext();
            } else if (text.length() == 0) {
                moveToPrevious();
            }
            updateNextValidation();
        }

        private void moveToNext() {
            if (!isLast) {
                editTexts[currentIndex + 1].requestFocus();
            }

            if (isAllEditTextsFilled() && isLast) {
                editTexts[currentIndex].clearFocus();
                UbookHelper.hideSoftKeyboard(VerifyActivity.this);
            }
        }

        private void moveToPrevious() {
            if (!isFirst) {
                editTexts[currentIndex - 1].requestFocus();
            }
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            return true;
        }
    }

    //onClick event
    @OnClick(R.id.btSubmit)
    public void onSubmit() {
        Intent intent = new Intent(this, MainActivity.class);
        BaseApplication.getInstance().sharedPreferencesUtils.setAccessToken("access_token");
        startActivity(intent);
    }
}
