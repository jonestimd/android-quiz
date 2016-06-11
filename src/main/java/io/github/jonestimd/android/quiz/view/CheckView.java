package io.github.jonestimd.android.quiz.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import io.github.jonestimd.android.quiz.graphics.PathBuilder;

public class CheckView extends View {
    private static final Path PATH = new PathBuilder()
        .moveTo(-0.500000f, 0.056911f)
        .cubicTo(-0.318428f, 0.176152f, -0.214770f, 0.310298f, -0.107046f, 0.495935f)
        .cubicTo(-0.037263f, 0.204607f, 0.275068f, -0.298103f, 0.500000f, -0.495935f)
        .cubicTo(0.239837f, -0.409214f, -0.035577f, -0.020794f, -0.142276f, 0.268293f)
        .cubicTo(-0.193767f, 0.193767f, -0.285230f, 0.090108f, -0.375000f, 0.035908f)
        .cubicTo(-0.402439f, 0.073848f, -0.498645f, 0.052846f, -0.500000f, 0.056911f)
        .close().get();

    private Path path = new Path(PATH);
    private Matrix matrix = new Matrix();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private LinearGradient shader;

    public CheckView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setStyle(Style.FILL);
        shader = new LinearGradient(-0.5f, 0.5f, 0f, 0f, 0xFF004000, 0xFF00FF00, Shader.TileMode.MIRROR);
        paint.setShader(shader);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void onDraw(Canvas canvas) {
        float scale = Math.min(getRight()-getLeft(), getBottom()-getTop());
        float midX = (getRight() - getLeft())/2;
        float midY = (getBottom() - getTop())/2;
        matrix.setScale(scale, scale);
        matrix.postTranslate(midX, midY);
        shader.setLocalMatrix(matrix);
        path.set(PATH);
        path.transform(matrix);
        canvas.drawPath(path, paint);
    }
}
