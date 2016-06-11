package io.github.jonestimd.android.quiz.animation;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.SystemClock;
import android.view.animation.Interpolator;

public class FadeDrawable extends DrawableContainer implements Animatable, Runnable {
    private int startAlpha;
    private int deltaAlpha;
    private int frameCount;
    private int frameDuration;
    private int currentFrame = -1;
    private Interpolator interpolator;

    /**
     * @param startAlpha 0 to 255
     * @param endAlpha 0 to 255
     * @param duration milliseconds
     * @param frameRate frames per second
     */
    public FadeDrawable(Drawable drawable, Interpolator interpolator, int startAlpha, int endAlpha, int duration, int frameRate) {
        selectDrawable(((DrawableContainerState) getConstantState()).addChild(drawable));
        this.interpolator = interpolator;
        this.startAlpha = startAlpha;
        this.deltaAlpha = endAlpha - startAlpha;
        this.frameCount = duration / 1000 * frameRate;
        this.frameDuration = duration / this.frameCount;
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        boolean changed = super.setVisible(visible, restart);
        if (visible) {
            if (changed || restart) {
                unscheduleSelf(this);
                setFrame(0, true);
            }
        } else {
            unscheduleSelf(this);
        }
        return changed;
    }

    public void start() {
        if (!isRunning()) {
            run();
        }
    }

    public void stop() {
        if (isRunning()) {
            unscheduleSelf(this);
        }
    }

    public boolean isRunning() {
        return currentFrame > -1;
    }

    public void run() {
        nextFrame();
    }

    @Override
    public void unscheduleSelf(Runnable what) {
        currentFrame = -1;
        super.unscheduleSelf(what);
    }

    private void nextFrame() {
        int next = currentFrame + 1;
        if (next >= frameCount) {
            next = -1;
        }
        setFrame(next, next >= 0);
    }

    private void setFrame(int frame, boolean animate) {
        if (frame >= frameCount) {
            return;
        }
        currentFrame = frame;
        setAlpha((int) interpolator.getInterpolation(frame/(float) frameCount) * deltaAlpha + startAlpha);
        if (animate) {
            scheduleSelf(this, SystemClock.uptimeMillis() + frameDuration);
        }
    }
}
