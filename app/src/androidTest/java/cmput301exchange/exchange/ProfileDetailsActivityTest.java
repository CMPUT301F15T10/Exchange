package cmput301exchange.exchange;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

/*
 * This class is for testing Profile Details UI
*/

public class ProfileDetailsActivityTest extends ActivityInstrumentationTestCase2 {
    private TextView name, phone, email, location;

    private Person person = new Person("Tony12");

    private Instrumentation mInstrumentation;
    private Activity profileDetails;

    public ProfileDetailsActivityTest() {
        super(cmput301exchange.exchange.Activities.ProfileDetailsActivity.class);

    }

    public void testActivityExists() {
        assertNotNull(profileDetails);
    }

    protected void setUp() throws Exception {
        super.setUp();
        person.setName("Tony12");
        person.setPhoneNumber("123456");
        person.setEmail("123@.com");
        person.setLocation("Edmonton");
        Gson gson = new Gson();
        String json = gson.toJson(person);
        Intent intent = new Intent();
        intent.putExtra("Person", json);
        setActivityIntent(intent);
        profileDetails = getActivity();
        testActivityExists();
        mInstrumentation = getInstrumentation();
    }


    public void testDetails() {
        name = (TextView) profileDetails.findViewById(R.id.profileNameDetails);
        phone = (TextView) profileDetails.findViewById(R.id.profilePhoneDetails);
        email = (TextView) profileDetails.findViewById(R.id.profileEmailDetails);
        location = (TextView) profileDetails.findViewById(R.id.profileLocationDetails);
//        name.setText(person.getName());
//        phone.setText(person.getPhoneNumber());
//        email.setText(person.getEmail());
//        location.setText(person.getLocation());
        assertEquals(name.getText().toString(), "Tony12");
        assertEquals(phone.getText().toString(), "123456");
        assertEquals(email.getText().toString(), "123@.com");
        assertEquals(location.getText().toString(), "Edmonton");
    }
}


































