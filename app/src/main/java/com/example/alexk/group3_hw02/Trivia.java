package com.example.alexk.group3_hw02;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ListIterator;

public class Trivia extends AppCompatActivity implements GetImageAsync.IPic {

    ArrayList<Question> questionArrayList;
    ListIterator<Question> listIterator;
    GetImageAsync imageAsync;
    Double correctRate;
    Double incorrectRate;
    TextView textView_questionNum;
    TextView textView_questionTitle;
    TextView textView_imageLoader;
    TextView textView_countDownText;
    ListView listView;
    ImageView imageView;
    Bitmap passedBit;
    ProgressBar progressBar;
    Button button_next;
    Button button_quit;


//    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        questionArrayList = new ArrayList<Question>();
        imageAsync = null;
        textView_questionNum = findViewById(R.id.textView_questionNum);
        textView_questionTitle = findViewById(R.id.textView_questionTitle);
        textView_imageLoader = findViewById(R.id.textView_imageProgress);
        textView_countDownText = findViewById(R.id.textView_timer);
        listView = findViewById(R.id.listView);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar_trivia);
        button_next = findViewById(R.id.button_nextTrivia);
        button_quit = findViewById(R.id.button_quitTrivia);
        correctRate = 0.0;
        incorrectRate = 0.0;
//        index = 0;



        if (getIntent() != null && getIntent().getExtras() != null && questionArrayList.isEmpty()) {
           questionArrayList =  (ArrayList<Question>) getIntent().getExtras().getSerializable("questionList");
        }

        listIterator = questionArrayList.listIterator();
        final CountDownTimer downTimer = new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                textView_countDownText.setText("Time Left: " + millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {
                Intent intent = new Intent(Trivia.this, Stats.class);
                int intTotal = questionArrayList.size();
                Double[] doublesList = {correctRate, (double) questionArrayList.size()};
                intent.putExtra("answerList", doublesList);
                startActivityForResult(intent, 420);
            }
        }.start();

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listIterator = questionArrayList.listIterator(0);
                Intent intent = new Intent(Trivia.this, Stats.class);
                Double[] doublesList = {correctRate, (double) questionArrayList.size()};
                intent.putExtra("answerList", doublesList);
                downTimer.cancel();
                startActivityForResult(intent, 420);
            }
        });

        button_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downTimer.cancel();
                Intent intent = new Intent(Trivia.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        if (textView_questionNum.getText().toString().equals("Q#")) {
            final Question fQuestion = listIterator.next();
            textView_questionNum.setText("Q" + Integer.toString(listIterator.previousIndex() + 1));
            textView_questionTitle.setText(fQuestion.getText());
            imageAsync = (GetImageAsync) new GetImageAsync(Trivia.this).execute(fQuestion.getUrlToImage());
            final QuestionAdapter questionAdapter = new QuestionAdapter(Trivia.this, R.layout.question_option_item, fQuestion.getChoices());
            listView.setAdapter(questionAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    imageAsync.cancel(true);
                    textView_imageLoader.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

                    if ((listIterator.previousIndex() <= 14)) {

                        Question question = listIterator.next();

                        imageView.setImageResource(0);
                        if (question.urlToImage != null) {
                            textView_imageLoader.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            imageAsync = (GetImageAsync) new GetImageAsync(Trivia.this).execute(question.getUrlToImage());
                        }
                        textView_questionNum.setText("Q" + Integer.toString(listIterator.previousIndex() + 1));
                        textView_questionTitle.setText(question.getText());

                        QuestionAdapter questionAdapter = new QuestionAdapter(Trivia.this, R.layout.question_option_item, question.getChoices());
                        listView.setAdapter(questionAdapter);
                        if (listIterator.previousIndex() == 1) {
                            if (fQuestion.getAnswer() == (position + 1)) {
                                correctRate++;
                            } else {
                                incorrectRate++;
                            }


                        } else if (listIterator.previousIndex() > 1) {

                            if (questionArrayList.get((listIterator.previousIndex() - 1)).getAnswer() == (position + 1)) {
                                correctRate++;
                            } else {
                                incorrectRate++;
                            }
                        }

                    } else if (!listIterator.hasNext()){

                        imageAsync.cancel(true);
                        int finalIndex = questionArrayList.size() - 1;
                        Question question = questionArrayList.get(finalIndex);

                        if ((correctRate + incorrectRate) < questionArrayList.size()) {
                            if (question.getAnswer() == (position + 1)) {
                                correctRate++;
                            } else {
                                incorrectRate++;
                            }
                            button_next.setEnabled(true);
                        }

                    }
                }
            });
        }

    }

    @Override
    public void handlePic(Bitmap data) {
        textView_imageLoader.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        if (data != null) {
            passedBit = data;
            imageView.setImageBitmap(passedBit);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 420) {
            if (resultCode == RESULT_OK) {
                Intent intent = getIntent();
                intent.putExtra("questionList", questionArrayList);
                startActivity(intent);
                finish();
            } else if (resultCode == RESULT_CANCELED) {
            }
        }

    }
}
