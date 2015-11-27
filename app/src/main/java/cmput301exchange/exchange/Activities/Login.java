

package cmput301exchange.exchange.Activities;
import android.app.Activity;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;
import cmput301exchange.exchange.Serializers.ElasticSearch;
import cmput301exchange.exchange.User;

public class Login extends AppCompatActivity {
    public EditText username;
    private ModelEnvironment globalENV;
    private User user;
    private String userString;
    private ElasticSearch elasticSearch = new ElasticSearch(this, this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.LoginName);
        try {
//            DataIO data = new DataIO(getApplicationContext(), ModelEnvironment.class);
//            globalENV = data.loadEnvironment("GlobalENV");
//            launchHome();
        }catch(RuntimeException e){
           return;
        }
    }

    public void CreateUser(){
        userString = username.getText().toString();
        globalENV = new ModelEnvironment(this,userString);
        launchHome();

    }


    public void login(View  view) {
//        CreateUser();
        userString = username.getText().toString();
        if(userString.equals("")){
            return;
        }

//        if (! elasticSearch.UserExists(userString)){
//            CreateUser();
//        }
        elasticSearch.fetchUserFromServer(username.getText().toString());


}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void launchHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        globalENV.saveInstance(this);
        this.finish();
    }

    public void Notified(){
        globalENV = new ModelEnvironment(this);
        globalENV.setOwner(elasticSearch.getUser());
        globalENV.saveInstance(this); //saving
        launchHome();
    }
}
