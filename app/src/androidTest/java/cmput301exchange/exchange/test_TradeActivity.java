package cmput301exchange.exchange;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cmput301exchange.exchange.Activities.TradeManagerActivity;
import cmput301exchange.exchange.Fragments.ItemsTradeFragment;
import cmput301exchange.exchange.Fragments.TradeFragment;
import cmput301exchange.exchange.Fragments.TradeListFragment;
import cmput301exchange.exchange.Fragments.TradeManagerFragment;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.TradeManager;

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
        super(cmput301exchange.exchange.Activities.TradeManagerActivity.class);
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
        getInstrumentation().waitForIdleSync();
    }

    public void initTradeManager(){

    }


    public void testTradeManager(){
        Button displayTrade= (Button) tradeManagerFragment.getView().findViewById(R.id.TM_displayTrade);
        Button currentTrade= (Button) tradeManagerFragment.getView().findViewById(R.id.TM_currentTrade);
        Button pastTrade= (Button) tradeManagerFragment.getView().findViewById(R.id.TM_pastTrade);
        Button checkTradeRequest= (Button) tradeManagerFragment.getView().findViewById(R.id.TM_checkRequest);
        TextView showTradeRequestNo= (TextView) tradeManagerFragment.getView().findViewById(R.id.TM_showRequest);



    }
}
