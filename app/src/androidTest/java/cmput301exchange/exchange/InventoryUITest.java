package cmput301exchange.exchange;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;

import cmput301exchange.exchange.Activities.InventoryActivity;
import cmput301exchange.exchange.Mocks.MockBook1;
import cmput301exchange.exchange.Mocks.MockInventory;

public class InventoryUITest extends ActivityInstrumentationTestCase2<InventoryActivity> {

    private Activity inventory;
    private Instrumentation mInstrumentation;
    private Spinner inventorySpinner;
    private Spinner inventoryViewSpinner;
    private ListView itemList;
    public Inventory mockInventory = new MockInventory();

    public InventoryUITest() {
        super(cmput301exchange.exchange.Activities.InventoryActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        Gson gson = new Gson();
        String json = gson.toJson(mockInventory);

        Intent intent = new Intent(); //Create a new Intent
        intent.putExtra("Inventory", json); //Pack the Intent with a mock Inventory
        setActivityIntent(intent); //Spoof the Intent

        inventory = getActivity(); // get a references to the app under test

        inventorySpinner = (Spinner) inventory.findViewById(R.id.spinner1);
        inventoryViewSpinner = (Spinner) inventory.findViewById(R.id.View_spinner);
        itemList = (ListView) inventory.findViewById(R.id.listView3);

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

    public void testInventoryViewSpinner() {

        inventory.runOnUiThread(new Runnable() {
            public void run() {
                inventoryViewSpinner.requestFocus();
            }
        });

        mInstrumentation.waitForIdleSync();

        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);

    }


    // UC 1.01
    public void testItemList() {

        inventory.runOnUiThread(new Runnable() {
            public void run() {
                itemList.requestFocus();
            }
        });

        mInstrumentation.waitForIdleSync();

        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);

        mInstrumentation.waitForIdleSync();

        this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);

        mInstrumentation.waitForIdleSync();

        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);

        mInstrumentation.waitForIdleSync();

        assertEquals(2, itemList.getCount());

        mInstrumentation.waitForIdleSync();

    }

    // UC 1.02
    public void testAddItem(){

        inventory.runOnUiThread(new Runnable() {
            public void run() {
                Book book = new MockBook1();
                mockInventory.add(book);
                getActivity().updateBookList(mockInventory);
                itemList.clearChoices();
                itemList.requestFocus();
            }
        });

        mInstrumentation.waitForIdleSync();

        assertEquals(3, itemList.getCount());

    }

    // UC 1.03
    public void testRemoveItem(){

        inventory.runOnUiThread(new Runnable() {
            public void run() {
                itemList.requestFocusFromTouch();
                itemList.setSelection(0);
                itemList.performItemClick(itemList.getAdapter().getView(0, null, null), 0, itemList.getItemIdAtPosition(0));
                getActivity().removeItems();
                itemList.requestFocus();
            }
        });

        mInstrumentation.waitForIdleSync();

        assertEquals(1, itemList.getCount());

    }

    // UC 3.01, 3.03
    public void testSearchInventory(){

        inventory.runOnUiThread(new Runnable() {
            public void run() {
                itemList.requestFocus();
                getActivity().searchQuery("books");
            }
        });

        mInstrumentation.waitForIdleSync();

        assertEquals(1, itemList.getCount());
    }

    // UC 3.02
    public void testSearchCategory(){

        inventory.runOnUiThread(new Runnable() {
            public void run() {
                inventorySpinner.requestFocus();
            }
        });

        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);

        mInstrumentation.waitForIdleSync();

        this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);

        mInstrumentation.waitForIdleSync();

        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);

        mInstrumentation.waitForIdleSync();

        assertEquals(1, itemList.getCount());

    }

}
