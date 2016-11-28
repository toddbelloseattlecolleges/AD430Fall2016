package northseattlecollege.ASLBuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;

/**
 * Author: Nathan Flint
 * Created 10/10/2016
 */

public class MenuInterpreter extends AppCompatActivity implements LocationTaskListener{

    private User user;
    private boolean locationStatusOn, videoStatusOn;
    private SharedPreferences mPrefs;
    private Switch videoSwitch, locationSwitch;
    public InterpreterStatus status;
    private LocationService locationService;
    private Location location;
    private int userId;
    public Thread updateThread;
    private final Handler mHandler = new Handler();
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    public final static int FIVEMINUTES = 360;
    public final static int MILISECONDS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_interpreter);
//        user = (User)getIntent().getParcelableExtra("user");
        //temp value for debugging
        userId = 1;
        locationService = new LocationService(this);
        status = new InterpreterStatus(userId, this);

        videoSwitch = (Switch)findViewById(R.id.videoSwitch);
        locationSwitch = (Switch)findViewById(R.id.locationSwitch);

        //checks the database to see the status
        status.getLocationStatus();
        status.getVideoStatus();

        videoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
//                if(isChecked){
                    //update the database here with the locationServices availability,
//                    status.sendVideoStatus(true);
//                    startUpdateLocationThread();
                    status.setVideoStatus(isChecked);
//                } else {
//                    status.setVideoStatus(false);
//                    status.sendVideoStatus(false);
//                    killUpdateLocationThread();
//                }
            }
        });

        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //if the interpreter has selected to do location services, turn on the settings
//                if(isChecked){
                    //update the database here with the locationServices availability,
//                    startUpdateLocationThread();
//                    status.sendLocationStatus(true);
                    status.setLocationStatus(isChecked);
//                } else {
//                    status.setLocationStatus(false);
//                    status.sendLocationStatus(false);
//                    locationServices = false;
//                    killUpdateLocationThread();
//                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent navigationIntent = new Intent(MenuInterpreter.this, LoginActivity.class);
        MenuInterpreter.this.startActivity(navigationIntent);
    }

    public void SetLocation(Location location) {
        this.location = location;
//        user.setLastKnownLocationLat(location.getLatitude());
//        user.setLastKnownLocationLong(location.getLongitude());
//        System.out.println(this.location.getLatitude() + " " + this.location.getLongitude());
    }

    public void sendLocation(){
        //call api here
        new AsyncTask<Boolean, Void, Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Boolean... params) {
                ObjectMapper mapper = new ObjectMapper();
                try{
                    JsonObj obj = mapper.readValue(new URL("http://54.69.18.19/updateUserLocation?userId=1&userLocLat=" +
                            location.getLatitude() + "&userLocLong=+" + location.getLongitude()), JsonObj.class);
                } catch (Exception e){
                    System.out.println(e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    public void processLocStatus(boolean status){
        locationStatusOn = status;
    }

    public void processVideoStatus(boolean status){
        videoStatusOn = status;
    }

    public void setVideoSwitch(boolean status){
        videoSwitch.setChecked(videoStatusOn);
    }

    public void setLocationSwitch(boolean status){
        locationSwitch.setChecked(locationStatusOn);
    }
}
interface LocationTaskListener {
    void processLocStatus(boolean status);
    void processVideoStatus(boolean status);
    void setVideoSwitch(boolean status);
    void setLocationSwitch(boolean status);
}