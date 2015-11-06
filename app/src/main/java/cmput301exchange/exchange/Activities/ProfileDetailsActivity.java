package cmput301exchange.exchange.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;
import cmput301exchange.exchange.User;

public class ProfileDetailsActivity extends AppCompatActivity {

    public ModelEnvironment GlobalENV = new ModelEnvironment();

    protected TextView name, phone, email, location;

    // TODO: implement photo once available

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        DataIO io = new DataIO(getApplicationContext(),ModelEnvironment.class);
        GlobalENV = io.loadEnvironment("GlobalENV");

        User user = GlobalENV.getOwner();

        name = (TextView)findViewById(R.id.profileNameDetails);
        phone = (TextView)findViewById(R.id.profilePhoneDetails);
        email = (TextView)findViewById(R.id.profileEmailDetails);
        location = (TextView)findViewById(R.id.profileLocationDetails);

        name.setText(user.getName());
        // phone.setText(Long.toString(user.getPhoneNumber())); will need to check if long!=NULL
        email.setText(user.getEmail());
        location.setText(user.getLocation());

        io.saveEnvironment("GlobalENV", GlobalENV);

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
}
