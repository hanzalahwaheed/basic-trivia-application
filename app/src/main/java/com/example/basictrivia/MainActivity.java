package com.example.basictrivia;

import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.basictrivia.data.Repository;
import com.example.basictrivia.databinding.ActivityMainBinding;
import com.example.basictrivia.model.Questions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Questions> questionsList;
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        questionsList = new Repository().getQuestions(questionsArrayList -> {
            binding.textViewQuestion.setText(questionsArrayList.get(currentQuestionIndex).getAnswer());
            updateCounter(questionsArrayList);
        });

        binding.buttonNext.setOnClickListener(v -> {
            currentQuestionIndex = (currentQuestionIndex + 1) % questionsList.size();
            updateQuestion();
        });
        binding.buttonPrevious.setOnClickListener(v -> {
            if (currentQuestionIndex == 0) {
                currentQuestionIndex = questionsList.size();
            } else {
                currentQuestionIndex = (currentQuestionIndex - 1) % questionsList.size();
                updateQuestion();
            }

        });
        binding.buttonTrue.setOnClickListener(v -> {
            checkAnswer(true);
            updateQuestion();
        });
        binding.buttonFalse.setOnClickListener(v -> {
            checkAnswer(false);
            updateQuestion();
        });
    }

    private void checkAnswer(boolean userChoseCorrect) {
        boolean answer = questionsList.get(currentQuestionIndex).isAnswerTrue();
        int snackMessage = 0;
        if (userChoseCorrect == answer) {
            snackMessage = R.string.correct_answer;
            randomAnimation();
        } else {
            snackMessage = R.string.incorrect_answer;
            shakeAnimation();
        }
        Snackbar.make(binding.cardView, snackMessage, Snackbar.LENGTH_SHORT).show();
    }

    private void updateCounter(ArrayList<Questions> questionsArrayList) {
        binding.textViewOutOf.setText("Question: " + (currentQuestionIndex + 1) + "/" + (questionsArrayList.size() + 1));
    }

    private void updateQuestion() {
        String question = questionsList.get(currentQuestionIndex).getAnswer();
        binding.textViewQuestion.setText(question);
        updateCounter((ArrayList<Questions>) questionsList);
    }

    private void shakeAnimation(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.shake_animation);
        binding.cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.textViewQuestion.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.textViewQuestion.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void randomAnimation(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.random_animation);
        binding.cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.textViewQuestion.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.textViewQuestion.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}