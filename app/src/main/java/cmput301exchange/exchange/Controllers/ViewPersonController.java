package cmput301exchange.exchange.Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SearchView;
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


import cmput301exchange.exchange.Activities.InventoryActivity;
import cmput301exchange.exchange.Activities.ProfileDetailsActivity;
import cmput301exchange.exchange.Activities.TradeManagerActivity;
import cmput301exchange.exchange.Interfaces.Observer;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.PersonList;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.ElasticSearch;
import cmput301exchange.exchange.User;

/**
 * Created by Charles on 11/28/2015.
 */
public class ViewPersonController implements Observer{
    private static final int MENU_Settings = Menu.FIRST;
    private static final int MENU_View_Profile = Menu.FIRST + 1;
    private static final int MENU_View_Inventory = Menu.FIRST + 2;
    private static final int MENU_Make_Friendship = Menu.FIRST + 3;
    private static final int MENU_View_RemoveFriend = Menu.FIRST + 4;
    private static final int MENU_Make_Trade = Menu.FIRST + 5;
    private static final int MENU_Group=1; //menu group of 0 is taken by the SearchView item

    private Context context;
    private ElasticSearch elasticSearch;
    private ModelEnvironment globalEnv;
    private User user;
    private Person selectedPerson;
    private Activity activity;
    private ListView lv;
    private ArrayAdapter<Person> friendListAdapter, personListAdapter;
    private ArrayList<Person> friendList = new ArrayList<>();
    private ArrayList<Person> personList = new ArrayList<>();
    private PersonList allPerson;
    private Integer state;
    private Spinner spinner;
    private SearchView mySearchView;





    public ViewPersonController(Context context, Activity activity){
        this.activity = activity;
        this.context = context;
        elasticSearch = new ElasticSearch(activity);
        initPersonList(0);
        InstantiateListView();
        InstantiateArrayAdapter();
        setState(1);
        SetClickListener();
        setSpinner();
        setSpinnerListener();
        init();






    }
    public void optionsItemSelected(MenuItem item){

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == MENU_Settings) {
            //return true;
        }

        if (id == MENU_View_Inventory) {
            Gson gson = new Gson();
            String json = gson.toJson(selectedPerson.getMyInventory());

            Intent intent = new Intent(context, InventoryActivity.class);
            intent.putExtra("Friend_Inventory",json);
            intent.putExtra("Inventory_State",2);
            activity.startActivity(intent);
            //return true;
        }

        if (id == MENU_View_Profile){
            Gson gson = new Gson();
            String json = gson.toJson(selectedPerson);

            Intent intent = new Intent(context, ProfileDetailsActivity.class).putExtra("Person",json);
            activity.startActivity(intent);
            //return true;
        }

        if (id == MENU_Make_Friendship){
            this.makeFriend();
            //return true;
        }

        if (id == MENU_View_RemoveFriend){
            this.removeFriend();
           // return true;
        }

        if (id == MENU_Make_Trade){
            this.sendBackTradePartner();
        }
    }
    public void createOptionsMenu(Menu menu){
        activity.getMenuInflater().inflate(R.menu.menu_view_person, menu);
        mySearchView=(SearchView) menu.findItem(R.id.person_search).getActionView();
    }
    public void prepareOptionsMenu(Menu menu){
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

    }
    private void setSpinnerListener(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //on selecting a spinner item
                String cat = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + cat, Toast.LENGTH_LONG).show();
                //show the result for the sort

                switch (cat) {
                    case "Friends":
                        state = 1;
                        updateAdapter(friendListAdapter);
                        break;
                    case "All People":
                        state = 2;
                        updateAdapter(personListAdapter);
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
    public void updateAdapter(ArrayAdapter adapter){
        lv.clearChoices();;
        lv.setAdapter(adapter);
    }
    private void setSpinner(){
        this.spinner = (Spinner) activity.findViewById(R.id.spinner2);
        // Create an friendListAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.view_person, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
    private void SetClickListener(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                if (lv.isItemChecked(position)) {
                    selectedPerson = (Person) lv.getItemAtPosition(position);
                    Toast.makeText(activity.getBaseContext(), selectedPerson.toString(), Toast.LENGTH_LONG).show();
                } else {
                    selectedPerson = null;
                    Toast.makeText(activity.getBaseContext(), "None selected", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void InstantiateArrayAdapter(){
        friendListAdapter = new ArrayAdapter<Person>(context,
                android.R.layout.simple_list_item_single_choice);
        friendListAdapter.addAll(friendList);

        personListAdapter = new ArrayAdapter<Person>(
                context,
                android.R.layout.simple_list_item_single_choice);
        personListAdapter.addAll(personList);

        lv.setAdapter(friendListAdapter);
    }
    private void InstantiateListView(){
        lv = (ListView) activity.findViewById(R.id.listView2);
        lv.setItemsCanFocus(false);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }

    public void init() {
//        Gson gson = new Gson();
//        Intent intent=getIntent();
//        String json=intent.getExtras().getString("User");
//        user = gson.fromJson(json, Person.class);
        Intent intent=activity.getIntent();
        if (intent.hasExtra("From_TradeManagerActivity")){
            setState(2);
        }
        globalEnv = new ModelEnvironment(context, null);
//        user=elasticSearch.getUser();
        user = globalEnv.getOwner();
        friendList = user.getMyFriendList().getPersonList();

        allPerson = globalEnv.getPersonList();
        personList = allPerson.getPersonList();



        personListAdapter.clear();
        personListAdapter.addAll(personList);
        personListAdapter.notifyDataSetChanged();

        friendListAdapter.clear();
        friendListAdapter.addAll(friendList);
        friendListAdapter.notifyDataSetChanged();
    }
    public void downloadServer(){
        user=elasticSearch.getUser();
    }
    public void updateOnline(){
        // This function should use elastic search to update any changes to user object
        elasticSearch.sendToServer(globalEnv);
    }

    public void loadUser(){
        globalEnv.loadInstance(context);
        user=globalEnv.getOwner();
    }

    public void saveUser(){
        globalEnv.setOwner(user);
        globalEnv.saveInstance(context);
    }

    public void sendBackTradePartner(){
        Gson gson = new Gson();
        Intent intent=new Intent(context,TradeManagerActivity.class);
        String json= gson.toJson(selectedPerson);
        intent.putExtra("Trade_Partner", json);

//        saveUser();
//        updateOnline();

        activity.setResult(activity.RESULT_OK, intent);
        activity.finish();

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

    public void makeFriend(){
        user.addFriend(selectedPerson);
//        saveUser();
        friendList=user.getMyFriendList().getPersonList();
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
        friendList=user.getMyFriendList().getPersonList();
        friendListAdapter.clear();
        friendListAdapter.addAll(friendList);
        friendListAdapter.notifyDataSetChanged();
        selectedPerson=null;
        lv.clearChoices();
    }

    public void initPersonList(Integer integer){
        String page = integer.toString();
        elasticSearch.addObserver(this);
        elasticSearch.fetchAllUsersFromServer("*", page);


    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public void update() {
        personList = elasticSearch.getPersonList().getPersonList();
        personListAdapter.clear();
        personListAdapter.addAll(personList);
        personListAdapter.notifyDataSetChanged();
    }
}
