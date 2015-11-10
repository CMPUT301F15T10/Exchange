package cmput301exchange.exchange.Activities;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import org.w3c.dom.Text;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.ModelEnvironment;

import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;
import cmput301exchange.exchange.User;
import cmput301exchange.exchange.ViewPerson;

public class HomeActivity extends AppCompatActivity {
    ModelEnvironment GlobalENV;

    protected User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String json = getIntent().getStringExtra("environment");
//        Gson gson = new Gson();
//        ModelEnvironment globalENV = gson.fromJson(json,ModelEnvironment.class);
//        assert(globalENV != null);
        DataIO io = new DataIO(getApplicationContext(),ModelEnvironment.class);
        GlobalENV = io.loadEnvironment("GlobalENV");

        user = GlobalENV.getOwner();


        setContentView(R.layout.activity_home);

        Context toastContext = getApplicationContext();
        Toast toast = new Toast(toastContext);

        toast.makeText(toastContext, GlobalENV.getOwner().getName(), Toast.LENGTH_LONG).show();
    }

    public void inventory(View view) {

        Gson gson = new Gson();
        String json = gson.toJson(user);

        Intent intent = new Intent(this, InventoryActivity.class).putExtra("Person",json);
        startActivity(intent);
    }

    public void tradeManager(View view) {
        Intent intent = new Intent(this, TradeManagerActivity.class);
        startActivity(intent);
    }

    public void searchPeople(View view) {
        Intent intent = new Intent(this, ViewPersonActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // TODO pass through intent the current user's profile
        if (id == R.id.action_view_profile) {
            Gson gson = new Gson();
            String json = gson.toJson(user);

            Intent intent = new Intent(this, ProfileDetailsActivity.class).putExtra("Person",json);
            startActivity(intent);
        }
        if (id == R.id.action_edit_profile) {
            Gson gson = new Gson();
            String json = gson.toJson(user);

            Intent intent = new Intent(this, EditProfileActivity.class).putExtra("User",json);
            startActivity(intent);
        }
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, ConfigurationActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
