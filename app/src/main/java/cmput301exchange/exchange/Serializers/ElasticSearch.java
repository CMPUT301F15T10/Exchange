package cmput301exchange.exchange.Serializers;

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
public class ElasticSearch {
    Gson gson = new Gson();


    public void sendModelEnvironment(ModelEnvironment modelEnvironment){
        HttpClient httpClient = new DefaultHttpClient();

        try{
            HttpPost addRequest = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301f15t10/DefaultModel");

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
}
