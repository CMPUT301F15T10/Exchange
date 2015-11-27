package cmput301exchange.exchange.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.gson.Gson;

import java.util.ArrayList;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Controllers.TradeController;
import cmput301exchange.exchange.Fragments.ItemsTradeFragment;
import cmput301exchange.exchange.Fragments.TradeFragment;
import cmput301exchange.exchange.Fragments.TradeListFragment;
import cmput301exchange.exchange.Fragments.TradeManagerFragment;
import cmput301exchange.exchange.Interfaces.BackButtonListener;
import cmput301exchange.exchange.Interfaces.TradeMaker;
import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Trade;
import cmput301exchange.exchange.TradeManager;
import cmput301exchange.exchange.User;

public class TradeManagerActivity extends AppCompatActivity implements TradeMaker {
    public FragmentManager fm;
    private FragmentTransaction fm_T;
    private TradeFragment myTradeFragment;
    private TradeListFragment myTradeListFragment;
    private ItemsTradeFragment myItemsTradeFragment;
    private TradeManagerFragment myTradeManagerFragment;
    private TradeManager myTradeManager;
//    private Trade myTrade=null;
    private int fragmentLayoutID=R.id.tradeManager_fragmentLayout;
    private Person tradePartner=null;
    private BackButtonListener currentFragment;
    private Integer tradeListFlag=null;
    private ModelEnvironment globalEnv=null;
    public static String tradeManagerTag="Trade_Manager_TAG",tradeTag="Trade_TAG",tradeItemsTag="Trade_Items_TAG",tradeListTag="Trade_List_TAG";
    private final int INVENTORY=1, EDIT_PROFILE=2, CONFIGURATION=3, SEARCH_PEOPLE=4;
    private Intent personIntent, inventoryIntent;
    private TradeController myTradeController;
    private Boolean callTradeFragment=null;

    public void initTradeController(){
        myTradeController=new TradeController(this,new Trade(),myTradeManager);
    }
    public TradeController getTradeController() {
        return myTradeController;
    }

    public void setTradeController(TradeController myTradeController) {
        this.myTradeController = myTradeController;
    }

    public Integer getTradeListFlag() {
        return tradeListFlag;
    }

    public void setTradeListFlag(int tradeListFlag) {
        this.tradeListFlag = tradeListFlag;
    }

    public void processIntent(){
        personIntent=getIntent();
        myTradeController.setTradePartner(retrieveTradePartner());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_manager_layout);
//        myTradeManager= new TradeManager();
        loadTradeManager();
        initTradeController();
        processIntent();
        initFragments();
//        newTrade=true; // Change this line once you add the functionality of view person calling this activity
        if (callTradeFragment==true) {
            displayTrade();
        } else{
            switchFragment(1); // Initiating trade manager fragment
        }
    }

    public void loadTradeManager(){
        globalEnv= new ModelEnvironment(this, null);
        myTradeManager=globalEnv.getTradeManager();
    }

    public void saveTradeManager(){
        globalEnv.setTradeManager(myTradeManager);
        globalEnv.saveInstance(this);
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
            fm_T.replace(fragmentLayoutID,myTradeManagerFragment,tradeManagerTag);
            currentFragment= myTradeManagerFragment;
        }
        if (flag==2){
            fm_T.replace(fragmentLayoutID,myTradeFragment,tradeTag);
            currentFragment= myTradeFragment;
        }
        if (flag==3){
            fm_T.replace(fragmentLayoutID,myTradeListFragment,tradeListTag);
            currentFragment= myTradeListFragment;
        }
        if (flag==4){
            fm_T.replace(fragmentLayoutID,myItemsTradeFragment,tradeItemsTag);
            currentFragment= myItemsTradeFragment;
        }

//        fm_T.addToBackStack(null);
//        fm_T.commit();
        fm_T.commitAllowingStateLoss();
        fm.executePendingTransactions();
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

    public void displayTrade(){
//        myTrade=null;
        switchFragment(2);
    }

    //TODO
    public void displayTradeRequests(){
        // Set ItemsTradeFragment for displaying current trades
        setTradeListFlag(3);
        switchFragment(3);
    }

    public void displayItemsToTrade(Trade trade){
        myTradeController.setTrade(trade);
        switchFragment(4);
    }

    public TradeManager getTradeManager(){
        return myTradeManager;
    }

    //TODO
    public void closeFragment(int fragmentID){
        //Code for switching back to other activity that also depends on the calling fragment
    }

//    public Trade getTrade(){
//        return myTrade;
////    }
//
//    public void setTrade(Trade trade){
//        myTrade=trade;
//    }

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

//    public boolean IsNewTrade(){
//        return true;
//    }

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

    @Override
    public void finish(){
        saveTradeManager();
        super.finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SEARCH_PEOPLE) {
//            data.setClass(this, TradeManagerActivity.class);
            personIntent=data;
            myTradeController.setTradePartner(retrieveTradePartner());
            switchFragment(2); //switch to trade fragment
        }
        if (requestCode == INVENTORY) {
            data.setClass(this, TradeManagerActivity.class);
            inventoryIntent = data;
            assignBooks();
//            getTradePartner();
//            switchFragment(2); //switch to trade fragment
        }
    }

    public Person retrieveTradePartner() {
        Gson gson= new Gson();
        if (personIntent.hasExtra("Trade_Partner")) {
            String json = personIntent.getExtras().getString("Trade_Partner");
            Log.e("got person","Trade");
            if (gson.fromJson(json, Person.class)!=null){
//                throw new RuntimeException("null person");
                callTradeFragment=true;
            }
            return gson.fromJson(json, Person.class);
        }
        callTradeFragment=false;
        return null; // if no person is found
    }

    public Inventory assignBooks(){
        Gson gson= new Gson();
        if (inventoryIntent.hasExtra("Trade_Items")) {
            String json = personIntent.getExtras().getString("Trade_Items");
            return gson.fromJson(json, Inventory.class);
        }
        return null;
    }

    public void selectPerson(){
        Intent intent= new Intent(this,ViewPersonActivity.class);
        startActivityForResult(intent, SEARCH_PEOPLE);
    }

    public void selectItems(){
        Intent intent= new Intent(this,Inventory.class);
        startActivityForResult(intent, INVENTORY);
    }

}
