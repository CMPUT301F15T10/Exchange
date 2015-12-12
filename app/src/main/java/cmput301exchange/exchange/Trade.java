package cmput301exchange.exchange;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Represents a trade object. Makes up the basis for the trademanager
 * @Author: Yishuo, Touqir
 * @version: 1.0.0
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

    /**
     * Creates an empty trade
     */
    public Trade() {

        Random rand = new Random(System.nanoTime());
        this.tradeId = rand.nextLong();
        stateHistory= new ArrayList<>();
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

    /**
     * Constructor of a trade
     * @param tradeUser the user that initates the trade
     * @param tradePartner the Partner that you are sending the trade to
     * @param tradeStatus trade status
     *                    0 -> getting composed;
     *                    1 -> ongoing trades offer made, not accepted by the other user yet;
     *                    2 -> accepted;
     *                    3 -> declined;
     *                    4 -> counterTrade;
     *                    5 -> tradeRequest
     *                    6-> Complete trade 7->Item returned
     * @param tradeType trade type
     * @param listBookUser List of the books the User is offering
     * @param listBookPartner List of the books the Partner is offering
     */
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

    /**
     * Constructor of a trade
     * Constructor of a trade
     * @param tradeUser the user that initates the trade
     * @param tradePartner the Partner that you are sending the trade to
     * @param tradeStatus trade status
     *                    0 -> getting composed;
     *                    1 -> ongoing trades offer made, not accepted by the other user yet;
     *                    2 -> accepted;
     *                    3 -> declined;
     *                    4 -> counterTrade;
     *                    5 -> tradeRequest
     *                    6-> Complete trade 7->Item returned
     * @param tradeType trade type
     * @param listBookUser List of the books the User is offering
     * @param listBookPartner List of the books the Partner is offering
     * @param tradeId trade id
     */
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

    /**
     * Getter of partnerId
     * @return partnerID
     */
    public Long getPartnerID() {
        return PartnerID;
    }

    /**
     * Getter of userId
     * @return userId
     */
    public Long getUserID() {
        return UserID;
    }

    /**
     * Setter of timeSeamp
     * @param timeStamp timeStamp you wish to set
     */
    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Getter of the creationDate
     * @return creationDate of a trade
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * Setter of creationDate
     * @param creationDate date you wish to set
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    // tradeUser

    /**
     * Getter of trade user
     * @return trade user
     */
    public Person getTradeUser() {
        return tradeUser;
    }

    /**
     * Checks if there is any trade partner in a trade
     * @return true if there is a trade partner, false otherwise
     */
    public boolean hasTradePartner()
    {
        if (PartnerID==null){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Setter of trade user
     * @param tradeUser person you wish to set
     */
    public void setTradeUser(Person tradeUser) {
        this.tradeUser = tradeUser;
        if (tradeUser!=null) {
            this.UserID = tradeUser.getID();
            this.userName = tradeUser.getName();
        }
    }

    /**
     * Removes both user and partner form one tradew
     */
    public void removePersons(){
        this.tradeUser=null;
        this.tradePartner=null;
    }

    /**
     * Getter of trade partner
     * @return trade partner
     */
    public Person getTradePartner() {
        return tradePartner;
    }

    /**
     * Setter of trade partner
     * @param tradePartner trade partner you wish to set
     * @param isCounterTrade true if it is countertrade, false otherwise
     */
    public void setTradePartner(Person tradePartner, Boolean isCounterTrade) {
        Log.e("Trade Partner selected","-----");

        this.tradePartner = tradePartner;

        if (tradePartner != null){
            this.partnerName=tradePartner.getName();
            this.PartnerID = tradePartner.getID();
            this.hasPartner = true;
        }
    }

    /**
     * Set timeStamp to current time
     */
    public void setTimeStamp(){
        timeStamp=System.currentTimeMillis();
    }

    /**
     * Getter of timeStamp
     * @return time of the creation of a trade
     */
    public Long getTimeStamp(){
        return timeStamp;
    }

    /**
     * Set date of trade creation to current date
     */
    public void setDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        creationDate=dateFormat.format(date);
    }

    /**
     * Checks if two trades are the same by comparing their trade id
     * @param trade trade you wish to compare to
     * @return true if two trades are the same, false otherwise
     */
    public boolean equals(Trade trade){
        if (trade.getTradeId().longValue()==tradeId.longValue()){
            return true;
        }
        return false;
    }

    /**
     * Getter of trade status
     * @return trade status
     *                    0 -> getting composed;
     *                    1 -> ongoing trades offer made, not accepted by the other user yet;
     *                    2 -> accepted;
     *                    3 -> declined;
     *                    4 -> counterTrade;
     *                    5 -> tradeRequest
     *                    6-> Complete trade 7->Item returned
     */
    public Integer getTradeStatus() {
        return tradeStatus;
    }

    /**
     * Set trade status to a given integer
     * @param tradeStatus integer you wish set tradestatus to
     */
    public void setTradeStatus(Integer tradeStatus) {
//        stateHistory.add(tradeStatus.intValue());
        this.tradeStatus = tradeStatus;
    }

    /**
     * returns the state starting from the last. So, position=1 would return the latest state,
     * position=2 would return the previous state(if there is any);
     * @param position choose where to return the state from
     * @return final position
     */
    public Integer getStateHistory(int position){
        int finalPosition=stateHistory.size()-position;
        if (finalPosition<0){
            return null;
        }
        return stateHistory.get(finalPosition);
    }

    /**
     * Get the trade id
     * @return trade id
     */
    public Long getTradeId() {
        return tradeId;
    }

    /**
     * Get a list of books from user
     * @return list of books from user
     */
    public ArrayList<Book> getListBookUser() {
        return listBookUser;
    }

    /**
     * Set list of books from user
     * @param listBookUser list of books you wish to set to the user
     */
    public void setListBookUser(ArrayList<Book> listBookUser) {
        this.listBookUser = listBookUser;
    }

    /**
     * Get a list of books from partner
     * @return list of books from partner
     */
    public ArrayList<Book> getListBookPartner() {
        return listBookPartner;
    }

    /**
     * Set lsit of books for partner
     * @param listBookPartner list of books you wish to set to the partner
     */
    public void setListBookPartner(ArrayList<Book> listBookPartner) {
        this.listBookPartner = listBookPartner;
    }


    /**
     * Return a string of owner's name, partner's name and date of creation
     * @return a string of owner's name, partner's name and date of creation
     */
    public String toString(){
        // You may have to add other texts depending on the status
        if (hasTradePartner()==false){
            return "Created: "+creationDate;
        }
        return "Owner: "+partnerName+". Burrower: "+userName + "\n"+ "Created: "+creationDate;
    }

    /**
     * Get the state history
     * @return state history
     */
    public ArrayList<Integer> getStateHistory() {
        return stateHistory;
    }

    /**
     * Set state history
     * @param stateHistory state history you wish to set
     */
    public void setStateHistory(ArrayList<Integer> stateHistory) {
        this.stateHistory = stateHistory;
    }

    /**
     * Set user id
     * @param userID id you wish to set to user id
     */
    public void setUserID(Long userID) {
        UserID = userID;
    }

    /**
     * Set trade id
     * @param tradeId trade id you wish to set
     */
    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    /**
     * Set partner id
     * @param partnerID partner id you wish to set
     */
    public void setPartnerID(Long partnerID) {
        PartnerID = partnerID;
    }

    /**
     * Clone an exactly the same trade object
     * @return trade with same details as the current trade
     */
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
