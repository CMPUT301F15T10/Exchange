package cmput301exchange.exchange;

import android.content.Context;

import java.util.ArrayList;

import cmput301exchange.exchange.Serializers.deepClone;


/*
NOTES:
- add a list (listTradeRequest) that stores all trades that were offered to the user
  - for now it goes through all the trades that involve the user as tradePartner
  - updates listTradeRequest everytime when getListTradeRequest and getListTradeRequest_no are called

- add two functions: getListTradeRequest and getListTradeRequest_no where
  - getListTradeRequest_no returns the size of listTradeRequest
 */

// TODO send email
/**

 * Instantiate this to deal with trades.
 * @Author Yishuo
 * @version 0.0.1
 */
public class TradeManager {

    /*
    private ArrayList<String> listAcceptedTradeOwner;
    private ArrayList<String> listCurrentTradeOwner;
    private ArrayList<String> listAcceptedTradeBorrower;
    private ArrayList<String> listCurrentTadeBorrower;
    */

    private ArrayList<Trade> listAcceptedTrade = new ArrayList<>(); // to read/load from file
    private ArrayList<Trade> listCurrentTrade = new ArrayList<>(); // to read/load from file
    private ArrayList<Trade> listCompleteTrade= new ArrayList<>();
    private ArrayList<Trade> listTradeRequest = new ArrayList<>();

    private String filePastTrade;
    private String fileCurrentTrade;
    private String message;

    private ArrayList<Trade> listTrade;

    public ArrayList<Trade> getListCompleteTrade() {
        return listCompleteTrade;
    }

    public void setListCompleteTrade(ArrayList<Trade> listCompleteTrade) {
        this.listCompleteTrade = listCompleteTrade;
    }

    public void setListAcceptedTrade(ArrayList<Trade> list){

        listAcceptedTrade=list;
    }

    public void setListCurrentTrade(ArrayList<Trade> list){
        listCurrentTrade=list;
    }

    public void setListTradeRequest(ArrayList<Trade> list){
        listTradeRequest=list;
    }

    /**
     * Adds a completed trade to the PastTrade List.
     * @param "Trade" trade
     */
    public void addCompleteTrade(Trade trade) {

        // trade is complete, add the trade to the listAcceptedTrade
        listAcceptedTrade.add(trade);
        // search the trade in the listCurrentTrade and remove it
        deleteOngoingTrade(trade);
    }

    public Trade load(Long ID, int choice, Context activity){

        switch(choice){
            case 1:
                for (Trade trade:listAcceptedTrade){
                    if (trade.getTradeId().longValue()==ID.longValue()){
                        loadPersons(trade,activity);
                        return trade;
                    }
                }
                break;
            case 2:
                for (Trade trade:listCurrentTrade){
                    if (trade.getTradeId().longValue()==ID.longValue()){
                        loadPersons(trade,activity);
                        return trade;
                    }
                }
                break;
            case 3:
                for (Trade trade:listTradeRequest){
                    if (trade.getTradeId().longValue()==ID.longValue()){
                        loadPersons(trade,activity);
                        return trade;
                    }
                }
                break;
        }
        return null;
    }

    public ArrayList<Trade> getTradeListByDate(int choice){
        ArrayList<Trade> tradeList= new ArrayList<>();
        Trade latestTrade= new Trade();
        Long latestTime= new Long(0);
        ArrayList<Trade> list;

        switch (choice){
            case 1:
                list= (ArrayList<Trade>) deepClone.deepClone(listAcceptedTrade);
                while (true) {
                    for (Trade trade : list) {
                        if (latestTime < trade.getTimeStamp()) {
                            latestTime = trade.getTimeStamp();
                            latestTrade = trade;
                        }
                    }
                    tradeList.add(latestTrade);
                    list.remove(latestTrade);
                    if (list.size()==0){
                        return tradeList;
                    }
                }

            case 2:
                list= (ArrayList<Trade>) deepClone.deepClone(listCurrentTrade);
                while (true) {
                    for (Trade trade : list) {
                        if (latestTime < trade.getTimeStamp()) {
                            latestTime = trade.getTimeStamp();
                            latestTrade = trade;
                        }
                    }
                    tradeList.add(latestTrade);
                    list.remove(latestTrade);
                    if (list.size()==0){
                        return tradeList;
                    }
                }

            case 3:
                list= (ArrayList<Trade>) deepClone.deepClone(listTradeRequest);
                while (true) {
                    for (Trade trade : list) {
                        if (latestTime < trade.getTimeStamp()) {
                            latestTime = trade.getTimeStamp();
                            latestTrade = trade;
                        }
                    }
                    tradeList.add(latestTrade);
                    list.remove(latestTrade);
                    if (list.size()==0){
                        return tradeList;
                    }
                }
        }
        return null;
    }

    public void loadPersons(Trade trade, Context activity){
        ModelEnvironment globalEnv=new ModelEnvironment(activity,null);
        trade.setTradeUser(globalEnv.getOwner());
        if (trade.hasTradePartner()==true) {
            trade.setTradePartner(globalEnv.getPersonList().getPersonByID(trade.getPartnerID()));
        }
    }


    /*

    this method removes the person objects
     */
    public void lightenTrade(Trade trade){
        trade.removePersons();
    }



    public void addOngoingTrade(Trade trade) {

        // add the trade to the listCurrentTrade
        listCurrentTrade.add(trade);
    }

    // creates an empty trade and user can user setters to add more details about the trade
    /**
     * Creates and empty trade and user can use setters to add more details about the trade
     * @param
     */
    public Trade createTrade() {

        Trade trade = new Trade();
        return trade;
    }


    public void addTrade(Trade trade) {

        // Trade(Person tradeUser, Person tradePartner, Integer tradeStatus, Integer tradeType)
        // create a new trade and add it to the listCurrentTrade
        listCurrentTrade.add(trade);
    }

    /**
     *search for that trade and change the tradeStatus to 1 and add the trade to listAcceptedTrade
     *update both user and partner's inventory
     * @param  trade The trade that you wish to accept
     */

    public void acceptTrade(Trade trade) {
        int i;
        int n = listCurrentTrade.size();
        // search for that trade
        for (i = 0; i < n; i++) {
            if (listCurrentTrade.get(i).getTradeId() == trade.getTradeId()) {
                // trade found
                // set the tradeStatus to 1: trade is accepted
                listCurrentTrade.get(i).setTradeStatus(1);
                listAcceptedTrade.add(listCurrentTrade.get(i));
                listCurrentTrade.remove(i);
                break;
            }
        }

    }

    /**
     * Search for trades and changes the tradeStatus to 2, which represents cancelled
     * If choice = 0 -> offer a counter trade
     * if choice = 1 -> not offering a counter trade
     * @param trade the trade that you wish to decline
     */
    public void declineTrade(Trade trade, int choice) {

        // search for that trade and change the tradeStatus to 2
        int i;
        int n = listCurrentTrade.size();
        for (i = 0; i < n;i++) {
            if ((listCurrentTrade.get(i).getTradeId() == trade.getTradeId()) && (listCurrentTrade.get(i).getTradeUser().getID() == trade.getTradeUser().getID())) {
                // remove the trade in listCurrentTrade and add it to the listAcceptedTrade
                listCurrentTrade.get(i).setTradeStatus(2);
                if (choice == 0) {
                    counterTrade(trade);
                }
                if (choice == 1) {
                    listAcceptedTrade.add(listCurrentTrade.get(i));
                    listCurrentTrade.remove(i);
                }
                break;
            }
        }
        // ask user if they want to make a counter trade
        //   -> swap the User and Borrower and their inventories
    }

    /**
     * Swaps the role of the owner and borrower, edits the trade
     * constructs a new trade, and asks the user to edit the trade
     * @param trade The trade that you want to counter
     */
    public void counterTrade(Trade trade) {

        // swap the role of owner and borrower, edit the trade
        // construct a new trade, and ask user to edit the trade
        Trade tradetemp = new Trade(trade.getTradePartner(), trade.getTradeUser(), 3, 0, trade.getListBookPartner(), trade.getListBookUser(), trade.getTradeId());
        // update the trade details from listCurrentTrade
        //   to do so, remove the trade and add the new trade in it
        deleteOngoingTrade(trade);
        addOngoingTrade(tradetemp);
        //return tradetemp;
    }

    public ArrayList<Trade> getCompleteTradeList() {
        int i;
        // TODO return both listCurrentTrade and listAcceptedTrade (???)
        ArrayList<Trade> tempList = new ArrayList<>();
        int n1 = listAcceptedTrade.size();
        // add all past trade to the tempList
        for (i = 0; i < n1; i++) {
            tempList.add(listAcceptedTrade.get(i));
        }
        return tempList;
    }

    /**
     * Getter for Past Trade List
     */
    public ArrayList<Trade> getListAcceptedTrade() {

        return listAcceptedTrade;
    }

    /**
     * Getter for current trade list
     */
    public ArrayList<Trade> getListCurrentTrade() {

        return listCurrentTrade;
    }

    /**
     * Deletes an ongoing trade
     * @param trade1 the trade you wish to cancel
     */
    public void deleteOngoingTrade(Trade trade1) {
        int index=0;
        for (Trade trade:listCurrentTrade){
            if (trade.getTradeId().longValue()==trade1.getTradeId().longValue()) {
                listCurrentTrade.remove(index);
                break;
            }
            index = index + 1;
        }

    }

    public void setMessage(String message) { this.message = message; }

    /**
     * Returns a list of all of your past trades
     * @param role the role that the user had in the trade
     *        0 -> User as an owner
     *        1 -> User as a borrower
     *        2 -> all
     * @param person the Person who's trade history you are viewing
     */
    public ArrayList<Trade> browsePastTrade(Integer role, Person person) {

        // role: 0 -> User as an owner
        //       1 -> User as a borrower
        //       2 -> All
        ArrayList<Trade> tempPastTrade = new ArrayList<>();
        if (role == 0) {
            int i;
            int n = listAcceptedTrade.size();
            for (i = 0; i < n; i++) {
                if (listAcceptedTrade.get(i).getTradeUser().getID() == person.getID()) {
                    tempPastTrade.add(listAcceptedTrade.get(i));
                }
            }
        } else if (role == 1) {
            int i;
            int n = listAcceptedTrade.size();
            for (i = 0; i < n; i++) {
                if (listAcceptedTrade.get(i).getTradePartner().getID() == person.getID()) {
                    tempPastTrade.add(listAcceptedTrade.get(i));
                }
            }
        } else {
            return listAcceptedTrade;
        }
        return tempPastTrade;
    }

    /**
     * Returns a list of current trades
     * @param role 0 -> Owner, 1 -> borrower, 2 -> All
     * @param person the person who's current trade list you want to view.
     */
    public ArrayList<Trade> browseCurrentTrade(Integer role, Person person) {

        // role: 0 -> User as an owner
        //       1 -> User as a borrower
        //       2 -> All
        ArrayList<Trade> tempPastTrade = new ArrayList<>();
        if (role == 0) {
            int i;
            int n = listCurrentTrade.size();
            for (i = 0; i < n; i++) {
                if (listCurrentTrade.get(i).getTradeUser().getID() == person.getID()) {
                    tempPastTrade.add(listCurrentTrade.get(i));
                }
            }
        } else if (role == 1) {
            int i;
            int n = listCurrentTrade.size();
            for (i = 0; i < n; i++) {
                if (listCurrentTrade.get(i).getTradePartner().getID() == person.getID()) {
                    tempPastTrade.add(listCurrentTrade.get(i));
                }
            }
        } else {
            return listCurrentTrade;
        }
        return tempPastTrade;
    }


    public ArrayList<Trade> getListTradeRequest() {
        return listTradeRequest;
    }

    public Integer getTradeRequest_no() {
        return listTradeRequest.size();
    }

    public void processTradeRequest(Trade trade){
        listTradeRequest.add(trade);
    }

    //TODO change tradeStatus
    public void acceptTradeRequest(Trade trade1){
        int index=0;
        for (Trade trade:listTradeRequest){
            if (trade.getTradeId().longValue()==trade1.getTradeId().longValue()) {
                listTradeRequest.remove(index);
                listAcceptedTrade.add(trade);
                trade.getTradeUser().getTradeManager().tradeGotAccepted(trade);
                break;
            }
            index = index + 1;
        }
    }

    //TODO change trade status
    public void tradeGotAccepted(Trade trade1){
        int index=0;
        for (Trade trade:listCurrentTrade){
            if (trade.getTradeId().longValue()==trade1.getTradeId().longValue()) {
                listCurrentTrade.remove(index);
                listAcceptedTrade.add(trade);
                trade.getTradeUser().getTradeManager().tradeGotAccepted(trade);
                break;
            }
            index = index + 1;
        }
    }

    //TODO change trade status
    public void sendTradeOffer(Trade trade){
        trade.getTradePartner().getTradeManager().processTradeRequest(trade);
    }
    /**
     * Searches the trade which involves user via tradeID
     * As a tradeID as a primary key, and one tradeID is assigned to one and only one trade
     * Return the trade as soon as we find it
     * Else returns NULL
     * @param id the ID of the trade you are looking for.
     */
    public Trade searchTrade(Long id, int choice) {

        if (choice==1){
            for (Trade trade:listAcceptedTrade){
                if (trade.getTradeId().longValue()==id.longValue()){
                    return trade;
                }
            }
        } else if (choice==2){
            for (Trade trade:listCurrentTrade) {
                if (trade.getTradeId().longValue() == id.longValue()) {
                    return trade;
                }
            }
        } else if (choice==3){
            for (Trade trade:listTradeRequest) {
                if (trade.getTradeId().longValue() == id.longValue()) {
                    return trade;
                }
            }
        }

        return null;
    }

    public Integer sendEmail(Trade trade, String comments) {
        // TODO: send email
        return 0;
    }
}
