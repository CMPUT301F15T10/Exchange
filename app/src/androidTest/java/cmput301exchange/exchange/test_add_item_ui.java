package cmput301exchange.exchange;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import cmput301exchange.exchange.Activities.AddBookActivity;
import cmput301exchange.exchange.Activities.InventoryActivity;

/**
 * Created by hzhu6 on 11/17/15.
 */
public class test_add_item_ui extends ActivityInstrumentationTestCase2 {
    private Activity activity;
    private EditText editname;
    private EditText editauthor;
    private EditText editquality;
    private EditText editquantity;
    private EditText editcomments;
    private Button   add_button;

    public test_add_item_ui() {
        super(cmput301exchange.exchange.Activities.AddBookActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }


    public void testAddValid(){
        activity = (AddBookActivity)getActivity();
        editname = (EditText)activity.findViewById(R.id.editName);
        editauthor = (EditText)activity.findViewById(R.id.editAuthor);
        editquality = (EditText)activity.findViewById(R.id.editQuality);
        editquantity = (EditText)activity.findViewById(R.id.editQuantity);
        editcomments = (EditText)activity.findViewById(R.id.editComment);
        add_button = (Button)activity.findViewById(R.id.add_button);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                editname.setText("abookname");
                editauthor.setText("anauthor");
            }
        });

        getInstrumentation().waitForIdleSync();

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(InventoryActivity.class.getName(),
                        null, false);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                add_button.performClick();
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


}
