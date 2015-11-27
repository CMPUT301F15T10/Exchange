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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import cmput301exchange.exchange.Activities.HomeActivity;
import cmput301exchange.exchange.Activities.Login;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.User;


/**
 * Created by Charles on 11/19/2015.
 */

public class ElasticSearch {
    private int Timeout = 3000;
    private int timeoutSocket = 3000;

    private User user;
    Gson gson = new Gson();
    private Login loginActivity;
    private Activity activity;
    private boolean networkStatus;
    ConnectivityManager connectivityManager;
    private ElasticSearchResult<User> ESResult;
    private boolean userExists;

    public ElasticSearch() {

    }

    public ElasticSearch(Activity activity) {
        this.activity = activity;
    }

    public ElasticSearch(Login setActivity, Activity ThisActitivy) { //Constructor for activity
        loginActivity = setActivity;
        activity = ThisActitivy;
    }

    /**
     * Getters and Setters go in here.
     * @return
     */

    public User getUser() {
        return this.user;
    }

    public boolean getUserExists() {
        return userExists;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Runnables go Here
     */
    private Runnable FinishFetch = new Runnable() {
        public void run() {
            //finish();
//            activity.getApplicationContext()
            if (getUserExists()) {
                loginActivity.Notified();
            } else {
                loginActivity.CreateUser();
            }
        }
    };
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * The following Functions allow you to send the POST request to the server.
     * Must be Called from the other methods.
     */
    public void sendUser(User user) {
        HttpClient httpClient = new DefaultHttpClient();
        user.setTimeStamp();
        try {
            HttpPost addRequest = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301f15t10/Users/" + user.getName());

            HttpParams httpParams = new BasicHttpParams(); //Set the Parameters
            HttpConnectionParams.setConnectionTimeout(httpParams,Timeout); // For Timeout
            HttpConnectionParams.setSoTimeout(httpParams,timeoutSocket); //For Socket Timeout
            addRequest.setParams(httpParams);

            StringEntity stringEntity = new StringEntity(gson.toJson(user));
            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();

            Log.i("ElasticGLOBALENV", status);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public boolean UserExists(String username) {
//        ElasticSearchResult<User> fetchedUser = null;
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet("http://cmput301.softwareprocess.es:8080/cmput301f15t10/Users/" + username);
//
//        HttpResponse response = null;
//
//        try {
//            response = httpClient.execute(httpGet);
//        } catch (ClientProtocolException e1) {
//            throw new RuntimeException(e1);
//        } catch (IOException e2) {
//            throw new RuntimeException(e2);
//        }
//
//        Type ElasticSearchResultType = new TypeToken<ElasticSearchResult<User>>() {
//        }.getType();
//
//        try {
//            fetchedUser = gson.fromJson(
//                    new InputStreamReader(response.getEntity().getContent()), ElasticSearchResultType);
//
//
//        } catch (JsonIOException e) {
//            throw new RuntimeException(e);
//        } catch (JsonSyntaxException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalStateException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        return fetchedUser.isFound();
//    }

    public User fetchUser(String username) {
        ElasticSearchResult<User> fetchedUser = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://cmput301.softwareprocess.es:8080/cmput301f15t10/Users/" + username);
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams,Timeout);
        HttpConnectionParams.setSoTimeout(httpParams,timeoutSocket);

        httpGet.setParams(httpParams);
        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }

        Type ElasticSearchResultType = new TypeToken<ElasticSearchResult<User>>() {
        }.getType();

        try {
            fetchedUser = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()), ElasticSearchResultType);


        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        userExists = fetchedUser.isFound();
        return fetchedUser.get_source();
    }





    /**
     * Thw following functions are what you would want to use when you send the user object. It calls the sendModelEnvironment
     * from the UI thread.
     * All of the details are handled here. Including dealing with the network state
     *
     * @param ClientEnvironment
     */
    public void sendToServer(ModelEnvironment ClientEnvironment) {
        Context context = activity.getApplicationContext();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = connectivityManager.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            Thread thread = new addThread(ClientEnvironment);
            thread.start();
        } else {
            return;
        }
    }

    public void fetchUserStatus(String username) {
        Context context = activity.getApplicationContext();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = connectivityManager.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            Thread thread = new getThread(username);
            thread.start();

        } else {
            return;
        }
    }


    public void fetchUserFromServer(String username) {
        Context context = activity.getApplicationContext();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = connectivityManager.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            Thread thread = new getThread(username);
            thread.start();

        } else {
            return;
        }

    }


    /**
     * The Two Functions Below are the Thread Functions. getThread and AddThread.
     * They invoke the caller of the methods above to instantiate a new thread to do the networking.
     */


    class getThread extends Thread {
        private String username;

        public getThread(String username) {
            this.username = username;

        }

        @Override
        public void run() {
            user = fetchUser(username);

            try {
                Thread.sleep(500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loginActivity.runOnUiThread(FinishFetch);
        }

    }

    class addThread extends Thread {
        private ModelEnvironment modelEnvironment;

        public addThread(ModelEnvironment modelEnvironment) {
            this.modelEnvironment = modelEnvironment;

        }

        @Override
        public void run() {
            sendUser(modelEnvironment.getOwner());

            try {
                Thread.sleep(500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //activity.runOnUiThread(doFinishAdd);
        }

    }
}



