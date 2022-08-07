package com.example.kg_cai.helpers;

public class TextModel {

    String Title, img, text;

    public TextModel() {
    }

    public TextModel(String title, String img, String text) {
        this.Title = title;
        this.img = img;
        this.text = text;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
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
