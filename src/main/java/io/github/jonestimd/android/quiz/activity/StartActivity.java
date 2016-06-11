package io.github.jonestimd.android.quiz.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Arrays;

import io.github.jonestimd.android.quiz.R;

public abstract class StartActivity extends Activity {
    private static final String LOG_TAG = "StartActivity";
    private final OnItemClickListener sectionListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            boolean enabled = toggle(position);
            reviewButton.setEnabled(enabled);
            quizButton.setEnabled(enabled);
        }
    };
    private Button reviewButton;
    private Button quizButton;
    private boolean[] selection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        reviewButton = (Button) findViewById(R.id.review_button);
        quizButton = (Button) findViewById(R.id.start_button);
        try {
            String[] sectionNames = getResources().getStringArray(getMetaDataResourceId("section_names"));
            selection = new boolean[sectionNames.length];
            Arrays.fill(selection, true);

            ListView sections = (ListView) findViewById(R.id.quiz_sections);
            sections.setAdapter(new ArrayAdapter<>(this, R.layout.section_selection, sectionNames));
            sections.setOnItemClickListener(sectionListener);
            // ensure view state matches model (all sections checked)
            for (int i = 0; i < sections.getCount(); i++) {
                sections.setItemChecked(i, true);
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Integer getMetaDataResourceId(String name) throws NameNotFoundException {
        return (Integer) getPackageManager().getActivityInfo(this.getComponentName(), PackageManager.GET_META_DATA).metaData.get(name);
    }

    private boolean toggle(int position) {
        this.selection[position] = ! this.selection[position];
        boolean any = false;
        for (int i = 0; i < selection.length && ! any; i++) {
            any = selection[i];
        }
        return any;
    }

    protected abstract QuestionGroupSelection getGroupSelection();

    public void startQuiz(View view) {
        Log.d(LOG_TAG, "startQuiz");
        Intent intent = new Intent(this, QuizActivity.class)
            .putExtra(QuestionGroup.class.getName(), getGroupSelection());
        startActivity(intent);
    }

    public void startReview(View view) {
        Log.d(LOG_TAG, "startReview");
        Intent intent = new Intent(this, ReviewActivity.class)
            .putExtra(QuestionGroup.class.getName(), getGroupSelection());
        startActivity(intent);
    }
}
