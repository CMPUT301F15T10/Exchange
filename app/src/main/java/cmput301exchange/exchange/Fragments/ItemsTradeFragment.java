package cmput301exchange.exchange.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cmput301exchange.exchange.Activities.TradeManagerActivity;
import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Controllers.BooksTradeController;
import cmput301exchange.exchange.Controllers.TradeController;
import cmput301exchange.exchange.Interfaces.BackButtonListener;
import cmput301exchange.exchange.Interfaces.TradeMaker;
import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.Item;
import cmput301exchange.exchange.Others.CharSequenceWrapper;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Trade;
import cmput301exchange.exchange.TradeManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsTradeFragment extends Fragment implements BackButtonListener {

    private int FragmentID=4;
    private TradeMaker myActivity;
    private View myView;
    private Button myAddItem,friendAddItem,Done;
    private ArrayAdapter<Book> myItemAdapter, friendItemAdapter;
    private ArrayList<Book> myItems=new ArrayList<>(),friendItems= new ArrayList<>();
//    private TradeManager myTradeManager;
    private ListView ItemsView;
    private BooksTradeController myBooksTradeController;
    private Integer inventoryType=null; // 1 for User trade items, 2 for friend's trade items
    private Menu menu;
    private TextView totalItemsView, itemQuantityView;
    private Book selectedBook=null;
    private EditText selectedQuantityView;

    public ItemsTradeFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        myTradeManager=myActivity.getTradeManager();
//        myBooksTradeController= new BooksTradeController(myTrade);
        initBooksTradeController();
    }

    public void initBooksTradeController(){
        myBooksTradeController=myActivity.getBookTradeController();
        inventoryType = myBooksTradeController.getInventoryType();

        Inventory inventory=myActivity.assignBooks();
        if (inventory!=null){
            myBooksTradeController.addFromInventory(inventory);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.activity_trade_items_inventory, container, false);
        initButtons();
        initAdapter();
        initListView();
        initTextViews();
        return myView;
    }

    public void initMenu(){
        menu.findItem(R.id.View_My_Inventory).setVisible(true);
        menu.findItem(R.id.View_Friend_Items).setVisible(true);
        menu.findItem(R.id.View_Friend_Inventory).setVisible(true);
        menu.findItem(R.id.View_My_Items).setVisible(true);

        if (inventoryType==1){
            menu.findItem(R.id.View_My_Inventory).setVisible(true);
            menu.findItem(R.id.View_Friend_Items).setVisible(true);
        } else if(inventoryType==2){
            menu.findItem(R.id.View_Friend_Inventory).setVisible(true);
            menu.findItem(R.id.View_My_Items).setVisible(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_trade__items_inventory, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
        initMenu();
    }


    public void initAdapter() {
        if (inventoryType==1) {
            myItems = myBooksTradeController.getMyBookList();
            myItemAdapter =
                    new ArrayAdapter<>((Activity) myActivity, R.layout.list_item, myItems);
        }
        if (inventoryType == 2) {
            friendItems = myBooksTradeController.getFriendBookList();
            friendItemAdapter =
                    new ArrayAdapter<>((Activity) myActivity, R.layout.list_item, friendItems);
        }
    }

    public void setVisibility(boolean visibility){
        if (visibility==true) {
            totalItemsView.setVisibility(View.VISIBLE);
            selectedQuantityView.setVisibility(View.VISIBLE);
            itemQuantityView.setVisibility(View.VISIBLE);
        } else{
            totalItemsView.setVisibility(View.INVISIBLE);
            selectedQuantityView.setVisibility(View.INVISIBLE);
            itemQuantityView.setVisibility(View.INVISIBLE);
        }
    }

    public void initTextViews(){
        totalItemsView= (TextView) myView.findViewById(R.id.IT_TotalItems);
        selectedQuantityView= (EditText) myView.findViewById(R.id.IT_SelectedQuantity);
        itemQuantityView=(TextView) myView.findViewById(R.id.IT_ItemQuantity);
        setVisibility(false);

        selectedQuantityView.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                //Nothing
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                //Nothing
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                myBooksTradeController.setTradeBookQuantity(selectedBook.getID(), Integer.parseInt(s.toString()));
            }
        });
    }

    public void updateTextViews(){
        String amount=" / "+myBooksTradeController.getOriginalBookQuantity(selectedBook.getID()).toString();
        totalItemsView.setText(amount);
        selectedQuantityView.setText(myBooksTradeController.getTradeBookQuantity(selectedBook.getID()).toString());
    }

    public void initListView() {
        ItemsView = (ListView) myView.findViewById(R.id.IT_Items);
        ItemsView.setItemsCanFocus(false);
        ItemsView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        if (inventoryType == 1) {
            ItemsView.setAdapter(myItemAdapter);
            myItemAdapter.notifyDataSetChanged();
        }
        else if (inventoryType == 2){
            ItemsView.setAdapter(friendItemAdapter);
            friendItemAdapter.notifyDataSetChanged();
        }

        ItemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                if (ItemsView.isItemChecked(position)) {
                    selectedBook = (Book) ItemsView.getItemAtPosition(position);
                    updateTextViews();
//                    Toast.makeText(getBaseContext(), selectedPerson.toString(), Toast.LENGTH_LONG).show();
                } else {
                    selectedBook = null;
                    setVisibility(false);
                    Toast.makeText(((Activity) myActivity).getBaseContext(), "None selected", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    
    public void initButtons(){

        Done= (Button) myView.findViewById(R.id.IT_Done);
        Done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                done_Handler();
            }
        });

    }


    public  void done_Handler(){
       exit();
    }

    public void updateAdapters(){
        if (inventoryType == 1) {
            myItems=myBooksTradeController.getMyBookList();
            myItemAdapter.clear();
            myItemAdapter.addAll(myItems);
            myItemAdapter.notifyDataSetChanged();
        }
        else if (inventoryType == 2){
            friendItems=myBooksTradeController.getFriendBookList();
            friendItemAdapter.clear();
            friendItemAdapter.addAll(friendItems);
            friendItemAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.View_My_Inventory:
                switchToInventory();
                return true;

            case R.id.View_Friend_Items:
                inventoryType=2;
                switchBookList();
                return true;

            case R.id.View_Friend_Inventory:
                return true;

            case R.id.View_My_Items:
                inventoryType=1;
                switchBookList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void switchToInventory(){
        if (inventoryType==1){
            myActivity.selectItems(inventoryType, myBooksTradeController.getTradeUserInventory(), myBooksTradeController.getTradeItemPosition());
        } else if (inventoryType==2){
            myActivity.selectItems(inventoryType, myBooksTradeController.getTradePartnerInventory(), myBooksTradeController.getTradeItemPosition());
        }
    }

    public void switchBookList(){

        myBooksTradeController.setInventoryType(inventoryType);
//        initButtons();
        myBooksTradeController.initQuantityDictionary();
        initMenu();
        initAdapter();
        initListView();

//        initTextViews();
    }

    public void onResume(){
        super.onResume();
        updateAdapters();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = (TradeMaker) activity;
    }

    public void exit(){
        myActivity.switchFragment(2);
    }
    @Override
    public void onBackPress() {
        exit();
    }
}
