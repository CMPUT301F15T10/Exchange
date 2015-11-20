package cmput301exchange.exchange;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import cmput301exchange.exchange.Activities.EditProfileActivity;

/**
 * Created by bq on 2015-11-20.
 */
public class EditProfileUITest extends ActivityInstrumentationTestCase2{
    private Activity editProfile;
    private EditText name,phone,email,location;
    private User user;
    private Instrumentation mInstrumentation;

    public EditProfileUITest(){
        super(cmput301exchange.exchange.Activities.EditProfileActivity.class);
    }

    protected void setUp() throws Exception{
        super.setUp();
        editProfile = getActivity();



        name = (EditText) editProfile.findViewById(R.id.editName);
        phone = (EditText) editProfile.findViewById(R.id.editPhone);
        email = (EditText) editProfile.findViewById(R.id.editEmail);
        location = (EditText) editProfile.findViewById(R.id.editLocation);
        mInstrumentation = getInstrumentation();
    }


    public void testActivityExists(){
        assertNotNull(editProfile);
    }


}
