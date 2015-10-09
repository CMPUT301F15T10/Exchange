public class Person {
    public String name;
    public String location;
    public int ID;
    public Picture myPicture;
    public Long phoneNumber;
    public String userName;
    public Inventory myInventory;
    public String email;

    public Person(String name, int ID, String userName, String email) {
        this.name = name;
        this.ID = ID;
        this.userName = userName;
        this.email = email;
    }
}

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
