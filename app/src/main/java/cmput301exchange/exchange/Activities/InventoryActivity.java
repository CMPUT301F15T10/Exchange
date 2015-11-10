package cmput301exchange.exchange.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;
import cmput301exchange.exchange.User;
import cmput301exchange.exchange.Person;


public class InventoryActivity extends AppCompatActivity {
    //THIS ACTIVITY MUST BE CALLED WITH A GSON STRING CONTAINING THE USER'S INVENTORY YOU WANT TO SEE.
    /**
     * ******TO USE THIS ACTIVITY PROPERLY, YOU NEED TO SEND IT A JSON USER OBJECT.*******
     *
     * The inventory activity takes the json value of a user object and displays the values in
     * and Array Adapter.
     *
     *
     */

    private int MENU_Settings = Menu.FIRST;
    private int MENU_View_Profile = Menu.FIRST + 1;
    private int MENU_View_Inventory = Menu.FIRST + 2;
    private int MENU_Make_Friendship = Menu.FIRST + 3;
    private int MENU_View_RemoveFriend = Menu.FIRST + 4;
    private int MENU_Group=1; //menu group of 0 is taken by the SearchView item

    private ListView lv;
    public ModelEnvironment globalENV;
    protected ArrayAdapter<Book> arrayAdapter;
    protected ArrayList<Book> bookList = new ArrayList<Book>();
    protected Person person;
    private Integer status=null; //status=1 means inventory of user and status=2 means inventory of a friend.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

//        Intent intent = getIntent();
//        String person_json = intent.getStringExtra("Person");
//        Gson gson = new Gson();
//        person=gson.fromJson(person_json,Person.class);

        globalENV=new ModelEnvironment(this,null); // null tells it to load modelEnvironment.
        person=globalENV.getOwner();
        lv = (ListView) findViewById(R.id.listView3);

        bookList.addAll(person.getMyInventory().getInventoryList());

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookList );

        lv.setAdapter(arrayAdapter);

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_selection, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //on selecting a spinner item
                String cat = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                //Toast.makeText(parent.getContext(), "Selected: " + cat, Toast.LENGTH_LONG).show();
                //show the result for the sort
                //bookList=InventoryOwner.getMyInventory().searchByCategory("cat").getInventoryList();
                bookList.clear();
                bookList.addAll(person.getMyInventory().searchByCategory(cat).getInventoryList());
                arrayAdapter.notifyDataSetChanged();
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void loadInventory(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {

            finish();
            startActivity(getIntent());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inventory, menu);

        // http://developer.android.com/training/appbar/action-views.html
        // Get the SearchView and set the searchable configuration
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_item) {
            Intent intent = new Intent(this, AddItemActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}