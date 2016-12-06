package northseattlecollege.ASLBuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Author: Nathan Flint
 * Created 10/10/2016
 */

public class MenuInterpreter extends AppCompatActivity implements CompoundButton.OnClickListener {

    private final RefreshSkypeStatus refreshSkypeStatus;
    private final RefreshVideoStatus refreshVideoSwitch;
    private final RefreshLocationStatus refreshLocationSwitch;
    private final RefreshSykpeName refreshSykpeName;
    private final UpdateInterpreterLocation updateInterpreterLocation;
    private UpdateLocationThread updateLocationThread;
    private SharedPreferences mPrefs;
    private Switch videoSwitch, locationSwitch;
    public InterpreterStatus status;
    public Location location;
    private TextView skypeStatus;
    private EditText skypeName;

    public MenuInterpreter() {
        refreshSkypeStatus = new RefreshSkypeStatus();
        refreshVideoSwitch = new RefreshVideoStatus();
        refreshLocationSwitch = new RefreshLocationStatus();
        refreshSykpeName = new RefreshSykpeName();
        updateInterpreterLocation = new UpdateInterpreterLocation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_interpreter);

        refreshSkypeStatus.execute();
        refreshVideoSwitch.execute();
        refreshLocationSwitch.execute();
        refreshSykpeName.execute();

        videoSwitch = (Switch)findViewById(R.id.videoSwitch);
        locationSwitch = (Switch)findViewById(R.id.locationSwitch);
        skypeStatus = (TextView) findViewById(R.id.skypeStatus);
        skypeName = (EditText)findViewById(R.id.skypeName);

        videoSwitch.setOnClickListener(this);
        locationSwitch.setOnClickListener(this);

        //getting the status from the database here in the separate class
        status = new InterpreterStatus(1);
        //setting to false for debugging purposes
        updateLocationThread = new UpdateLocationThread(false, this);
        updateLocationThread.start();

    }

    private AsyncTask<Void, Void, Void> updateLocationStatusAsync(final boolean isChecked) {
        return new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                status.setLocationStatus(isChecked);
                return null;
            }}.execute();
    }

    private AsyncTask<Void, Void, Void> updateVideoStatusAsync(final boolean isChecked) {
        return new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                status.setVideoStatus(isChecked);
                return null;
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent navigationIntent = new Intent(MenuInterpreter.this, LoginActivity.class);
        MenuInterpreter.this.startActivity(navigationIntent);
    }

    public void SetLocation(Location location) {
        this.location = location;
    }

    public void sendLocationToServer(){
        updateInterpreterLocation.execute();
    }

    @Override
    public void onClick(View v) {
        if (!(v instanceof Switch))
            return;

        boolean checkState = ((Switch) v).isChecked();

        if (v == videoSwitch)
            updateVideoStatusAsync(checkState);
        else if (v == locationSwitch)
            updateLocationStatusAsync(checkState);
    }


    private class RefreshSkypeStatus extends AsyncTask<Void, Void, Boolean> {
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

    private class RefreshVideoStatus extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return status.getVideoStatus();
        }

        protected void onPostExecute(Boolean status) {
            videoSwitch.setChecked(status);
        }
    }

    private class RefreshLocationStatus extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return status.getLocationStatus();
        }

        protected void onPostExecute(Boolean status) {
            locationSwitch.setChecked(status);
            if(updateLocationThread.isAlive()){
                //kills thread
                updateLocationThread.kill();
            }
            updateLocationThread.setLocationStatusOn(status);
        }
    }

    private class RefreshSykpeName extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... params) { return status.getSkypeName(); }
        protected void onPostExecute(String s) {
            skypeName.setText(s);
        }
    }

    private class UpdateInterpreterLocation extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            if(location != null){
                status.setInterpreterLocation(location.getLatitude(), location.getLongitude());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}

