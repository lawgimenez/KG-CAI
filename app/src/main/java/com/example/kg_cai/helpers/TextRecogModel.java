package com.example.kg_cai.helpers;

import android.net.Uri;

public class TextRecogModel {

    String Instructions, img, CorrectAns;

    public TextRecogModel() {
    }

    public TextRecogModel(String instructions, String img, String correctAns) {
        Instructions = instructions;
        this.img = img;
        CorrectAns = correctAns;
    }

    public String getInstructions() {
        return Instructions;
    }

    public void setInstructions(String instructions) {
        Instructions = instructions;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCorrectAns() {
        return CorrectAns;
    }

    public void setCorrectAns(String correctAns) {
        CorrectAns = correctAns;
    }
}
