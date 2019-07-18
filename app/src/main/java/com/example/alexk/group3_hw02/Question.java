package com.example.alexk.group3_hw02;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by AlexK on 3/5/2018.
 */

public class Question implements Serializable {

    String text;
    ArrayList<String> choices;
    int answer;
    String urlToImage;

    public Question() {
    }

    public Question(String text, ArrayList<String> choices, int answer, String urlToImage) {
        this.text = text;
        this.choices = choices;
        this.answer = answer;
        this.urlToImage = urlToImage;
    }

    @Override
    public String toString() {
        return "Question{" +
                "text='" + text + '\'' +
                ", choices=" + choices +
                ", answer=" + answer +
                ", urlToImage='" + urlToImage + '\'' +
                '}';
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
}
