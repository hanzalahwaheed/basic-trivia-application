package com.example.basictrivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.basictrivia.controller.AppController;
import com.example.basictrivia.model.Questions;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    ArrayList<Questions> questionsArrayList = new ArrayList<>();

    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Questions> getQuestions(AnswerListAsyncResponse callBack) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            Questions questions = new Questions(response.getJSONArray(i).getString(0),
                                    response.getJSONArray(i).getBoolean(1));
                            questionsArrayList.add(questions);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if(null != callBack){
                        callBack.processFinished(questionsArrayList);

                    }
                }, error -> {
            Log.d("MAIN", "getQuestions: Failed to Import!");
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return questionsArrayList;
    }
}
