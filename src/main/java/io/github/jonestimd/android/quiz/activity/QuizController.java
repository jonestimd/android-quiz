package io.github.jonestimd.android.quiz.activity;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizController {
    private final Statistics statistics = new Statistics();
    private final List<? extends QuestionGroup> groups;
    private int groupIndex = 0;
    private int questionIndex = 0;
    private String answer;
    private final List<String> choices = new ArrayList<>(4);
    private final Random random = new Random();
    private int[] questionOrder;

    public QuizController(List<? extends QuestionGroup> questionGroups) {
        this.groups = questionGroups;
        shuffleQuestions();
        selectChoices();
    }

    public void answered(Context context, boolean correct) {
        statistics.answer(correct);
        groups.get(groupIndex).answered(context, correct, questionOrder[questionIndex]);
    }

    public boolean nextQuestion() {
        questionIndex++;
        if (questionIndex < groups.get(groupIndex).getQuestionCount() || nextGroup()) {
            selectChoices();
            return true;
        }
        return false;
    }

    private boolean nextGroup() {
        groupIndex++;
        questionIndex = 0;
        if (groupIndex < groups.size()) {
            shuffleQuestions();
            return true;
        }
        return false;
    }

    private void shuffleQuestions() {
        questionOrder = new int[groups.get(groupIndex).getQuestionCount()];
        for (int i = 0; i < questionOrder.length; i++) {
            questionOrder[i] = i;
        }
        for (int i = questionOrder.length-1; i > 0; i--) {
            int r = random.nextInt(i+1);
            int temp = questionOrder[i];
            questionOrder[i] = questionOrder[r];
            questionOrder[r] = temp;
        }
    }

    private void selectChoices() {
        answer = groups.get(groupIndex).getAnswer(questionOrder[questionIndex]);
        choices.clear();
        choices.add(answer);
        List<String> available = groups.get(groupIndex).getAvailableAnswers();
        available.remove(answer);
        for (int i = 0; i < 3; i++) {
            choices.add(available.remove(random.nextInt(available.size())));
        }
        Collections.shuffle(choices, random);
    }

    public int getQuestionId() {
        return groups.get(groupIndex).getQuestionKey();
    }

    public boolean isGraphicQuestion() {
        return groups.get(groupIndex).isGraphic();
    }

    public QuestionImage getImage() {
        return groups.get(groupIndex).getGraphic(questionOrder[questionIndex]);
    }

    public List<String> getChoices() {
        return choices;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean hasHint() {
        return groups.get(groupIndex).hasHint(questionOrder[questionIndex]);
    }

    public String getHint() {
        return groups.get(groupIndex).getHint(questionOrder[questionIndex]);
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void answerSelected(Context context, String answer) {
        groups.get(groupIndex).answerSelected(context, answer);
    }
}
