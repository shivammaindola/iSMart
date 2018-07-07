package com.example.lenovo.ismart;

public class Fund {
    private String Title;
    private String Image;

    public Fund() {
    }

    public Fund(String title, String image) {
        Title = title;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}