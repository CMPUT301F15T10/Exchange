package cmput301exchange.exchange.Serializers;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.lang.reflect.Type;

import cmput301exchange.exchange.Activities.HomeActivity;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.Photo;

/**
 * Created by Charles on 11/19/2015.
 */
public class ElasticSearch{
    Gson gson = new Gson();
    private Activity activity;
    private boolean networkStatus;
    ConnectivityManager connectivityManager;

    public ElasticSearch(){
        return;
    }
    public ElasticSearch(Activity setActivity){ //Constructor for activity
        activity = setActivity;
    }

    /**
    The following Functions allow you to send the POST request to the server.
     */
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

    /**
     * Thw following functions are what you would want to use when you send the user object. It calls the sendModelEnvironment
     * from the UI thread.
     * All of the details are handled here. Including dealing with the network state
     * @param ClientEnvironment
     */
    public void sendToServer(ModelEnvironment ClientEnvironment){
        Context context = activity.getApplicationContext();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = connectivityManager.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            Thread thread = new addThread(ClientEnvironment);
            thread.start();
        }else{return;}
    }

    public ModelEnvironment getFromServer(){
        Context context = activity.getApplicationContext();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = connectivityManager.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()){
            /* TODO Thread thread = new getThread();
            thread.start(); */
            return null; //Change
        }else{return new ModelEnvironment(context.getApplicationContext(), "null");}

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
