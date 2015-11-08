package cmput301exchange.exchange.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import cmput301exchange.exchange.Fragments.ItemsTradeFragment;
import cmput301exchange.exchange.Fragments.TradeFragment;
import cmput301exchange.exchange.Fragments.TradeListFragment;
import cmput301exchange.exchange.Fragments.TradeManagerFragment;
import cmput301exchange.exchange.Interfaces.BackButtonListener;
import cmput301exchange.exchange.Interfaces.TradeMaker;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Trade;
import cmput301exchange.exchange.TradeManager;

public class TradeManagerActivity extends AppCompatActivity implements TradeMaker {
    private FragmentManager fm;
    private FragmentTransaction fm_T;
    private TradeFragment myTradeFragment;
    private TradeListFragment myTradeListFragment;
    private ItemsTradeFragment myItemsTradeFragment;
    private TradeManagerFragment myTradeManagerFragment;
    private TradeManager myTradeManager;
    private Trade myTrade=null;
    private int fragmentLayoutID=R.id.tradeManager_fragmentLayout;
    private Person tradePartner=null;
    private BackButtonListener currentFragment;
    private Integer tradeListFlag=null;

    public Integer getTradeListFlag() {
        return tradeListFlag;
    }

    public void setTradeListFlag(int tradeListFlag) {
        this.tradeListFlag = tradeListFlag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_manager_layout);
        myTradeManager= new TradeManager();
        initFragments();
        switchFragment(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trade_manager, menu);
        return true;
    }

    //TODO
    public void initFragments(){
        fm=getFragmentManager();
        fm_T=fm.beginTransaction();
        myTradeFragment = new TradeFragment();
        myTradeListFragment = new TradeListFragment();
        myItemsTradeFragment= new ItemsTradeFragment();
        myTradeManagerFragment= new TradeManagerFragment();
        // Put here code for initializing photo view/edit fragment
    }

    public void switchFragment(int flag){
        fm_T=fm.beginTransaction();

        if (flag==1){
            fm_T.replace(fragmentLayoutID,myTradeManagerFragment);
            currentFragment= myTradeManagerFragment;
        }
        if (flag==2){
            fm_T.replace(fragmentLayoutID,myTradeFragment);
            currentFragment= myTradeFragment;
        }
        if (flag==3){
            fm_T.replace(fragmentLayoutID,myTradeListFragment);
            currentFragment= myTradeListFragment;
        }
        if (flag==4){
            fm_T.replace(fragmentLayoutID,myItemsTradeFragment);
            currentFragment= myItemsTradeFragment;
        }

//        fm_T.addToBackStack(null);
        fm_T.commit();
//        fm.executePendingTransactions();
    }

    //TODO
    public void displayCurrentTrades(){
        // Set TradeListFragment for displaying current trades
        setTradeListFlag(1);
        switchFragment(3);
    }

    //TODO
    public void displayPastTrades(){
        // // Set TradeListFragment for displaying past trades
        setTradeListFlag(2);
        switchFragment(3);
    }

    public void makeTrade(){
        myTrade=null;
        switchFragment(2);
    }

    //TODO
    public void displayTradeRequests(){
        // Set ItemsTradeFragment for displaying current trades
        setTradeListFlag(3);
        switchFragment(3);
    }

    public void displayItemsToTrade(Trade trade){
        myTrade=trade;
        switchFragment(4);
    }

    public TradeManager getTradeManager(){
        return myTradeManager;
    }

    //TODO
    public void closeFragment(int fragmentID){
        //Code for switching back to other activity that also depends on the calling fragment
    }

    public Trade getTrade(){
        return myTrade;
    }

    public void setTrade(Trade trade){
        myTrade=trade;
    }

    public TradeManagerFragment getTradeManagerFragment(){
        return myTradeManagerFragment;
    }

    public TradeFragment getTradeFragment(){
        return myTradeFragment;
    }

    public TradeListFragment getTradeListingFragment(){
        return myTradeListFragment;
    }

    public ItemsTradeFragment getItemsTradeFragment(){
        return myItemsTradeFragment;
    }

    public Person getTradePartner(){
        return tradePartner;
    }

    public boolean IsNewTrade(){
        return true;
    }

    public void setCurrentFragment(BackButtonListener fragment){
        currentFragment=fragment;
    }
    @Override
    public void onBackPressed(){
        if (currentFragment!=null){
            currentFragment.onBackPress();
        } else{
            super.onBackPressed();
        }
    }

}
