package northseattlecollege.ASLBuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Author: Kellan Nealy
 * Created 10/10/2016
 */

public class MenuHOH extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_hoh);

        // Set main action button onClick attributes
        Button hearingToolButton = (Button) findViewById(R.id.hearing_tool_button);
        hearingToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHearingTool();
            }
        });
        Button videoRequestButton = (Button) findViewById(R.id.video_interpreter_button);
        hearingToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestVideo();
            }
        });
        Button physicalRequestButton = (Button) findViewById(R.id.physical_interpreter_button);
        hearingToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPhysical();
            }
        });
        Button logoutButton = (Button) findViewById(R.id.logout_button);
        hearingToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    /**
     * Starts new activity HearingTool.java
     * Session information should persist while users are in the Hearing Tool
     */
    private void startHearingTool() {

    }

    /**
     * Starts new video interpreter request through Skype
     * Application may be dormant after the user starts the request
     */
    private void requestVideo() {

    }

    /**
     * Starts new physical interpreter request through Skype
     * Application may be dormant after the user starts the request
     */
    private void requestPhysical() {

    }

    /**
     * Terminates this user's session and returns to the login/signup page
     * Starts activity LoginActivity.java
     */
    private void logout() {

    }
}
