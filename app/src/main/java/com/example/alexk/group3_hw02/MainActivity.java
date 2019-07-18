package com.example.alexk.group3_hw02;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetAPIAsync.IData {

    ArrayList<Question> questionArrayList;
    ProgressBar progressBar;
    TextView textView_parsingStatus;
    ImageView imageView;
    Button buttonExit;
    Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar_parse);
        textView_parsingStatus = findViewById(R.id.textView_parsingStatus);
        imageView = findViewById(R.id.imageView_logo);
        buttonExit = findViewById(R.id.button_exit);
        buttonStart = findViewById(R.id.button_start);

        if (isConnected()) {
            Toast.makeText(MainActivity.this, "Successful Connections", Toast.LENGTH_SHORT).show();
            new GetAPIAsync(MainActivity.this).execute("http://dev.theappsdr.com/apis/trivia_json/index.php");
            progressBar = findViewById(R.id.progressBar_parse);
            imageView = findViewById(R.id.imageView_logo);
            buttonExit = findViewById(R.id.button_exit);
            buttonStart = findViewById(R.id.button_start);
        } else {
            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Trivia.class);
                intent.putExtra("questionList", questionArrayList);
                startActivity(intent);
            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAndRemoveTask();
            }
        });
    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Log.d("demo", "Network type = " + networkInfo.getType());

        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    @Override
    public void handleData(ArrayList<Question> data) {
        questionArrayList = new ArrayList<>(data);
        progressBar.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);
        buttonStart.setEnabled(true);
        textView_parsingStatus.setText(MainActivity.this.getResources().getString(R.string.textView_parsingStatusFinal));
    }
}
