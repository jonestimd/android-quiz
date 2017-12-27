package io.github.jonestimd.android.quiz.graphics;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.truth.Truth.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PaintBuilderTest {
    @Mock
    private Paint paint;

    @Test
    public void style() throws Exception {
        assertThat(new PaintBuilder(paint).style(Style.STROKE).get()).isSameAs(paint);

        verify(paint).setStyle(Style.STROKE);
    }

    @Test
    public void strokeWidth() throws Exception {
        assertThat(new PaintBuilder(paint).strokeWidth(2).get()).isSameAs(paint);

        verify(paint).setStrokeWidth(2);
    }

    @Test
    public void color() throws Exception {
        assertThat(new PaintBuilder(paint).color(Color.GRAY).get()).isSameAs(paint);

        verify(paint).setColor(Color.GRAY);
    }

    @Test
    public void align() throws Exception {
        assertThat(new PaintBuilder(paint).align(Align.CENTER).get()).isSameAs(paint);

        verify(paint).setTextAlign(Align.CENTER);
    }
}