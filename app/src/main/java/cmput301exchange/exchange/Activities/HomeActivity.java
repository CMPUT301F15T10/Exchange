package cmput301exchange.exchange.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cmput301exchange.exchange.EditProfile;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.ViewPerson;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void inventory(View view) {
        Intent intent = new Intent(this, InventoryActivity.class);
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
            Intent intent = new Intent(this, ViewPersonActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_edit_profile) {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, ConfigurationActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
