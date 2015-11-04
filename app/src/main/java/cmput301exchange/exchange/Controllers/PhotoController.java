package cmput301exchange.exchange.Controllers;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import cmput301exchange.exchange.Serializers.Photo;
import cmput301exchange.exchange.Person;

/**
 * code Written by joshua2ua on github:
 * https://github.com/kstem/AndroidElasticSearch/blob/master/app/src/main/java/ca/ualberta/ssrg/movies/MoviesController.java
 * Modified by kstember on 11/4/15.
 **/
public class PhotoController {
    private Gson gson = new Gson();
    private Photo photo;
    private static final String TAG = "PhotoController";
    private final int MAX_SIZE = 65536; // in bytes. might need to change
    private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/testing/";
    private Person person; // temp, not sure how to deal
    public boolean downloadYesNo = true;

    public void enableDownload() {downloadYesNo = true;}
    public void disableDownload() {downloadYesNo = false;}

    public PhotoController(Photo photo) {
        super();
        this.photo = photo;
    }

    public void addPhoto(Photo photo) {
        if (photo.photoSize(photo) /*?*/ <= MAX_SIZE) {

            HttpClient httpClient = new DefaultHttpClient();

            try {
                HttpPost addRequest = new HttpPost(/*photo.getResourceUrl()*/RESOURCE_URL + person.getID());

                StringEntity stringEntity = new StringEntity(gson.toJson(photo));
                addRequest.setEntity(stringEntity);
                addRequest.setHeader("Accept", "application/json");

                HttpResponse response = httpClient.execute(addRequest);
                String status = response.getStatusLine().toString();
                Log.i(TAG, status);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //TODO: else throw an error of some sort

}