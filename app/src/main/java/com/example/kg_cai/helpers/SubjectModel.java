package com.example.kg_cai.helpers;

public class SubjectModel {
    String title, subjImg;

    public SubjectModel() {
    }

    public SubjectModel(String title, String subjImg) {
        this.title = title;
        this.subjImg = subjImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubjImg() {
        return subjImg;
    }

    public void setSubjImg(String subjImg) {
        this.subjImg = subjImg;
    }
}
