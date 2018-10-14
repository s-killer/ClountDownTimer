package killer.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {
    static final long DefaultStartingTime = 10000; //10sec
    private static final long START_TIME_IN_MILLIS = DefaultStartingTime;

    //    TODO Accept Input from user & store it in  START_TIME_IN_MILLIS


    private TextView CountDown_Tv;
    private Button StartPauseButton;
    private Button ResetButton;
    TimePicker TimePicker;

    private CountDownTimer MyCountDownTimer;
    private boolean TimerRunning;
    int numberOfSeconds = (int) (START_TIME_IN_MILLIS / 1000); // Ex : 20000/1000 = 20

    ProgressBar MyProgressBar;
    // 100/20 = 5, for each second multiply this, for sec 1 progressPercentage = 1x5 =5,
    // for sec 5 progressPercentage = 5x5 = 25, for sec 20 progressPercentage = 20x5 =100
    int factor = 100 / numberOfSeconds;
    //        Initially TimeLeftInMillis will be same as START_TIME_IN_MILLIS
    private long TimeLeftInMillis = START_TIME_IN_MILLIS, remainingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        CountDown_Tv = findViewById(R.id.TimerTV);

        StartPauseButton = findViewById(R.id.StartPauseButton);
        ResetButton = findViewById(R.id.ResetButton);
        MyProgressBar = findViewById(R.id.circular_progress_bar);

        TimePicker = findViewById(R.id.TimePicker);
        //Get a new instance of Calendar
        final Calendar c = Calendar.getInstance();
        int hr = c.get(Calendar.HOUR_OF_DAY); //Current Hour
        int min = c.get(Calendar.MINUTE); //Current Minute
        int sec = c.get(Calendar.SECOND); //Current Second

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

        //Display the TimePicker initial time
        CountDown_Tv.setText(" " + hr + ":" + min + ":" + sec);


        //Set a TimeChangedListener for TimePicker widget
        TimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //Display the new time to app interface
                CountDown_Tv.setText("Time changed\nH:M | " + hourOfDay + ":" + minute);
            }
        });


//        showDialog(1);
    }

//    protected Dialog onCreateDialog(int id) {
//
//        // Get the calander
//        Calendar c = Calendar.getInstance();
//
//        // From calander get the year, month, day, hour, minute
////        int year = c.get(Calendar.YEAR);
////        int month = c.get(Calendar.MONTH);
////        int day = c.get(Calendar.DAY_OF_MONTH);
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//        int minute = c.get(Calendar.MINUTE);
//
//
//        // Open the timepicker dialog
//        return new TimePickerDialog(TimerActivity.this, time_listener, hour,
//                minute, false);
//
//
//    }
//
//    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {
//
//        @Override
//        public void onTimeSet(TimePicker view, int hour, int minute) {
//            // store the data in one string and set it to text
//            String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
//            CountDown_Tv.setText(time1);
//        }
//    };


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
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                int progressPercentage = (numberOfSeconds - secondsRemaining) * factor;
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
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

//        String newtime = hours + ":" + minutes + ":" + seconds;
        remainingTime = START_TIME_IN_MILLIS - TimeLeftInMillis;

        CountDown_Tv.setText(timeLeftFormatted);

    }

}