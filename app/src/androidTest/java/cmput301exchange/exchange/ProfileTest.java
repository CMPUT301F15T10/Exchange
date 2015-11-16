package cmput301exchange.exchange;
import android.app.Application;
import android.test.ApplicationTestCase;

public class ProfileTest extends ApplicationTestCase<Application> {

    public ProfileTest(){
        super(Application.class);
    }


    //test case for editing name
    public void testName(){
        Person profile = new Person("Bob");
        assertTrue(profile.getUserName()=="Bob");
    }

    //test case for editing phone
    public void testPhone(){
        Person profile = new Person("Bob");
        profile.setPhoneNumber("123456790");
        assertTrue(profile.getPhoneNumber() == "123456790");
    }

    //test case for editing location
    public void testLocation(){
        Person profile = new Person("Bob");
        profile.setLocation("lalaland");
        assertTrue(profile.getLocation() == "lalaland");
    }


}




