package io.github.jonestimd.android.quiz.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import java.util.List;

import io.github.jonestimd.android.quiz.R;
import io.github.jonestimd.android.quiz.animation.SineInterpolator;
import io.github.jonestimd.android.quiz.view.CheckView;
import io.github.jonestimd.android.quiz.view.QuestionImageView;

public class QuizFragment extends Fragment {
    private static final String LOG_TAG = "QuizFragment";
    private static final int FEEDBACK_DURATION = 1000;
    private static final int[] ANSWER_BUTTON_IDS = {
        R.id.quiz_answer1,
        R.id.quiz_answer2,
        R.id.quiz_answer3,
        R.id.quiz_answer4,
    };
    private Interpolator feedbackInterpolator = new SineInterpolator();
    private Interpolator highlightInterpolator = new DecelerateInterpolator(1f);
    private QuizController quizController;
    private TextView quizQuestion;
    private QuestionImageView imageView;
    private RadioGroup answerButtonGroup;
    private SparseArray<RadioButton> answerButtons;
    private Button okButton;
    private Button hintButton;
    private int answerButtonId;
    private boolean answered = false;
    private CheckView checkView;
    private Runnable nextQuestionRunner = new Runnable() {
        public void run() {
            nextQuestion();
        }
    };
    private OnCheckedChangeListener answerListener = new OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            okButton.setEnabled(checkedId > 0);
            if (checkedId > 0 && ((Checkable) group.findViewById(checkedId)).isChecked()) {
                String answer = ((TextView) group.findViewById(checkedId)).getText().toString();
                Log.d(LOG_TAG, answer);
                quizController.answerSelected(getActivity(), answer);
            }
        }
    };
    private OnClickListener answerlistener = new OnClickListener() {
        public void onClick(View v) {
            if (answered) {
                unhighlightAnswers();
                nextQuestion();
            } else {
                boolean correct = answerButtonGroup.getCheckedRadioButtonId() == answerButtonId;
                quizController.answered(getActivity(), correct);
                if (correct) {
                    showCheck();
                }
                else {
                    answered = true;
                    highlightAnswers();
                }
            }
        }
    };
    private OnClickListener hintListener = new OnClickListener() {
        public void onClick(View v) {
            HintFragment.create(quizController.getHint())
                .show(getActivity().getFragmentManager(), "hint");
        }
    };

    public QuizFragment() {
        super();
        Log.d(LOG_TAG, "new QuizFragment()");
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        QuestionGroupSelection selection = (QuestionGroupSelection) getActivity().getIntent().getSerializableExtra(QuestionGroup.class.getName());
        selection.initialize(getActivity());
        quizController = new QuizController(selection.get());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        quizController = null;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_fragment, container, false);
        quizQuestion = (TextView) view.findViewById(R.id.quiz_question);
        imageView = (QuestionImageView) view.findViewById(R.id.question_graphic_view);
        answerButtonGroup = (RadioGroup) view.findViewById(R.id.multiple_choice_answer);
        answerButtonGroup.setOnCheckedChangeListener(answerListener);
        answerButtons = new SparseArray<>(ANSWER_BUTTON_IDS.length);
        for (int buttonId : ANSWER_BUTTON_IDS) {
            answerButtons.put(buttonId, (RadioButton) view.findViewById(buttonId));
        }
        okButton = (Button) view.findViewById(R.id.ok_button);
        okButton.setOnClickListener(answerlistener);
        hintButton = (Button) view.findViewById(R.id.hint_button);
        hintButton.setOnClickListener(hintListener);
        checkView = (CheckView) view.findViewById(R.id.answer_feedback);
        return view;
    }

    public void onDestroyView() {
        super.onDestroyView();
        quizQuestion = null;
        imageView = null;
        answerButtonGroup = null;
        answerButtons = null;
        okButton = null;
        hintButton = null;
    }

    public void onStart() {
        super.onStart();
        checkView.setVisibility(View.INVISIBLE);
        showQuestion();
    }

    private void showQuestion() {
        quizQuestion.setText(getResources().getString(quizController.getQuestionId()));
        if (quizController.isGraphicQuestion()) {
            imageView.setQuestionImage(quizController.getImage());
        }
        else {
            imageView.setQuestionImage(null);
        }

        String answer = quizController.getAnswer();
        List<String> choices = quizController.getChoices();
        for (int i = 0; i < choices.size(); i++) {
            answerButtons.get(ANSWER_BUTTON_IDS[i]).setText(choices.get(i));
        }
        answerButtonId = ANSWER_BUTTON_IDS[choices.indexOf(answer)];

        hintButton.setEnabled(quizController.hasHint());
        answerButtonGroup.clearCheck();
        okButton.setEnabled(false);
    }

    public void answerSelected(View view) {
        okButton.setEnabled(true);
    }

    private void nextQuestion() {
        answered = false;
        if (quizController.nextQuestion()) {
            showQuestion();
        }
        else {
            StatisticsFragment.create(quizController.getStatistics())
                .show(getFragmentManager(), "statistics");
        }
    }

    private void showCheck() {
        okButton.setEnabled(false);
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setInterpolator(feedbackInterpolator);
        animation.setRepeatCount(0);
        animation.setDuration(FEEDBACK_DURATION);
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                Log.d(LOG_TAG, "onAnimationEnd");
                checkView.setVisibility(View.INVISIBLE);
                checkView.getHandler().post(nextQuestionRunner);
            }
        });
        checkView.startAnimation(animation);
        checkView.setVisibility(View.VISIBLE);
    }

    private void highlightAnswers() {
        okButton.setText(getResources().getString(R.string.next_question));
        AlphaAnimation animation = new AlphaAnimation(1f, 0f);
        animation.setInterpolator(highlightInterpolator);
        animation.setRepeatCount(0);
        animation.setDuration(FEEDBACK_DURATION);
        animation.setFillAfter(true);
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                Log.d(LOG_TAG, "onAnimationEnd");
            }
        });
        for (int i = 0; i < answerButtons.size(); i++) {
            if (answerButtons.keyAt(i) != answerButtonId) {
                answerButtons.valueAt(i).setAnimation(animation);
            }
        }
        animation.start();
        answerButtonGroup.invalidate();
    }

    private void unhighlightAnswers() {
        okButton.setEnabled(false);
        okButton.setText(getResources().getString(R.string.ok));
        for (int i = 0; i < answerButtons.size(); i++) {
            answerButtons.valueAt(i).clearAnimation();
        }
        answerButtonGroup.invalidate();
    }
}
