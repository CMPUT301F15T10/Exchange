package cmput301exchange.exchange.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import cmput301exchange.exchange.Fragments.LogoutFragment;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;

public class ProfileDetailsActivity extends AppCompatActivity{

//    public ModelEnvironment GlobalENV = new ModelEnvironment();

    protected TextView name, phone, email, location;
    private Person person;
    
    // TODO: implement photo once available


    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        initPerson();

        name = (TextView)findViewById(R.id.profileNameDetails);
        phone = (TextView)findViewById(R.id.profilePhoneDetails);
        email = (TextView)findViewById(R.id.profileEmailDetails);
        location = (TextView)findViewById(R.id.profileLocationDetails);


        name.setText(person.getName());
        phone.setText(person.getPhoneNumber());
        email.setText(person.getEmail());
        location.setText(person.getLocation());

//        io.saveEnvironment("GlobalENV", GlobalENV);

    }

    public void initPerson(){
        Gson gson = new Gson();
        Intent intent=getIntent();
        String json=intent.getExtras().getString("Person");
        person = gson.fromJson(json, Person.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_details, menu);
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
        if (id == R.id.action_Logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Logout();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setMessage("Are you sure you want to Log out?");
            builder.show();

        }


        return super.onOptionsItemSelected(item);
    }


    public void Logout(){
        DataIO dataIO = new DataIO(this,ModelEnvironment.class);
        dataIO.saveInFile("GlobalENV","");
        this.startActivity(new Intent(this, Login.class));
        this.finishAffinity();
    }
}
