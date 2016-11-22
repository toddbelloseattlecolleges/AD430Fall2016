package northseattlecollege.ASLBuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;
import android.widget.TextView;


/**
 * Author: Nathan Flint
 * Created 10/10/2016
 */

public class MenuInterpreter extends AppCompatActivity {

    private final UpdateSkypeStatus updateSkypeStatus;
    private final UpdateVideoStatus updateVideoSwitch;
    private final UpdateLocationStatus updateLocationSwitch;
    private SharedPreferences mPrefs;
    private Switch videoSwitch, locationSwitch;
    public InterpreterStatus status;
    private LocationService locationService;
    private Location location;
    private TextView skypeStatus;

    public MenuInterpreter() {

        updateSkypeStatus = new UpdateSkypeStatus();
        updateVideoSwitch = new UpdateVideoStatus();
        updateLocationSwitch = new UpdateLocationStatus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_interpreter);

        updateSkypeStatus.execute();
        updateVideoSwitch.execute();
        updateLocationSwitch.execute();

        videoSwitch = (Switch)findViewById(R.id.videoSwitch);
        locationSwitch = (Switch)findViewById(R.id.locationSwitch);
        skypeStatus = (TextView) findViewById(R.id.skypeStatus);

        //getting the status from the database here in the separate class
        status = new InterpreterStatus("1");
        locationService = new LocationService(this);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent navigationIntent = new Intent(MenuInterpreter.this, LoginActivity.class);
        MenuInterpreter.this.startActivity(navigationIntent);
    }

    public void SetLocation(Location location) {
        this.location = location;
        System.out.println(this.location.getLatitude() + " " + this.location.getLongitude());

    }


    private class UpdateSkypeStatus extends AsyncTask<Void, Void, Boolean> {
        protected Boolean doInBackground(Void... asdf) {

            return SkypeResources.isSkypeClientInstalled(MenuInterpreter.this);
        }

        protected void onPostExecute(Boolean isSkypeInstalled) {

            String statusText = isSkypeInstalled ? "Installed" : "Not Installed";
            int statusTextColor = isSkypeInstalled ? Color.GREEN : Color.RED;
            skypeStatus.setTextColor(statusTextColor);
            skypeStatus.setText(statusText);
        }
    }

    private class UpdateVideoStatus extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return status.getVideoStatus();
        }

        protected void onPostExecute(Boolean status) {
            videoSwitch.setChecked(status);
        }
    }

    private class UpdateLocationStatus extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return status.getLocationStatus();
        }

        protected void onPostExecute(Boolean status) {
            locationSwitch.setChecked(status);
        }
    }
}


