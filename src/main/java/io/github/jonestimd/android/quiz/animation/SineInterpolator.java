package io.github.jonestimd.android.quiz.animation;

import android.view.animation.Interpolator;

public class SineInterpolator implements Interpolator {
    public float getInterpolation(float input) {
        return (float) Math.sin(input * (float) Math.PI);
    }
}
