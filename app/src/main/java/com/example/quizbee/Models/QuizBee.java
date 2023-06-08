package com.example.quizbee.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuizBee {

    @SerializedName("_id")
    private String id;
    @SerializedName("module")
    private Model model;
    @SerializedName("questions")
    private ArrayList<Question> questions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}

