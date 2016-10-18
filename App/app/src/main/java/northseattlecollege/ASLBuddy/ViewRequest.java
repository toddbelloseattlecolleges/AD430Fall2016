package northseattlecollege.ASLBuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Author: Brandon Lorenz
 * Created 10/10/2016
 *
 * Interpreter opens this page from the push notification, sees details
 * about the physical/video request that the HOH user created.
 */

public class ViewRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);
    }
}
