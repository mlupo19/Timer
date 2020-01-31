package gov.unsc.lupo.timer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class WorldClock extends Activity {

    public String sendGetRequest(String link) {
        try {
            return new UpdateTask().execute(link).get();
        }
        catch (ExecutionException | InterruptedException e) {

        }
        return null;
    }

    public void parseJSON(String JSON) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_clock);

        String baseURL = "http://api.timezonedb.com/v2.1/get-time-zone?key=EJEFWNO52XKX&format=xml&by=position";
        String lat = "&lat=40.689247";
        String lon = "&lng=-74.044502";
        parseJSON(sendGetRequest(baseURL + lat + lon));

    }
}
