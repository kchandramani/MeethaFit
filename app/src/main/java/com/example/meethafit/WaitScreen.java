package com.example.meethafit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Locale;

public class WaitScreen extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 5000;

    private TextView mTextViewCountDown;
    private ToggleButton mButtonStartPause;
    //private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    public int argument1;
    private TextView waitTexting;
    //public int argument2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_screen);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        Intent intent = getIntent();
        argument1 = intent.getIntExtra("ARGUMENT_1_KEY",0);
       // argument2 = intent.getIntExtra("ARGUMENT_2_KEY", 0);

        mTextViewCountDown = findViewById(R.id.waitCountdown);
        waitTexting = findViewById(R.id.waitText);
        //waitTexting.setText("Next : " + argument1);
        waitTexting.setText("RELAX");
        mButtonStartPause = findViewById(R.id.waitToggle);
        //mButtonStartPause.setText("");
        if (!mTimerRunning) {
            startTimer();
        }
        // mButtonReset = findViewById(R.id.button_reset);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

    }
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mCountDownTimer.cancel();
                //mButtonStartPause.setText("Start");
                //mButtonStartPause.setVisibility(View.INVISIBLE);
                //mGifSrc.setImageResource(gifNameList[n]);
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                // Pass arguments (data) with the intent

                intent.putExtra("ARGUMENT_1_KEY", argument1);
                startActivity(intent);
                finish();
                // mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        //mButtonStartPause.setText("pause");
        //mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        //mButtonStartPause.setText("Start");
        //mButtonReset.setVisibility(View.VISIBLE);
    }

//    private void resetTimer() {
//        mTimeLeftInMillis = START_TIME_IN_MILLIS;
//        updateCountDownText();
//        //mButtonReset.setVisibility(View.INVISIBLE);
//        mButtonStartPause.setVisibility(View.VISIBLE);
//    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }
}