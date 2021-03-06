

package cmput301exchange.exchange.Activities;
import android.app.Activity;
import android.app.ProgressDialog;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;

import cmput301exchange.exchange.Controllers.LoginController;
import cmput301exchange.exchange.Interfaces.Observer;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.PersonManager;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;
import cmput301exchange.exchange.Serializers.ElasticSearch;
import cmput301exchange.exchange.TradeManager;
import cmput301exchange.exchange.User;
/*
login page, request a user name to process
after login, it creat a globle user for further use
 */
public class Login extends AppCompatActivity implements Observer{
    private LoginController controller = new LoginController();
    public EditText username;
    private ModelEnvironment globalENV;
    private User user;
    private String userString;
    private ProgressDialog progressDialog;
    private ElasticSearch elasticSearch = new ElasticSearch(this);

    @Override
    public void update() {
        if (elasticSearch.getUserExists()){
            Notified();
            Log.e("User exists","");
        }else{
            CreateUser();
            Log.e("User doesnt exist","");
        }
        launchHome();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.LoginName);
        try{
            globalENV = new ModelEnvironment(this).loadInstance(this);
            this.launchHome();
        }catch(Exception e){
        }
    }

    public void CreateUser(){
        userString = username.getText().toString();
        globalENV = new ModelEnvironment(this,userString);
//        globalENV.getOwner().setTradeManager(new TradeManager());

    }


    public void login(View  view) {
        progressDialog = ProgressDialog.show(this, "Logging You In...", "Just one Moment", true);
        userString = username.getText().toString();
        if(userString.equals("")){
            return;
        }

        elasticSearch.addObserver(this);

        elasticSearch.fetchUserFromServer(username.getText().toString()); //Right Now it will rely on offline data


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
//        Log.e("Before launch", "");
        globalENV.saveInstance(this);
        PersonManager pm= new PersonManager(this);
//        Log.e("got before updatePersonList","");
        pm.updatePersonList();
//        Log.e("got after updatePersonList", "");
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

        this.finish();
    }

    public void Notified(){
        Log.e("Before launch","notified");
        globalENV = new ModelEnvironment(this); // shouldnt be null //TODO
        globalENV.setOwner(elasticSearch.getUser());
        globalENV.saveInstance(this); //saving
        progressDialog.dismiss();
    }
}
