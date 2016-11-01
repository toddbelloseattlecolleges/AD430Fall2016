package northseattlecollege.ASLBuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;

/**
 * Author: Nathan Flint
 * Created 10/10/2016
 */

public class MenuInterpreter extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener{

    private SharedPreferences mPrefs;
    private boolean locationServices;
    private Location location;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Switch videoSwitch, locationSwitch;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private final static int MILISECONDS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_interpreter);

        Button backButton = (Button) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent navigationIntent = new Intent(MenuInterpreter.this, LoginActivity.class);
                MenuInterpreter.this.startActivity(navigationIntent);
            }
        });
        videoSwitch = (Switch)findViewById(R.id.videoSwitch);
        locationSwitch = (Switch)findViewById(R.id.locationSwitch);

        videoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });

        //have to check the status of whether or not the locationServices are already on
        //based on the status of the interpreter from the database
        locationServices = false;
        if(locationServices){
            startLocationServices();
        }

        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //if the interpreter has selected to do location services, turn on the settings
                if(isChecked){
                    startLocationServices();
                    locationServices = true;
                    onLocationChanged(location);

                    double currentLatitude = location.getLatitude();
                    double currentLongitude = location.getLongitude();
                    //update the database here with the locationServices availability,
                    //as well as the new location

                    //call thread here too
                } else {

                }
                // true if the switch is in the On position
            }
        });

        //sets shared preferences so that settings can remember if an interpreter
        //is available for location services
//        mPrefs = getSharedPreferences("locationServices", 0);
        //test button implemented, ultimately, we will only want to use location services

    }

    /**
     * Method that starts the google api services
     */
    public void startLocationServices() {
        //only instantiate this when called
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * MILISECONDS)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * MILISECONDS); // 1 second, in milliseconds
        //checks if gps is enabled
        try {
            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException e) {
            popupUserSettings();
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        //only connect the location if location services are available
        if (locationServices) {
            try {
                location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            if (location == null) {
                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                } catch (SecurityException e) {
                    // notify user
                    popupUserSettings();
                }
            } else {
                onLocationChanged(location);
            }
        }
    }


    @Override
    protected void onResume() {
        //connect only if the user settings were checked for locationservices
        mGoogleApiClient.connect();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putBoolean("locationServices", locationServices);
        ed.commit();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.i("", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            this.location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException e){
            popupUserSettings();
        }
    }


    //Method that sends user to settings menu, to be used if GPS is not enabled and services are
    //asked for
    private void popupUserSettings() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("");
        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent myIntent = new Intent(ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
                //get gps
            }
        });
        dialog.setNegativeButton("", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub

            }
        });
        dialog.show();
    }

    /**
     * Method that calls the script to update the current location of the interpreter
     * to the server database, to be called in the activity when location services
     * are set to available by interpreter
     */
    public void updateInterpreterLocation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //run the method here to update database here
                try {
                    Thread.sleep(360 * MILISECONDS);
                } catch(Exception e){

                }
            }
        }).start();
    }
}
