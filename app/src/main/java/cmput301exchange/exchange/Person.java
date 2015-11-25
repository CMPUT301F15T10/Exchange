package cmput301exchange.exchange;

import android.graphics.Picture;

import java.util.Random;

/**
 *
 * Representation of a user. Is inhereted by friend and User
 * @Author Yishuo, Chuck, Baihong Hang
 */

public class Person {

    private String name;
    private String location;
    private Long ID;
    private Picture myPicture;
    private String phoneNumber;
    private String userName;
    protected Inventory myInventory;
    private String email;
    protected PersonList myFriendList= new PersonList();
    private long TradeCount;
    protected boolean isUser=false;
   // private ArrayList<History> historylist;
   // private ArrayList<Trade> tradelist;


    public Person(String username) {
        this.userName = username;
        myInventory = new Inventory(); //Each user has only one Inventory.
        initID();
    }

    public boolean isUser(){
        return isUser;
    }
    public void initID(){
        Random generator = new Random(System.nanoTime());
        ID= generator.nextLong();
    }

    public long getTradeCount() {
        return TradeCount;
    }

    public void setTradeCount(long tradeCount) {
        TradeCount = tradeCount;
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return location;
    }

    public long getID() {
        return ID;
    }

    public Picture getMyPicture() {
        return myPicture;
    }

    public String getPhoneNumber() {
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

    public PersonList getMyFriendList(){
        return myFriendList;
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

    public PersonList getMyfriendlist() {
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return userName;
    }

    public void addFriend(Person friend){
        myFriendList.addPerson(friend);
    }

    public void removeFriend(Person person){
        myFriendList.removePerson(person);
    }

}
