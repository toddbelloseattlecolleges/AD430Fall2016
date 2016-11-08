package northseattlecollege.ASLBuddy;


//JSON PARSING
import android.app.Activity;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ServerMethods {


    //need to define internet permission
    //user need to know that my application can use permission
    public class ServerRequestTask extends AsyncTask<String, String, String>{


        //this should allow us to use this generic AsyncTask in multiple activities

        private Activity activity;

        public ServerRequestTask(Activity activity){
            this.activity = activity;
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try{
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while((line = reader.readLine()) !=null){
                    buffer.append(line);
                }
                System.out.println(buffer);
                return buffer.toString();
            } catch(MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
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
            return null;
        }

        //override this method again when you call the AsyncTask in another activity
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }
}