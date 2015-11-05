package cmput301exchange.exchange;

import android.graphics.Picture;

import java.util.ArrayList;

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
    private FriendList myfriendlist;
   // private ArrayList<History> historylist;
   // private ArrayList<Trade> tradelist;


    public Person(String username) {
        this.userName = username;
        myInventory = new Inventory(); //Each user has only one Inventory.
    }

    public String getName() {
        return userName;
    }

    public String getLocation() {
        return location;
    }

    public int getID() {
        return ID;
    }

    public Picture getMyPicture() {
        return myPicture;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public Inventory getMyInventory() {
        return myInventory;
    }

    public String getEmail() {
        return email;
    }

/* TODO IMPLEMENT THE HISTORY AND TRADE OBJECTS
    public void setHistorylist(ArrayList<History> historylist) {
        this.historylist = historylist;
    }

    public void addHistorylist(History event) {
        this.historylist.add(event);
    }

    public void setTradelist(ArrayList<Trade> tradelist) {
        this.tradelist = tradelist;
    }

    public void addTradelist(Trade trade) {
        this.tradelist.add(trade);
    }

    public FriendList getMyfriendlist() {
        return myfriendlist;
    }

    public ArrayList<History> getHistorylist() {
        return historylist;
    }

    public ArrayList<Trade> getTradelist() {
        return tradelist;
    }

*/
    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMyPicture(Picture myPicture) {
        this.myPicture = myPicture;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return this.name;
    }

}
