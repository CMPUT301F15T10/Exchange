package cmput301exchange.exchange;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.google.gson.Gson;

/**
 * Created by bq on 11/21/15.
 */
/*public class ProfileDetailsActivityTest extends ActivityInstrumentationTestCase2{
    private EditText name, phone, email, location;

    private Person person=new Person("Person");

    private Instrumentation mInstrumentation;
    private Activity profileDetails;

    public ProfileDetailsActivityTest(){
        super(cmput301exchange.exchange.Activities.ProfileDetailsActivity.class);

    }
    protected void setUp() throws Exception{
        super.setUp();
        person.setName("Tony");
        person.setPhoneNumber("123456");
        person.setEmail("123@.com");
        person.setLocation("Edmonton");
        Gson gson = new Gson();
        String json =gson.toJson(person);
        Intent intent = new Intent();
        intent.putExtra("Person", json);
        setActivityIntent(intent);
        profileDetails =getActivity();
        mInstrumentation = getInstrumentation();


    }
    public void testActivityExists() {
        assertNotNull(profileDetails);
    }

    public void testDetails(){
        Gson gson = new Gson();
        Intent intent=getIntent();
        String json=intent.getExtras().getString("Person");
        person = gson.fromJson(json, Person.class);


        name = (EditText) profileDetails.findViewById(R.id.editName);
        phone = (EditText) profileDetails.findViewById(R.id.editPhone);
        email = (EditText) profileDetails.findViewById(R.id.editEmail);
        location = (EditText) profileDetails.findViewById(R.id.editLocation);
        name.setText(person.getName());
        phone.setText(person.getPhoneNumber());
        email.setText(person.getEmail());
        location.setText(person.getLocation());
        assertEquals(name, "Tony");
        assertEquals(phone, "123456");
        assertEquals(email, "123@.com");
        assertEquals(location,"Edmonton");
    }




























}
*/






