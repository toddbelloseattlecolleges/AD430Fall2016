package northseattlecollege.ASLBuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Author: Nathan Flint
 * Created 10/10/2016
 */

public class MenuInterpreter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_interpreter);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent navigationIntent = new Intent(MenuInterpreter.this, LoginActivity.class);
        MenuInterpreter.this.startActivity(navigationIntent);
    }
}
