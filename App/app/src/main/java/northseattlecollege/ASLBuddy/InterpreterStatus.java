package northseattlecollege.ASLBuddy;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class that updates the status by querying database using get and post data to update the
 * Interpreter status
 * Created by samueliox on 10/31/2016.
 */

public class InterpreterStatus {
    int userId;
    LocationTaskListener callback;
    public InterpreterStatus(int userId, LocationTaskListener callback){
        //figuring out who is currently logged in
        //pass in the userid as a parameter of the constructor
        this.userId = userId;
        this.callback = callback;
    }

    //send video status to the server
    public void setVideoStatus(boolean videoTrue){
        //call api here
        final boolean vid = videoTrue;
        new AsyncTask<Boolean, Void, Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Boolean... params) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    //call API to update the video status here, if true set true else false
                    URL url = new URL("http://54.69.18.19:8081/setVideoStatus?userId=" +
                                userId + "&status=" + (vid ? 1 : 0));
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    InputStream inputStream = connection.getInputStream();
//                        JsonObj obj = mapper.readValue(new URL("http://54.69.18.19/setVideoStatus?userId=" +
//                                userId + "&status=" + (vid ? 1 : 0)), JsonObj.class);
//                    connection.disconnect();
                }catch(Exception e){
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.processVideoStatus(vid);
            }
        }.execute(videoTrue);
    }

    //method that sets the video
    public void getVideoStatus(){
        //call api here
        new AsyncTask<Void, Void, Boolean>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                ObjectMapper mapper = new ObjectMapper();
                boolean status = false;
                try {
                    //call API to update the video status here
                    JsonObj[] obj = mapper.readValue(new URL("http://54.69.18.19/getVideoStatus?userId=" +
                            userId), JsonObj[].class);
                    //set status here from object
                    status = obj[0].getOk_to_chat();
                }catch(Exception e){
                    System.out.println(e);
                }
                //return the result from the query (video status)
                return status;
            }

            @Override
            protected void onPostExecute(Boolean status) {
                super.onPostExecute(status);
                callback.processVideoStatus(status);
                callback.setVideoSwitch(status);
            }
        }.execute();
    }

    //method that sends the location status to the server
    public void setLocationStatus(boolean locationTrue){
        //call api here
        final boolean loc = locationTrue;
        new AsyncTask<Boolean, Void, Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Boolean... params) {
                ObjectMapper mapper = new ObjectMapper();
                try{
//                    JsonObj obj = mapper.readValue(new URL("http://54.69.18.19/setLocationStatus?userId=" +
//                            userId + "&status=" + (loc ? 1 : 0)), JsonObj.class);
                    URL url = new URL("http://54.69.18.19/setLocationStatus?userId=" +
                            userId + "&status=" + (loc ? 1 : 0));
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                } catch (Exception e){
                    System.out.println(e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.processVideoStatus(loc);
            }
        }.execute(locationTrue);
    }

    //method that sets the location status in the activity
    public void getLocationStatus() {
        //call api here
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... aVoid) {
                ObjectMapper mapper = new ObjectMapper();
                boolean status = false;
                try {
                    //call API to update the video status here
                    JsonObj[] obj = mapper.readValue(new URL("http://54.69.18.19/getLocationStatus?userId=" +
                            userId), JsonObj[].class);
                    status = obj[0].getOk_to_show_location();
                } catch (Exception e) {
                    System.out.println(e);
                }
                return status;
            }

            @Override
            protected void onPostExecute(Boolean status) {
                super.onPostExecute(status);
                callback.processLocStatus(status);
                callback.setLocationSwitch(status);
            }
        }.execute();
    }
}

