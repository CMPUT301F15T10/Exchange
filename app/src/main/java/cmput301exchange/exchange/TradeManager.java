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
    private ArrayList<String> listTransactionedTradeOwner;
    private ArrayList<String> listCurrentTradeOwner;
    private ArrayList<String> listTransactionedTradeBorrower;
    private ArrayList<String> listCurrentTadeBorrower;
    */

    private ArrayList<Trade> listTransactionedTrade = new ArrayList<>(); // to read/load from file
    private ArrayList<Trade> listCurrentTrade = new ArrayList<>(); // to read/load from file
    private ArrayList<Trade> listCompleteTrade= new ArrayList<>();
    private ArrayList<Trade> listTradeRequest = new ArrayList<>();
    
    private ArrayList<ArrayList<Book>> temporaryInsufficientBookList= new ArrayList<>();

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

    public void setListTransactionedTrade(ArrayList<Trade> list){

        listTransactionedTrade=list;
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

        // trade is complete, add the trade to the listTransactionedTrade
        listTransactionedTrade.add(trade);
        // search the trade in the listCurrentTrade and remove it
        deleteOngoingTrade(trade);
    }

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
     *search for that trade and change the tradeStatus to 1 and add the trade to listTransactionedTrade
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
                listTransactionedTrade.add(listCurrentTrade.get(i));
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
                // remove the trade in listCurrentTrade and add it to the listTransactionedTrade
                listCurrentTrade.get(i).setTradeStatus(2);
                if (choice == 0) {
                    counterTrade(trade);
                }
                if (choice == 1) {
                    listTransactionedTrade.add(listCurrentTrade.get(i));
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
     * @param trade1 The trade that you want to counter
     */
    public void counterTrade(Trade trade1) {

        int index=0;
        for (Trade trade:listTradeRequest) {
            if (trade.getTradeId().longValue() == trade1.getTradeId().longValue()) {
                listTradeRequest.remove(index);
                break;
            }
            index=index+1;
        }
        listCurrentTrade.add(trade1);
    }

    public ArrayList<Trade> getCompleteTradeList() {
        int i;
        // TODO return both listCurrentTrade and listTransactionedTrade (???)
        ArrayList<Trade> tempList = new ArrayList<>();
        int n1 = listTransactionedTrade.size();
        // add all past trade to the tempList
        for (i = 0; i < n1; i++) {
            tempList.add(listTransactionedTrade.get(i));
        }
        return tempList;
    }

    /**
     * Getter for Past Trade List
     */
    public ArrayList<Trade> getListTransactionedTrade() {

        return listTransactionedTrade;
    }

    /**
     * Getter for current trade list
     */
    public ArrayList<Trade> getListCurrentTrade() {

        return listCurrentTrade;
    }

    /*
    This method is for completing the trade
     */
    public void returnTradeItems(Trade trade){
        revertTransaction(trade);
    }

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
            return true;
        }
    }

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
    public void deleteOngoingTrade(Trade trade1) {
    // choice = 1 meaning trade only composed or getting composed and not offered.
    // choice = 2 meaning we delete

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
            int n = listTransactionedTrade.size();
            for (i = 0; i < n; i++) {
                if (listTransactionedTrade.get(i).getTradeUser().getID() == person.getID()) {
                    tempPastTrade.add(listTransactionedTrade.get(i));
                }
            }
        } else if (role == 1) {
            int i;
            int n = listTransactionedTrade.size();
            for (i = 0; i < n; i++) {
                if (listTransactionedTrade.get(i).getTradePartner().getID() == person.getID()) {
                    tempPastTrade.add(listTransactionedTrade.get(i));
                }
            }
        } else {
            return listTransactionedTrade;
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
    public void declineTradeRequest(Trade trade1){
        int index=0;
        for (Trade trade:listTradeRequest){
            if (trade.getTradeId().longValue()==trade1.getTradeId().longValue()) {
                listTradeRequest.remove(index);
                listTransactionedTrade.add(trade);
                trade.getTradeUser().getTradeManager().tradeGotAccepted(trade);
                break;
            }
            index = index + 1;
        }

    }
    
    //TODO change tradeStatus
    public void acceptTradeRequest(Trade trade1){
        int index=0;
        for (Trade trade:listTradeRequest){
            if (trade.getTradeId().longValue()==trade1.getTradeId().longValue()) {
                listTradeRequest.remove(index);
                listTransactionedTrade.add(trade);
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
                listTransactionedTrade.add(trade);
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
            for (Trade trade:listTransactionedTrade){
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
