package cmput301f15t10.exchange.Fragments;


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

import cmput301f15t10.exchange.Activities.TradeManagerActivity;
import cmput301f15t10.exchange.Controllers.ItemsTradeController;
import cmput301f15t10.exchange.Controllers.TradeController;
import cmput301f15t10.exchange.Item;
import cmput301f15t10.exchange.Others.CharSequenceWrapper;
import cmput301f15t10.exchange.R;
import cmput301f15t10.exchange.Trade;
import cmput301f15t10.exchange.TradeManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsTradeFragment extends Fragment {

    private int FragmentID=4;
    private TradeManagerActivity myActivity;
    private View myView;
    private Button myAddItem,friendAddItem;
    private ArrayAdapter<String> myItemAdapter, friendItemAdapter;
    private ArrayList<String> myItems= new ArrayList<>(), friendItems= new ArrayList<>();
    private Trade myTrade;
    private TradeManager myTradeManager;
    private ListView myItemsView,friendItemsView;
    private ItemsTradeController myItemsTradeController;

    public ItemsTradeFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTrade();
        myTradeManager=myActivity.getTradeManager();
        myItemsTradeController= new ItemsTradeController(myTrade);
    }

    public void initTrade(){
        myTrade=myActivity.getTrade();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.activity_trade, container, false);
        initButtons();
        initAdapter();
        initListView();
        return myView;
    }

    public void initAdapter() {
        myItemAdapter =
                new ArrayAdapter<>(myActivity, R.layout.list_item, myItems);

        friendItemAdapter =
                new ArrayAdapter<>(myActivity, R.layout.list_item, friendItems);
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
        myItems=myItemsTradeController.getMyItemList();
        friendItems=myItemsTradeController.getFriendItemList();
        myItemAdapter.notifyDataSetChanged();
        friendItemAdapter.notifyDataSetChanged();
    }

    public void myAddItem_Handler(){
        
    }
    
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

    public void exit(){
        myActivity.closeFragment(FragmentID);
    }


}
