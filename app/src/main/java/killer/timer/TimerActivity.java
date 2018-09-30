package killer.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 15000; //20sec

//    TODO Accept Input from user & store it in  START_TIME_IN_MILLIS
    int progress;

    private TextView CountDown_Tv;
    private Button StartPauseButton;
    private Button ResetButton;

    private CountDownTimer MyCountDownTimer;
    private boolean TimerRunning;

//        Initially TimeLeftInMillis will be same as START_TIME_IN_MILLIS
    private long TimeLeftInMillis = START_TIME_IN_MILLIS,remainingTime;

    ProgressBar MyProgressBar;
    private int  ProgressBarStatus;
    int numberOfSeconds = (int) (START_TIME_IN_MILLIS/1000); // Ex : 20000/1000 = 20
    int factor = 100/numberOfSeconds; // 100/20 = 5, for each second multiply this, for sec 1 progressPercentage = 1x5 =5, for sec 5 progressPercentage = 5x5 = 25, for sec 20 progressPercentage = 20x5 =100


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        CountDown_Tv = findViewById(R.id.TimerTV);

        StartPauseButton = findViewById(R.id.StartPauseButton);
        ResetButton = findViewById(R.id.ResetButton);
        MyProgressBar = findViewById(R.id.circular_progress_bar);

        StartPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();

        MyProgressBar.setVisibility(View.VISIBLE);
        MyProgressBar.setProgress(0);
        MyProgressBar.setMax(100);


    }

    private void startTimer() {
//        Creating CountDownTimer for START_TIME_IN_MILLIS milliseconds and 1000(1sec) interval
        MyCountDownTimer = new CountDownTimer(START_TIME_IN_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
          /*  ONTick
                  increase progress -> Progressbar
                  update CountDown_Tv

          */
                TimeLeftInMillis = millisUntilFinished;
                updateCountDownText(); //  Updating CountDown_Tv
                /*for incrementing progressbar every second calculating progress*/
//                progress = (int) (START_TIME_IN_MILLIS / (1 * 100));
//                remainingTime=START_TIME_IN_MILLIS-millisUntilFinished;
//                remainingTime=millisUntilFinished;
//                progress = (int) (((START_TIME_IN_MILLIS - remainingTime)/START_TIME_IN_MILLIS) * 100);
//                ProgressBarStatus +=progress; // Incrementing progress
//                MyProgressBar.setProgress(ProgressBarStatus);

                int secondsRemaining = (int) (millisUntilFinished / 1000);
                int progressPercentage = (numberOfSeconds-secondsRemaining) * factor ;
                MyProgressBar.setProgress(progressPercentage);

            }

            @Override
            public void onFinish() {
                TimerRunning = false;
                CountDown_Tv.setText("00:00");
                MyProgressBar.setProgress(100);
                StartPauseButton.setText("Start");
                StartPauseButton.setVisibility(View.INVISIBLE);
                ResetButton.setVisibility(View.VISIBLE);
            }
        }.start();

        TimerRunning = true;
        StartPauseButton.setText("Pause");
        ResetButton.setVisibility(View.INVISIBLE);


    }

    private void pauseTimer() {
        MyCountDownTimer.cancel();
        TimerRunning = false;
        StartPauseButton.setText("Resume");
        ResetButton.setVisibility(View.VISIBLE);
        MyProgressBar.clearAnimation();
    }

    private void resetTimer() {
        TimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        ResetButton.setVisibility(View.INVISIBLE);
        StartPauseButton.setVisibility(View.VISIBLE);
        MyProgressBar.setProgress(0);
        StartPauseButton.setText("Start");
    }

    private void updateCountDownText() {
        int minutes = (int) (TimeLeftInMillis / 1000) / 60;
        int seconds = (int) (TimeLeftInMillis / 1000) % 60;
//        int millis = (int) (TimeLeftInMillis / 1000);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

//        int seconds = (int) (millisUntilFinished / 1000) % 60;
//        int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
//        int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
//        String newtime = hours + ":" + minutes + ":" + seconds;
//        remainingTime=START_TIME_IN_MILLIS-TimeLeftInMillis;

        CountDown_Tv.setText(timeLeftFormatted);

    }


}