package com.example.quizbee;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizbee.Models.Question;
import com.example.quizbee.Models.QuizBee;
import com.example.quizbee.databinding.QuestionItemBinding;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionViewHolder> {

    public int currentQuestionPosition = 0;

    List<Question> questions;

    OnItemActionListener onItemActionListener;

    void setOnItemActionListener(OnItemActionListener onItemActionListener){
        this.onItemActionListener = onItemActionListener;
    }

    void setData(List<Question> questionList){
    this.questions = questionList;
    notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionItemBinding binding = QuestionItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        QuestionViewHolder questionViewHolder = new QuestionViewHolder(binding);
        return questionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
     Question question = questions.get(position);
     holder.binding.numberTxt.setText(String.valueOf(question.getNumber()));
     holder.binding.getRoot().setOnClickListener(v -> {
         currentQuestionPosition = position;
         notifyDataSetChanged();
         onItemActionListener.OnItemClicked((question));
     });
     if (currentQuestionPosition == position) {
         holder.binding.numberTxt.setTextColor(Color.parseColor("#D61D1D"));
     } else  {
         holder.binding.numberTxt.setTextColor(Color.parseColor("#161717"));
     }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
