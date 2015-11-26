package cmput301exchange.exchange;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.net.Inet4Address;

import cmput301exchange.exchange.Activities.ViewPersonActivity;
import cmput301exchange.exchange.Mocks.MockBook1;
import cmput301exchange.exchange.Mocks.MockInventory;
import cmput301exchange.exchange.Mocks.MockPersonList;
import cmput301exchange.exchange.Mocks.MockUser;

/**
 * Created by Yishuo Wang on 2015/11/21.
 */


public class test_ViewPersonUI extends ActivityInstrumentationTestCase2<ViewPersonActivity> {
    private Activity viewPersonActivity;
    private Instrumentation mInstrumentation;

    private Spinner viewPersonSpinner;
    private ListView viewPersonListView;
    private TextView viewPersonTextView;

    private Person mockPerson = new MockUser("A");

    public test_ViewPersonUI(){
        super(cmput301exchange.exchange.Activities.ViewPersonActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        Gson gson = new Gson();
        String json = gson.toJson(mockPerson);
        Intent intent = new Intent();
        intent.putExtra("User", json); // HERE
        setActivityIntent(intent);
        viewPersonActivity = getActivity();
        getActivity().initPersonList();
        viewPersonSpinner = (Spinner) viewPersonActivity.findViewById(R.id.spinner2);
        viewPersonListView = (ListView) viewPersonActivity.findViewById(R.id.listView2);
        viewPersonTextView = (TextView) viewPersonActivity.findViewById(R.id.textView17);
        mInstrumentation = getInstrumentation();
    }

    // checks the activity exists
    public void testActivityExists() {
        assertNotNull(viewPersonActivity);
    }

    // test the spinner
    public void testViewPersonySpinner() {
        viewPersonActivity.runOnUiThread(new Runnable() {
            public void run() {
                viewPersonSpinner.requestFocus();
            }
        });
        mInstrumentation.waitForIdleSync();
        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
    }

    /*
     * US02.01.01
     * As an owner, I want to track people I know. Adding a textual username should be enough.
     */
    public void testViewFriendListView() {
        viewPersonActivity.runOnUiThread(new Runnable() {
            public void run() {
                viewPersonListView.requestFocus();
            }
        });
        mInstrumentation.waitForIdleSync();
        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
        mInstrumentation.waitForIdleSync();
        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
        mInstrumentation.waitForIdleSync();
        assertEquals(2, viewPersonListView.getCount());
        mInstrumentation.waitForIdleSync();
    }

    public void testViewAllPeopleListView() {
        viewPersonActivity.runOnUiThread(new Runnable() {
            public void run() {
                viewPersonSpinner.requestFocus();
            }
        });
        mInstrumentation.waitForIdleSync();
        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
        mInstrumentation.waitForIdleSync();
        this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
        mInstrumentation.waitForIdleSync();
        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
        mInstrumentation.waitForIdleSync();
        assertEquals(4, viewPersonListView.getCount());
        mInstrumentation.waitForIdleSync();
    }

    /*
     * US02.02
     */
    // TODO fix this up not passing
    public void testAddFriend(){
        viewPersonActivity.runOnUiThread(new Runnable() {
            public void run() {
                viewPersonSpinner.requestFocus();
                viewPersonSpinner.setSelection(1);
                viewPersonSpinner.performClick();

                viewPersonListView.requestFocusFromTouch();
                viewPersonListView.setSelection(0);
                viewPersonListView.performItemClick(viewPersonListView.getAdapter().getView(0, null, null), 0, viewPersonListView.getItemIdAtPosition(0));

                getActivity().makeFriend();

                viewPersonSpinner.requestFocus();
            }
        });
        mInstrumentation.waitForIdleSync();
        this.sendKeys(KeyEvent.KEYCODE_DPAD_UP);
        mInstrumentation.waitForIdleSync();
        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
        mInstrumentation.waitForIdleSync();
        assertEquals(3, viewPersonListView.getCount());
        mInstrumentation.waitForIdleSync();

    }

    /*
     * US02.03
     */
    public void testRemoveFriend(){
        viewPersonActivity.runOnUiThread(new Runnable() {
            public void run() {
                viewPersonListView.requestFocusFromTouch();
                viewPersonListView.setSelection(0);
                viewPersonListView.performItemClick(viewPersonListView.getAdapter().getView(0, null, null), 0, viewPersonListView.getItemIdAtPosition(0));
                getActivity().removeFriend();
                viewPersonListView.requestFocus();
            }
        });

        mInstrumentation.waitForIdleSync();

        assertEquals(1, viewPersonListView.getCount());

        mInstrumentation.waitForIdleSync();

    }

    /*
     * US02.03
     */
    public void testViewFriend(){

    }

    // TODO test top traders when implemented



}
