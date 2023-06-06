package com.example.quizbee.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuizBee {

    @SerializedName("_id")
    private String id;
    @SerializedName("module")
    private Model model;
    @SerializedName("questions")
    private ArrayList<Questions> questions;
}

