package io.github.jonestimd.android.quiz.graphics;

import java.util.Random;

import android.graphics.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.truth.Truth.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PathBuilderTest {
    @Mock
    private Path path;
    private Random random = new Random();

    @Test
    public void getReturnsPath() throws Exception {
        assertThat(new PathBuilder(path).get()).isSameAs(path);
    }

    @Test
    public void moveTo() throws Exception {
        float x = random.nextFloat();
        float y = random.nextFloat();

        new PathBuilder(path).moveTo(x, y);

        verify(path).moveTo(x, y);
    }

    @Test
    public void rMoveTo() throws Exception {
        float x = random.nextFloat();
        float y = random.nextFloat();

        new PathBuilder(path).rMoveTo(x, y);

        verify(path).rMoveTo(x, y);
    }

    @Test
    public void lineTo() throws Exception {
        float x = random.nextFloat();
        float y = random.nextFloat();

        new PathBuilder(path).lineTo(x, y);

        verify(path).lineTo(x, y);
    }

    @Test
    public void rLineTo() throws Exception {
        float x = random.nextFloat();
        float y = random.nextFloat();

        new PathBuilder(path).rLineTo(x, y);

        verify(path).rLineTo(x, y);
    }

    @Test
    public void horizontalLineTo() throws Exception {
        float x = random.nextFloat();
        float y = random.nextFloat();
        float x2 = random.nextFloat();
        PathBuilder builder = new PathBuilder(path);
        builder.moveTo(x, y);

        builder.horizontalLineTo(x2);

        verify(path).moveTo(x, y);
        verify(path).lineTo(x2, y);
    }

    @Test
    public void verticalLineTo() throws Exception {
        float x = random.nextFloat();
        float y = random.nextFloat();
        float y2 = random.nextFloat();
        PathBuilder builder = new PathBuilder(path);
        builder.moveTo(x, y);

        builder.verticalLineTo(y2);

        verify(path).moveTo(x, y);
        verify(path).lineTo(x, y2);
    }

    @Test
    public void cubicTo() throws Exception {
        float x = random.nextFloat();
        float y = random.nextFloat();
        float x2 = random.nextFloat();
        float y2 = random.nextFloat();
        float x3 = random.nextFloat();
        float y3 = random.nextFloat();

        new PathBuilder(path).cubicTo(x, y, x2, y2, x3, y3);

        verify(path).cubicTo(x, y, x2, y2, x3, y3);
    }

    @Test
    public void rCubicTo() throws Exception {
        float x1 = random.nextFloat();
        float y1 = random.nextFloat();
        float x2 = random.nextFloat();
        float y2 = random.nextFloat();
        float x3 = random.nextFloat();
        float y3 = random.nextFloat();

        new PathBuilder(path).rCubicTo(x1, y1, x2, y2, x3, y3);

        verify(path).rCubicTo(x1, y1, x2, y2, x3, y3);
    }

    @Test
    public void smoothCubicTo() throws Exception {
        float x1 = random.nextFloat();
        float y1 = random.nextFloat();
        float x2 = random.nextFloat();
        float y2 = random.nextFloat();
        float x3 = random.nextFloat();
        float y3 = random.nextFloat();
        float x4 = random.nextFloat();
        float y4 = random.nextFloat();
        float x5 = random.nextFloat();
        float y5 = random.nextFloat();
        PathBuilder builder = new PathBuilder(path);
        builder.cubicTo(x1, y1, x2, y2, x3, y3);

        builder.smoothCubicTo(x4, y4, x5, y5);

        verify(path).cubicTo(x1, y1, x2, y2, x3, y3);
        verify(path).cubicTo(2 * x3 - x2, 2 * y3 - y2, x4, y4, x5, y5);
    }

    @Test
    public void rSmoothCubicTo() throws Exception {
        float x1 = random.nextFloat();
        float y1 = random.nextFloat();
        float x2 = random.nextFloat();
        float y2 = random.nextFloat();
        float x3 = random.nextFloat();
        float y3 = random.nextFloat();
        float x4 = random.nextFloat();
        float y4 = random.nextFloat();
        float x5 = random.nextFloat();
        float y5 = random.nextFloat();
        PathBuilder builder = new PathBuilder(path);
        builder.cubicTo(x1, y1, x2, y2, x3, y3);

        builder.rSmoothCubicTo(x4, y4, x5, y5);

        verify(path).cubicTo(x1, y1, x2, y2, x3, y3);
        verify(path).rCubicTo(x3 - x2, y3 - y2, x4, y4, x5, y5);
    }

    @Test
    public void quadTo() throws Exception {
        float x1 = random.nextFloat();
        float y1 = random.nextFloat();
        float x2 = random.nextFloat();
        float y2 = random.nextFloat();

        new PathBuilder(path).quadTo(x1, y1, x2, y2);

        verify(path).quadTo(x1, y1, x2, y2);
    }

    @Test
    public void rQuadTo() throws Exception {
        float x1 = random.nextFloat();
        float y1 = random.nextFloat();
        float x2 = random.nextFloat();
        float y2 = random.nextFloat();

        new PathBuilder(path).rQuadTo(x1, y1, x2, y2);

        verify(path).rQuadTo(x1, y1, x2, y2);
    }

    @Test
    public void smoothQuadTo() throws Exception {
        float x1 = random.nextFloat();
        float y1 = random.nextFloat();
        float x2 = random.nextFloat();
        float y2 = random.nextFloat();
        float x3 = random.nextFloat();
        float y3 = random.nextFloat();
        PathBuilder builder = new PathBuilder(path);
        builder.quadTo(x1, y1, x2, y2);

        builder.smoothQuadTo(x3, y3);

        verify(path).quadTo(x1, y1, x2, y2);
        verify(path).quadTo(2 * x2 - x1, 2 * y2 - y1, x3, y3);
    }

    @Test
    public void rSmoothQuadTo() throws Exception {
        float x1 = random.nextFloat();
        float y1 = random.nextFloat();
        float x2 = random.nextFloat();
        float y2 = random.nextFloat();
        float x3 = random.nextFloat();
        float y3 = random.nextFloat();
        PathBuilder builder = new PathBuilder(path);
        builder.quadTo(x1, y1, x2, y2);

        builder.rSmoothQuadTo(x3, y3);

        verify(path).quadTo(x1, y1, x2, y2);
        verify(path).rQuadTo(x2 - x1, y2 - y1, x3, y3);
    }

    @Test
    public void close() throws Exception {
        new PathBuilder(path).close();

        verify(path).close();
    }
}