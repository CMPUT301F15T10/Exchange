package cmput301exchange.exchange;

import android.graphics.Picture;

import java.util.ArrayList;

/**
 * @Author: Chuck
 * User extends person. It serves as a distinction to use between Friend and User.
 * User represents the person that is using the program
 * Friend represents a seperate user.
 * @param Username
 */
public class User extends Person{

    public User(String Username){
        super(Username);
        isUser=true;
    }

    public void setInventory(Inventory inventory){
        this.myInventory=inventory;
    }
    public void setFriendList(PersonList friends){
        this.myFriendList=friends;
    }
}
