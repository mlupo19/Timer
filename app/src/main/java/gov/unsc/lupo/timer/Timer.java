package gov.unsc.lupo.timer;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Timer extends Activity {

    private EditText etHours, etMinutes, etSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        etHours = findViewById(R.id.tEditHours);
        etMinutes = findViewById(R.id.tEditMinutes);
        etSeconds = findViewById(R.id.tEditSeconds);
    }

    public void editClick(View v) {
        ((EditText) v).setText("");
    }

    public void startClick(View v) {
        if (!numberIsValid())
            return;

        etHours.setEnabled(false);
        etMinutes.setEnabled(false);
        etSeconds.setEnabled(false);


    }

    private boolean numberIsValid() {
        return false;
    }

    public void resetClick(View v) {

    }

}
