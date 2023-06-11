package com.example.quizbee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
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

    int currentPosition = 0;
    Integer[] answerOptionIndexes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupApiService();
        setupAdapter();
        setupRecyclerView();
        fetchQuestions();
        nextBtn();
        previousBtn();
        handleSubmitBtn();
        showRadioGroup();

    }

    private void showRadioGroup() {
        binding.radioGroupAnswers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (binding.ans1RdBtn.isChecked()) {
                    answerOptionIndexes[currentPosition] = 0;
                } else if (binding.ans2RdBtn.isChecked()) {
                    answerOptionIndexes[currentPosition] = 1;
                } else if (binding.ans3RdBtn.isChecked()) {
                    answerOptionIndexes[currentPosition] = 2;
                } else if (binding.ans4RdBtn.isChecked()) {
                    answerOptionIndexes[currentPosition] = 3;
                }
            }
        });
    }

    private void handleSubmitBtn() {
      binding.submitBtn.setOnClickListener(v -> {
          Intent intent = new Intent(this, ResultActivity.class);
          startActivity(intent);
      });
    }

    private void previousBtn() {
         binding.previousBtn.setOnClickListener(v -> {
             try {
                 currentPosition = questionsAdapter.currentQuestionPosition;
                 currentPosition--;
                 Question question = questionArrayList.get(currentPosition);
                 onBindData(question);
                 questionsAdapter.currentQuestionPosition = currentPosition;
                 questionsAdapter.notifyDataSetChanged();
             }catch (IndexOutOfBoundsException exception) {
                 Toast.makeText(this, "This is the 1st question", Toast.LENGTH_SHORT).show();
             }
         });
    }

    private void nextBtn() {
        binding.nextBtn.setOnClickListener(v -> {
            currentPosition = questionsAdapter.currentQuestionPosition;
            currentPosition++;
            if (currentPosition == questionArrayList.size() - 1) {
                binding.nextBtn.setVisibility(View.GONE);
                binding.submitBtn.setVisibility(View.VISIBLE);
            } else {
                binding.nextBtn.setVisibility(View.VISIBLE);
                binding.submitBtn.setVisibility(View.GONE);
            }
                Question question = questionArrayList.get(currentPosition);
                onBindData(question);
                questionsAdapter.currentQuestionPosition = currentPosition;
                questionsAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Questions completed", Toast.LENGTH_SHORT).show();
        });
    }

    private void  onBindData(Question question) {
        binding.quizquestionTxt.setText(question.getQuestion());
        binding.radioGroupAnswers.clearCheck();
        binding.ans1RdBtn.setText(question.getAnswers().get(0));
        binding.ans2RdBtn.setText(question.getAnswers().get(1));
        binding.ans3RdBtn.setText(question.getAnswers().get(2));
        binding.ans4RdBtn.setText(question.getAnswers().get(3));
        if (answerOptionIndexes[currentPosition]!= null) {
            if (answerOptionIndexes[currentPosition] == 0) {
                binding.ans1RdBtn.setChecked(true);
            } else  if (answerOptionIndexes[currentPosition] == 1){
                binding.ans2RdBtn.setChecked(true);
            } else  if (answerOptionIndexes[currentPosition] == 2){
                binding.ans3RdBtn.setChecked(true);
            }else if (answerOptionIndexes[currentPosition] == 3) {
                binding.ans4RdBtn.setChecked(true);
            }
        }
    }

    private void fetchQuestions() {
        Call<List<QuizBee>> call = quizApiService.fetchQuizApis();
        call.enqueue(new Callback<List<QuizBee>>() {
            @Override
            public void onResponse(Call<List<QuizBee>> call, Response<List<QuizBee>> response) {
                List<QuizBee> quizBees = response.body();
                questionsAdapter.setData(quizBees.get(0).getQuestions());
                questionArrayList = quizBees.get(0).getQuestions();
                answerOptionIndexes = new Integer[questionArrayList.size()];
                onBindData(questionArrayList.get(0));
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
            onBindData(question);
            /*binding.quizquestionTxt.setText(question.getQuestion());
            binding.ans1RdBtn.setText(question.getAnswers().get(0));
            binding.ans2RdBtn.setText(question.getAnswers().get(1));
            binding.ans3RdBtn.setText(question.getAnswers().get(2));
            binding.ans4RdBtn.setText(question.getAnswers().get(3));

             */
        }
    });
    }

    private void setupApiService() {
        QuizApi quizApi = new QuizApi();
        quizApiService = quizApi.createQuizApiService();
    }
}