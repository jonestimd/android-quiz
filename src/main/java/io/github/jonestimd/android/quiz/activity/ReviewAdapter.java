package io.github.jonestimd.android.quiz.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class ReviewAdapter extends BaseAdapter {
    private final LayoutInflater mInflater;
    private final int viewResourceId;

    protected ReviewAdapter(Context context, int viewResourceId) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.viewResourceId = viewResourceId;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView == null ? mInflater.inflate(viewResourceId, parent, false) : convertView;
        bindView(position, view);
        return view;
    }

    protected abstract void bindView(int position, View view);
}
