package io.github.jonestimd.android.quiz.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import io.github.jonestimd.android.quiz.activity.QuestionImage;

public class QuestionImageView extends View {
    private QuestionImage image;

    public QuestionImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setQuestionImage(QuestionImage image) {
        this.image = image;
        image.initialize(getContext().getAssets());
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (image != null) {
            image.draw(canvas, getWidth(), getHeight());
        }
    }
}
