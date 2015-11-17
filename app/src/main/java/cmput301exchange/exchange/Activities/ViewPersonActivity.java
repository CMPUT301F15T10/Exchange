package cmput301exchange.exchange.Activities;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.PersonList;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;
import cmput301exchange.exchange.User;

public class ViewPersonActivity extends AppCompatActivity {


    private static final int MENU_Settings = Menu.FIRST;
    private static final int MENU_View_Profile = Menu.FIRST + 1;
    private static final int MENU_View_Inventory = Menu.FIRST + 2;
    private static final int MENU_Make_Friendship = Menu.FIRST + 3;
    private static final int MENU_View_RemoveFriend = Menu.FIRST + 4;
    private static final int MENU_Group=1; //menu group of 0 is taken by the SearchView item

    public ModelEnvironment globalENV;
    private ListView lv;
    private ArrayList<Person> friendList;
    private ArrayList<Person> personList;
    private PersonList allPerson;
    ArrayAdapter<Person> friendListAdapter, personListAdapter;
    private Person selectedPerson=null;
    private Person user;
    private Integer state=0;
    private SearchView mySearchView=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_person);

        initPerson();
        friendList=user.getMyFriendList().getPersonList();
        initPersonList();

        lv = (ListView) findViewById(R.id.listView2);


        lv.setItemsCanFocus(false);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        friendListAdapter = new ArrayAdapter<Person>(
                this,
                android.R.layout.simple_list_item_single_choice);
        friendListAdapter.addAll(friendList);

        personListAdapter = new ArrayAdapter<Person>(
                this,
                android.R.layout.simple_list_item_single_choice);
        personListAdapter.addAll(personList);

        lv.setAdapter(friendListAdapter);
        state=1; //initiated with friendList.

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                if (lv.isItemChecked(position)) {
                    selectedPerson=(Person) lv.getItemAtPosition(position);
                    Toast.makeText(getBaseContext(), selectedPerson.toString(), Toast.LENGTH_LONG).show();
                } else{
                    selectedPerson=null;
                    Toast.makeText(getBaseContext(), "None selected", Toast.LENGTH_LONG).show();
                }
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        // Create an friendListAdapter using the string array and a default spinner layout
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
                        state=1;
                        lv.clearChoices();
                        lv.setAdapter(friendListAdapter);
                        break;
                    case "All People":
                        state=2;
                        lv.clearChoices();
                        lv.setAdapter(personListAdapter);
                        break;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }

    public void initPerson(){
        Gson gson = new Gson();
        Intent intent=getIntent();
        String json=intent.getExtras().getString("User");
        user = gson.fromJson(json, Person.class);
    }

    public void finish(){
        Gson gson = new Gson();
        Intent intent=new Intent();
        String json= gson.toJson(user);
        intent.putExtra("User",json);
        setResult(RESULT_OK, intent);
        super.finish();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.removeGroup(MENU_Group);
        if((state==1) && (selectedPerson!=null)) {
            menu.add(MENU_Group, MENU_View_Inventory, Menu.NONE, "View Inventory");
            menu.add(MENU_Group, MENU_View_Profile, Menu.NONE, "View Profile");
            menu.add(MENU_Group, MENU_View_RemoveFriend, Menu.NONE, "Remove "+selectedPerson.toString());
        }
        if((state==2) && (selectedPerson!=null)) {
            menu.add(MENU_Group, MENU_Make_Friendship, Menu.NONE, "Make Friendship!");
            menu.add(MENU_Group, MENU_View_Profile, Menu.NONE, "View Profile");
        }

        menu.add(MENU_Group, MENU_Settings, Menu.NONE, "Settings");
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_person, menu);
        mySearchView=(SearchView) menu.findItem(R.id.person_search).getActionView();
        initSearchView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == MENU_Settings) {
            return true;
        }

        if (id == MENU_View_Inventory) {
            Gson gson = new Gson();
            String json = gson.toJson(selectedPerson.getMyInventory());

            Intent intent = new Intent(this, InventoryActivity.class).putExtra("Inventory",json);
            startActivity(intent);
            return true;
        }

        if (id == MENU_View_Profile){
            Gson gson = new Gson();
            String json = gson.toJson(selectedPerson);

            Intent intent = new Intent(this, ProfileDetailsActivity.class).putExtra("Person",json);
            startActivity(intent);
            return true;
        }

        if (id == MENU_Make_Friendship){
            user.addFriend(selectedPerson);
            friendList=user.getMyFriendList().getPersonList();
            friendListAdapter.clear();
            friendListAdapter.addAll(friendList);
            friendListAdapter.notifyDataSetChanged();
            selectedPerson=null;
            lv.clearChoices();
            return true;
        }

        if (id == MENU_View_RemoveFriend){
            user.removeFriend(selectedPerson);
            selectedPerson=null;
            lv.clearChoices();
            friendList=user.getMyFriendList().getPersonList();
            friendListAdapter.clear();
            friendListAdapter.addAll(friendList);
            friendListAdapter.notifyDataSetChanged();
            selectedPerson=null;
            lv.clearChoices();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void initSearchView(){
        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchQuery(s);
                return false;
            }
        });
    }

    public void searchQuery(String query){

        if (state==1){
            if (query.isEmpty()){ //if query is empty
                friendList=user.getMyFriendList().getPersonList();
            }else{
                friendList = user.getMyFriendList().searchPerson(query);
            }
            friendListAdapter.clear();
            friendListAdapter.addAll(friendList);
            friendListAdapter.notifyDataSetChanged();

        }
        if (state==2){
            if (query.isEmpty()){ //if query is empty
                personList=allPerson.getPersonList();
            }else{
                personList=allPerson.searchPerson(query);
            }
            personListAdapter.clear();
            personListAdapter.addAll(personList);
            personListAdapter.notifyDataSetChanged();
        }
    }

    public void initPersonList(){
        allPerson=new PersonList();
        Person A=new Person("Harry1");
        A.setName("Harry");
        allPerson.addPerson(A);
        Person B=new Person("James1");
        B.setName("James");
        allPerson.addPerson(B);
        Person C=new Person("Lily1");
        C.setName("Lily");
        allPerson.addPerson(C);
        Person D=new Person("Dumbledore1");
        D.setName("Dumbledore");
        allPerson.addPerson(D);
        personList=allPerson.getPersonList();
    }
}
