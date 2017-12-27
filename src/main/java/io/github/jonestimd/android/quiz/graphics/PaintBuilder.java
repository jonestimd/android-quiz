package io.github.jonestimd.android.quiz.graphics;

import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;

public class PaintBuilder {
    private final Paint paint;

    public PaintBuilder() {
        this(new Paint(Paint.ANTI_ALIAS_FLAG));
    }

    protected PaintBuilder(Paint paint) {
        this.paint = paint;
    }

    public PaintBuilder style(Style style) {
        paint.setStyle(style);
        return this;
    }

    public PaintBuilder strokeWidth(float width) {
        paint.setStrokeWidth(width);
        return this;
    }

    public PaintBuilder color(int color) {
        paint.setColor(color);
        return this;
    }

    public PaintBuilder align(Align align) {
        paint.setTextAlign(align);
        return this;
    }

    public Paint get() {
        return paint;
    }
}
