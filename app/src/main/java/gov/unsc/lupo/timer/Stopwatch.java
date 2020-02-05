package gov.unsc.lupo.timer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Stopwatch extends Activity {



    TextView timeDisplay, lapDisplay;
    Button start, end, lap, pause;
    Handler h;
    long startTime, time, holdTime, uTime = 0;
    int sec, min, milliSec;
    List<String> laps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);



        findViewById(R.id.sBackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        timeDisplay = findViewById(R.id.sTimeLabel);
        start = findViewById(R.id.sStartButton);
        end = findViewById(R.id.sEndButton);
        lap = findViewById(R.id.sLapButton);
        pause = findViewById(R.id.sPauseButton);
        lapDisplay = findViewById(R.id.sLapText);
        laps = new ArrayList<>();

        timeDisplay.setText("" + 0 + ":"
                + String.format("%02d", 0) + ":"
                + String.format("%03d", 0));
        h = new Handler();
    }

    public Runnable r = new Runnable() {
        @Override
        public void run() {
            time = (SystemClock.uptimeMillis() - startTime);
            uTime = time + holdTime;
            sec = (int) uTime/1000;
            min = sec / 60;
            sec = sec % 60;
            milliSec = (int) uTime % 1000;
            timeDisplay.setText("" + min + ":"
                    + String.format("%02d", sec) + ":"
                    + String.format("%03d", milliSec));
            h.postDelayed(this, 0);
        }
    };

    public void startClick(View v){
        startTime = SystemClock.uptimeMillis();
        h.post(r);
        pause.setEnabled(true);
        end.setText("End");
    }
    public void endClick(View v){
        if (end.getText().toString().equals("End")){
            holdTime = 0;
            startTime = SystemClock.uptimeMillis();
            h.removeCallbacks(r);
            timeDisplay.setText("" + 0 + ":"
                    + String.format("%02d", 0) + ":"
                    + String.format("%03d", 0));
            pause.setEnabled(false);
            end.setText("Reset");
        } else {
            laps.clear();
            lapDisplay.setText("");
        }

    }
    public void lapClick(View v){
        laps.add(timeDisplay.getText().toString());
        updateLaps();
    }
    public void pauseClick(View v){
        holdTime += time;
        h.removeCallbacks(r);
        pause.setEnabled(false);
    }

    public void updateLaps(){
        lapDisplay.setText(lapDisplay.getText().toString() + "\n" + laps.get(laps.size()-1));
    }
}
