

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
import cmput301exchange.exchange.User;

public class Login extends AppCompatActivity {
    public EditText username;
    private ModelEnvironment globalENV;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.LoginName);
        try {
            DataIO data = new DataIO(getApplicationContext(), ModelEnvironment.class);
            globalENV = data.loadEnvironment("GlobalENV");
            launchHome();
        }catch(RuntimeException e){
           return;
        }
    }

    public void CreateUser(){
        String userString = username.getText().toString();

        if(userString.equals("")){
            return;
        }
        globalENV = new ModelEnvironment(this,userString);
    }


    public void login(View  view) {
        CreateUser();
        globalENV.saveInstance(this); //saving
        launchHome();

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
        this.finish();
    }
}
