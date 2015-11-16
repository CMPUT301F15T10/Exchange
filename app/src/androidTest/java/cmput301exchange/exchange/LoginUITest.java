package cmput301exchange.exchange;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;

import cmput301exchange.exchange.Activities.Login;

public class LoginUITest extends ActivityInstrumentationTestCase2<Login> {

    private Activity login;
    private EditText username;
    private Instrumentation mInstrumentation;

    public LoginUITest() {
        super(cmput301exchange.exchange.Activities.Login.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        login = getActivity(); // get a references to the app under test

      /*
       * Get a reference to the widget of the app under test
       */
        username = (EditText) login.findViewById(R.id.LoginName);
        mInstrumentation = getInstrumentation();
    }

    public void testActivityExists() {
        assertNotNull(login);
    }

    public void testLoginInput() {

        login.runOnUiThread(new Runnable() {
            public void run() {
                username.requestFocus();
            }
        });

        mInstrumentation.waitForIdleSync();

        mInstrumentation.sendStringSync("Hello");

        mInstrumentation.waitForIdleSync();

        assertEquals("Hello", username.getText().toString());
    }

    public void testLoginButton(){

        login.runOnUiThread(new Runnable() {
            public void run() {
                username.requestFocus();
            }
        });

        mInstrumentation.waitForIdleSync();

        mInstrumentation.sendStringSync("Hello");

        mInstrumentation.waitForIdleSync();

        Button loginButton = (Button) login.findViewById(R.id.button);

        assertNotNull(loginButton);

        TouchUtils.clickView(this, loginButton);

        mInstrumentation.waitForIdleSync();


    }
}
