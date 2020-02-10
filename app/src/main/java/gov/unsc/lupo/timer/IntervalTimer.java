package gov.unsc.lupo.timer;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IntervalTimer extends Activity {
    private EditText act, rest, reps;
    private boolean running; // track if cdt is running
    private CountDownTimer cdt;
    private int actOriginal, restOriginal, repsOriginal, loops;

    /*
        A timer with hours, minutes, seconds.  The edittexts are set to numbers only.
        Once timer is started, you can pause or reset.  You can't edit the numbers while timer is running.
     */
    @Override
    // super is called in other onCreate method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    private void initLayout() {
        // back button
        setContentView(R.layout.activity_interval_timer);
        findViewById(R.id.tBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        act = findViewById(R.id.etAct);
        rest = findViewById(R.id.etRest);
        reps = findViewById(R.id.etReps);
    }

    public void editClick(View v) {
        if (v.isEnabled())
            ((EditText) v).setText("");
    }

    public void startClick(View v) {
        if (running) {
            running = false;
            cdt.cancel();

            ((Button) v).setText("Start");
            return;
        }

        for (EditText et : inputs())
            et.setEnabled(false);

        ((Button) v).setText("Pause");

        running = true;
        startTimer();
    }

    // amount
    private int amt(EditText et)
    {
        try
        {
            return Integer.parseInt(et.getText().toString());
        }
        catch(NumberFormatException e)
        {
            return 1;
        }
    }

    private void countDownDone() {
        if(loops / 2 < repsOriginal)
        {
            if(loops % 2 == 0) {
                if(amt(reps) - 1 == 0)
                    countDown(rest, 0);
                else
                    countDown(rest, restOriginal);
            }
            else {
                countDown(act, actOriginal);
                reps.setText(fmt(amt(reps) - 1)); // string allocation breaks without ""
            }
            loops++;
        }
        else {
            assert loops == repsOriginal * (actOriginal + restOriginal);
            loops = 0;
            resetClick(null);
        }
    }

    private void startTimer() {
        actOriginal = amt(act);
        restOriginal = amt(rest);
        repsOriginal = amt(reps);
        countDownDone();
    }

    private String toSecs(long millis)
    {
        return fmt((int) (millis / 1000));
    }

    private String fmt(int num)
    {
        return String.format(Locale.US, "%02d", num);
    }

    private void countDown(final EditText et, final int reset)
    {
        cdt = new CountDownTimer(amt(et) * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                et.setText(toSecs(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                et.setText(fmt(reset));
                countDownDone();
            }
        };
        cdt.start();
    }

    private List<EditText> inputs() {
        List<EditText> output = new ArrayList<>();
        output.add(act);
        output.add(rest);
        output.add(reps);
        return output;
    }

    public void resetClick(View v) {
        if(cdt != null)
            cdt.cancel();
        running = false;

        for (EditText et : inputs()) {
            et.setText("00");
            et.setEnabled(true);
        }

        ((Button) findViewById(R.id.tStartButton)).setText("Start");

    }

}
