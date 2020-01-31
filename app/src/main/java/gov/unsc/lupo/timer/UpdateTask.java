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
            System.out.println("JSON string result: " + response.toString());
//            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
//            httpsURLConnection.setRequestMethod("GET");
//            httpsURLConnection.setInstanceFollowRedirects(false);

//            int responseCode = httpsURLConnection.getResponseCode();
//            if (responseCode == HttpsURLConnection.HTTP_OK) {
//                BufferedReader in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
//                StringBuffer response = new StringBuffer();
//                System.out.println("JSON string result: " + response.toString());
//                return response.toString();
//            }
//            else
//                System.out.println("Failed to get request");

        }
        catch (IOException e) {
            Log.e("IOException", e.toString());
        }
        return null;
    }

}