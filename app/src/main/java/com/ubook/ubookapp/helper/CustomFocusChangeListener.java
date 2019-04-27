package com.ubook.ubookapp.helper;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.EditText;

import com.ubook.ubookapp.R;

public class CustomFocusChangeListener implements View.OnFocusChangeListener {

    private EditText mEditText;
    private Context mContext;

    public CustomFocusChangeListener(EditText mEditText, Context mContext) {
        this.mEditText = mEditText;
        this.mContext = mContext;
    }

    @Override
    public void onFocusChange(View view, boolean b) {

        if (mEditText.getText().length() > 0) {
            mEditText.getBackground().setColorFilter(mContext.getResources().getColor(R.color.bgNextActive), PorterDuff.Mode.SRC_ATOP);
        } else {
            mEditText.getBackground().setColorFilter(mContext.getResources().getColor(R.color.bgNextInActive), PorterDuff.Mode.SRC_ATOP);
        }
    }
}
