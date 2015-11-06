package cmput301exchange.exchange;

import java.util.ArrayList;


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
    private ArrayList<String> listPastTradeOwner;
    private ArrayList<String> listCurrentTradeOwner;
    private ArrayList<String> listPastTradeBorrower;
    private ArrayList<String> listCurrentTadeBorrower;
    */

    private ArrayList<Trade> listPastTrade = new ArrayList<>(); // to read/load from file
    private ArrayList<Trade> listCurrentTrade = new ArrayList<>(); // to read/load from file

    private ArrayList<Trade> listTradeRequest = new ArrayList<>();

    private String filePastTrade;
    private String fileCurrentTrade;
    private String message;

    private ArrayList<Trade> listTrade;

    public void addCompleteTrade(Trade trade) {
        /**
         * Adds a completed trade to the PastTrade List.
         * @param Trade trade
         */
        // trade is complete, add the trade to the listPastTrade
        listPastTrade.add(trade);
        // search the trade in the listCurrentTrade and remove it
        deleteOngoingTrade(trade);
    }

    public void addOngoingTrade(Trade trade) {
        /**
         * Creates a new trade that will be sent to the other user.
         * @param Trade trade
         */
        // add the trade to the listCurrentTrade
        listCurrentTrade.add(trade);
    }

    // creates an empty trade and user can user setters to add more details about the trade
    public Trade createTrade() {
        /**
         * Creates and empty trade and user can use setters to add more details about the trade
         * @param None
         */
        Trade trade = new Trade();
        return trade;
    }

    public void addTrade(Trade trade) {
        /**
         * Creates a new trade and adds it to the listCurrentTrade
         * @param tradeUser The user of the application
         * @param tradePartner The friend that the user wishes to trade with
         * @param tradeStatus The status of the trade
         * @param tradeType The type of trade
         */
        // Trade(Person tradeUser, Person tradePartner, Integer tradeStatus, Integer tradeType)
        // create a new trade and add it to the listCurrentTrade
        listCurrentTrade.add(trade);
    }

    public void acceptTrade(Trade trade) {
        /**
         *search for that trade and change the tradeStatus to 1 and add the trade to listPastTrade
         *update both user and partner's inventory
         * @param  trade The trade that you wish to accept
         */

        int i;
        int n = listCurrentTrade.size();
        for (i = 0; i < n;i++) {
            if ((listCurrentTrade.get(i).getTradeId() == trade.getTradeId()) && (listCurrentTrade.get(i).getTradeUser().getID() == trade.getTradeUser().getID())) {
                // remove the trade in listCurrentTrade and add it to the listPastTrade
                listCurrentTrade.get(i).setTradeStatus(1);
                // NOTE: make sure that change the inventory outside this class
                // remove books from both user (if any) and give the book to the other user
                ArrayList<Book> booklist0 = trade.getListBookUser();
                ArrayList<Book> booklist1 = trade.getListBookPartner();
                ArrayList<Book> booklisttemp = new ArrayList<>();
                Book tempBook;
                int j;
                int n0 = booklist0.size(); // number of books from user
                int n1 = booklist1.size(); // number of books from partner
                // remove books from user and add them to the partner
                for (j = 0; j < n0; j++) {
                    booklisttemp.add(booklist0.get(j));
                    booklist0.remove(j);
                }
                // swap their booklist
                booklist0 = booklist1;
                booklist1 = booklisttemp;
                // now update inventory from both user and partner
                n0 = booklist0.size(); // number of books from user
                n1 = booklist1.size(); // number of books from partner
                // update the user's inventory
                for (j = 0; j < n0; j++) {
                    listCurrentTrade.get(i).getTradeUser().getMyInventory().add(booklist0.get(j));
                }
                listCurrentTrade.get(i).setListBookUser(booklist0);
                // update the partner's inventory
                for (j = 0; j < n1; j++) {
                    listCurrentTrade.get(i).getTradePartner().getMyInventory().add(booklist1.get(j));
                }
                listCurrentTrade.get(i).setListBookPartner(booklist1);
                addCompleteTrade(listCurrentTrade.get(i));
                break;
            }
        }
        // TODO send notification to the other user
    }

    public void declineTrade(Trade trade) {
        /**
         * Search for trades and changes the tradeStatus to 2, which represents cancelled
         * @param trade the trade that you wish to decline
         */
        // search for that trade and change the tradeStatus to 2
        int i;
        int n = listCurrentTrade.size();
        for (i = 0; i < n;i++) {
            if ((listCurrentTrade.get(i).getTradeId() == trade.getTradeId()) && (listCurrentTrade.get(i).getTradeUser().getID() == trade.getTradeUser().getID())) {
                // remove the trade in listCurrentTrade and add it to the listPastTrade
                listCurrentTrade.get(i).setTradeStatus(2);
                listPastTrade.add(listCurrentTrade.get(i));
                listCurrentTrade.remove(i);
                break;
            }
        }
        // ask user if they want to make a counter trade
        //   -> swap the User and Borrower and their inventories
    }

    public Trade counterTrade(Trade trade) {
        /**
         * Swaps the role of the owner and borrower, edits the trade
         * constructs a new trade, and asks the user to edit the trade
         * @param trade The trade that you want to counter
         */
        // swap the role of owner and borrower, edit the trade
        // construct a new trade, and ask user to edit the trade
        Trade tradetemp = new Trade(trade.getTradePartner(), trade.getTradeUser(), 3, 0, trade.getListBookPartner(), trade.getListBookUser(), trade.getTradeId());
        editTrade(tradetemp);
        // update the trade details from listCurrentTrade
        //   to do so, remove the trade and add the new trade in it
        deleteOngoingTrade(trade);
        addOngoingTrade(tradetemp);
        return tradetemp;
    }

    public void editTrade(Trade trade) {
        /**
         * Ask the user to edit the trade details, more like creating a new trade, but gives details
         * about the trade. User can click the area on where they want to edit. Call setter from trade
         * class.
         * @param trade trade that you wish to edit
         */

    }

    public ArrayList<Trade> completeTradeList() {
        int i;
        // TODO return both listCurrentTrade and listPastTrade (???)
        ArrayList<Trade> tempList = new ArrayList<>();
        int n0 = listCurrentTrade.size();
        int n1 = listPastTrade.size();
        // add all current trade to the tempList
        for (i = 0; i < n0; i++) {
            tempList.add(listCurrentTrade.get(i));
        }
        // add all past trade to the tempList
        for (i = 0; i < n1; i++) {
            tempList.add(listPastTrade.get(i));
        }
        return tempList;
    }

    public ArrayList<Trade> getListPastTrade() {
        /**
         * Getter for Past Trade List
         */
        return listPastTrade;
    }

    public ArrayList<Trade> getListCurrentTrade() {
        /**
         * Getter for current trade list
         */
        return listCurrentTrade;
    }

    public void changePartner(Trade trade, Person newPartner) {
        /**
         * Change the trade partner
         * Updates the Current Trade List
         * @param trade Trade you want to change
         * @param newPartner The person you want to turn into a trade partner.
         *
         */
        // change the trade Partner
        // update the listCurrentTrade
        deleteOngoingTrade(trade);
        trade.setTradePartner(newPartner);
        addOngoingTrade(trade); // make sure we have the right trade partner
    }

    public void sendNotification() {}

    public void deleteOngoingTrade(Trade trade) {
        /**
         * Deletes an ongoing trade
         * @param trade the trade you wish to cancel
         */
        int i;
        int n = listCurrentTrade.size();
        for (i = 0; i < n;i++) {
            if ((listCurrentTrade.get(i).getTradeId() == trade.getTradeId()) && (listCurrentTrade.get(i).getTradeUser().getID() == trade.getTradeUser().getID())) {
                // remove the trade in listCurrentTrade
                listCurrentTrade.remove(i);
                break;
            }
        }
    }

    public void setMessage(String message) { this.message = message; }

    public ArrayList<Trade> browsePastTrade(Integer role, Person person) {
        /**
         * Returns a list of all of your past trades
         * @param role the role that the user had in the trade
         *        0 -> User as an owner
         *        1 -> User as a borrower
         *        2 -> all
         * @param person the Person who's trade history you are viewing
         */
        // role: 0 -> User as an owner
        //       1 -> User as a borrower
        //       2 -> All
        ArrayList<Trade> tempPastTrade = new ArrayList<>();
        if (role == 0) {
            int i;
            int n = listPastTrade.size();
            for (i = 0; i < n; i++) {
                if (listPastTrade.get(i).getTradeUser().getID() == person.getID()) {
                    tempPastTrade.add(listPastTrade.get(i));
                }
            }
        } else if (role == 1) {
            int i;
            int n = listPastTrade.size();
            for (i = 0; i < n; i++) {
                if (listPastTrade.get(i).getTradePartner().getID() == person.getID()) {
                    tempPastTrade.add(listPastTrade.get(i));
                }
            }
        } else {
            return listPastTrade;
        }
        return tempPastTrade;
    }

    public ArrayList<Trade> browseCurrentTrade(Integer role, Person person) {
        /**
         * Returns a list of current trades
         * @param role 0 -> Owner, 1 -> borrower, 2 -> All
         * @param person the person who's current trade list you want to view.
         */
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

    public void searchPastTrade() {}

    private void updateListTradeRequest(Person person) {
        /**
         * Updates the Trade Request List
         * @param person the person who's trade request list that is needed to be updated.
         */
        ArrayList<Trade> tempList = new ArrayList<>();
        int i;
        int n0 = listCurrentTrade.size();
        int n1 = listPastTrade.size();
        // add trades that are offered to the user
        for (i = 0; i < n0; i++) {
            if (listCurrentTrade.get(i).getTradePartner().getID() == person.getID()) {
                tempList.add(listCurrentTrade.get(i));
            }
        }
        // add thades that were offered to the user
        for (i = 0; i < n1; i++) {
            if (listPastTrade.get(i).getTradePartner().getID() == person.getID()) {
                tempList.add(listPastTrade.get(i));
            }
        }
        this.listTradeRequest = tempList;
    }

    public ArrayList<Trade> getListTradeRequest(Person person) {
        updateListTradeRequest(person);
        return listTradeRequest;
    }

    public Integer getTradeRequest_no(Person person) {
        updateListTradeRequest(person);
        return listTradeRequest.size();
    }

    public Trade searchTrade(Integer id) {
        /**
         * Searches the trade which involves user via tradeID
         * As a tradeID as a primary key, and one tradeID is assigned to one and only one trade
         * Return the trade as soon as we find it
         * Else returns NULL
         * @param id the ID of the trade you are looking for.
         */
        // search the trade which involves user via tradeId
        // as tradeId is a primary key and one tradeId is assigned to one and the only one trade,
        //   return the trade as soon as we find it
        // else, return NULL
        int i;
        int n0 = listCurrentTrade.size();
        int n1 = listPastTrade.size();
        // search through listCurrentTrade
        for (i = 0; i < n0; i++) {
            if (listCurrentTrade.get(i).getTradeId() == id) {
                return listCurrentTrade.get(i);
            }
        }
        // search through listPastTrade
        for (i = 0; i < n1; i++) {
            if (listPastTrade.get(i).getTradeId() == id) {
                return listPastTrade.get(i);
            }
        }
        return null;
    }
}
