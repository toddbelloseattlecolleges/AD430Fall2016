package northseattlecollege.ASLBuddy;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Author: Nathan Flint
 * Created 10/10/2016
 */

public class MenuInterpreter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_interpreter);

        UpdateSkypeStatus updateSkypeStatus = new UpdateSkypeStatus();
        updateSkypeStatus.execute();
   }

    @Override
    public void onBackPressed() {
        finish();
        Intent navigationIntent = new Intent(MenuInterpreter.this, LoginActivity.class);
        MenuInterpreter.this.startActivity(navigationIntent);
    }

    private class UpdateSkypeStatus extends AsyncTask<Void, Void, Boolean> {
        protected Boolean doInBackground(Void... asdf) {

            return SkypeResources.isSkypeClientInstalled(MenuInterpreter.this);
        }

        protected void onPostExecute(Boolean isSkypeInstalled) {

            String statusText = isSkypeInstalled ? "Installed" : "Not Installed";
            int statusTextColor = isSkypeInstalled ? Color.GREEN : Color.RED;

            TextView skypeStatus = (TextView) findViewById(R.id.skypeStatus);
            skypeStatus.setTextColor(statusTextColor);
            skypeStatus.setText(statusText);
        }
    }
}


