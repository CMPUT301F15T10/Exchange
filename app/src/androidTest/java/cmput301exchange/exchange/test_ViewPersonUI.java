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

import cmput301exchange.exchange.Activities.InventoryActivity;
import cmput301exchange.exchange.Activities.ViewPersonActivity;
import cmput301exchange.exchange.Mocks.MockBook1;
import cmput301exchange.exchange.Mocks.MockInventory;
import cmput301exchange.exchange.Mocks.MockUser;

/**
 * Created by Yishuo Wang on 2015/11/21.
 */
public class test_ViewPersonUI extends ActivityInstrumentationTestCase2 {
    private Activity viewPersonActivity;
    private Instrumentation mInstrumentation;

    private Spinner viewPersonSpinner;
    private ListView viewPersonListView;
    private TextView viewPersonTextView;

    public Person mockPerson = new MockUser("A");

    public Person person0 = new Person("User");

    private Integer num;

    public test_ViewPersonUI(){
        super(cmput301exchange.exchange.Activities.ViewPersonActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        Gson gson = new Gson();
        String json = gson.toJson(person0);
        Intent intent = new Intent();
        intent.putExtra("User", json); // HERE
        setActivityIntent(intent);
        viewPersonActivity = getActivity();
        viewPersonSpinner = (Spinner) viewPersonActivity.findViewById(R.id.spinner2);
        viewPersonListView = (ListView) viewPersonActivity.findViewById(R.id.listView2);
        viewPersonTextView = (TextView) viewPersonActivity.findViewById(R.id.textView17);
        mInstrumentation = getInstrumentation();
        num = viewPersonListView.getCount(); // number of friends
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
    public void testViewPeopleListView() {
        viewPersonActivity.runOnUiThread(new Runnable() {
            public void run() {
                viewPersonListView.requestFocus();
            }
        });
        mInstrumentation.waitForIdleSync();
        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
        mInstrumentation.waitForIdleSync();
        this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
        mInstrumentation.waitForIdleSync();
        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
        mInstrumentation.waitForIdleSync();
        assertEquals(4, viewPersonListView.getCount()); // ---- 4 on real device, 0 on emulator
        mInstrumentation.waitForIdleSync();
    }

    /*
     * US02.02.01
     * As an owner, I want to add friends
     */

    public void testAddFriend() {
        viewPersonActivity.runOnUiThread(new Runnable() {
            public void run() {
                Person person1 = new Person("B");
                person0.addFriend(person1);

                getActivity().onOptionsItemSelected(); // ---- add a friend here, call MENU_Make_Friendship but do not know how to do it
                                                       // update the friendList and shows the result, selected person is person1

                viewPersonListView.clearChoices();
                viewPersonListView.requestFocus();
            }
        });

        mInstrumentation.waitForIdleSync();

        assertEquals(5, viewPersonListView.getCount());
    }



    /*
     * US02.03.01
     * As an owner, I want to remove friends
     */
    public void testRemoveFriend() {
        viewPersonActivity.runOnUiThread(new Runnable() {
            public void run() {
                Person person1 = new Person("B");

                getActivity().onOptionsItemSelected(); // ---- delete a friend here, call MENU_View_RemoveFriend but do not know how to do it
                                                       // update the friendList and shows the result, selected person is person1

                viewPersonListView.clearChoices();
                viewPersonListView.requestFocus();
            }
        });

        mInstrumentation.waitForIdleSync();

        assertEquals(5, viewPersonListView.getCount());
    }

    /*
     * US02.04.01
     * As an owner or borrower, I will have a profile where by my contact information and city are recorded.
     */
    // This is ProfileDetailsActivity

    /*
     * US02.05.01
     * As an owner or borrower, I will be able to view the profile of anyone I know of including friends.
     */
    // This is ProfileDetailsActivity
}
