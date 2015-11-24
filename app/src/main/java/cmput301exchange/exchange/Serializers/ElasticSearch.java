package cmput301exchange.exchange.Serializers;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.User;


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
    public void sendUser(User user){
        HttpClient httpClient = new DefaultHttpClient();

        try{
            HttpPost addRequest = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301f15t10/Users/"+user.getName());

            StringEntity stringEntity = new StringEntity(gson.toJson(user));
            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept","application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();

            Log.i("ElasticGLOBALENV", status);


        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public User fetchUser(String username){
        return new User(username);
    }

//    public ModelEnvironment getModelEnvironment(int id){
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpResponse response = null;
//        HttpGet getRequest = new HttpGet("http://cmput301.softwareprocess.es:8080/cmput301f15t10/DefaultModel/");
//        try{
//            response = httpClient.execute(getRequest);
//        }catch(ClientProtocolException e1){
//            Log.e("ClientProtocolException Thrown", e1.toString());
//        }catch(IOException e2){
//            Log.e("IOException Thrown", e2.toString());
//        }
//
//    }

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
            //TODO Thread thread = new getThread();
            //TODO thread.start();
            return null; //Change
        }else{return new ModelEnvironment(context.getApplicationContext(), "null");}

    }
//    class getThread extends Thread{
//        private ModelEnvironment modelEnvironment;
//
//        public getThread(ModelEnvironment modelEnvironment){
//            this.modelEnvironment = modelEnvironment;
//
//        }
//        @Override
//        public void run(){
//            modelEnvironment = getModelEnvironment();
//
//            try{
//                Thread.sleep(500);
//
//            }catch(InterruptedException e){
//                e.printStackTrace();
//            }
//            activity.runOnUiThread(doFinishAdd);
//        }
//
//    }

    class addThread extends Thread {
        private ModelEnvironment modelEnvironment;

        public addThread(ModelEnvironment modelEnvironment){
            this.modelEnvironment = modelEnvironment;

        }

        @Override
        public void run(){
            sendUser(modelEnvironment.getOwner());

            try{
                Thread.sleep(500);

            }catch(InterruptedException e){
                e.printStackTrace();
            }
            activity.runOnUiThread(doFinishAdd);
        }

    }
}
    class getThread extends Thread{}