package com.example.basictrivia.data;

import com.example.basictrivia.model.Questions;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList <Questions> questionsArrayList);
}
