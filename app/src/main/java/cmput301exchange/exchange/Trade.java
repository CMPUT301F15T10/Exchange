package cmput301exchange.exchange;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


/*
NOTES:
- tradeUser is the one that offer trades to the other person, trade Partner is the one that receives
    the offer
- tradeId is generated by a random number for now. TODO pick one number that hasn't used before
- able to construct a new empty trade
*/
/**
 * Represents a trade object. Makes up the basis for the trademanager
 */
public class Trade {

    private Person tradeUser; // the one that offer trades to their friends
    private Person tradePartner=null; // the one that receive trades from their friends
    private Integer tradeStatus; // 0 -> getting composed; 1 -> ongoing trades offer made, not accepted by the other user yet;
                                  // 2 -> accepted; 3 -> declined; 4 -> counterTrade; 5 -> tradeRequest 6-> Complete trade 7->Item returned

    private ArrayList<Book> listBookUser; // list of books the user wants to trade
    private ArrayList<Book> listBookPartner; // list of books the partner wants to trade
    private ArrayList<Integer> stateHistory;

    private Long tradeId; // generate a trade id, PRIMARY KEY
    private Long UserID=null,PartnerID=null;

    private String creationDate;
    private Long timeStamp;
    private boolean hasPartner=false;

    private String partnerName;
    private String userName;

    private boolean isLoaded=false;

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    // call this constructor
    // creates an empty trade
    public Trade() {
        Random rand = new Random(System.nanoTime());
        this.tradeId = rand.nextLong();
        stateHistory= new ArrayList<>();
        this.hasPartner = true;
    }

    public Trade(Person tradeUser, Person tradePartner) {
        Random rand = new Random(System.nanoTime());
        this.tradeId = rand.nextLong();
        this.tradeUser = tradeUser;
        this.tradePartner = tradePartner;
        this.hasPartner = true;
    }
    /**
     * Constructor of the trade
     * @param tradeUser the user that initates the trade
     * @param tradePartner the Partner that you are sending the trade to
     * @param listBookUser List of the books the User is offering
     */
    public Trade(Person tradeUser, Person tradePartner, ArrayList<Book> listBookUser, ArrayList<Book> listBookPartner) {//to creat a trade, include traders, items and booklist for both side

        this.tradeUser = tradeUser;
        this.tradePartner = tradePartner;
        this.listBookUser = listBookUser;
        this.listBookPartner = listBookPartner;
        this.tradeStatus = 0; // default
        if (tradePartner!=null) {
            this.hasPartner = true;
        }
        Random rand = new Random(System.nanoTime());
        this.tradeId = rand.nextLong();
    }

    public Trade(Person tradeUser, Person tradePartner, Integer tradeStatus, Integer tradeType, ArrayList<Book> listBookUser, ArrayList<Book> listBookPartner) {
        this.tradeUser = tradeUser;
        this.tradePartner = tradePartner;
        this.tradeStatus = tradeStatus;
        this.listBookUser = listBookUser;
        this.listBookPartner = listBookPartner;
        if (tradePartner != null) {
            this.hasPartner = true;
        }
        Random rand = new Random(System.nanoTime());
        this.tradeId = rand.nextLong();
    }

    public Trade(Person tradeUser, Person tradePartner, Integer tradeStatus, Integer tradeType, ArrayList<Book> listBookUser, ArrayList<Book> listBookPartner, Integer tradeId) {
        this.tradeUser = tradeUser;
        this.tradePartner = tradePartner;
        this.tradeStatus = tradeStatus;
        this.listBookUser = listBookUser;
        this.listBookPartner = listBookPartner;
        Random rand = new Random(System.nanoTime());
        this.tradeId = rand.nextLong();
        if (tradePartner!=null) {
            this.hasPartner = true;
        }
    }

    public Long getPartnerID() {
        return PartnerID;
    }

//    public void setPartnerID(Long partnerID) {
//        PartnerID = partnerID;
//    }

    public Long getUserID() {
        return UserID;
    }

//    public void setUserID(Long userID) {
//        UserID = userID;
//    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }


    /*
    public Trade(Person tradeUser, Person tradePartner, Integer tradeStatus, ArrayList<Item> listItemUser, ArrayList<Item> listItemPartner, Integer tradeType) {
        this.tradeUser = tradeUser;
        this.tradePartner = tradePartner;
        this.tradeStatus = tradeStatus;
        this.listItemUser = listItemUser;
        this.listItemPartner = listItemPartner;
        this.tradeType = tradeType;
        Random rand = new Random();
        this.tradeId = rand.nextInt();
    }

    */

    // tradeUser
    public Person getTradeUser() {
        return tradeUser;
    }

    public boolean hasTradePartner()
    {
        if (PartnerID==null){
            return false;
        }
        else {
            return true;
        }
    }

    public void setTradeUser(Person tradeUser) {
        this.tradeUser = tradeUser;
        if (tradeUser!=null) {
            this.UserID = tradeUser.getID();
            this.userName = tradeUser.getName();
        }
    }

    public void removePersons(){
        this.tradeUser=null;
        this.tradePartner=null;
    }

    // tradePartner
    public Person getTradePartner() {
        return tradePartner;
    }

    public void setTradePartner(Person tradePartner, Boolean isCounterTrade) {
        Log.e("Trade Partner selected","-----");
        this.tradePartner = tradePartner;
        if (tradePartner != null){
            this.partnerName=tradePartner.getName();
            this.PartnerID = tradePartner.getID();
            this.hasPartner = true;
        }
    }

    public void setTimeStamp(){
        timeStamp=System.currentTimeMillis();
    }

    public Long getTimeStamp(){
        return timeStamp;
    }

    public void setDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        creationDate=dateFormat.format(date);
    }

    public boolean equals(Trade trade){
        if (trade.getTradeId().longValue()==tradeId.longValue()){
            return true;
        }
        return false;
    }

    // tradeStatus
    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        stateHistory.add(tradeStatus.intValue());
        this.tradeStatus = tradeStatus;
    }

    /*
    returns the state starting from the last. So, position=1 would return the latest state, position=2 would return
    the previous state(if there is any);
     */
    public Integer getStateHistory(int position){
        int finalPosition=stateHistory.size()-position;
        if (finalPosition<0){
            return null;
        }
        return stateHistory.get(finalPosition);
    }
    // tradeId
    public Long getTradeId() {
        return tradeId;
    }

    // listBookUser
    public ArrayList<Book> getListBookUser() {
        return listBookUser;
    }

    public void setListBookUser(ArrayList<Book> listBookUser) {
        this.listBookUser = listBookUser;
    }

    // listBookPartner
    public ArrayList<Book> getListBookPartner() {
        return listBookPartner;
    }

    public void setListBookPartner(ArrayList<Book> listBookPartner) {
        this.listBookPartner = listBookPartner;
    }

    //TODO
    public String toString(){
        // You may have to add other texts depending on the status
        if (hasTradePartner()==false){
            return "Created: "+creationDate;
        }
        return "Owner: "+partnerName+". Burrower: "+userName + "\n"+ "Created: "+creationDate;
    }

    public ArrayList<Integer> getStateHistory() {
        return stateHistory;
    }

    public void setStateHistory(ArrayList<Integer> stateHistory) {
        this.stateHistory = stateHistory;
    }

    public void setUserID(Long userID) {
        UserID = userID;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public void setPartnerID(Long partnerID) {
        PartnerID = partnerID;
    }

    public Trade clone(){
        Trade cloned=new Trade();
        cloned.setTradeUser(tradeUser);
        cloned.setUserID(UserID);

        cloned.setTradePartner(tradePartner, false);
        cloned.setPartnerID(PartnerID);

        ArrayList<Book> userBooks=new ArrayList<>();
        for (Book book:listBookUser){
            userBooks.add(book);
        }
        cloned.setListBookUser(userBooks);

        ArrayList<Book> partnerBooks=new ArrayList<>();
        for (Book book:listBookPartner){
            partnerBooks.add(book);
        }

        cloned.setListBookPartner(partnerBooks);
        cloned.setTradeStatus(tradeStatus.intValue());
        cloned.setTradeId(this.tradeId.longValue());
        cloned.setCreationDate(creationDate);
        cloned.setTimeStamp(timeStamp);
        cloned.setStateHistory(stateHistory);
        return cloned;
    }
}
