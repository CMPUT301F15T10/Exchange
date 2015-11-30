package cmput301exchange.exchange.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cmput301exchange.exchange.Interfaces.Observer;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.PersonList;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.ElasticSearch;
import cmput301exchange.exchange.Serializers.SearchHit;
import cmput301exchange.exchange.User;

public class ViewPersonActivity extends AppCompatActivity implements Observer {


    private static final int MENU_Settings = Menu.FIRST;
    private static final int MENU_View_Profile = Menu.FIRST + 1;
    private static final int MENU_View_Inventory = Menu.FIRST + 2;
    private static final int MENU_Make_Friendship = Menu.FIRST + 3;
    private static final int MENU_View_RemoveFriend = Menu.FIRST + 4;
    private static final int MENU_Make_Trade = Menu.FIRST + 5;
    private static final int MENU_Group=1; //menu group of 0 is taken by the SearchView item

    public ModelEnvironment globalEnv;
    private ListView lv;
    private ArrayList<Person> friendList= new ArrayList<>();
    private ArrayList<Person> personList= new ArrayList<>();
    private PersonList allPerson = new PersonList();
    private ArrayAdapter<Person> friendListAdapter, personListAdapter;
    private Person selectedPerson=null;
    private User user;
    private Integer state=0; // 1 for user inventory, 2 for friend inventory
    private SearchView mySearchView=null;

    private ElasticSearch elasticSearch;
    private List<SearchHit<Person>> SearchList;
    private PersonList testlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_person);
//        init();
        elasticSearch = new ElasticSearch(this);
        init();
//        friendList=user.getMyFriendList().getPersonList();
        initPersonList(10);

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


    public void init() {
//        Gson gson = new Gson();
//        Intent intent=getIntent();
//        String json=intent.getExtras().getString("User");
//        user = gson.fromJson(json, Person.class);
        Intent intent=getIntent();
        if (intent.hasExtra("From_TradeManagerActivity")){

        }
        globalEnv = new ModelEnvironment(this, null);
//        user=elasticSearch.getUser();
        user = globalEnv.getOwner();

//        allPerson = globalEnv.getPersonList();
//        personList = allPerson.getPersonList();


//        personListAdapter.clear();
//        personListAdapter.addAll(personList);
//        personListAdapter.notifyDataSetChanged();
    }

    @Override
//    TODO
    public void onPause(){
        saveUser();
        updateOnline();
        super.onPause();
    }

    @Override
    public void onResume(){
        loadUser();
//        downloadServer();
        super.onResume();
    }
    public void finish(){
//        Gson gson = new Gson();
//        Intent intent=new Intent();
//        String json= gson.toJson(user);
//        intent.putExtra("User",json);
//        setResult(RESULT_OK, intent);

//        saveUser();
//        updateOnline();
        setResult(RESULT_OK, new Intent());

        super.finish();
    }

    public void downloadServer(){
        user=elasticSearch.getUser();
    }
    public void updateOnline(){
        // This function should use elastic search to update any changes to user object
        elasticSearch.sendToServer(globalEnv);
    }

    public void loadUser(){
        globalEnv.loadInstance(this);
        user=globalEnv.getOwner();
    }
    public void saveUser(){
        globalEnv.setOwner(user);
        globalEnv.saveInstance(this);
    }

    public void sendBackTradePartner(){
        Gson gson = new Gson();
        Intent intent=new Intent(this,TradeManagerActivity.class);
        String json= gson.toJson(selectedPerson);
        intent.putExtra("Trade_Partner", json);

//        saveUser();
//        updateOnline();

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
            menu.add(MENU_Group, MENU_Make_Trade, Menu.NONE, "Offer a Trade");
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

            Intent intent = new Intent(this, InventoryActivity.class);
            intent.putExtra("Friend_Inventory",json);
            intent.putExtra("Inventory_State",2);
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
            makeFriend();
            return true;
        }

        if (id == MENU_View_RemoveFriend){
            removeFriend();
            return true;
        }

        if (id == MENU_Make_Trade){
            sendBackTradePartner();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        this.finish();
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
                friendList=user.getMyFriendList(this).getPersonList();
            }else{
                friendList = user.getMyFriendList(this).searchPerson(query);
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

    public void makeFriend(){
        user.addFriend(selectedPerson);
//        saveUser();
        friendList=user.getMyFriendList(this).getPersonList();
        friendListAdapter.clear();
        friendListAdapter.addAll(friendList);
        friendListAdapter.notifyDataSetChanged();
        selectedPerson=null;
        lv.clearChoices();
    }

    public void removeFriend(){
        user.removeFriend(selectedPerson);
//        saveUser();
//            selectedPerson=null;
//            lv.clearChoices();
        friendList=user.getMyFriendList(this).getPersonList();
        friendListAdapter.clear();
        friendListAdapter.addAll(friendList);
        friendListAdapter.notifyDataSetChanged();
        selectedPerson=null;
        lv.clearChoices();
    }

    //TODO
    public void initPersonList(Integer integer){
        String page = integer.toString();
        elasticSearch.addObserver(this);

        elasticSearch.fetchAllUsersFromServer("*", page);


    }

    @Override
    public void update() {

        PersonList list = elasticSearch.getPersonList();
//        list.removePerson(user);
        personList=list.getPersonList();
        globalEnv.setPersonList(list);
//        for (int i=0;i<personList.size();++i){
//            if (personList.get(i).getID()==user.getID()){
//                personList.remove(i);
//            }
//        }
//        personList.remove(user.getID());
        Log.e("update_viewperson: ",String.valueOf(personList.size()));
        personListAdapter.clear();
        personListAdapter.addAll(personList);
        personListAdapter.notifyDataSetChanged();

        friendList = user.getMyFriendList(this).getPersonList();
        friendListAdapter.clear();
        friendListAdapter.addAll(friendList);
        friendListAdapter.notifyDataSetChanged();
    }


}



//
//package cmput301exchange.exchange.Activities;
//
//        import android.content.Intent;
//        import android.support.v7.app.AppCompatActivity;
//        import android.os.Bundle;
//        import android.support.v7.widget.SearchView;
//        import android.view.Menu;
//        import android.view.MenuItem;
//        import android.view.View;
//        import android.widget.AbsListView;
//        import android.widget.AdapterView;
//        import android.widget.ArrayAdapter;
//        import android.widget.ListView;
////import android.widget.SearchView;
//        import android.widget.Spinner;
//        import android.widget.Toast;
//
//        import com.google.gson.Gson;
//
//        import java.util.ArrayList;
//        import java.util.List;
//
//        import cmput301exchange.exchange.Controllers.ViewPersonController;
//        import cmput301exchange.exchange.Interfaces.Observer;
//        import cmput301exchange.exchange.ModelEnvironment;
//        import cmput301exchange.exchange.PersonList;
//        import cmput301exchange.exchange.Person;
//        import cmput301exchange.exchange.R;
//        import cmput301exchange.exchange.Serializers.ElasticSearch;
//        import cmput301exchange.exchange.Serializers.SearchHit;
//        import cmput301exchange.exchange.User;
//
//public class ViewPersonActivity extends AppCompatActivity {
//    private ViewPersonController controller;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_person);
//        controller = new ViewPersonController(this, this);
//        controller.init();
//        controller.initPersonList(10);
//    }
//
//    @Override
//    public void onPause(){
//        controller.saveUser();
//        controller.updateOnline();
//        super.onPause();
//    }
//
//    @Override
//    public void onResume(){
//        controller.loadUser();
////        downloadServer();
//        super.onResume();
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        controller.prepareOptionsMenu(menu);
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        controller.createOptionsMenu(menu);
//        controller.initSearchView();
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        controller.optionsItemSelected(item);
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onBackPressed(){
//        this.finish();
//    }
//
//    @Override
//    public void finish(){
//        if (controller.getState() == 2) {
//            controller.sendBackTradePartner();
//        }else{
//            setResult(RESULT_OK, new Intent());
//
//        }
//        super.finish();
//    }
//
//
//}
