package cmput301exchange.exchange.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;


public class InventoryActivity extends AppCompatActivity {

    private ListView lv;
    public ModelEnvironment globalENV = new ModelEnvironment();
    public Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //I can't get this to work
//        DataIO io = new DataIO(getApplicationContext(),ModelEnvironment.class);
//        io.setFileName("GlobalENV");
////        ArrayList<ModelEnvironment> LoadArray = io.loadFromFile();
////        globalENV = LoadArray.get(0);
//        globalENV = (ModelEnvironment) io.loadFromFile().get(0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        lv = (ListView) findViewById(R.id.listView3);

        List<String> person_list = new ArrayList<String>();
        person_list.add("Item1");
        person_list.add("Item2");



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                person_list );

        lv.setAdapter(arrayAdapter);

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}