package cmput301f15t10.exchange.Tests;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import cmput301f15t10.exchange.Activities.TradeManagerActivity;
import cmput301f15t10.exchange.Fragments.ItemsTradeFragment;
import cmput301f15t10.exchange.Fragments.TradeFragment;
import cmput301f15t10.exchange.Fragments.TradeListFragment;
import cmput301f15t10.exchange.Fragments.TradeManagerFragment;
import cmput301f15t10.exchange.R;
import cmput301f15t10.exchange.TradeManager;

/**
 * Created by touqir on 06/11/15.
 */
public class test_TradeActivity extends ActivityInstrumentationTestCase2 {
    private TradeManagerActivity activity;
    private TradeFragment tradeFragment;
    private TradeManagerFragment tradeManagerFragment;
    private TradeListFragment tradeListFragment;
    private ItemsTradeFragment itemsTradeFragment;
    private TradeManager myTradeManager;

    public test_TradeActivity(){
        super(cmput301f15t10.exchange.Activities.TradeManagerActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
        activity= (TradeManagerActivity)getActivity();
        tradeFragment=activity.getTradeFragment();
        tradeManagerFragment=activity.getTradeManagerFragment();
        tradeListFragment=activity.getTradeListingFragment();
        itemsTradeFragment=activity.getItemsTradeFragment();
        initBook();
        initBook2();
        getInstrumentation().waitForIdleSync();
    }

    public void initTradeManager(){

    }


    public void testTradeManager(){
        Button makeTrade= (Button) tradeManagerFragment.getView().findViewById(R.id.TM_makeTrade);
        Button currentTrade= (Button) tradeManagerFragment.getView().findViewById(R.id.TM_currentTrade);
        Button pastTrade= (Button) tradeManagerFragment.getView().findViewById(R.id.TM_pastTrade);
        Button checkTradeRequest= (Button) tradeManagerFragment.getView().findViewById(R.id.TM_checkRequest);
        EditText showTradeRequestNo= (EditText) tradeManagerFragment.getView().findViewById(R.id.TM_showRequest);



    }
}
