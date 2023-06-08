package com.example.quizbee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.quizbee.Models.Question;
import com.example.quizbee.Models.QuizBee;
import com.example.quizbee.apis.QuizApi;
import com.example.quizbee.apis.QuizApiService;
import com.example.quizbee.databinding.ActivityQuestionsBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {

    public ArrayList<Question> questionArrayList = new ArrayList<>();

    public ActivityQuestionsBinding binding;
    public QuestionsAdapter questionsAdapter;
    public QuizApiService quizApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupApiService();
        setupAdapter();
        setupRecyclerView();
        fetchQuestions();
    }

    private void fetchQuestions() {
        Call<List<QuizBee>> call = quizApiService.fetchQuizApis();
        call.enqueue(new Callback<List<QuizBee>>() {
            @Override
            public void onResponse(Call<List<QuizBee>> call, Response<List<QuizBee>> response) {
                List<QuizBee> quizBees = response.body();
                questionsAdapter.setData(quizBees.get(0).getQuestions());
                Toast.makeText(QuestionActivity.this, "Successfully load the data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<QuizBee>> call, Throwable t) {
                Toast.makeText(QuestionActivity.this, "fail to get load data", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setupRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
    binding.questionsRv.setLayoutManager(layoutManager);
    binding.questionsRv.setAdapter(questionsAdapter);
    }

    private void setupAdapter() {
    questionsAdapter = new QuestionsAdapter();
    questionsAdapter.setData(questionArrayList);
    questionsAdapter.setOnItemActionListener(new OnItemActionListener() {
        @Override
        public void OnItemClicked(Question question) {
            binding.quizquestionTxt.setText(question.getQuestion());
            binding.ans1RdBtn.setText(question.getAnswers().get(0));
            binding.ans2RdBtn.setText(question.getAnswers().get(1));
            binding.ans3RdBtn.setText(question.getAnswers().get(2));
            binding.ans4RdBtn.setText(question.getAnswers().get(3));
        }
    });
    }

    private void setupApiService() {
        QuizApi quizApi = new QuizApi();
        quizApiService = quizApi.createQuizApiService();
    }
}