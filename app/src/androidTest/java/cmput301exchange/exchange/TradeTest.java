package cmput301exchange.exchange;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.google.gson.Gson;

import cmput301exchange.exchange.Activities.EditBookActivity;
import cmput301exchange.exchange.Activities.TradeItemsInventoryActivity;
import cmput301exchange.exchange.Activities.TradeManagerActivity;
import cmput301exchange.exchange.Fragments.ItemsTradeFragment;
import cmput301exchange.exchange.Fragments.TradeFragment;
import cmput301exchange.exchange.Fragments.TradeListFragment;
import cmput301exchange.exchange.Fragments.TradeManagerFragment;
import cmput301exchange.exchange.Mocks.MockTrade;
import cmput301exchange.exchange.Mocks.MockTradeList;

/**
 * Created by touqir on 21/11/15.
 */
public class TradeTest extends ActivityInstrumentationTestCase2 {

    MockTradeList mockTradeList= new MockTradeList();
    TradeManagerFragment tradeManager;
    TradeFragment tradeFragment;
    TradeListFragment tradeListFragment;
    ItemsTradeFragment itemsTradeFragment;

    private TradeManagerActivity activity;
    public TradeTest(){
        super(cmput301exchange.exchange.Activities.TradeManagerActivity.class);
    }

    // Tests whether activity starts or not
    public void testStart() {
        activity = (TradeManagerActivity)getActivity();
        assertNotNull(activity);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void prepareTradeManager(){
        ModelEnvironment globalEnv= new ModelEnvironment(activity, null);
        TradeManager myTradeManager=new TradeManager();
        myTradeManager.setListCurrentTrade(mockTradeList.currentTradeList1);
        myTradeManager.setListCurrentTrade(mockTradeList.completedTradeList1);
        myTradeManager.setListCurrentTrade(mockTradeList.requestTradeList1);
        globalEnv.saveInstance(activity);
        restartActivity();
    }

    public void restartActivity(){
        activity = (TradeManagerActivity)getActivity();
        assertNotNull(activity);
    }
}
