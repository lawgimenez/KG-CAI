package com.example.kg_cai;

import java.util.List;

public class Question {

    String question, optionA, OptionB, OptionC, OptionD;
    int answer;


    public Question(String question, String optionA, String optionB, String optionC, String optionD, int answer) {
        this.question = question;
        this.optionA = optionA;
        OptionB = optionB;
        OptionC = optionC;
        OptionD = optionD;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return OptionB;
    }

    public void setOptionB(String optionB) {
        OptionB = optionB;
    }

    public String getOptionC() {
        return OptionC;
    }

    public void setOptionC(String optionC) {
        OptionC = optionC;
    }

    public String getOptionD() {
        return OptionD;
    }

    public void setOptionD(String optionD) {
        OptionD = optionD;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
