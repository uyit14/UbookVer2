package com.ubook.ubookapp.components.dialog;

public class DialogResultItem {
    private Integer dialogId;
    private ResultMessageDialog resultMessageDialog;
    private Object object;

    public Integer getDialogId() {
        return dialogId;
    }

    public void setDialogId(Integer dialogId) {
        this.dialogId = dialogId;
    }

    public ResultMessageDialog getResultMessageDialog() {
        return resultMessageDialog;
    }

    public void setResultMessageDialog(ResultMessageDialog resultMessageDialog) {
        this.resultMessageDialog = resultMessageDialog;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public DialogResultItem(Integer dialogId, ResultMessageDialog resultMessageDialog, Object object) {
        this.dialogId = dialogId;
        this.resultMessageDialog = resultMessageDialog;
        this.object = object;
    }
}
