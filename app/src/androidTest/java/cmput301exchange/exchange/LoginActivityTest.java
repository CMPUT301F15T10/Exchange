package cmput301exchange.exchange;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import cmput301exchange.exchange.Activities.HomeActivity;
import cmput301exchange.exchange.Activities.Login;

/**
 * Created by hzhu6 on 11/17/15.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2 {
    private Activity activity;
    private Button login_button;
    private EditText login_text;

    public LoginActivityTest() {
        super(cmput301exchange.exchange.Activities.Login.class);
    }

    public void testStart() throws Exception {
        activity = getActivity();
    }

    public void testlogin(){
        activity= (Login)getActivity();
        login_button = (Button)activity.findViewById(R.id.button);
        login_text = (EditText)activity.findViewById(R.id.LoginName);
        final String acc = "acc";
       activity.runOnUiThread(new Runnable() {
           public void run() {
               login_text.setText(acc);
           }
       });

        getInstrumentation().waitForIdleSync();

        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(HomeActivity.class.getName(),null, false);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                login_button.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();


       HomeActivity receiverActivity = (HomeActivity)
                receiverActivityMonitor.waitForActivityWithTimeout(1000);


        assertNotNull("ReceiverActivity is null", receiverActivity.getApplication());
        assertEquals("Monitor for ReceiverActivity has not been called",
                1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type",
                HomeActivity.class, receiverActivity.getClass());
        getInstrumentation().removeMonitor(receiverActivityMonitor);

    }


}
