package cmput301exchange.exchange;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import cmput301exchange.exchange.Activities.HomeActivity;
import cmput301exchange.exchange.Activities.InventoryActivity;
import cmput301exchange.exchange.Activities.Login;
import cmput301exchange.exchange.Activities.TradeActivity;
import cmput301exchange.exchange.Activities.ViewPersonActivity;

/**
 * Created by hzhu6 on 11/21/15.
 */
public class HomeActivitiyUiTest extends ActivityInstrumentationTestCase2 {
    private Activity activity;
    private Button inventory_button;
    private Button trade_button;
    private Button browse_button;

    public HomeActivitiyUiTest() {
        super(cmput301exchange.exchange.Activities.HomeActivity.class);
    }

    public  void test_inventory_button (){
        activity= (HomeActivity)getActivity();
        inventory_button = (Button)activity.findViewById(R.id.button2);

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                inventory_button.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        InventoryActivity receiverActivity = (InventoryActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);

        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                InventoryActivity.class, receiverActivity.getClass());
        getInstrumentation().removeMonitor(receiverActivityMonitor);
    }


    public  void test_browse_button (){
        activity= (HomeActivity)getActivity();
        browse_button = (Button)activity.findViewById(R.id.button5);

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(ViewPersonActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                browse_button.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        ViewPersonActivity receiverActivity = (ViewPersonActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);

        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                ViewPersonActivity.class, receiverActivity.getClass());
        getInstrumentation().removeMonitor(receiverActivityMonitor);
    }

    // TODO test view my profile





}
