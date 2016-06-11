package io.github.jonestimd.android.quiz.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Html;

import io.github.jonestimd.android.quiz.R;

public class StatisticsFragment extends DialogFragment {
    private static final String INCORRECT = "incorrect";
    private static final String CORRECT = "correct";

    public static StatisticsFragment create(Statistics statistics) {
        Bundle args = new Bundle();
        args.putInt(CORRECT, statistics.getCorrectAnswers());
        args.putInt(INCORRECT, statistics.getWrongAnswers());
        StatisticsFragment fragment = new StatisticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int correct = getArguments().getInt(CORRECT);
        int incorrect = getArguments().getInt(INCORRECT);
        return new AlertDialog.Builder(getActivity())
            .setTitle(R.string.summary_title)
            .setCancelable(false)
            .setMessage(Html.fromHtml(getResources().getString(R.string.summary, correct, incorrect)))
            .setNeutralButton(R.string.ok, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            })
            .create();
    }
}
