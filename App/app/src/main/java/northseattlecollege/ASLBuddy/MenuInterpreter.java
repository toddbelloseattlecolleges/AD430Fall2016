package northseattlecollege.ASLBuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Author: Nathan Flint
 * Created 10/10/2016
 */

public class MenuInterpreter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_interpreter);

        Button backButton = (Button) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent navigationIntent = new Intent(MenuInterpreter.this, LoginActivity.class);
                MenuInterpreter.this.startActivity(navigationIntent);
            }
        });
    }
}
