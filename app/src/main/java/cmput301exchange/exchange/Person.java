package cmput301exchange.exchange;

import android.content.Context;
import android.graphics.Picture;

import java.util.ArrayList;
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
    protected ArrayList<Long> myFriendList= new ArrayList<>();
    private long TradeCount;
    protected boolean isUser=false;
    private TradeManager tradeManager;

    public long getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp() {
        TimeStamp = System.currentTimeMillis()/1000L; //Sets the timestamp to the current unix time.
    }

    private long TimeStamp;

    public Person(String username) {
        this.userName = username;
        myInventory = new Inventory(); //Each user has only one Inventory.
        initID();
        initTradeManager();
    }

    public void initTradeManager(){
        tradeManager=new TradeManager();
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
        return this.userName;
    }

    public String getLocation() {
        return location;
    }

    public Long getID() {
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

    public PersonList getMyFriendList(Context context){
        PersonManager pm= new PersonManager(context);
        return pm.extractPersonList(myFriendList);
    }

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
        myFriendList.add(friend.getID());
    }

    public void removeFriend(Person person){
        myFriendList.remove(person.getID());
    }

    public boolean equals(Person person){
        if (person.getID().longValue()==ID.longValue()){
            return true;
        }

        return false;
    }

    public void setTradeManager(TradeManager tradeManager){
        this.tradeManager=tradeManager;
    }

    public TradeManager getTradeManager(){
        return this.tradeManager;
    }

}
