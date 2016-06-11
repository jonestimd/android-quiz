package io.github.jonestimd.android.quiz.graphics;

import android.graphics.Path;

public class PathBuilder {
//    private static final String LOG_TAG = "PathBuilder";
    float lastX;
    float lastY;
    float lastCx;
    float lastCy;

    private Path path = new Path();

    public PathBuilder moveTo(float x, float y) {
        path.moveTo(setX(x), setY(y));
        return this;
    }

    public PathBuilder rMoveTo(float dx, float dy) {
        path.rMoveTo(addX(dx), addY(dy));
        return this;
    }

    public PathBuilder lineTo(float x, float y) {
        path.lineTo(setX(x), setY(y));
        return this;
    }

    public PathBuilder hLineTo(float x) {
        path.lineTo(setX(x), lastY);
        return this;
    }

    public PathBuilder vLineTo(float y) {
        path.lineTo(lastX, setY(y));
        return this;
    }

    public PathBuilder rLineTo(float dx, float dy) {
        path.rLineTo(addX(dx), addY(dy));
        return this;
    }

    public PathBuilder cubicTo(float x1, float y1, float x2, float y2, float x3, float y3) {
        path.cubicTo(x1, y1, setCx(x2), setCy(y2), setX(x3), setY(y3));
        return this;
    }

    public PathBuilder smoothCubicTo(float x2, float y2, float x3, float y3) {
        path.cubicTo(2 * lastX - lastCx, 2 * lastY - lastCy, setCx(x2), setCy(y2), setX(x3), setY(y3));
        return this;
    }

    public PathBuilder rCubicTo(float dx1, float dy1, float dx2, float dy2, float dx3, float dy3) {
        path.rCubicTo(dx1, dy1, addCx(dx2), addCy(dy2), addX(dx3), addY(dy3));
        return this;
    }

    public PathBuilder rSmoothCubicTo(float dx2, float dy2, float dx3, float dy3) {
        path.rCubicTo(lastX - lastCx, lastY - lastCy, addCx(dx2), addCy(dy2), addX(dx3), addY(dy3));
        return this;
    }

    public PathBuilder quadTo(float x1, float y1, float x2, float y2) {
        path.quadTo(setCx(x1), setCy(y1), setX(x2), setY(y2));
        return this;
    }

    public PathBuilder smoothQuadTo(float x2, float y2) {
        path.quadTo(mirrorCx(), mirrorCy(), setX(x2), setY(y2));
        return this;
    }

    public PathBuilder rQuadTo(float dx1, float dy1, float dx2, float dy2) {
        path.rQuadTo(addCx(dx1), addCy(dy1), addX(dx2), addY(dy2));
        return this;
    }

    public PathBuilder rSmoothQuadTo(float dx2, float dy2) {
        path.rQuadTo(relativeMirrorCx(), relativeMirrorCy(), addX(dx2), addY(dy2));
        return this;
    }

    public PathBuilder close() {
        path.close();
        return this;
    }

    public Path get() {
        return path;
    }

    float addX(float dx) {
        lastX += dx;
        return dx;
    }

    float addY(float dy) {
        lastY += dy;
        return dy;
    }

    float setX(float x) {
        return lastX = x;
    }

    float setY(float y) {
        return lastY = y;
    }

    float setCx(float cx) {
        return lastCx = cx;
    }

    float setCy(float cy) {
        return lastCy = cy;
    }

    float addCx(float dcx) {
        lastCx = lastX + dcx;
        return dcx;
    }

    float addCy(float dcy) {
        lastCy = lastY + dcy;
        return dcy;
    }

    float mirrorCx() {
        return lastCx = 2 * lastX - lastCx;
    }

    float mirrorCy() {
        return lastCy = 2 * lastY - lastCy;
    }

    float relativeMirrorCx() {
        float relativeCx = lastX - lastCx;
        lastCx = lastX + relativeCx;
        return relativeCx;
    }

    float relativeMirrorCy() {
        float relativeCy = lastY - lastCy;
        lastCy = lastY + relativeCy;
        return relativeCy;
    }
}
