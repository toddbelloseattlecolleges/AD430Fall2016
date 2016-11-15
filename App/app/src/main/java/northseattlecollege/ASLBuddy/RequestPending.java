package northseattlecollege.ASLBuddy;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Author: Brandon Lorenz
 * Created 10/10/2016
 */

public class RequestPending extends AppCompatActivity {


    Button Cancel;

    // Allows us to notify the user that something happened in the background
    NotificationManager notificationManager;

    // Used to track notifications
    int notifID = 33;

    // Used to track if notification is active in the task bar
    boolean isNotificActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_pending);

        // Initialize buttons
        Cancel = (Button) findViewById(R.id.Cancel);

    }


    public void showNotification(View view) {

        // Builds a notification
        NotificationCompat.Builder notificBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Interpreter Available!")
                .setContentText("Click to chat now!")
                .setTicker("Interpreter Available!")
                .setSmallIcon(R.drawable.ear);

        notificBuilder.setPriority(Notification.PRIORITY_HIGH);
        if (Build.VERSION.SDK_INT >= 21)
            notificBuilder.setDefaults(Notification.DEFAULT_SOUND);



        // Define that we have the intention of opening MoreInfoNotification
        Intent moreInfoIntent = new Intent(this, ViewRequest.class);

        // Used to stack tasks across activites so we go to the proper place when back is clicked
        TaskStackBuilder tStackBuilder = TaskStackBuilder.create(this);

        // Add all parents of this activity to the stack
        tStackBuilder.addParentStack(ViewRequest.class);

        // Add our new Intent to the stack
        tStackBuilder.addNextIntent(moreInfoIntent);

        // Define an Intent and an action to perform with it by another application
        // FLAG_UPDATE_CURRENT : If the intent exists keep it but update it if needed
        PendingIntent pendingIntent = tStackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Defines the Intent to fire when the notification is clicked
        notificBuilder.setContentIntent(pendingIntent);

        // Gets a NotificationManager which is used to notify the user of the background event
        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Post the notification
        notificationManager.notify(notifID, notificBuilder.build());

        // Used so that we can't stop a notification that has already been stopped
        isNotificActive = true;


    }






}