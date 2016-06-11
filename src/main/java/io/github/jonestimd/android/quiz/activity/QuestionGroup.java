package io.github.jonestimd.android.quiz.activity;

import android.content.Context;
import android.widget.ListAdapter;

import java.io.Serializable;
import java.util.List;

public interface QuestionGroup extends Serializable {
    int getQuestionKey();

    boolean isGraphic();

    QuestionImage getGraphic(int index);

    AnswerType getAnswerType();

    List<String> getAvailableAnswers();

    String getAnswer(int index);

    int getQuestionCount();

    boolean hasHint(int index);

    String getHint(int index);

    ListAdapter getReviewItems(Context context);

    void onReviewItemClick(Context context, int position);

    void answerSelected(Context context, String answer);

    void answered(Context context, boolean correct, int questionIndex);
}
