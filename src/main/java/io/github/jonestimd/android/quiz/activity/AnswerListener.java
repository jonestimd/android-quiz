package io.github.jonestimd.android.quiz.activity;

public interface AnswerListener {
    void hint();

    void answer(boolean correct);
}
