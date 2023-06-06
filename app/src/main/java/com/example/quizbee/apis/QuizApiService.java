package com.example.quizbee.apis;

import com.example.quizbee.Models.QuizBee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuizApiService {

    @GET("api/479dd07f8c1d482e9219f7dcb48e25f4/renuTodo")
    Call<List<QuizBee>> fetchQuizApis();
}
