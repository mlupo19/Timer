package gov.unsc.lupo.timer;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UpdateTask extends AsyncTask<String, String, String> {
    protected String doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            URLConnection request = url.openConnection();
            request.connect();
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader((InputStream) request.getContent()));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
        catch (IOException e) {
            Log.e("IOException", e.toString());
        }
        return null;
    }

}