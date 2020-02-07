package gov.unsc.lupo.timer;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class Timer extends Activity {

    private EditText etHours, etMinutes, etSeconds;
    private int hours, mins, seconds;
    private boolean running;
    private CountDownTimer cdt;


    /*
        A timer with hours, minutes, seconds.  The edittexts are set to numbers only.
        Once timer is started, you can pause or reset.  You can't edit the numbers while timer is running.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // back button
        findViewById(R.id.tBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etHours = findViewById(R.id.tEditHours);
        etMinutes = findViewById(R.id.tEditMinutes);
        etSeconds = findViewById(R.id.tEditSeconds);
    }

    public void editClick(View v) {
        if (v.isEnabled())
            ((EditText) v).setText("");
    }

    public void startClick(View v) {
        if (!numberIsValid())
            return;
        if (running) {
            running = false;
            cdt.cancel();

            ((Button) v).setText("Start");
            return;
        }

        etHours.setEnabled(false);
        etMinutes.setEnabled(false);
        etSeconds.setEnabled(false);

        ((Button) v).setText("Pause");

        running = true;
        startTimer();
    }

    private void startTimer() {
        int time = (hours * 3600) + (mins * 60) + seconds;
        cdt = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                resetClick(null);
            }
        };
        cdt.start();
    }

    private void updateTimer(int currentTime) {
        if (!running)
            return;
        seconds = currentTime % 60;
        currentTime /= 60;
        mins = currentTime % 60;
        currentTime /= 60;
        hours = currentTime;

        etHours.setText(String.format(Locale.US, "%02d", hours));
        etMinutes.setText(String.format(Locale.US, "%02d", mins));
        etSeconds.setText(String.format(Locale.US, "%02d", seconds));
    }

    private boolean numberIsValid() {
        try {
            hours = Integer.parseInt(etHours.getText().toString());
            mins = Integer.parseInt(etMinutes.getText().toString());
            seconds = Integer.parseInt(etSeconds.getText().toString());

            if (seconds >= 60 || mins >= 60 || hours < 0 || mins < 0 || seconds < 0) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void resetClick(View v) {
        cdt.cancel();
        running = false;

        etHours.setText("00");
        etMinutes.setText("00");
        etSeconds.setText("00");

        etHours.setEnabled(true);
        etMinutes.setEnabled(true);
        etSeconds.setEnabled(true);

        ((Button) findViewById(R.id.tStartButton)).setText("Start");
    }

}
