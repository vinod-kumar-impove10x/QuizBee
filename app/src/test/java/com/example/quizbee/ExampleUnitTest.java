package com.example.quizbee;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.quizbee.Models.QuizBee;
import com.example.quizbee.apis.QuizApi;
import com.example.quizbee.apis.QuizApiService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getQuizApis() throws IOException {

        QuizApi api = new QuizApi();
        QuizApiService service = api.createQuizApiService();
        Call<List<QuizBee>> call = service.fetchQuizApis();
        List<QuizBee> quizBees = call.execute().body();
        assertNotNull(quizBees);
        assertFalse(quizBees.isEmpty());
        System.out.println(new Gson().toJson(quizBees));
    }
}