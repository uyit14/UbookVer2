package com.ubook.ubookapp.components.dialog;

import java.util.Stack;

public class MessageDialogManger {
    private Stack<MessageDialogHelper> mMessageDialogHelperQueue;

    public MessageDialogManger() {
        mMessageDialogHelperQueue = new Stack<>();
    }

    public void onCreate(MessageDialogHelper messageDialogHelper) {
        mMessageDialogHelperQueue.push(messageDialogHelper);
    }

    public void onToast(MessageDialogHelper messageDialogHelper) {

    }


    public void onShow() {
        mMessageDialogHelperQueue.peek().onShow();
    }

    public void onDimiss() {
        if (mMessageDialogHelperQueue.size() > 0)
            mMessageDialogHelperQueue.pop().onDimiss();
    }

}
