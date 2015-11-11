package cmput301exchange.exchange.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301exchange.exchange.Activities.TradeManagerActivity;
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
    private ArrayAdapter<String> myItemAdapter, friendItemAdapter;
    private ArrayList<String> myItems= new ArrayList<>(), friendItems= new ArrayList<>();
    private Trade myTrade;
    private TradeManager myTradeManager;
    private ListView myItemsView,friendItemsView;
    private BooksTradeController myBooksTradeController;

    public ItemsTradeFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTrade();
        myTradeManager=myActivity.getTradeManager();
        myBooksTradeController= new BooksTradeController(myTrade);
    }

    public void initTrade(){
        myTrade=myActivity.getTrade();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.activity_trade_items_inventory, container, false);
        initButtons();
        initAdapter();
        initListView();
        return myView;
    }

    public void initAdapter() {
        myItemAdapter =
                new ArrayAdapter<>((Activity)myActivity, R.layout.list_item, myItems);

        friendItemAdapter =
                new ArrayAdapter<>((Activity)myActivity, R.layout.list_item, friendItems);
    }
    
    public void initListView(){
        myItemsView=(ListView) myView.findViewById(R.id.IT_myItems);
        friendItemsView=(ListView) myView.findViewById(R.id.IT_friendItems);
        myItemsView.setAdapter(myItemAdapter);
        friendItemsView.setAdapter(friendItemAdapter);
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
        myItems=myBooksTradeController.getMyBookList();
        friendItems=myBooksTradeController.getFriendBookList();
        myItemAdapter.notifyDataSetChanged();
        friendItemAdapter.notifyDataSetChanged();
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
        myActivity.switchFragment(2);
    }
}