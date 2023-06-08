package com.example.quizbee;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizbee.Models.Question;
import com.example.quizbee.databinding.QuestionItemBinding;

public class QuestionViewHolder extends RecyclerView.ViewHolder {
    public QuestionItemBinding binding;

    public QuestionViewHolder(QuestionItemBinding questionItemBinding) {
        super(questionItemBinding.getRoot());
        binding = questionItemBinding;
    }
}
