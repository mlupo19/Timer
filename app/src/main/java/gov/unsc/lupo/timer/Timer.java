package gov.unsc.lupo.timer;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class Timer extends Activity {

    private EditText etHours, etMinutes, etSeconds;
    private int hours, mins, seconds;
    private boolean running;
    private CountDownTimer cdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        etHours = findViewById(R.id.tEditHours);
        etMinutes = findViewById(R.id.tEditMinutes);
        etSeconds = findViewById(R.id.tEditSeconds);
    }

    public void editClick(View v) {
        if (v.isEnabled())
            ((EditText) v).setText("");
    }

    public void startClick(View v) {
        if (!numberIsValid() || running)
            return;

        etHours.setEnabled(false);
        etMinutes.setEnabled(false);
        etSeconds.setEnabled(false);

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
                running = false;
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
    }

}
