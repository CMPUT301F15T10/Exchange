package cmput301exchange.exchange;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import cmput301exchange.exchange.Activities.HomeActivity;
import cmput301exchange.exchange.Activities.Login;

/**
 * Created by bq on 2015-11-20.
 */
/*public class EditProfileUITest extends ActivityInstrumentationTestCase2 {
    private Activity editProfile;
    private EditText name, phone, email, location;
    private Person user;
    private Instrumentation mInstrumentation;
    private Button add_button;


    public EditProfileUITest() {
        super(cmput301exchange.exchange.Activities.EditProfileActivity.class);
    }
    public void testStart() throws Exception {
        Activity editProfile = getActivity();
        assertNotNull(editProfile);
    }

    protected void setUp() throws Exception {
        super.setUp();
        Gson gson = new Gson();
        Intent intent=getIntent();
        String json=intent.getExtras().getString("User");
        user = gson.fromJson(json, Person.class);


        mInstrumentation = getInstrumentation();
    }


    public void testedit(){

        name = (EditText) editProfile.findViewById(R.id.editName);
        phone = (EditText) editProfile.findViewById(R.id.editPhone);
        email = (EditText) editProfile.findViewById(R.id.editEmail);
        location = (EditText) editProfile.findViewById(R.id.editLocation);
        name.setText(user.getName());
        phone.setText(user.getPhoneNumber());
        email.setText(user.getEmail());
        location.setText(user.getLocation());
        add_button = (Button) editProfile.findViewById(R.id.add_button);
        mInstrumentation = getInstrumentation();

        editProfile.runOnUiThread(new Runnable() {
            public void run() {
                name.setText("tony");
                phone.setText("123456");
                email.setText("bq@123.com");
                location.setText("Edmonton");
            }
        });
        getInstrumentation().waitForIdleSync();

        editProfile.runOnUiThread(new Runnable() {
            public void run() {
                add_button.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        editProfile = getActivity();




        assertTrue(editProfile.isFinishing());




    }



}*/

