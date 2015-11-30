package cmput301exchange.exchange.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.User;

public class EditProfileActivity extends AppCompatActivity {

    ModelEnvironment GlobalENV;
    ModelEnvironment globalEnv;

//    protected DataIO io;
    protected EditText name, phone, email, location;
    protected User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initPerson();

        name = (EditText)findViewById(R.id.editName);
        phone = (EditText)findViewById(R.id.editPhone);
        email = (EditText)findViewById(R.id.editEmail);
        location = (EditText)findViewById(R.id.editLocation);

        name.setText(user.getName());
        phone.setText(user.getPhoneNumber());
        email.setText(user.getEmail());
        location.setText(user.getLocation());
    }

    public void done(View view){

        String profileName = name.getText().toString();
        String profilePhone = phone.getText().toString();
        String profileEmail = email.getText().toString();
        String profileLocation = location.getText().toString();

        user.setName(profileName);
        user.setPhoneNumber(profilePhone);
        user.setEmail(profileEmail);
        user.setLocation(profileLocation);

        this.finish();
    }

    public void initPerson(){
        globalEnv= new ModelEnvironment(this,null);
        user=globalEnv.getOwner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
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

    public void finish(){
        globalEnv.setOwner(user);
        globalEnv.saveInstance(this);
        super.finish();
    }
}
