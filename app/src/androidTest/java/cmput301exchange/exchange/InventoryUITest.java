package cmput301exchange.exchange;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Spinner;

import com.google.gson.Gson;

import cmput301exchange.exchange.Activities.InventoryActivity;

public class InventoryUITest extends ActivityInstrumentationTestCase2<InventoryActivity> {

    private Activity inventory;
    private Instrumentation mInstrumentation;
    private Spinner inventorySpinner;
    public Inventory inventory1 = new Inventory();

    public InventoryUITest() {
        super(cmput301exchange.exchange.Activities.InventoryActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        Gson gson = new Gson();
        String json = gson.toJson(inventory1);

        Intent intent = new Intent(); //Create a new Intent
        intent.putExtra("Inventory",json); //Pack the Intent with a blank Inventory
        setActivityIntent(intent); //Spoof the Intent

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
