package com.example.meethafit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 10000;

    private TextView mTextViewCountDown;
    private ToggleButton mButtonStartPause;
    public Button mButtonNext;
    public Button mButtonPrev;
    public pl.droidsonroids.gif.GifImageView mGifSrc;
    //private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private boolean pevClicked = false;

    //int[] mario = new int[]{R.drawable.image_a,R.drawable.image_b,R.drawable.image_c};
    int[] gifNameList = new int[]{R.drawable.push_up,
            R.drawable.wide_push_up,
            R.drawable.knee_push_up,
            R.drawable.push_up_streached,
            R.drawable.staggered_arm_push_up,
            R.drawable.tricep_dips,
            R.drawable.push_up,
            R.drawable.wide_push_up,
            R.drawable.knee_push_up,
            R.drawable.push_up_streached,
            R.drawable.staggered_arm_push_up,
            R.drawable.tricep_dips,
            R.drawable.asymmetrical_pushup_right,
            R.drawable.asymmetrical_pushup_left,
            R.drawable.plank_exercise,
            R.drawable.side_plank_exercise_right,
            R.drawable.side_plank_exercise_left,
            R.drawable.plank_kick_throughs_exercise,
            R.drawable.plank_exercise,
            R.drawable.side_plank_exercise_right,
            R.drawable.side_plank_exercise_left,
            R.drawable.plank_kick_throughs_exercise,
            R.drawable.crunches_exercise,
            R.drawable.frog_crunches_exercise,
            R.drawable.sit_up_exercise,
            R.drawable.sprinter_crunch_exercise,
            R.drawable.straight_leg_raise_exercise,
            R.drawable.windshield_wipers_exercise,
            R.drawable.wall_bridge_exercise,
            R.drawable.crunches_exercise,
            R.drawable.frog_crunches_exercise,
            R.drawable.sit_up_exercise,
            R.drawable.sprinter_crunch_exercise,
            R.drawable.straight_leg_raise_exercise,
            R.drawable.windshield_wipers_exercise,
            R.drawable.wall_bridge_exercise,
            R.drawable.cat_back_stretch_exercise,
            R.drawable.back_extensions_exercise,
            R.drawable.finger_stretch,
            R.drawable.bicep_stretch
    };
    int gifNameListIndex = 0;

    String[] nameList = {"Push Up ",
            "Wide Push Up",
            "Knee Push Up",
            "Stretch Push Up",
            "Staggered Arm Push Up",
            "Triceps",
            "Push Up",
            "Wide Push Up",
            "Knee Push Up",
            "Stretch Push Up",
            "Staggered Arm Push Up",
            "Triceps",
            "Right Asymmetrical Push-Up",
            "Left Asymmetrical Push-Up",
            "Plank",
            "Side Plank Right",
            "Side Plank Left",
            "Plank Kick Through Exercise",
            "Plank",
            "Side Plank Right",
            "Side Plank Left",
            "Plank Kick Through Exercise",
            "Crunches",
            "Frog Crunches",
            "Sit-Up",
            "Sprinter Crunch",
            "Straight Leg Raise",
            "Windshield Wipers",
            "Wall Bridge",
            "Crunches",
            "Frog Crunches",
            "Sit-Up",
            "Sprinter Crunch",
            "Straight Leg Raise",
            "Windshield Wipers",
            "Wall Bridge",
            "Cat Back Stretch",
            "Back Extension",
            "Finger Stretch",
            "Bicep/Triceps Stretch"
    };

    public int argument1;
    public int argument2;
    public TextView exName;
    public TextView exDisc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        mTextViewCountDown = findViewById(R.id.countdown);
        mGifSrc = findViewById(R.id.header);
        exName = findViewById(R.id.headerName);
        exDisc = findViewById(R.id.headerDisc);



        mButtonStartPause = findViewById(R.id.Toggle);
        //mButtonStartPause.setText("");
        // mButtonReset = findViewById(R.id.button_reset);
        Intent intent = getIntent();
        argument1 = intent.getIntExtra("ARGUMENT_1_KEY", 0);
        if (argument1 >= gifNameList.length) {
            argument1 = 0;
        }


        mGifSrc.setImageResource(gifNameList[argument1]);
        exName.setText(nameList[argument1]);
        //argument2 = intent.getIntExtra("ARGUMENT_2_KEY", 0);
        if (!mTimerRunning) {
            startTimer();
        }

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

        mButtonNext = findViewById(R.id.next_btn);
        // mButtonReset = findViewById(R.id.button_reset);

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextClick();
            }
        });

        mButtonPrev = findViewById(R.id.prev_btn);
        if (argument1 <= 0)
            mButtonPrev.setVisibility(View.INVISIBLE);
        else if (argument1 == gifNameList.length - 1) {
            mButtonNext.setVisibility(View.INVISIBLE);
        }
        // mButtonReset = findViewById(R.id.button_reset);

        mButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevClick();
            }
        });

//        mButtonReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resetTimer();
//            }
//        });

        updateCountDownText();
    }

    public void argumentMapping() {
        int n;
        if (gifNameListIndex >= argument1) {

            n = gifNameListIndex;
        } else
            n = argument1;

        if (pevClicked) {
            argument1--;
            pevClicked = false;
        } else
            argument1++;
    }


    public void nextClick() {
        mCountDownTimer.cancel();
        argumentMapping();

        Intent intent = new Intent(getApplicationContext(), WaitScreen.class);
        // Pass arguments (data) with the intent

        intent.putExtra("ARGUMENT_1_KEY", argument1);
        //intent.putExtra("ARGUMENT_2_KEY", nameListIndex);

        startActivity(intent);
        finish();
        //mGifSrc.setImageResource(R.drawable.uri);
    }

    public void prevClick() {
        pevClicked = true;
        mCountDownTimer.cancel();
        argumentMapping();


        Intent intent = new Intent(getApplicationContext(), WaitScreen.class);
        // Pass arguments (data) with the intent

        intent.putExtra("ARGUMENT_1_KEY", argument1);
        //intent.putExtra("ARGUMENT_2_KEY", nameListIndex);

        startActivity(intent);
        finish();

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
                argumentMapping();
                mTimerRunning = false;
                // mButtonStartPause.setText("Start");
                //mButtonStartPause.setVisibility(View.INVISIBLE);
                resetTimer();
                Intent intent = new Intent(getApplicationContext(), WaitScreen.class);
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

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        //mButtonReset.setVisibility(View.INVISIBLE);
        // mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    /* private void gifData() {
         String[] nameList = {"push_up ",
                 "wide_push_up",
                 "knee_push_up",
                 "push_up_streached",
                 "staggered_arm_push_up",
                 "tricep_dips",
                 "push_up ",
                 "wide_push_up",
                 "knee_push_up",
                 "push_up_streached",
                 "staggered_arm_push_up",
                 "tricep_dips",
                 "asymmetrical_pushup_right",
                 "asymmetrical_pushup_left",
                 "plank_exercise",
                 "side_plank-exercise_right",
                 "side_plank_exercise_left",
                 "plank_kick-throughs_exercise",
                 "plank_exercise",
                 "side_plank-exercise_right",
                 "side_plank_exercise_left",
                 "plank_kick-throughs_exercise",
                 "crunches_exercise",
                 "frog_crunches_exercise",
                 "sit_up_exercise",
                 "sprinter_crunch_exercise",
                 "straight_leg_raise_exercise",
                 "windshield_wipers_exercise",
                 "wall_bridge_exercise",
                 "crunches_exercise",
                 "frog_crunches_exercise",
                 "sit_up_exercise",
                 "sprinter_crunch_exercise",
                 "straight_leg_raise_exercise",
                 "windshield_wipers_exercise",
                 "wall_bridge_exercise",
                 "cat_back_stretch_exercise",
                 "back_extensions_exercise",
                 "finger_stretch",
                 "bicep_stretch"
         };

        /* List<String> gifList = new ArrayList<>();

         // Add values to the list
         gifList.add("dsfghj");


         // Access values using indexing
         int size = gifList.size()-1;
         String lastValue = gifList.get(gifList.size() - 1);
         String firstValue = gifList.get(0);
     }*/
    private void discriptionData() {
        List<String> disList = new ArrayList<>();

        // Add values to the list
        disList.add("dsfghj");


        // Access values using indexing
        int size = disList.size() - 1;
        String lastValue = disList.get(disList.size() - 1);
        String firstValue = disList.get(0);
    }
    /*private void nameData() {
        String[] nameList = {"Push Up ",
                "Wide Push Up",
                "Knee Push Up",
                "Stretch Push Up",
                "Staggered Arm Push Up",
                "Triceps",
                "Push Up",
                "Wide Push Up",
                "Knee Push Up",
                "Stretch Push Up",
                "Staggered Arm Push Up",
                "Triceps",
                "Right Asymmetrical Push-Up",
                "Left Asymmetrical Push-Up",
                "Plank",
                "Side Plank Right",
                "Side Plank Left",
                "Plank Kick Through Exercise",
                "Plank",
                "Side Plank Right",
                "Side Plank Left",
                "Plank Kick Through Exercise",
                "Crunches",
                "Frog Crunches",
                "Sit-Up",
                "Sprinter Crunch",
                "Straight Leg Raise",
                "Windshield Wipers",
                "Wall Bridge",
                "Crunches",
                "Frog Crunches",
                "Sit-Up",
                "Sprinter Crunch",
                "Straight Leg Raise",
                "Windshield Wipers",
                "Wall Bridge",
                "Cat Back Stretch",
                "Back Extension",
                "Finger Stretch",
                "Bicep/Triceps Stretch"
        };

}*/



}