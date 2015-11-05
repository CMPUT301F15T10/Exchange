package cmput301f15t10.exchange.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301f15t10.exchange.Activities.TradeManagerActivity;
import cmput301f15t10.exchange.Controllers.BooksTradeController;
import cmput301f15t10.exchange.Controllers.TradeListController;
import cmput301f15t10.exchange.R;
import cmput301f15t10.exchange.Trade;
import cmput301f15t10.exchange.TradeManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class TradeListFragment extends Fragment {
    private int FragmentID=3;
    private TradeManagerActivity myActivity;
    private View myView;
    private ArrayAdapter<Trade> tradeListAdapter;
    private ArrayList<Trade> tradeList= new ArrayList<>();
    private Trade myTrade;
    private TradeManager myTradeManager;
    private ListView tradeListView;
    private TradeListController myTradeListController;
    private SearchView tradeSearchView;
    private int queryType=1;


    public TradeListFragment() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myTradeManager=myActivity.getTradeManager();
        myTradeListController= new TradeListController(myTradeManager,0);
    }


    public void initAdapter() {
        tradeListAdapter =
                new ArrayAdapter<>(myActivity, R.layout.list_item, tradeList);
    }

    public void initSearchView(){
        tradeSearchView= (SearchView) myView.findViewById(R.id.TL_searchView);
        tradeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchQuery(s);
                updateAdapters();
                return false;
            }
        });
    }

    public void getOriginalTradeList(){
        tradeList=myTradeListController.getTradeRequestList();
    }

    public void searchQuery(String query){
        ArrayList<Trade> result=myTradeListController.searchTrade(query, queryType);
        if (result==null){
            getOriginalTradeList();
            return;
        } else {
            tradeList = result;
        }
    }

    public void updateAdapters(){
        tradeListAdapter.notifyDataSetChanged();
    }

    public void initListView(){
        tradeListView=(ListView) myView.findViewById(R.id.TL_itemList);
        tradeListView.setAdapter(tradeListAdapter);
        tradeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            Trade trade;
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                trade=(Trade) tradeListView.getItemAtPosition(i);
                return false;
            }
        });
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.activity_trade_listing, container, false);
        getOriginalTradeList();
        initAdapter();
        initListView();
        initSearchView();
        return myView;
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
