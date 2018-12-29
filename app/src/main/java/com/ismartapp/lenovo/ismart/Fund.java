package com.ismartapp.lenovo.ismart;

public class Fund {
    private String Title;
    private String Image;
    private String sections;

    public Fund() {
    }

    public Fund(String title, String image, String sections) {
        Title = title;
        Image = image;
        this.sections = sections;
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

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }
}