package northseattlecollege.ASLBuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Author: Nathan Flint
 * Created 10/10/2016
 */

public class MenuInterpreter extends AppCompatActivity {
    private final UpdateInterpreterLocation updateInterpreterLocation;
    private UpdateLocationThread updateLocationThread;
    private SharedPreferences mPrefs;
    private Switch videoSwitch, locationSwitch;
    public InterpreterStatus status;
    public Location location;
    private TextView skypeStatus;
    private TextView skypeName;
    private int userId;

    public MenuInterpreter() {
        updateInterpreterLocation = new UpdateInterpreterLocation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_interpreter);

        skypeStatus = (TextView) findViewById(R.id.skypeStatus);

        skypeName = (TextView)findViewById(R.id.skypeName);
        skypeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromptSkypeNameChange();
            }
        });

        videoSwitch = (Switch)findViewById(R.id.videoSwitch);
        videoSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkState = ((Switch) v).isChecked();
                updateVideoStatusAsync(checkState);
            }
        });

        locationSwitch = (Switch)findViewById(R.id.locationSwitch);
        locationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkState = ((Switch) v).isChecked();
                updateLocationStatusAsync(checkState);
            }
        });

        //get switch status from server
        userId = getIntent().getIntExtra("userId", userId);
        status = new InterpreterStatus(userId);
        new RefreshSkypeAppStatus().execute();
        new RefreshVideoStatus().execute();
        new RefreshLocationStatus().execute();
        new RefreshSkypeName().execute();

        //setting to false for debugging purposes
        updateLocationThread = new UpdateLocationThread(false, this);
        updateLocationThread.start();

        //setting the logout button handler


        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void PromptSkypeNameChange() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Skype Name");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = input.getText().toString();
                updateSkypeNameAsync(newName);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void updateLocationStatusAsync(final boolean isChecked) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                status.setLocationStatus(isChecked);
                return null;
            }
        }.execute();
    }

    private void updateVideoStatusAsync(final boolean isChecked) {
         new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                status.setVideoStatus(isChecked);
                return status.getVideoStatus();
            }

            protected void onPostExecute(Boolean status) {
                videoSwitch.setChecked(status);
            }
        }.execute();
    }

    private void updateSkypeNameAsync(final String newName) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                status.setSkypeName(newName);
                return null;
            }

            protected void onPostExecute(Void status) {
                new RefreshSkypeName().execute();
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        // do nothing, we want users to use the logout button
    }

    public void SetLocation(Location location) {
        this.location = location;
    }

    public void sendLocationToServer(){
        updateInterpreterLocation.execute();
    }

    /**
     * Method for inflating settings button into options menu
     * @param menu
     * @return wasSuccessful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    /**
     * Handler for selecting settings button, will come back to this
     * activity after settings activity (using native back button)
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent navigationIntent = new Intent(MenuInterpreter.this, Settings.class);
                // pass userId to the following activity
                navigationIntent.putExtra("userId", userId);
                MenuInterpreter.this.startActivity(navigationIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("userId", userId);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userId = savedInstanceState.getInt("userId");
    }

    /**
     * Terminates this user's session and returns to the login/signup page,
     * starts activity LoginActivity.java using Android Intent.
     */
    private void logout() {
        finish();
        Intent navigationIntent = new Intent(MenuInterpreter.this, LoginActivity.class);
        MenuInterpreter.this.startActivity(navigationIntent);
    }

    private class RefreshSkypeAppStatus extends AsyncTask<Void, Void, Boolean> {
        protected Boolean doInBackground(Void... asdf) {

            return SkypeResources.isSkypeClientInstalled(MenuInterpreter.this);
        }

        protected void onPostExecute(Boolean isSkypeInstalled) {
            String statusText = isSkypeInstalled ? "Installed" : "Not Installed";
            int statusTextColor = isSkypeInstalled ? Color.GREEN : Color.RED;
            skypeStatus.setTextColor(statusTextColor);
            skypeStatus.setText(statusText);

            if (!isSkypeInstalled)
                showSkypeNotReadyDialog();
        }
    }

    private void showSkypeNotReadyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");

        // Set up the input
        final TextView message = new TextView(this);
        message.setText("Skype is not installed or your Skype name is not set." +
                "Skype must be installed and a Skype name must set inorder to" +
                "receive video requests. Please install Skype and click your Skype name to change it.");

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(message);

        // Set up the buttons
        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateVideoStatusAsync(false);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
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

    private class RefreshSkypeName extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... params) { return status.getSkypeName(); }
        protected void onPostExecute(String s) {
            if (s == null || s.equals("")) {
                skypeName.setText("Not Set");
                skypeName.setTextColor(Color.RED);
            }
            else {
                skypeName.setText(s);
                skypeName.setTextColor(Color.GREEN);
            }


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

