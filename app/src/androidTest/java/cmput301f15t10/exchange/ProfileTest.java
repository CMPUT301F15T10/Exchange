package cmput301f15t10.exchange;

public class ProfileTest {
    public void testInstantiate(){
        try {
            Person profile = new Person();
        }catch (Exception e){
            throw new RuntimeException;
        }
    }

    //test case for editing name
    public void testName(){
        Person profile = new Person();
        profile.setName('Bob')
        assertTrue(config.getName()='Bob');
    }

    //test case for editing phone
    public void testPhone(){
        Person profile = new Person();
        profile.setPhoneNumber('123456790')
        assertTrue(config.getPhoneNumber()='123456790');
    }

    //test case for editing location
    public void testPhone(){
        Person profile = new Person();
        profile.setLocation('lalaland')
        assertTrue(config.getLocation()='lalaland');
    }


}



}
