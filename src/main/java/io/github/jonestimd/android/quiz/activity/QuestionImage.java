package io.github.jonestimd.android.quiz.activity;

import android.content.res.AssetManager;
import android.graphics.Canvas;

public interface QuestionImage {
    void initialize(AssetManager assetManager);
    void draw(Canvas canvas, int width, int height);
}
