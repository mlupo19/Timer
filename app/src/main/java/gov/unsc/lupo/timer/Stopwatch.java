package gov.unsc.lupo.timer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Stopwatch extends Activity {

    TextView timeDisplay;
    Button start, end, lap;
    Handler h;
    long startTime, time = 0;
    int sec, min, milliSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        timeDisplay = findViewById(R.id.sTimeLabel);
        start = findViewById(R.id.sStartButton);
        end = findViewById(R.id.sEndButton);
        lap = findViewById(R.id.sLapButton);

        h = new Handler();
    }

    public Runnable r = new Runnable() {
        @Override
        public void run() {
            time = SystemClock.uptimeMillis() - startTime;
            sec = (int) time/1000;
            min = sec / 60;
            sec = sec % 60;
            milliSec = (int) time % 1000;
            timeDisplay.setText("" + min + ":"
                    + String.format("%02d", sec) + ":"
                    + String.format("%03d", milliSec));

        }
    };

    public void startClick(View v){
        startTime = SystemClock.uptimeMillis();
        h.post(r);
    }
    public void endClick(View v){

    }
    public void lapClick(View v){

    }
    public void pauseClick(View v){
        h.removeCallbacks(r);
    }
}
