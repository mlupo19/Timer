package gov.unsc.lupo.timer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WorldClock extends Activity {

    public void sendGetRequest(String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setInstanceFollowRedirects(false);

            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                StringBuffer response = new StringBuffer();
                // print result
                System.out.println("JSON String Result " + response.toString());
                //GetAndPost.POSTRequest(response.toString());
            } else {
                System.out.println("GET NOT WORKED");
            }

        }
        catch (IOException e) {
            Log.e("IOException", e.toString());
        }
    }

    public void parseJSON(String JSON) {
        JSONParser
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_clock);
        String baseURL = "http://api.timezonedb.com/v2.1/get-time-zone?key=EJEFWNO52XKX&format=json&by=position";
        String lat = "&lat=40.689247";
        String lon = "&lng=-74.044502";
        parseJSON(sendGetRequest(baseURL + lat + lon));


    }
}
