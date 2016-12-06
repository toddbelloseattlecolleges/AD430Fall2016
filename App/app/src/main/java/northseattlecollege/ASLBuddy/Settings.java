package northseattlecollege.ASLBuddy;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Settings extends AppCompatActivity {

    // Variables for UI elements
    private EditText editFullName;
    private ToggleButton toggleDisturb;
    private ToggleButton toggleLocation;

    // Variables for API calls
    private int userId;
    private String fullName;
    private boolean doNotDisturb;
    private boolean locationEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set userId from origin activity
        userId = getIntent().getIntExtra("userId", userId);

        editFullName = (EditText) findViewById(R.id.editTextFullname);
        editFullName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (!editFullName.getText().toString().isEmpty()) {
                    fullName = editFullName.getText().toString();
                    return true;
                }
                return false;
            }
        });

        // Handle the Toggle Buttons
        toggleDisturb = (ToggleButton) findViewById(R.id.toggleDisturb);
        toggleDisturb.setChecked(true);
        toggleLocation = (ToggleButton) findViewById(R.id.toggleLocation);
        toggleLocation.setChecked(true);

        // Handle Apply Button
        Button applyButton = (Button) findViewById(R.id.apply_button);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateWithAPI();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // do nothing, we want users to use the APPLY button
    }

    /**
     * handles the toggle button for Do Not Disturb setting
     * @param view selected view
     */
    protected void toggleDoNotDisturb (View view) {
        // Change Value
    }

    /**
     * handles the toggle button choices for location services setting
     * @param view selected view
     */
    protected void toggleLocationStatus (View view) {
        // Change Value
    }

    /**
     * Terminates this Settings activity and returns to the previous activity,
     * after making the necessary API call to update this user's settings.
     */
    private void updateWithAPI() {
        // Use your own AsyncTask to call methods on the API
        this.finish();
    }
}