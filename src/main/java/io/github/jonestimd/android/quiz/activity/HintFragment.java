package io.github.jonestimd.android.quiz.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Html;

import io.github.jonestimd.android.quiz.R;

public class HintFragment extends DialogFragment {
    private static final String HINT_ARG = "hint";

    public static HintFragment create(String hint) {
        Bundle args = new Bundle();
        args.putString(HINT_ARG, hint);
        HintFragment fragment = new HintFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String hint = getArguments().getString(HINT_ARG);
        return new AlertDialog.Builder(getActivity())
            .setTitle(R.string.hint)
            .setCancelable(false)
            .setMessage(Html.fromHtml(hint))
            .setNeutralButton(R.string.ok, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    getFragmentManager().popBackStack();
                }
            })
            .create();
    }
}
