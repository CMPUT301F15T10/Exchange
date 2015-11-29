package cmput301exchange.exchange.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301exchange.exchange.Activities.TradeManagerActivity;
import cmput301exchange.exchange.Controllers.BooksTradeController;
import cmput301exchange.exchange.Controllers.TradeListController;
import cmput301exchange.exchange.Interfaces.BackButtonListener;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Trade;
import cmput301exchange.exchange.TradeManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class TradeListFragment extends Fragment implements BackButtonListener {
    private int FragmentID=3;
    private TradeManagerActivity myActivity;
    private View myView;
    private ArrayAdapter<Trade> tradeListAdapter;
    private ArrayList<Trade> tradeList;
    private Trade myTrade;
    private TradeManager myTradeManager;
    private ListView tradeListView;
    private TradeListController myTradeListController;
    private SearchView tradeSearchView;
    private Integer queryType=null;
    private Menu menu;

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public TradeListFragment() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tradeList=new ArrayList<>();
        setQueryType(myActivity.getTradeListFlag());
        myTradeManager=myActivity.getTradeManager();
        myTradeListController= new TradeListController(myTradeManager,getQueryType(),myActivity);
        getOriginalTradeList();
    }


    public void initAdapter() {
        tradeListAdapter =
                new ArrayAdapter<>(myActivity, R.layout.list_item, tradeList);
    }

    public void initSearchView(){
        tradeSearchView= (SearchView) menu.findItem(R.id.trade_search);
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
        tradeList=myTradeListController.getTradeList();
    }

    public void searchQuery(String query){
        ArrayList<Trade> result=myTradeListController.searchTrade(query);
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
                trade = (Trade) tradeListView.getItemAtPosition(i);
                showTrade(trade);
                return false;
            }
        });
    }

    public void showTrade(Trade trade){
        myActivity.displayTrade(trade);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_trade_listing, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
        this.menu=menu;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.activity_trade_listing, container, false);
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

    @Override
    public void onBackPress() {
        myActivity.switchFragment(1);
    }
}
