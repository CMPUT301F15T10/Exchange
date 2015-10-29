package cmput301f15t10.exchange;

import android.graphics.Picture;

/**
 * Created by Yishuo Wang on 2015/10/5.
 */
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
