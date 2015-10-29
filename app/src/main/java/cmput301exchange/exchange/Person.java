package cmput301f15t10.exchange;

import android.graphics.Picture;

/**
 * Created by Yishuo Wang on 2015/10/5.
 */
public abstract class Person {
    private String name;
    private String location;
    private int ID;
    private Picture myPicture;
    private Long phoneNumber;
    private String userName;
    private Inventory myInventory;
    private String email;

    private Inventory inventory;

    public Person() {
        myInventory = new Inventory(); //Each user has only one Inventory.
    }

    private Inventory getInventory(){return this.inventory;}





}
