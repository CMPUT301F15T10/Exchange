package cmput301exchange.exchange.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.FriendList;
import cmput301exchange.exchange.R;
public class ViewPersonActivity extends AppCompatActivity {

    private ListView lv;
    private FriendList userfriendlist;
    private FriendList personlist;
    ArrayAdapter<String> arrayAdapter;
    List<String> displaylist= new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_person);

        lv = (ListView) findViewById(R.id.listView2);






        final List<String> person_list = new ArrayList<String>();
        person_list.add("Person1");
        person_list.add("Person2");


        final List<String> friend_list = new ArrayList<String>();
        friend_list.add("friend1");



        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                displaylist);

        lv.setAdapter(arrayAdapter);

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.view_person, android.R.layout.simple_spinner_item);
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
                Toast.makeText(parent.getContext(), "Selected: " + cat, Toast.LENGTH_LONG).show();
                //show the result for the sort

                switch (cat){
                    case "Friends":
                        arrayAdapter.clear();
                        arrayAdapter.addAll(friend_list);
                        arrayAdapter.notifyDataSetChanged();
                        break;
                    case "All People":
                        arrayAdapter.clear();
                        arrayAdapter.addAll(person_list);
                        arrayAdapter.notifyDataSetChanged();
                        break;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });























    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_person, menu);
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
