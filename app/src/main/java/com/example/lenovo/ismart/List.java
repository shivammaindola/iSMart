package com.example.lenovo.ismart;

public class List {
    private String Name;
    private String List_image;
    private String Text;

    public List() {

    }

    public List(String name, String list_image, String text) {
        Name = name;
        List_image = list_image;
        Text = text;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getList_image() {
        return List_image;
    }

    public void setList_image(String list_image) {
        List_image = list_image;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
