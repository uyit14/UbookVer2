package com.ubook.ubookapp.notify;

public class NotifyData {
    private int Image;
    private String Title;
    private String Body;

    public NotifyData(int image, String title, String body) {
        Image = image;
        Title = title;
        Body = body;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }
}
