package com.example.thequizapp;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GetStarted extends AppCompatActivity {

    TextView questionLabel, questionCountLabel, scoreLabel;
    EditText answerEdt;
    Button submitButton;
    ProgressBar progressBar;
    ArrayList<QuestionModel> questionModelArraylist;

    int currentPosition = 0;
    int numberOfCorrectAnswer = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else {
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.night_mode) {
            int nightMode = AppCompatDelegate.getDefaultNightMode();

            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode
                        (AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode
                        (AppCompatDelegate.MODE_NIGHT_YES);
            }

            recreate();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);


        questionCountLabel = findViewById(R.id.noQuestion);
        questionLabel = findViewById(R.id.question);
        scoreLabel = findViewById(R.id.score);

        answerEdt = findViewById(R.id.answer);
        submitButton = findViewById(R.id.submit);
        progressBar = findViewById(R.id.progress);

        questionModelArraylist = new ArrayList<>();

        setUpQuestion();

        setData();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer();
            }
        });

        answerEdt.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                Log.e("event.getAction()", event.getAction() + "");
                Log.e("event.keyCode()", keyCode + "");
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    checkAnswer();
                    return true;
                }
                return false;
            }
        });

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public void checkAnswer() {
        String answerString = answerEdt.getText().toString().trim();


        if (answerString.equalsIgnoreCase(questionModelArraylist.get(currentPosition).getAnswer())) {
            numberOfCorrectAnswer++;


            new SweetAlertDialog(GetStarted.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Good job!")
                    .setContentText("Right Answer")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            currentPosition++;

                            setData();
                            answerEdt.setText("");
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();


        } else {

            new SweetAlertDialog(GetStarted.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Wrong Answer")
                    .setContentText("The right answer is : " + questionModelArraylist.get(currentPosition).getAnswer())
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismiss();

                            currentPosition++;

                            setData();
                            answerEdt.setText("");
                        }
                    })
                    .show();
        }


        int x = ((currentPosition + 1) * 100) / questionModelArraylist.size();

        progressBar.setProgress(x);


    }


    public void setUpQuestion() {


        questionModelArraylist.add(new QuestionModel("What is 6 * 7 ? ", "42"));
        questionModelArraylist.add(new QuestionModel("What is 18 - 5 ? ", "13"));
        questionModelArraylist.add(new QuestionModel("What is 123 + 17? ", "140"));
        questionModelArraylist.add(new QuestionModel("What is 8 / 8 ? ", "1"));
        questionModelArraylist.add(new QuestionModel("What is 69 * 47 ? ", "3243"));
        questionModelArraylist.add(new QuestionModel("What is  the volume of a box with the dimensions of 10ft. by 5ft. by 6ft? ",
                "300 cubic ft"));
        questionModelArraylist.add(new QuestionModel("What is 45678 + 9876 ? ", "55554"));
        questionModelArraylist.add(new QuestionModel("What is 3^(4)÷3^(2) ? ", "3"));
        questionModelArraylist.add(new QuestionModel("What is  8.563 + 4.8292 ? ", "42"));
        questionModelArraylist.add(new QuestionModel("I am an odd number. Take away one letter and I become even. What number am I?", "7"));
        questionModelArraylist.add(new QuestionModel("What is (7+7) * (7 + (1/7)) ? ", "100"));
        questionModelArraylist.add(new QuestionModel("Which 3 numbers have the same answer whether they’re added or multiplied together?", "1,2 and 3"));
        questionModelArraylist.add(new QuestionModel("What is 12 / 3 ? ", "4"));
        questionModelArraylist.add(new QuestionModel(" How many feet are in a mile? ", "5280"));
        questionModelArraylist.add(new QuestionModel("What is -15+ (-5x) =0 ? ", "-3"));


    }

    public void setData() {


        if (questionModelArraylist.size() > currentPosition) {

            questionLabel.setText(questionModelArraylist.get(currentPosition).getQuestionString());

            scoreLabel.setText("Score :" + numberOfCorrectAnswer + "/" + questionModelArraylist.size());
            questionCountLabel.setText("Question No : " + (currentPosition + 1));


        } else {


            new SweetAlertDialog(GetStarted.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("You have successfully completed the quiz")
                    .setContentText("Your score is : " + numberOfCorrectAnswer + "/" + questionModelArraylist.size())
                    .setConfirmText("Restart")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            sDialog.dismissWithAnimation();
                            currentPosition = 0;
                            numberOfCorrectAnswer = 0;
                            progressBar.setProgress(0);
                            setData();
                        }
                    })
                    .setCancelText("Close")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            sDialog.dismissWithAnimation();
                            finish();
                        }
                    })
                    .show();

        }

    }


}