package com.example.alexk.group3_hw02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Stats extends AppCompatActivity implements DoMathAsync.IMath{

    Double [] doubles;
    Double correctRate;
    TextView textView_percent;
    TextView textView_finished;
    ProgressBar progressBar;
    Button button_tryAgain;
    Button button_quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        textView_percent = findViewById(R.id.textView_percent);
        textView_finished = findViewById(R.id.textView_finish);
        progressBar = findViewById(R.id.progressBar_correct);
        button_quit = findViewById(R.id.button_quitStats);
        button_tryAgain = findViewById(R.id.button_tryAgain);

        if (getIntent() != null && getIntent().getExtras() != null) {
            doubles =  ((Double[]) getIntent().getSerializableExtra("answerList"));
            new DoMathAsync(Stats.this).execute(doubles);
        }

        button_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Stats.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        button_tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public void handleData(Double mathAnswer) {
        correctRate = mathAnswer;
        textView_percent.setText(mathAnswer.toString() + "%");
        progressBar.setMax(100);
        progressBar.setProgress(mathAnswer.intValue());
        if (correctRate == 100.0) {
            textView_finished.setText("Wow! You got 100%! You are the ultimate master of knowledge!");
        }
    }
}
