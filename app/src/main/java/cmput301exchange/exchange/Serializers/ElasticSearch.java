package cmput301exchange.exchange.Serializers;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import cmput301exchange.exchange.ModelEnvironment;

/**
 * Created by Charles on 11/19/2015.
 */
public class ElasticSearch{
    Gson gson = new Gson();
    private Activity activity;

    public ElasticSearch(){
        return;
    }
    public ElasticSearch(Activity setActivity){ //Constructor for activity
        activity = setActivity;
    }

    public void sendModelEnvironment(ModelEnvironment modelEnvironment){
        HttpClient httpClient = new DefaultHttpClient();

        try{
            HttpPost addRequest = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301f15t10/DefaultModel/"+modelEnvironment.getOwner().getName());

            StringEntity stringEntity = new StringEntity(gson.toJson(modelEnvironment));
            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept","application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();

            Log.i("ElasticGLOBALENV", status);


        }catch(Exception e){
            e.printStackTrace();
        }

    }
    private Runnable doFinishAdd = new Runnable() {
        public void run() {
            //finish();
        }
    };
    public void sendToServer(ModelEnvironment ClientEnvironment){
        Thread thread = new addThread(ClientEnvironment);
        thread.start();
    }

    class addThread extends Thread {
        private ModelEnvironment modelEnvironment;

        public addThread(ModelEnvironment modelEnvironment){
            this.modelEnvironment = modelEnvironment;

        }

        @Override
        public void run(){
            sendModelEnvironment(modelEnvironment);

            try{
                Thread.sleep(500);

            }catch(InterruptedException e){
                e.printStackTrace();
            }
            activity.runOnUiThread(doFinishAdd);
        }

    }
}
