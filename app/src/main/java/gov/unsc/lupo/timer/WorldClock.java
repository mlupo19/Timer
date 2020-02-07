package gov.unsc.lupo.timer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;


/*
    The WorldClock starts blank with a back button showing... during this time it is collecting the users current location then
    it is pinging the timezonedb api to gather information regarding its timezone... this is done in UpdateTask. In android tasks that do not
    respond immediately like web requests must be done on a separate thread which is why UpdateTask is needed. After the api responds
    the information is formatted and the ISO time date is turned into a date with the current day and month name.
    This is displays and refreshed every second by re-requesting the api. Seconds is not shown because the time is only updated when the api
    responds. This is basically an "online" clock
 */
public class WorldClock extends Activity {

    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;

    private final String BASEURL = "http://api.timezonedb.com/v2.1/get-time-zone?key=EJEFWNO52XKX&format=xml&by=position";
    private final String LAT = "&lat=";
    private final String LON = "&lng=";

    public String sendGetRequest(String link) {
        try {
            return new UpdateTask().execute(link).get();
        }
        catch (ExecutionException | InterruptedException e) {

        }
        return null;
    }

    public HashMap<String, String> parseXML(String XML) {
        System.out.println("XML string result: " + XML);
        if (XML != null && XML.length() > 0 && !XML.substring(XML.indexOf("<status>") + "<status>".length(), XML.indexOf("</status>")).equals("FAILED")) {
            HashMap<String, String> timeMap = new HashMap<>();
            try {
                String date = new SimpleDateFormat("yyyy-MM-dd").parse(XML.substring(XML.indexOf("<formatted>") + "<formatted>".length(), XML.indexOf("</formatted>") - 9)).toString();
                timeMap.put("Date", date.substring(0, date.indexOf("00:00:00")) + date.substring(date.indexOf("00:00:00") + 13));
            }
            catch (ParseException e) {
                Log.e("Parse Error", e.toString());
            }
            timeMap.put("Time", XML.substring(XML.indexOf("<formatted>") + "<formatted>".length() + 11, XML.indexOf("</formatted>") - 3));
            timeMap.put("Zone", XML.substring(XML.indexOf("<abbreviation>") + "<abbreviation>".length(), XML.indexOf("</abbreviation>")));

            System.out.println("Current time (" + timeMap.get("Zone") + "): " + timeMap.get("Date") + " | " + timeMap.get("Time"));
            return timeMap;
        }
        return null;
    }

    public void startLocating() {
        // requesting location changes if location permissions is enabled
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_clock);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // back button
        findViewById(R.id.w_backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                List<Location> locationList = locationResult.getLocations();
                if (locationList.size() > 0) {
                    //The last location in the list is the newest
                    System.out.println("Test");
                    Location currentLocation = locationList.get(locationList.size() - 1);
                    System.out.println("Current location: " + currentLocation.getLatitude() + " | " + currentLocation.getLongitude());
                    HashMap<String, String> timeData = parseXML(sendGetRequest(BASEURL + LAT + currentLocation.getLatitude() + LON + currentLocation.getLongitude()));

                    if (timeData != null) {
                        ((TextView) findViewById(R.id.w_worldClock)).setText(timeData.get("Time"));
                        ((TextView) findViewById(R.id.w_worldDate)).setText(timeData.get("Date"));
                        ((TextView) findViewById(R.id.w_worldZone)).setText(timeData.get("Zone"));

                        findViewById(R.id.w_textLayout).setVisibility(View.VISIBLE);
                    }
                }
            }
        };

        startLocating();
    }

    // required function
    @Override
    public void onPause() {
        super.onPause();

        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFusedLocationClient != null) {
            startLocating();
        }
    }

    // Everything below here is obtaining user location permissions
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(WorldClock.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();
            }
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                }
            }
            else
                Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
        }
    }



}
