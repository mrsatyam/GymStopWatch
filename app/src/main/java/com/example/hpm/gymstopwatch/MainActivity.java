package com.example.hpm.gymstopwatch;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    Button startButton,stopButton,resetButton;

     TextView tv;
     TextToSpeech textToSpeech;
     CountDownTimer timer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String min = String.format("%02d", millisUntilFinished / 60000);
                int sec = (int) ((millisUntilFinished % 60000) / 1000);

                if (millisUntilFinished < 6000) {
                    tv.setTextColor(Color.RED);
                }
                tv.setText(min + ":" + String.format("%02d", sec));
            }


            @Override
            public void onFinish() {
                CountDownTimer timerRest = new CountDownTimer(11000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        String min = String.format("%02d", millisUntilFinished / 60000);
                        int sec = (int) ((millisUntilFinished % 60000) / 1000);
                        tv.setTextColor(Color.YELLOW);
                        tv.setText(min + ":" + String.format("%02d", sec));

                    }


                    @Override
                    public void onFinish() {
                        tv.setText("00:00");
                        textToSpeech.speak("Go ", TextToSpeech.QUEUE_FLUSH, null);
                        timer.start();
                        tv.setTextColor(Color.BLUE);


                    }
                };
                tv.setText("00:00");
                textToSpeech.speak("REST ", TextToSpeech.QUEUE_FLUSH, null);
                timerRest.start();
            }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         textToSpeech=new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.UK);
                textToSpeech.setPitch(0);
            }
        });
        resetButton=(Button) findViewById(R.id.resetButton);
        startButton= (Button) findViewById(R.id.startButton);
        tv=(TextView)findViewById(R.id.textView);

        stopButton= (Button) findViewById(R.id.stopButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timer.start();
               tv.setTextColor(Color.BLUE);
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();

                tv.setText("00:00");
            }
        });

    }
}
