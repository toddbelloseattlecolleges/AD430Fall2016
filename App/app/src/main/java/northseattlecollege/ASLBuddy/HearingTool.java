package northseattlecollege.ASLBuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Author: Kellan Nealy
 * Created 10/10/2016
 *
 * This activity will be built from the conversate project:
 * https://github.com/chrisjmendoza/Conversate
 */
public class HearingTool extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_tool);

        // Back button for easy navigation
        Button backButton = (Button) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent navigationIntent = new Intent(HearingTool.this, MenuHOH.class);
                HearingTool.this.startActivity(navigationIntent);
            }
        });
    }
}
