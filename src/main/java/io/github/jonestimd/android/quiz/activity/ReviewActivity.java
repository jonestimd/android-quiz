package io.github.jonestimd.android.quiz.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import io.github.jonestimd.android.quiz.R;

public class ReviewActivity extends ListActivity {
    private static final String LOG_TAG = "ReviewActivity";
    private static final String GROUP_INDEX = "GroupIndex";
    private List<String> groupTitles;
    private List<QuestionGroup> questionGroups;
    private int groupIndex;
    private TextView sectionTitle;
    private ListView reviewList;
    private Button previousButton;
    private Button nextButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        QuestionGroupSelection selection = (QuestionGroupSelection) getIntent().getSerializableExtra(QuestionGroup.class.getName());
        selection.initialize(this);
        groupTitles = selection.getSelectedSections();
        questionGroups = selection.get();
        groupIndex = savedInstanceState == null ? 0 : savedInstanceState.getInt(GROUP_INDEX);

        setContentView(R.layout.activity_review);
        sectionTitle = (TextView) findViewById(R.id.review_section_title);
        reviewList = (ListView) findViewById(android.R.id.list);
        previousButton = (Button) findViewById(R.id.previous_button);
        previousButton.setEnabled(groupIndex > 0);
        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setEnabled(questionGroups.size() > groupIndex+1);
        showGroup();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(GROUP_INDEX, groupIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        questionGroups.get(groupIndex).onReviewItemClick(this, position);
    }

    private void showGroup() {
        sectionTitle.setText(groupTitles.get(groupIndex));
        reviewList.setAdapter(questionGroups.get(groupIndex).getReviewItems(this));
    }

    public void previousGroup(View view) {
        groupIndex--;
        previousButton.setEnabled(groupIndex > 0);
        nextButton.setEnabled(true);
        showGroup();
    }

    public void nextGroup(View view) {
        groupIndex++;
        previousButton.setEnabled(true);
        nextButton.setEnabled(questionGroups.size() > groupIndex+1);
        showGroup();
    }
}
