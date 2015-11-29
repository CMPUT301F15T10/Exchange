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
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import cmput301exchange.exchange.Interfaces.Observable;
import cmput301exchange.exchange.Interfaces.Observer;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.PersonList;
import cmput301exchange.exchange.User;


/**
 * Created by Charles on 11/19/2015.
 */

public class ElasticSearch implements Observable {
    private int Timeout = 3000;
    private int timeoutSocket = 3000;

    private User user;
    Gson gson = new Gson();
    private Activity activity;
    private ArrayList<Observer> ObserverList = new ArrayList<>();
    ConnectivityManager connectivityManager;
    private boolean userExists;
    private PersonList personList = new PersonList();

    public ElasticSearch() {

    }

    public ElasticSearch(Activity activity) {
        this.activity = activity;
    }

    public PersonList getPersonList() {
        return personList;


    }

    @Override
    public void addObserver(Observer observer) {
        ObserverList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        ObserverList.remove(observer);
    }

    @Override
    public void notifyObserver(Observer observer) {
        observer.update();
    }

    @Override
    public void notifyAllObserver() {
        for (Observer i : ObserverList){
            i.update();
            Log.i("Notified",i.toString());
        }
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
    private Runnable FinishThread = new Runnable() {
        public void run() {
            notifyAllObserver();
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

    public User fetchUser(String username) {
        SearchHit<User> fetchedUser = null;
        HttpResponse response = null;
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpParams httpParams = httpClient.getParams().setIntParameter("CONNECTION_MANAGER_TIMEOUT",3000);
            HttpConnectionParams.setConnectionTimeout(httpParams, Timeout);
            HttpConnectionParams.setSoTimeout(httpParams,timeoutSocket);

            HttpGet httpGet = new HttpGet("http://cmput301.softwareprocess.es:8080/cmput301f15t10/Users/" + username);

            response = httpClient.execute(httpGet);

        }catch (ConnectTimeoutException e1){
            return new User("null");
        }catch (ClientProtocolException e2) {
            Log.i("FetchUser", e2.toString());
        }catch(IOException e3){
            Log.i("FetchUser",e3.toString());
        }

        Type ElasticSearchResultType = new TypeToken<SearchHit<User>>() {
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

    public void SearchByPage (String query, String page){

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://cmput301.softwareprocess.es:8080/cmput301f15t10/Users/_search?+id=" + query+"&size=30&from="+ page );
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams,Timeout);
        HttpConnectionParams.setSoTimeout(httpParams,timeoutSocket);

        httpGet.setParams(httpParams);
        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
            Log.i("HttpGet",response.getStatusLine().toString());
        } catch (ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }

        Type ElasticSearchResultType = new TypeToken<SearchResponse<Person>>() {
        }.getType();

        SearchResponse<Person> fetchedUsers;

        try {
            fetchedUsers = gson.fromJson(
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

        for (SearchHit<Person> hit : fetchedUsers.getHits().getHits()){
            personList.addPerson(hit.get_source());
        }


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
    public void fetchAllUsersFromServer(String username, String page) {
        Context context = activity.getApplicationContext();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = connectivityManager.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            Thread thread = new getAllUsersThread(username, page);

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
            activity.runOnUiThread(FinishThread);
        }

    }
    class getAllUsersThread extends Thread {
        private String username;
        private String page;
        public getAllUsersThread(String username, String page) {
            this.username = username;
            this.page = page;

        }

        @Override
        public void run() {
             SearchByPage(username, page);

            try {
                Thread.sleep(500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            activity.runOnUiThread(FinishThread);
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



