package cmput301exchange.exchange;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.google.gson.Gson;

import cmput301exchange.exchange.Activities.EditBookActivity;
import cmput301exchange.exchange.Activities.TradeManagerActivity;
import cmput301exchange.exchange.Mocks.MockTrade;

/**
 * Created by touqir on 21/11/15.
 */
public class test_Trade extends ActivityInstrumentationTestCase2 {

    public test_Trade(){
        super(cmput301exchange.exchange.Activities.TradeManagerActivity.class);
    }

    public void testStart() {
        Activity activity = getActivity();
        assertNotNull(activity);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockTrade mockTrade= new MockTrade();
        Gson gson = new Gson();
//        String json = gson.toJson(myBook);
//
//        Intent intent = new Intent(); //Create a new Intent
//        intent.putExtra("Edit_Item", json); //Pack the Intent with a blank Inventory
//        setActivityIntent(intent); //Spoof the Intent
//
//        activity= (EditBookActivity)getActivity();
//        assertNotNull(activity.fm.findFragmentByTag(EditBookActivity.editBookTag)); // By default EditBookFragment should be started
//        editBook=activity.getEditBookFragment();
//        editComment=activity.getEditCommentFragment();
//        initBook2();
//        getInstrumentation().waitForIdleSync();
    }
}
