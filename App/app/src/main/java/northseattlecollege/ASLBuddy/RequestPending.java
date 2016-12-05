package northseattlecollege.ASLBuddy;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Author: Brandon Lorenz
 * Created 10/10/2016
 */

public class RequestPending extends AppCompatActivity {

    //initialize useful variables for holding and iterating through available interpreters
    JSONArray userArray;
    int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_pending);

        Intent intent = getIntent();
        String requestType = intent.getStringExtra(CreateRequest.REQUEST_TYPE);

        if(requestType.compareTo(CreateRequest.REQUEST_TYPE_VIDEO) == 0) {
            userArray = null;
            position = 0;


            final TextView response = (TextView) findViewById(R.id.label_request_pending);
            Button call = (Button) findViewById(R.id.label_finish_request);
            final Button skip = (Button) findViewById(R.id.label_skip_user);
            //can't skip until there are users in the array
            skip.setClickable(false);

            // ToDo: remove back button once system back button is working
            // Back button for easy navigation
            Button backButton = (Button) findViewById(R.id.button_back);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    Intent navigationIntent = new Intent(RequestPending.this, MenuHOH.class);
                    RequestPending.this.startActivity(navigationIntent);
                }
            });

            //TODO: Make this button set current user ok_to_chat to false before switching to the new user
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //make sure we haven't reached the end of available users

                    if (position < userArray.length()) {
                        position++;
                        try {
                            JSONObject skypeName = userArray.getJSONObject(position);
                            response.setText(skypeName.get("skype_username").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("Invalid Data from Server");
                            skip.setClickable(false);
                        }
                    } else {
                        //get a new list of users
                        skip.setClickable(false);
                        finish();
                        startActivity(getIntent());
                    }

                }
            });


            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //initiate a call by grabbing the username from the TextView after it is updated
                    //this should only be available once the AsyncTask has completed and made the button visible
                    SkypeResources.initiateSkypeCall(getApplicationContext(), response.getText().toString());

                }
            });
            //create a new request to contact the server and get the username of the interpreter

            ServerRequestTask usernameGet = new ServerRequestTask("http://54.69.18.19/getVideoInterpreters?userId=1");
        } else {
            CreateRequest.setError(true);
            finish();
            Intent navigationIntent = new Intent(this, CreateRequest.class);
            navigationIntent.putExtra(CreateRequest.REQUEST_TYPE, requestType);
            startActivity(navigationIntent);
        }
    }

    //need to define internet permission
    //user need to know that my application can use permission
     class ServerRequestTask extends AsyncTask<String, String, String> {


        //this should allow us to use this generic AsyncTask in multiple activities

        String urlString;
        String responseUsername;
        private Exception exception;

        protected ServerRequestTask(String urlString){
            //set the desired URL
            this.urlString = urlString;
            super.execute();
        }
        //TODO: use publishProgress() and onProgressUpdate() to provide helpful feedback to user
        @Override
        protected String doInBackground(String... params) {

            //initialize URLconnection object and JSON result object and BufferedReader
            HttpURLConnection connection = null;
            //JSONObject resultObject = null;
            BufferedReader reader = null;

            StringBuffer buffer = new StringBuffer();
            //try to make the connection to the provided URL
            try{
                //get the URL
                URL url = new URL(urlString);
                //open the connection
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                //create InputSteam from the connection
                InputStream stream = connection.getInputStream();
                //assign reader to stream
                reader = new BufferedReader(new InputStreamReader(stream));
                //StringBuffer to append results to

                //read inputstream line by line and append to buffer
                String line = "";
                while((line = reader.readLine()) !=null){
                    buffer.append(line);
                }

                JSONArray username = new JSONArray(buffer.toString());
                userArray = username;
                //for debugging
                System.out.println(username.toString());
                System.out.println(username.length());

                JSONObject skypeName = username.getJSONObject(position);
                return skypeName.get("skype_username").toString();

                //convert the buffered string to a JSON object
                //this may throw a JSONException if the input is bad
                //resultObject = new JSONObject(buffer.toString());

                //catch exceptions, errors and close connection
            } catch(MalformedURLException e){
                e.printStackTrace();
                System.out.println("Bad URL");
            } catch (IOException e) {
                e.printStackTrace();
            }catch (JSONException e) {
                e.printStackTrace();
                System.out.println("Invalid Data from Server");
            } finally{
                if(connection!=null) {
                    connection.disconnect();
                }
                try{
                    if(reader !=null){
                        reader.close();
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
            }


            return "No interpreters found please try again.";
        }

        //override this method again when you call the AsyncTask in another activity
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            TextView response = (TextView) findViewById(R.id.label_request_pending);
            TextView intFound = (TextView)findViewById(R.id.label_interpreter_found);
            response.setText(result);
            //make the call button appear - this step could be removed but is helpful for testing
            Button call = (Button)findViewById(R.id.label_finish_request);
            call.setVisibility(View.VISIBLE);
            Button skip = (Button)findViewById(R.id.label_skip_user);
            if(userArray.length() >0 ) {
                skip.setClickable(true);
            }


        }
    }
}


