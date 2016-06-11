package io.github.jonestimd.android.quiz.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Statistics {
    private int correctAnswers;
    private int wrongAnswers;
    private int hints;
    private long totalTimeInMillis;

    private long startTime;

    public Statistics() {
        reset();
    }

    public void reset() {
        correctAnswers = 0;
        wrongAnswers = 0;
        hints = 0;
        totalTimeInMillis = 0;
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    public void answer(boolean correct) {
        totalTimeInMillis += System.currentTimeMillis() - startTime;
        if (correct) {
            correctAnswers++;
        }
        else {
            wrongAnswers++;
        }
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public int getHints() {
        return hints;
    }

    public void hint() {
        hints++;
    }

    public float getTotalTimeInSeconds() {
        return totalTimeInMillis / 1000f;
    }

    public float getAverageResponseTimeInSeconds() {
        return totalTimeInMillis / (correctAnswers + wrongAnswers) / 1000f;
    }

    public String toXml() {
        return "<quiz>\n" +
                "  <correct>" + correctAnswers + "</correct>\n" +
                "  <wrong>" + wrongAnswers + "</wrong>\n" +
                "  <hints>" + hints + "</hints>\n" +
                "  <avg-response-time>" + getAverageResponseTimeInSeconds() + "</avg-response-time>\n" +
                "</quiz>";
    }

    public void write(File file) throws IOException {
        FileOutputStream statsFile = new FileOutputStream(file, true);
        try {
            statsFile.write(toXml().getBytes());
        }
        finally {
            statsFile.close();
        }
    }
}
