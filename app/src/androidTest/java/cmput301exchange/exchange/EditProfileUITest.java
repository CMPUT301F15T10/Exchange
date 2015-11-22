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
 * US10.02.01 UI test case!!
 */
public class EditProfileUITest extends ActivityInstrumentationTestCase2 {
    private Activity editProfile;
    private EditText name, phone, email, location;
    private Person user;
    private Instrumentation mInstrumentation;
    private Button add_button;


    public EditProfileUITest() {
        super(cmput301exchange.exchange.Activities.EditProfileActivity.class);
    }

    public void testStart() throws Exception {
        editProfile = getActivity();
        assertNotNull(editProfile);
    }

    protected void setUp() throws Exception {
        super.setUp();
        testStart();
        editProfile();
        mInstrumentation = getInstrumentation();
    }


    public void editProfile() {

        name = (EditText) editProfile.findViewById(R.id.editName);
        phone = (EditText) editProfile.findViewById(R.id.editPhone);
        email = (EditText) editProfile.findViewById(R.id.editEmail);
        location = (EditText) editProfile.findViewById(R.id.editLocation);
//        name.setText(user.getName());
//        phone.setText(user.getPhoneNumber());
//        email.setText(user.getEmail());
//        location.setText(user.getLocation());
        add_button = (Button) editProfile.findViewById(R.id.add_button);
        mInstrumentation = getInstrumentation();

        editProfile.runOnUiThread(new Runnable() {
            public void run() {
                name.setText("tony");
                phone.setText("123456");
                email.setText("bq@123.com");
                location.setText("Edmonton");
                add_button.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        testEditedProfile();
    }

    public void testEditedProfile(){
        ModelEnvironment global=new ModelEnvironment(editProfile,null);
        User user=global.getOwner();
        assertEquals(user.getName(),"tony");
        assertEquals(user.getPhoneNumber(),"123456");
        assertEquals(user.getEmail(),"bq@123.com");
        assertEquals(user.getLocation(),"Edmonton");
    }

}

