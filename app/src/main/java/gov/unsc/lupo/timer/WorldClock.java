package gov.unsc.lupo.timer;

import android.app.Activity;
import android.os.Bundle;

import java.util.concurrent.ExecutionException;


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
        System.out.println("JSON string result: " + JSON);
        String timeDate = JSON.substring(JSON.indexOf("<formatted>") + "<formatted>".length(), JSON.indexOf("</formatted>"));
        System.out.println("Formatted time: " + timeDate);
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
