package cmput301exchange.exchange.Activities;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import org.w3c.dom.Text;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.ModelEnvironment;

import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.PersonList;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;
import cmput301exchange.exchange.Serializers.ElasticSearch;
import cmput301exchange.exchange.User;

public class  HomeActivity extends AppCompatActivity {
    ModelEnvironment GlobalENV = null;
    Gson gson = new Gson();
    Intent intent;
    ElasticSearch elasticSearch = new ElasticSearch(this);
    protected User user;
    private final int INVENTORY = 1, EDIT_PROFILE = 2, CONFIGURATION = 3, SEARCH_PEOPLE = 4;

    private DrawerLayout leftDrawer;
    private ListView leftNavList;
    private String[] NavTitles;
    private ActionBarDrawerToggle navToggle;

    private boolean getNetworkStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        intent = getIntent();
        getUser();
        //initFriendList();
        //getInventory();
        //initInventory();
        GlobalENV.setOwner(user);
        GlobalENV.saveInstance(this);
        setContentView(R.layout.activity_home);

        NavTitles = getResources().getStringArray(R.array.NavigationArray);
        leftDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        leftNavList = (ListView) findViewById(R.id.nav_drawer);


        leftNavList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, NavTitles));

        leftNavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clickedItem = ((TextView)view).getText().toString();

                if (clickedItem.equals("Inventory") ){
                    inventory(view);
                }
                if (clickedItem.equals("Trade Manager")){
                    tradeManager(view);
                }
                if (clickedItem.equals("Browse Friends")){
                    searchPeople(view);
                }
            }
        });

        TextView message = (TextView) findViewById(R.id.home_message);
        String string = "Hello " + GlobalENV.getOwner().getName() + "!\n" + message.getText().toString();
        message.setText(string);
        elasticSearch.sendToServer(GlobalENV);

    }

    @Override
    public void onResume(){
        super.onResume();
        elasticSearch.sendToServer(GlobalENV);
    }





    public void inventory(View view) {
        Gson gson = new Gson();
        String json = gson.toJson(user.getMyInventory());
        Intent intent = new Intent(this, InventoryActivity.class);
        intent.putExtra("Inventory", json);
        intent.putExtra("Inventory_State", 1);
        startActivityForResult(intent, INVENTORY);
    }

    public void tradeManager(View view) {
        Intent intent = new Intent(this, TradeManagerActivity.class);
        startActivity(intent);
    }

    public void searchPeople(View view) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        Intent intent = new Intent(this, ViewPersonActivity.class);
        startActivityForResult(intent, SEARCH_PEOPLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public void getInventory() {
        if (intent.hasExtra("Inventory")) {
            String json = intent.getExtras().getString("Inventory");
            Inventory inventory = gson.fromJson(json, Inventory.class);
            user.setInventory(inventory);
        }
    }

    public void getUser() {
        if (intent.hasExtra("User")) {
            String json = intent.getExtras().getString("User");
            user = gson.fromJson(json, User.class);
            Log.e("User friendlist size: ", String.valueOf(user.getMyFriendList().getPersonList().size()));
            if (GlobalENV == null) {
                GlobalENV = new ModelEnvironment(this, "i");
            }
        } else {
            GlobalENV = new ModelEnvironment(this, null);
            user = GlobalENV.getOwner();
        }

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
            Intent intent = new Intent(this, ProfileDetailsActivity.class);
            ;
            String json = gson.toJson(user);
            intent.putExtra("Person", json);
            startActivity(intent);
        }
        if (id == R.id.action_edit_profile) {
            Intent intent = new Intent(this, EditProfileActivity.class);
            String json = gson.toJson(user);
            intent.putExtra("User", json);
            startActivityForResult(intent, EDIT_PROFILE);
        }
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == INVENTORY) {
            data.setClass(this, HomeActivity.class);
            intent = data;
            getInventory();

        }
        if (requestCode == EDIT_PROFILE) {
            data.setClass(this, HomeActivity.class);
            intent = data;
            getUser();
        }
        if (requestCode == CONFIGURATION) {
            data.setClass(this, HomeActivity.class);
            if (data.hasExtra("Configuration_picDown")) {
                GlobalENV.setAutoPicDownloads(data.getExtras().getBoolean("Configuration_picDown"));
            }
        }

        if (requestCode == SEARCH_PEOPLE) {
            startActivity(data);
        }
    }

    public void initInventory() {
        Book EternalNight = new Book();
        EternalNight.setShareable(true);
        EternalNight.updateTitle("Eternal Night");
        EternalNight.updateCategory("Category2");
        EternalNight.updateQuality(1);
        EternalNight.updateQuantity(1);
        user.getMyInventory().add(EternalNight);
        Book HackMe = new Book();
        HackMe.setShareable(false);
        HackMe.updateTitle("Hack Me!");
        HackMe.updateCategory("None");
        user.getMyInventory().add(HackMe);
    }

    public void finish() {
        GlobalENV.setOwner(user);
        GlobalENV.saveInstance(this);
        super.finish();
    }

    public void initFriendList() {
        Person A = new Person("Harry1");
        A.setName("Harry");
        Person B = new Person("James1");
        B.setName("James");
        user.addFriend(A);
        user.addFriend(B);
    }

}
