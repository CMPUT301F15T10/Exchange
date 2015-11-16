package cmput301exchange.exchange;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Spinner;

import cmput301exchange.exchange.Activities.InventoryActivity;

public class InventoryUITest extends ActivityInstrumentationTestCase2<InventoryActivity> {

    private Activity inventory;
    private Instrumentation mInstrumentation;
    private Spinner inventorySpinner;

    public InventoryUITest() {
        super(cmput301exchange.exchange.Activities.InventoryActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        inventory = getActivity(); // get a references to the app under test

        inventorySpinner = (Spinner) inventory.findViewById(R.id.spinner1);
        mInstrumentation = getInstrumentation();
    }

    public void testActivityExists() {
        assertNotNull(inventory);
    }

    public void testInventorySpinner() {

        inventory.runOnUiThread(new Runnable() {
            public void run() {
                inventorySpinner.requestFocus();
            }
        });

        mInstrumentation.waitForIdleSync();

        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);

    }
}
