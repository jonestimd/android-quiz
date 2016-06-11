package io.github.jonestimd.android.quiz.activity;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class QuestionGroupSelection implements Serializable {
    private static final long serialVersionUID = 1L;
    private final boolean[] selection;

    protected QuestionGroupSelection(int sections) {
        selection = new boolean[sections];
        Arrays.fill(selection, true);
    }

    public List<String> getSelectedSections() {
        List<String> selected = new ArrayList<String>();
        int i = 0;
        for (String section : getSections()) {
            if (selection[i++]) {
                selected.add(section);
            }
        }
        return selected;
    }

    protected abstract List<String> getSections();

    public void initialize(Context context) {
    }

    public final boolean toggle(int position) {
        this.selection[position] = ! this.selection[position];
        boolean any = false;
        for (boolean item : this.selection) {
            any |= item;
        }
        return any;
    }

    public List<QuestionGroup> get() {
        List<QuestionGroup> groups = new ArrayList<QuestionGroup>(selection.length);
        for (int i = 0; i < selection.length; i++) {
            if (selection[i]) {
                groups.add(getGroup(i));
            }
        }
        return groups;
    }

    protected abstract QuestionGroup getGroup(int index);
}
