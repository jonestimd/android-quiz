package io.github.jonestimd.android.quiz.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import io.github.jonestimd.android.quiz.R;

public class QuizActivity extends Activity {
    private static final String LOG_TAG = "QuizActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_quiz);
    }
}
