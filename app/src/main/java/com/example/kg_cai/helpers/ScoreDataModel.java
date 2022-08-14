package com.example.kg_cai.helpers;

public class ScoreDataModel {

    String name,image;
    Long overallScore;

    public ScoreDataModel() {
    }

    public ScoreDataModel(String name, String image, Long overallScore) {
        this.name = name;
        this.image = image;
        this.overallScore = overallScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Long overallScore) {
        this.overallScore = overallScore;
    }
}
