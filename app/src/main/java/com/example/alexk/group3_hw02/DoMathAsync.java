package com.example.alexk.group3_hw02;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by AlexK on 3/10/2018.
 */

public class DoMathAsync extends AsyncTask<Double, Void, Double> {

    IMath iMath;

    public DoMathAsync(IMath iMath) { this.iMath = iMath; }

    @Override
    protected Double doInBackground(Double... correct) {
        Double[] doubles = new Double[correct.length];
        doubles[0] = correct[0];
        doubles[1] = correct[1];

        double totalQs = doubles[1];
        double correctAnswers = doubles[0];
        Log.d("demo", "Correct Answers: " + correctAnswers);

        double correctRate = (correctAnswers/totalQs) * 100;
        return correctRate;
    }

    @Override
    protected void onPostExecute(Double rate) {
        iMath.handleData(rate);
    }

    public static interface IMath {
        public void handleData(Double mathAnswer);
    }

}
