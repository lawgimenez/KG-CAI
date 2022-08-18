package com.example.kg_cai.helpers;

public class LessonModel {

    int id;
    String img, audio, text;

    public LessonModel() {
    }

    public LessonModel(int id, String img, String audio, String text) {
        this.id = id;
        this.img = img;
        this.audio = audio;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
