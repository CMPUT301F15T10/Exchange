package cmput301exchange.exchange.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.SearchView;
//import android.support.v7.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.R;
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

    private static final int MENU_Settings = Menu.FIRST;
    private static final int MENU_View_InventoryDetails = Menu.FIRST + 1;
    private static final int MENU_Edit_Item = Menu.FIRST + 2;
    private static final int MENU_Add_Item = Menu.FIRST + 3;
    private static final int MENU_Remove_Item = Menu.FIRST + 4;
    private static final int MENU_View_Item= Menu.FIRST + 5;
    private static final int MENU_Group=1; //menu group of 0 is taken by the SearchView item

    private ListView lv;
    public ModelEnvironment globalENV;
    protected ArrayAdapter<Book> arrayAdapterBook;
    protected ArrayList<Book> bookList = new ArrayList<Book>();
    protected Person person1;
    private Integer state=0; //state=1 means inventory of user and state=2 means inventory of a friend.
    private Spinner viewSpinner=null;
    private String category="None";
    private ArrayList<Book> selectedBooks=new ArrayList<>();
    private Inventory inventory;
    private Integer bookListState=0;
    private SearchView mySearchView=null;

    private DrawerLayout leftDrawer;
    private ListView leftNavList;
    private String[] NavTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
//        Log.e("inside inventoryActivity","Before intent received");

        Intent intent = getIntent();
        String inventory_json = intent.getStringExtra("Inventory");
        state=intent.getIntExtra("Inventory_State",0);
//        Log.e("inside inventoryActivity","After intent received");
        Gson gson = new Gson();
        inventory=gson.fromJson(inventory_json,Inventory.class);
//        Log.e("got far","lo");

//        globalENV=new ModelEnvironment(this,null); // null tells it to load modelEnvironment.
//        person=globalENV.getOwner();
//        initInventory();
        initViewSpinner();

        NavTitles = getResources().getStringArray(R.array.NavigationArray);
        leftDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftNavList = (ListView) findViewById(R.id.left_drawer);

        leftNavList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, NavTitles));

        lv = (ListView) findViewById(R.id.listView3);
        lv.setItemsCanFocus(false);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        arrayAdapterBook = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice);
        arrayAdapterBook.addAll(inventory.getInventoryList());

        lv.setAdapter(arrayAdapterBook);

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
                category = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                //Toast.makeText(parent.getContext(), "Selected: " + cat, Toast.LENGTH_LONG).show();
                //show the result for the sort
                //bookList=InventoryOwner.getMyInventory().searchByCategory("cat").getInventoryList();
//                bookList.clear();
                updateBookList(inventory);
                lv.clearChoices();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                if (!(selectedBooks.contains((Book)lv.getItemAtPosition(position)))) {
                    selectedBooks.add((Book) lv.getItemAtPosition(position));
                    Toast.makeText(getBaseContext(), selectedBooks.toString(), Toast.LENGTH_LONG).show();
                } else {
                    selectedBooks.remove((Book) lv.getItemAtPosition(position));
                    Toast.makeText(getBaseContext(), "None selected", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void initViewSpinner(){
        viewSpinner=(Spinner) findViewById(R.id.View_spinner);
        ArrayAdapter<CharSequence> adapter;
        if (state==1) {

            adapter = ArrayAdapter.createFromResource(this,
                    R.array.inventory_view_user, android.R.layout.simple_spinner_item);
            viewSpinner.setAdapter(adapter);

        } else if (state==2){

            adapter = ArrayAdapter.createFromResource(this,
                    R.array.inventory_view_person, android.R.layout.simple_spinner_item);
            viewSpinner.setAdapter(adapter);
        }

        viewSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //on selecting a spinner item
                switch((String) viewSpinner.getItemAtPosition(position)){
                    case "All Items":
                        bookListState=0;
                        break;
                    case "Shared Items":
                        bookListState=1;
                        break;
                    case "Non-Shared Items":
                        bookListState=2;
                        break;
                }
                updateBookList(inventory);
                lv.clearChoices();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

//    public void initInventory(){
//        Book EternalNight=new Book();
//        EternalNight.setShareable(true);
//        EternalNight.updateTitle("Eternal Night");
//        EternalNight.updateCategory("None");
//        inventory.add(EternalNight);
//        Book HackMe= new Book();
//        HackMe.setShareable(false);
//        HackMe.updateTitle("Hack Me!");
//        HackMe.updateCategory("None");
//        inventory.add(HackMe);
//    }

    public void updateBookList(Inventory inventory){
        arrayAdapterBook.clear();

        if (bookListState==0){
            arrayAdapterBook.addAll(inventory.searchByCategory(category).getInventoryList());
        }
        else if (bookListState==1){
            arrayAdapterBook.addAll(inventory.searchByCategory(category).getSharedItems());
        }
        else if (bookListState==2){
            arrayAdapterBook.addAll(inventory.searchByCategory(category).getNonSharedItems());
        }
        arrayAdapterBook.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Gson gson= new Gson();

        if (requestCode == MENU_Add_Item) {
            if (data.hasExtra("Inventory")){
                String json=data.getExtras().getString("Inventory");
                inventory=gson.fromJson(json,Inventory.class);
                updateBookList(inventory);
                lv.clearChoices();
                selectedBooks.clear();
            }
        }

        if (requestCode == MENU_Edit_Item) {
            if (data.hasExtra("Book")){
                String json=data.getExtras().getString("Book");
                Book book=gson.fromJson(json, Book.class);
                int index=inventory.getInventoryList().indexOf(selectedBooks.get(0));
                selectedBooks.clear();
                lv.clearChoices();
                inventory.getInventoryList().set(index, book);
                updateBookList(inventory);
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.action_view).setVisible(false);
        menu.findItem(R.id.action_edit).setVisible(false);
        menu.findItem(R.id.action_remove_single).setVisible(false);
        menu.findItem(R.id.action_remove_multi).setVisible(false);
        menu.findItem(R.id.action_clone).setVisible(false);

        if(state==1) {
            if (selectedBooks!=null) {
                if (selectedBooks.size() == 1) {
                    menu.findItem(R.id.action_view).setVisible(true);
                    menu.findItem(R.id.action_edit).setVisible(true);
                    menu.findItem(R.id.action_remove_single).setVisible(true);
                    menu.findItem(R.id.action_clone).setVisible(true); // temporairly for testing purposes
                } else if(selectedBooks.size()>1) {
                    menu.findItem(R.id.action_remove_single).setVisible(false);
                    menu.findItem(R.id.action_remove_multi).setVisible(true);
                }
            }
        }

        if((state==2) && (selectedBooks!=null)) {
            if (selectedBooks.size() == 1) {
                menu.findItem(R.id.action_add).setVisible(false);
                menu.findItem(R.id.action_view).setVisible(true);
                menu.findItem(R.id.action_clone).setVisible(true);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inventory, menu);
        mySearchView=(SearchView) menu.findItem(R.id.item_search).getActionView();
        initSearchView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        Gson gson=new Gson();
        String json;
        Bundle extras = new Bundle();
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_add:
                json = gson.toJson(inventory);
                extras.putString("Inventory",json);
                extras.putString("Book",null);
                intent = new Intent(this, AddBookActivity.class).putExtras(extras);
                startActivityForResult(intent, MENU_Add_Item);

                return true;

            case R.id.action_edit:
                json = gson.toJson(selectedBooks.get(0));
                intent = new Intent(this, EditBookActivity.class).putExtra("Edit_Item", json);
                startActivityForResult(intent, MENU_Edit_Item);

                return true;

            case R.id.action_clone:
                String json1 = gson.toJson(inventory);
                String json2 = gson.toJson(selectedBooks.get(0));
                extras.putString("Inventory", json1);
                extras.putString("Book", json2);
                intent = new Intent(this, AddBookActivity.class).putExtras(extras);
                startActivityForResult(intent, MENU_Add_Item);

                return true;

            case R.id.action_remove_single:
                removeItems();

                return true;

            case R.id.action_remove_multi:
                removeItems();

                return true;

            case R.id.action_view:
                json = gson.toJson(selectedBooks.get(0));
                intent = new Intent(this, BookDetailsActivity.class).putExtra("Book", json);
                startActivity(intent);

                return true;

            case R.id.action_details:
                json = gson.toJson(inventory);
                intent = new Intent(this, InventoryDetailsActivity.class).putExtra("Inventory", json);
                startActivity(intent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void removeItems(){
        for (Book b:selectedBooks){
            inventory.removeItem(b);
        }
        selectedBooks=new ArrayList<>();

        lv.clearChoices();
        updateBookList(inventory);
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

        if (query.isEmpty()){ //if query is empty
            updateBookList(inventory);
        }else{
            Inventory inventory= this.inventory.searchByText(query);
            updateBookList(inventory);
        }
        lv.clearChoices();
    }

    @Override
    public void finish(){
        Log.e("Destroyed","On Destroy");
        Gson gson = new Gson();
        String json = gson.toJson(inventory);
        Intent inventory = new Intent();
        inventory.putExtra("Inventory", json);
        setResult(RESULT_OK, inventory);
        super.finish();
    }

}