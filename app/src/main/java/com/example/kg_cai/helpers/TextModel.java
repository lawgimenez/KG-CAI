package com.example.kg_cai.helpers;

public class TextModel {

    String title, img, text;

    public TextModel() {
    }

    public TextModel(String title, String img, String text) {
        this.title = title;
        this.img = img;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
