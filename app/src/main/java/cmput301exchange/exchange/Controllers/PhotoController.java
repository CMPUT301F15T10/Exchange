package cmput301exchange.exchange.Controllers;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import cmput301exchange.exchange.Serializers.Photo;
import cmput301exchange.exchange.Person;

/**
 *  by kstember on 11/4/15.
 **/
public class PhotoController {
    private Gson gson = new Gson();
    private Photo photo;
    private static final String TAG = "PhotoController";
    private final int MAX_SIZE = 65536; // in bytes. might need to change to bits idk yet.
    //private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/testing/";
    private Person person; // temp, not sure how to deal

    public boolean downloadYesNo = true;

    public void enableDownload() {
        downloadYesNo = true;
    }

    public void disableDownload() {
        downloadYesNo = false;
    }

    public PhotoController(Photo photo) {
        super();
        this.photo = photo;
    }

    public Photo getPhoto() {
        return this.photo;
    }

    public void addPhoto(/*Photo photo*/) {
        // todo: allow user to select which photo to upload here
        // todo: if (photo.photoSize(photo) <= MAX_SIZE) { ...do stuff}
        // todo: another function will be called to upload photo
        // todo: else {throw an error}


    }

    public void deletePhoto(/*int photoID*/) {
        // todo: allow user to select which photo to delete here
        // todo: another function will be implemented to call to delete photo

    }
}
