package cmput301exchange.exchange.Serializers;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.User;


/**
 * Created by Charles on 11/19/2015.
 */

public class ElasticSearch{
    private User user;
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
    public User getUser(){
        return this.user;
    }

    /**
    The following Functions allow you to send the POST request to the server.
     */
    public void sendUser(User user){
        HttpClient httpClient = new DefaultHttpClient();
        user.setTimeStamp();
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
    public boolean UserExists(String username){
        ElasticSearchResult<User> fetchedUser = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://cmput301.softwareprocess.es:8080/cmput301f15t10/Users/"+username);

        HttpResponse response = null;

        try{
            response = httpClient.execute(httpGet);
        } catch(ClientProtocolException e1){
            throw new RuntimeException(e1);
        } catch (IOException e2){
            throw new RuntimeException(e2);
        }

        Type ElasticSearchResultType = new TypeToken<ElasticSearchResult<User>>(){}.getType();

        try{
            fetchedUser = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),ElasticSearchResultType);


        }catch (JsonIOException e){
            throw new RuntimeException(e);
        }catch (JsonSyntaxException e){
            throw new RuntimeException(e);
        }catch (IllegalStateException e){
            throw new RuntimeException(e);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return fetchedUser.isFound();
    }
    public User fetchUser(String username){
        ElasticSearchResult<User> fetchedUser = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://cmput301.softwareprocess.es:8080/cmput301f15t10/Users/"+username);

        HttpResponse response = null;

        try{
            response = httpClient.execute(httpGet);
        } catch(ClientProtocolException e1){
            throw new RuntimeException(e1);
        } catch (IOException e2){
            throw new RuntimeException(e2);
        }

        Type ElasticSearchResultType = new TypeToken<ElasticSearchResult<User>>(){}.getType();

        try{
            fetchedUser = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),ElasticSearchResultType);


        }catch (JsonIOException e){
            throw new RuntimeException(e);
        }catch (JsonSyntaxException e){
            throw new RuntimeException(e);
        }catch (IllegalStateException e){
            throw new RuntimeException(e);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        return fetchedUser.get_source();
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





    public void fetchUserFromServer(String username){
        Context context = activity.getApplicationContext();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = connectivityManager.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()){
            Thread thread = new getThread(username);
            thread.start();

        }else{
            return;
        }

    }



    class getThread extends Thread{
        private String username;

        public getThread(String username){
            this.username = username;

        }
        @Override
        public void run(){
            user = fetchUser(username);

            try{
                Thread.sleep(500);

            }catch(InterruptedException e){
                e.printStackTrace();
            }
            activity.runOnUiThread(doFinishAdd);
        }

    }

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
