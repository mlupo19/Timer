package gov.unsc.lupo.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;

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

    public void watchOnClick(View v) {
        startActivity(new Intent(this, Stopwatch.class));
    }

    public void clockOnClick(View v) {
        startActivity(new Intent(this, WorldClock.class));
    }


}
