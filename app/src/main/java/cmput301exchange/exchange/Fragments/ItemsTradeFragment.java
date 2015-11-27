package cmput301exchange.exchange.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301exchange.exchange.Activities.TradeManagerActivity;
import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Controllers.BooksTradeController;
import cmput301exchange.exchange.Controllers.TradeController;
import cmput301exchange.exchange.Interfaces.BackButtonListener;
import cmput301exchange.exchange.Interfaces.TradeMaker;
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
    private Button myAddItem,friendAddItem;
    private ArrayAdapter<Book> myItemAdapter, friendItemAdapter;
    private ArrayList<Book> myItems=new ArrayList<>(),friendItems= new ArrayList<>();
//    private TradeManager myTradeManager;
    private ListView ItemsView;
    private BooksTradeController myBooksTradeController;
    private Integer inventoryType=1; // 1 for User trade items, 2 for friend's trade items
    private Menu menu;

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
        myBooksTradeController=new BooksTradeController(myActivity.getTradeController().getTrade());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.activity_trade_items_inventory, container, false);
        initButtons();
        initAdapter();
        initListView();
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
        myItemAdapter =
                new ArrayAdapter<>((Activity)myActivity, R.layout.list_item, myItems);
        friendItemAdapter =
                new ArrayAdapter<>((Activity)myActivity, R.layout.list_item, friendItems);
    }
    
    public void initListView() {
        ItemsView = (ListView) myView.findViewById(R.id.IT_Items);
        if (inventoryType == 1) {
            ItemsView.setAdapter(myItemAdapter);
            myItemAdapter.notifyDataSetChanged();
        }
        else if (inventoryType == 2){
            ItemsView.setAdapter(friendItemAdapter);
            friendItemAdapter.notifyDataSetChanged();
        }
    }

    
    public void initButtons(){
        myAddItem= (Button) myView.findViewById(R.id.IT_myAddItem);
        friendAddItem= (Button) myView.findViewById(R.id.IT_friendAddItem);

        myAddItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myAddItem_Handler();
            }
        });

        friendAddItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                friendAddItem_Handler();
            }
        });
    }

    public void updateAdapters(){
        if (inventoryType == 1) {
            myItems=myBooksTradeController.getMyBookList();
            myItemAdapter.notifyDataSetChanged();
        }
        else if (inventoryType == 2){
            friendItems=myBooksTradeController.getFriendBookList();
            friendItemAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        menu.findItem(R.id.View_My_Inventory).setVisible(true);
        menu.findItem(R.id.View_Friend_Items).setVisible(true);
        menu.findItem(R.id.View_Friend_Inventory).setVisible(true);
        menu.findItem(R.id.View_My_Items).setVisible(true);
        switch(item.getItemId()) {
            case R.id.View_My_Inventory:
                return true;
            case R.id.View_Friend_Items:
                return true;
            case R.id.View_Friend_Inventory:
                return true;
            case R.id.View_My_Items:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO
    public void myAddItem_Handler(){
        
    }

    //TODO
    public void friendAddItem_Handler(){
        
    }

    public void onResume(){
        super.onResume();
        updateAdapters();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = (TradeManagerActivity) activity;
    }

    @Override
    public void onBackPress() {
        myActivity.displayTrade();
    }
}
