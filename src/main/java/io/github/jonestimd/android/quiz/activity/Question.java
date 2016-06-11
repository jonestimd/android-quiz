package io.github.jonestimd.android.quiz.activity;

import java.util.List;

public interface Question<T> {
    String getQuestion();

    boolean hasGraphic();

    List<T> getChoices();

    boolean isCorrect(T answer);
}