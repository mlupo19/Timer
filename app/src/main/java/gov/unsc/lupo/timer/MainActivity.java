package gov.unsc.lupo.timer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void timerOnClick(View v) {
        startActivity(new Intent(this, Timer.class));
    }

    public void intervalTimerOnClick(View v) {
        startActivity(new Intent(this, IntervalTimer.class));
    }

    public void watchOnClick(View v) {
        startActivity(new Intent(this, Stopwatch.class));
    }

    public void clockOnClick(View v) {
        startActivity(new Intent(this, WorldClock.class));
    }

}
