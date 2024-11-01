package com.qrprototype.model;

import java.util.List;

public class Question {
    private String text;
    private String type;
    private List<Answer> choices;

    public Question(String text, String type, List<Answer> choices) {
        this.text = text;
        this.type = type;
        this.choices = choices;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Answer> getChoices() {
        return choices;
    }

    public void setChoices(List<Answer> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return text;
    }
}
