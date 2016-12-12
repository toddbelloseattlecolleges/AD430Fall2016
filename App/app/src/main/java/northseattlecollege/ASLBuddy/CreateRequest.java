package northseattlecollege.ASLBuddy;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Author: Jesse Bernoudy
 * Created 10/10/2016
 * <p>
 * NOTE: Will use the same XML layout as the ViewRequest activity, but
 * instead display the text boxes as "Edit Texts" using the Java Android library
 * <p>
 * Starts new video interpreter request through Skype.
 */

public class CreateRequest extends AppCompatActivity
        implements SetRadiusFragment.OnSetRadiusFragmentInteractionListener {

    public final static String REQUEST_TYPE = "northseattlecollege.ASLBuddy.REQUEST_TYPE";
    public final static String REQUEST_TYPE_VIDEO = "northseattlecollege.ASLBuddy.REQUEST_TYPE_VIDEO";
    public final static String REQUEST_TYPE_PHYSICAL = "northseattlecollege.ASLBuddy.REQUEST_TYPE_PHYSICAL";

    private String requestType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        Intent intent = getIntent();
        requestType = intent.getStringExtra(REQUEST_TYPE);

        InitializeUI();

        Button submitButton = (Button) findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitRequest();
            }
        });

        // ToDo: remove back button once system back button is working
        // Back button for easy navigation
        Button backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent navigationIntent = new Intent(CreateRequest.this, MenuHOH.class);
                CreateRequest.this.startActivity(navigationIntent);
            }
        });

        final Spinner requestTypeSpinner = (Spinner) findViewById(R.id.spinner_request_type);
        requestTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                UpdateOtherEditView(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void SubmitRequest() {
        if (requestType.compareTo(REQUEST_TYPE_PHYSICAL) == 0) {
            finish();
            // ToDo: how are physical requests handled?
            Intent navigationIntent = new Intent(CreateRequest.this, RequestPending.class);
            CreateRequest.this.startActivity(navigationIntent);
        } else {
            if(SkypeResources.isSkypeClientInstalled(getApplicationContext())){
                finish();
                SkypeResources.initiateSkypeCall(getApplicationContext(), SkypeResources.getUsernameFromServer());
                Intent navigationIntent = new Intent(CreateRequest.this, RequestPending.class);
                CreateRequest.this.startActivity(navigationIntent);
            } else {
                // ToDo: What happens after this call?
                SkypeResources.goToMarket(getApplicationContext());
            }
        }
    }

    private void InitializeUI() {
        TextView title = (TextView) findViewById(R.id.label_create_request);
        if (requestType.compareTo(REQUEST_TYPE_PHYSICAL) == 0) {
            title.setText(R.string.label_create_physical_request);
            // ToDo: Get default/saved radius from Settings
            SetRadiusFragment newFragment = SetRadiusFragment.newInstance(.25f);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.set_radius_fragment_container, newFragment);
            transaction.commit();
        } else {
            title.setText(R.string.label_create_video_request);
        }
    }

    private void UpdateOtherEditView(String selectedItem) {
        EditText otherDescEditText = (EditText) findViewById(R.id.edit_description);
        // ToDo: Replace hard-coded other with value from strings.xml
        if (selectedItem.compareTo("Other") == 0) {
            otherDescEditText.setEnabled(true);
        } else {
            otherDescEditText.setEnabled(false);
        }
    }

    @Override
    public void onFragmentInteraction(float radius) {

    }

}
