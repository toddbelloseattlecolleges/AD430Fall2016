package northseattlecollege.ASLBuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Author: Kellan Nealy
 * Created 10/10/2016
 *
 * Main menu for hard-of-hearing users, where they can navigate to the Hearing Tool,
 * Create a video or in-person request for an interpreter, and logout.
 */

public class MenuHOH extends AppCompatActivity implements Serializable {

    // User related variables
    private int userId;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_hoh);

        // Set userId and userEmail from successful login
        userId = getIntent().getIntExtra("userId", userId);
        userEmail = getIntent().getStringExtra("userEmail");

        // Populate welcome greeting
        TextView greeting  = (TextView) findViewById(R.id.welcomeHOH);
        greeting.setText("Welcome, " + userEmail + "!");

        // Set main action button onClick attributes
        Button hearingToolButton = (Button) findViewById(R.id.hearing_tool_button);
        hearingToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHearingTool();
            }
        });
        Button videoRequestButton = (Button) findViewById(R.id.video_interpreter_button);
        videoRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestVideo();
            }
        });
        Button physicalRequestButton = (Button) findViewById(R.id.physical_interpreter_button);
        physicalRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPhysical();
            }
        });
        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("userId", userId);
        savedInstanceState.putString("userEmail", userEmail);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userId = savedInstanceState.getInt("userId");
        userEmail = savedInstanceState.getString("userEmail");
    }

    @Override
    public void onBackPressed() {
        // do nothing, we want users to use the logout button
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
                Intent navigationIntent = new Intent(MenuHOH.this, Settings.class);
                // pass userId to the following activity
                navigationIntent.putExtra("userId", userId);
                MenuHOH.this.startActivity(navigationIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Starts new activity HearingTool.java using Android Intent,
     * session information should persist while users are in the Hearing Tool.
     */
    private void startHearingTool() {
        finish();
        Intent navigationIntent = new Intent(MenuHOH.this, HearingTool.class);
        // pass userId to the following activity
        navigationIntent.putExtra("userId", userId);
        MenuHOH.this.startActivity(navigationIntent);
    }

    /**
     * Starts new activity CreateRequest.javausing Android Intent,
     * where users can specify details about their video request.
     */
    private void requestVideo() {
        finish();
        Intent navigationIntent = new Intent(MenuHOH.this, CreateRequest.class);
        navigationIntent.putExtra(CreateRequest.REQUEST_TYPE, CreateRequest.REQUEST_TYPE_VIDEO);
        // pass userId to the following activity
        navigationIntent.putExtra("userId", userId);
        MenuHOH.this.startActivity(navigationIntent);
    }

    /**
     * Starts new activity CreateRequest.java using Android Intent,
     * where users can specify details about their physical request.
     */
    private void requestPhysical() {
        finish();
        Intent navigationIntent = new Intent(MenuHOH.this, CreateRequest.class);
        navigationIntent.putExtra(CreateRequest.REQUEST_TYPE, CreateRequest.REQUEST_TYPE_PHYSICAL);
        // pass userId to the following activity
        navigationIntent.putExtra("userId", userId);
        MenuHOH.this.startActivity(navigationIntent);
    }

    /**
     * Terminates this user's session and returns to the login/signup page,
     * starts activity LoginActivity.java using Android Intent.
     */
    private void logout() {
        finish();
        Intent navigationIntent = new Intent(MenuHOH.this, LoginActivity.class);
        MenuHOH.this.startActivity(navigationIntent);
    }
}
