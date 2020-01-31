package gov.unsc.lupo.timer;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Timer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);


    }

    public void editClick(View v) {
        ((EditText) v).setText("");
    }

    public void startClick(View v) {

    }

}
