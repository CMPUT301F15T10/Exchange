package cmput301exchange.exchange;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import cmput301exchange.exchange.Serializers.deepClone;

/**
 * Instantiate this to deal with trades.
 * @Author Touqir, Yishuo
 * @version 0.0.1
 */
public class TradeManager {

    private ArrayList<Trade> listTransactionedTrade = new ArrayList<>(); // to read/load from file
    private ArrayList<Trade> listCurrentTrade = new ArrayList<>(); // to read/load from file
    private ArrayList<Trade> listCompleteTrade= new ArrayList<>();
    private ArrayList<Trade> listTradeRequest = new ArrayList<>();
    private ArrayList<ArrayList<Book>> temporaryInsufficientBookList= new ArrayList<>();
    private String filePastTrade;
    private String fileCurrentTrade;
    private String message;
    private ArrayList<Trade> listTrade;

    /**
     * Getter for listCompleteTrade
     * @param
     */
    public ArrayList<Trade> getListCompleteTrade() {
        return listCompleteTrade;
    }

    /**
     * Setter for lsitCompleteTrade
     * @param listCompleteTrade the list of complete trades you wish to save
     */
    public void setListCompleteTrade(ArrayList<Trade> listCompleteTrade) {
        this.listCompleteTrade = listCompleteTrade;
    }

    /**
     * Setter for listTransactionedTrade
     * @param list the list you wish to save
     */
    public void setListTransactionedTrade(ArrayList<Trade> list){

        listTransactionedTrade=list;
    }

    /**
     * Setter for listCurrentTrade
     * @param list the lsit you wish to save
     */
    public void setListCurrentTrade(ArrayList<Trade> list){
        listCurrentTrade=list;
    }

    /**
     * Setter for listTradeRequest
     * @param list the list you wish to save
     */
    public void setListTradeRequest(ArrayList<Trade> list){
        listTradeRequest=list;
    }


    /**
     * Load a trade from various list of trades by choice
     * @param ID the trade id you wish to load
     * @param choice 1: load from lsitTransactionedTrade
     *                 2: load from listCurrentTrade
     *                 3: load from listTradeRequest
     *                 4: load from listCompleteTrade
     * @param activity current activity
     * @return trade object
     */
    public Trade load(Long ID, int choice, Context activity){

        switch(choice){
            case 1:
                for (Trade trade:listTransactionedTrade){
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

            case 4:
                for (Trade trade:listCompleteTrade){
                    if (trade.getTradeId().longValue()==ID.longValue()){
                        loadPersons(trade,activity);
                        return trade;
                    }
                }
                break;
        }
        return null;
    }

    /**
     * Remove trade from listTranactionedTrade
     * @param trade trade you wish to remove
     */
    public void deleteDeclinedTrade(Trade trade){
        int i=0;
        for (Trade trade1:listTransactionedTrade){
            if (trade1.getTradeId().equals(trade.getTradeId())){
                listTransactionedTrade.remove(i);
            }
            i=i+1;
        }
    }

    /**
     * Returns a list of trades within given time
     * @param choice 1: load from listTransactionedTrade
     *                 2: load from listCurrentTrade
     *                 3: load from listTradeRequest
     * @return a list of trades
     */
    public ArrayList<Trade> getTradeListByDate(int choice){
        ArrayList<Trade> tradeList= new ArrayList<>();
        Trade latestTrade= new Trade();
        Long latestTime= new Long(0);
        ArrayList<Trade> list;

        switch (choice){
            case 1:
                list= (ArrayList<Trade>) deepClone.deepClone(listTransactionedTrade);
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

    /**
     * Remove a trade from listCompleteTrade
     * @param trade1 the trade you wish to remove
     */
    public void deleteCompleteTrade(Trade trade1){
        int i=0;
        for (Trade trade:listCompleteTrade){
            if (trade.getTradeId().longValue()==trade1.getTradeId().longValue()){
                listCompleteTrade.remove(i);
                break;
            }
            i=i+1;
        }
    }

    /**
     * Loads the person that login to the app and sets the tradePartner to false
     * @param trade the trade you wish to change
     * @param activity current activity
     */
    public void loadPersons(Trade trade, Context activity){
        ModelEnvironment globalEnv=new ModelEnvironment(activity,null);

        if (trade.getUserID().equals(globalEnv.getOwner().getID())){
            trade.setTradeUser(globalEnv.getOwner());
            if (trade.hasTradePartner()==true) {
                trade.setTradePartner(globalEnv.getPersonList().getPersonByID(trade.getPartnerID()),false);
            }
        } else if (trade.getPartnerID().equals(globalEnv.getOwner().getID())){
            trade.setTradePartner(globalEnv.getOwner(), false);
            trade.setTradeUser(globalEnv.getPersonList().getPersonByID(trade.getPartnerID()));
        }
    }

    /**
     * Removes the person objects from a trade
     * @param trade the trade you wish to remove two person objects from
     */
    public void lightenTrade(Trade trade){
        trade.removePersons();
    }

    /**
     * Adds one trade to listCurrentTrade
     * @param trade the trade you wish to add to listCurrentTrade
     */
    public void addUnInitiatedTrade(Trade trade) {
        // add the trade to the listCurrentTrade
        //lightenTrade(trade);
        for (Trade trade1: listCurrentTrade){
            if (trade.getTradeId().equals(trade1.getTradeId())){
                return;
            }
        }
        listCurrentTrade.add(trade);
    }

    /**
     * Creates an empty trade and user can use setters to add more details about the trade
     * @param
     */
    public Trade createTrade() {

        Trade trade = new Trade();
        return trade;
    }

    /**
     * Swaps the role of the owner and borrower, edits the trade
     * constructs a new trade, and asks the user to edit the trade
     * @param trade1 The trade that you want to counter
     */
    public void counterTrade(Trade trade1, Person partner) {

        int index=0;
        trade1.setTradeStatus(4);
        for (Trade trade:listTradeRequest) {
            if (trade.getTradeId().longValue() == trade1.getTradeId().longValue()) {
                listTradeRequest.remove(index);
                break;
            }
            index=index+1;
        }
        listCurrentTrade.add(trade1);
        ArrayList<Book> partnerBooks;
        if (partner.getID().equals(trade1.getTradeUser().getID())){
            partnerBooks=trade1.getListBookUser();
        } else{
            partnerBooks= new ArrayList<>();
            counterTradeHelper(trade1.getTradeUser(),trade1);
        }
        //pushChanges(trade1,1,activity);
        trade1.setTradeUser(trade1.getTradePartner());
        trade1.setListBookUser(trade1.getListBookPartner());
        trade1.setListBookPartner(partnerBooks);
        trade1.setTradePartner(partner, true);
    }

    /**
     * Put one trade from one person's listTransactionedTrade to listCurrentTrade
     * @param originalTrader person you wish to make change to
     * @param t trade that needed to be changed
     */
    public void counterTradeHelper(Person originalTrader, Trade t){
        int i=0;
        Trade trade1=t.clone();
        trade1.setTradeStatus(3); //declined!
        originalTrader.getTradeManager().getListTransactionedTrade().add(trade1);
        for (Trade trade:originalTrader.getTradeManager().getListCurrentTrade()){
            if (trade1.getTradeId().equals(trade.getTradeId())){
                originalTrader.getTradeManager().getListCurrentTrade().remove(i);
                break;
            }
            i=i+1;
        }
    }

    /**
     * Getter for listTranactionedTrade
     * @return
     */
    public ArrayList<Trade> getListTransactionedTrade() {

        return listTransactionedTrade;
    }

    /**
     * Getter for listCurrentTrade
     * @return
     */
    public ArrayList<Trade> getListCurrentTrade() {

        return listCurrentTrade;
    }

    /**
     * Return items
     * @param trade the trade you wish to make changes to
     * @param activity current activity
     * @return
     */
    public boolean returnTradeItems(Trade trade, Activity activity){
        if (revertTransaction(trade)==false){
            return false;
        } else {
//            setTradeComplete(trade);
            for (Trade t1:trade.getTradeUser().getTradeManager().getListTransactionedTrade()){
                if (t1.getTradeId().equals(trade.getTradeId())){
                    t1.setTradeStatus(7);
                }
            }
            for (Trade t1:trade.getTradePartner().getTradeManager().getListTransactionedTrade()){
                if (t1.getTradeId().equals(trade.getTradeId())){
                    t1.setTradeStatus(7);
                }
            }
            pushChanges(trade,3, activity);
            return true;
        }
    }

    /**
     * Checks and see if there is any insufficient books from either owner or borrower.
     * @param trade1 the trade you wish to check
     * @param choice 1: checks the borrower
     *                 2: checks the owner
     * @return ture if there is any insufficient books from either owner or borrower.
     *          false if there is no insufficient books from both owner and borrower
     */
    public boolean findInsufficientBooks(Trade trade1, int choice){
        //choice 1 is for performTransaction, choice 2 is for revertTransaction
        temporaryInsufficientBookList= new ArrayList<>();
        temporaryInsufficientBookList.add(new ArrayList<Book>());
        temporaryInsufficientBookList.add(new ArrayList<Book>());

        boolean insufficientUserBook=false;
        boolean insufficientPartnerBook=false;

        Inventory userInventory=trade1.getTradeUser().getMyInventory();
        Inventory partnerInventory=trade1.getTradePartner().getMyInventory();

        if (choice==1) {
            for (Book book : trade1.getListBookUser()) {
                if (userInventory.hasBook(book) == false) {
                    insufficientUserBook = true;
                    temporaryInsufficientBookList.get(0).add(book);
                    continue;
                }
                if (userInventory.getBookByID(book.getID()).getQuantity() < book.getQuantity()) {
                    insufficientUserBook = true;
                    temporaryInsufficientBookList.get(0).add(book);
                }
            }

            for (Book book : trade1.getListBookPartner()) {
                if (partnerInventory.hasBook(book) == false) {
                    insufficientPartnerBook = true;
                    temporaryInsufficientBookList.get(1).add(book);
                    continue;
                }

                if (partnerInventory.getBookByID(book.getID()).getQuantity() < book.getQuantity()) {
                    insufficientPartnerBook = true;
                    temporaryInsufficientBookList.get(1).add(book);
                }
            }

        } else if (choice==2){
            for (Book book : trade1.getListBookUser()) {
                if (partnerInventory.hasBook(book) == false) {
                    insufficientPartnerBook = true;
                    temporaryInsufficientBookList.get(1).add(book);
                    continue;
                }
                if (partnerInventory.getBookByID(book.getID()).getQuantity() < book.getQuantity()) {
                    insufficientPartnerBook = true;
                    temporaryInsufficientBookList.get(1).add(book);
                }
            }

            for (Book book : trade1.getListBookPartner()) {
                if (userInventory.hasBook(book) == false) {
                    insufficientUserBook = true;
                    temporaryInsufficientBookList.get(0).add(book);
                    continue;
                }

                if (userInventory.getBookByID(book.getID()).getQuantity() < book.getQuantity()) {
                    insufficientUserBook = true;
                    temporaryInsufficientBookList.get(0).add(book);
                }
            }
        }
        return insufficientPartnerBook || insufficientUserBook;
    }

    /**
     * Accepts the trade and swap each users' books
     * @param trade1 the trade you wish to make change to
     * @return false if there is any insufficient books
     *          true otherwise
     */
    public boolean performTransaction(Trade trade1){
        if (findInsufficientBooks(trade1,1)){ // choice is 1
            return false;
        } else {
            Inventory userInventory=trade1.getTradeUser().getMyInventory();
            Inventory partnerInventory=trade1.getTradePartner().getMyInventory();

            for (Book book:trade1.getListBookUser()){
                Book book1=userInventory.getBookByID(book.getID());
                book1.updateQuantity(book1.getQuantity()-book.getQuantity());
                if (partnerInventory.hasBook(book)){
                    Book book2=partnerInventory.getBookByID(book.getID());
                    book2.updateQuantity(book2.getQuantity()+book.getQuantity());
                } else {
                    partnerInventory.add(book);
                }
            }

            for (Book book:trade1.getListBookPartner()){
                Book book1=partnerInventory.getBookByID(book.getID());
                book1.updateQuantity(book1.getQuantity()-book.getQuantity());
                if (userInventory.hasBook(book)){
                    Book book2=userInventory.getBookByID(book.getID());
                    book2.updateQuantity(book2.getQuantity()+book.getQuantity());
                } else {
                    userInventory.add(book);
                }
            }
            trade1.setTradeStatus(2);
            return true;
        }
    }

    /**
     * Similar to performTransaction(), but it checks partner's list books
     * @param trade1 trade you wish make changes to
     * @return false if there is any insufficient books
     *          true otherwise
     */
    public boolean revertTransaction(Trade trade1){

        if (findInsufficientBooks(trade1,2)){
            return false;
        } else {
            Inventory userInventory=trade1.getTradeUser().getMyInventory();
            Inventory partnerInventory=trade1.getTradePartner().getMyInventory();

            for (Book book:trade1.getListBookUser()){
                Book book1=partnerInventory.getBookByID(book.getID());
                book1.updateQuantity(book1.getQuantity()-book.getQuantity());
                if (userInventory.hasBook(book)){
                    Book book2=userInventory.getBookByID(book.getID());
                    book2.updateQuantity(book2.getQuantity()+book.getQuantity());
                } else {
                    userInventory.add(book);
                }
            }

            for (Book book:trade1.getListBookPartner()){
                Book book1=userInventory.getBookByID(book.getID());
                book1.updateQuantity(book1.getQuantity()-book.getQuantity());
                if (partnerInventory.hasBook(book)){
                    Book book2=partnerInventory.getBookByID(book.getID());
                    book2.updateQuantity(book2.getQuantity()+book.getQuantity());
                } else {
                    partnerInventory.add(book);
                }
            }
            return true;
        }

    }

    /**
     * Deletes an ongoing trade
     * @param trade1 the trade you wish to cancel
     */
    public void deleteUnInitiatedTrade(Trade trade1) {

        int index=0;
        for (Trade trade:listCurrentTrade){
            if (trade.getTradeId().longValue()==trade1.getTradeId().longValue()) {
                listCurrentTrade.remove(index);
                break;
            }
            index = index + 1;
        }

    }

    /**
     * Returns a list of all of your past trades
     * @param role the role that the user had in the trade
     *        0 -> User as an owner
     *        1 -> User as a borrower
     *        2 -> all
     * @param person the Person who's trade history you are viewing
     */
    public ArrayList<Trade> browseCompletedTrade(Integer role, Person person) {

        // role: 0 -> User as an owner
        //       1 -> User as a borrower
        //       2 -> All
        ArrayList<Trade> tempPastTrade = new ArrayList<>();
        if (role == 1) {
            int i;
            int n = listCompleteTrade.size();
            for (i = 0; i < n; i++) {
                if (listCompleteTrade.get(i).getUserID() == person.getID()) {
                    tempPastTrade.add(listCompleteTrade.get(i));
                }
            }
        } else if (role == 0) {
            int i;
            int n = listTransactionedTrade.size();
            for (i = 0; i < n; i++) {
                if (listCompleteTrade.get(i).getPartnerID() == person.getID()) {
                    tempPastTrade.add(listCompleteTrade.get(i));
                }
            }
        } else {
            return listCompleteTrade;
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
        if (role == 1) {
            int i;
            int n = listTransactionedTrade.size();
            for (i = 0; i < n; i++) {
                if (listTransactionedTrade.get(i).getUserID() == person.getID()) {
                    tempPastTrade.add(listTransactionedTrade.get(i));
                }
            }
        } else if (role == 0) {
            int i;
            int n = listTransactionedTrade.size();
            for (i = 0; i < n; i++) {
                if (listTransactionedTrade.get(i).getPartnerID() == person.getID()) {
                    tempPastTrade.add(listTransactionedTrade.get(i));
                }
            }
        } else {
            return listTransactionedTrade;
        }
        return tempPastTrade;
    }

    /**
     * Getter for the listTradeRequest
     * @return list of trade requests that have sent to user
     */
    public ArrayList<Trade> getListTradeRequest() {
        return listTradeRequest;
    }

    /**
     * Returns the size of listTradeRequest
     * @return size of listTradeRequest
     */
    public Integer getTradeRequest_no() {
        return listTradeRequest.size();
    }

    /**
     * Adds one trade to listTradeRequest
     * @param trade the trade you wish to add to listTradeRequest
     */
    public void processTradeRequest(Trade trade){
//        lightenTrade(trade);
        Gson gson= new Gson();
        Log.e("push 1", String.valueOf(gson.toJson(trade.getTradePartner()).length()));
        Trade trade1=trade.clone();
        Log.e("push 2", String.valueOf(gson.toJson(trade1.getTradePartner()).length()));
        trade1.setTradeStatus(5); //trade request
        Log.e("new push", "");
        //lightenTrade(trade1);
        Log.e("push 3,",String.valueOf(gson.toJson(trade1.getTradePartner()).length()));
        listTradeRequest.add(trade1);
    }

    /**
     * Decline a trade
     * @param trade1 trade you wish to decline
     * @param activity current activity
     */
    public void declineTradeRequest(Trade trade1, Activity activity){
        int index=0;
        trade1.setTradeStatus(3); //declined
        for (Trade trade:listTradeRequest){
            if (trade.getTradeId().longValue()==trade1.getTradeId().longValue()) {
                listTradeRequest.remove(index);
                trade.getTradeUser().getTradeManager().tradeGotDeclined(trade1);
//                lightenTrade(trade1);
                pushChanges(trade1, 1, activity);// push the user. pushchanges lighten the trade
                listTransactionedTrade.add(trade1);
                break;
            }
            index = index + 1;
        }
    }

    /**
     * Removes a trade from listCurrentTrade and adds it to listTransactionedTrade
     * @param trade1 trade you wish to remove
     */
    public void tradeGotDeclined(Trade trade1){
        trade1.setTradeStatus(3); //declined
        int index=0;
        for (Trade trade:listCurrentTrade){
            if (trade.getTradeId().longValue()==trade1.getTradeId().longValue()) {
                Trade toSave=listCurrentTrade.get(index);
                listCurrentTrade.remove(index);
                toSave.setTradeStatus(3);
                //lightenTrade(toSave);
                listTransactionedTrade.add(toSave);
                break;
            }
            index = index + 1;
        }
    }

    /**
     * Accepts the trade
     * @param trade1 trade you wish to accept
     * @param activity current activity
     * @return returns true if one trade got accepted successfully. Otherwise false
     */
    public boolean acceptTradeRequest(Trade trade1, Activity activity){
        boolean success=performTransaction(trade1);
        if (success==false){
            return false;
        }
        int index=0;
        for (Trade trade:listTradeRequest){
            if (trade.getTradeId().equals(trade1.getTradeId())) {
                listTradeRequest.remove(index);
                trade1.getTradePartner().getTradeManager().getListTradeRequest().remove(index);
                trade1.getTradeUser().getTradeManager().tradeGotAccepted(trade1);
                trade1.setTradeStatus(2); //Accepted
                Trade trade2=trade1.clone();
                //lightenTrade(trade2);
                listTransactionedTrade.add(trade2);
                break;
            }
            index = index + 1;
        }
        pushChanges(trade1, 3, activity);
        return true;
    }

    /**
     * Accept one trade, remove it from listCurrentTrade and add it to listTransactionedTrade
     * @param trade1 trade you wish to accept
     */
    public void tradeGotAccepted(Trade trade1){
        int index=0;
        for (Trade trade:listCurrentTrade){
            if (trade.getTradeId().equals(trade1.getTradeId().longValue())) {
                Trade toSave=listCurrentTrade.get(index);
                listCurrentTrade.remove(index);
                toSave.setTradeStatus(2); //Accepted
                //lightenTrade(toSave);
                listTransactionedTrade.add(toSave);
                break;
            }
            index = index + 1;
        }
    }

    /**
     * Push changes to the server
     * @param trade1 trade you wish to push
     * @param type choose where to save trade
     *             1: save to User
     *             2: save to Partner
     *             3: save to both User and Partner
     * @param activity currnet activity
     */
    public void pushChanges(Trade trade1, int type,Activity activity){
        ModelEnvironment globalEnv=new ModelEnvironment(activity,null);
        PersonManager pm = new PersonManager(activity);
        switch(type){
            case 1:
                if (trade1.getTradeUser().getID().equals(globalEnv.getOwner().getID())) {
                    globalEnv.loadInstance(activity);
                    globalEnv.setOwner(trade1.getTradeUser().toUser());
                    globalEnv.saveInstance(activity);
                }
                else {
                    pm.saveIntoPersonList(trade1.getTradeUser());
                }
                break;

            case 2:
                if (trade1.hasTradePartner()){
                    if (trade1.getTradePartner().getID().equals(globalEnv.getOwner().getID())){
                        globalEnv.loadInstance(activity);
                        globalEnv.setOwner(trade1.getTradePartner().toUser());
                        globalEnv.saveInstance(activity);
                    } else {
                        Log.e("starting for saving","==");
                        pm.saveIntoPersonList(trade1.getTradePartner());
                        Log.e("Saving done","--");
                    }
                }
                break;

            case 3:
                if (trade1.getTradeUser().getID().equals(globalEnv.getOwner().getID())) {
                    globalEnv.loadInstance(activity);
                    globalEnv.setOwner(trade1.getTradeUser().toUser());
                    globalEnv.saveInstance(activity);
                }
                else {
                    pm.saveIntoPersonList(trade1.getTradeUser());
                }

                if (trade1.hasTradePartner()){
                    if (trade1.getTradePartner().getID().equals(globalEnv.getOwner().getID())){
                        globalEnv.loadInstance(activity);
                        globalEnv.setOwner(trade1.getTradePartner().toUser());
                        globalEnv.saveInstance(activity);
                    } else {
                        pm.saveIntoPersonList(trade1.getTradePartner());
                    }
                }

                break;
        }
//        lightenTrade(trade1);
    }

    /**
     * Sets the trade to Complete
     * @param trade1 trade you wish to change
     */
    public void setTradeComplete(Trade trade1){
        int i=0;
        trade1.setTradeStatus(6); // for complete
        //lightenTrade(trade1);
        listCompleteTrade.add(trade1);
        for (Trade trade: listTransactionedTrade){
            if (trade.getTradeId().longValue()==trade1.getTradeId().longValue()){
                listTransactionedTrade.remove(i);
            }
            i=i+1;
        }
    }

    /**
     * Send trade offer to the other person
     * @param trade trade you wish to send
     * @param activity current activity
     * @return
     */
    public boolean sendTradeOffer(Trade trade, Activity activity){
//        lightenTrade(trade);
        if (trade.hasTradePartner()==false){
            return false;
        }

        trade.setTradeStatus(1);
        Gson gson = new Gson();
        trade.getTradePartner().getTradeManager().processTradeRequest(trade);
        Log.e("After push changes", String.valueOf(gson.toJson(trade).length()));
        pushChanges(trade, 2, activity); // will push only the partner

        return true;
    }
}
