package cmput301exchange.exchange;

import java.util.ArrayList;

/**
 * Created by Yishuo Wang on 2015/10/9.
 */

// TO DO send email
public class TradeManager {
    /*
    private ArrayList<String> listPastTradeOwner;
    private ArrayList<String> listCurrentTradeOwner;
    private ArrayList<String> listPastTradeBorrower;
    private ArrayList<String> listCurrentTadeBorrower;
    */

    private ArrayList<Trade> listPastTrade = new ArrayList<>(); // to read/load from file
    private ArrayList<Trade> listCurrentTrade = new ArrayList<>(); // to read/load from file

    private String filePastTrade;
    private String fileCurrentTrade;
    private String message;

    private ArrayList<Trade> listTrade;

    public void addCompleteTrade(Trade trade) {
        // trade is complete, add the trade to the listPastTrade
        listPastTrade.add(trade);
        // search the trade in the listCurrentTrade and remove it
        deleteOngoingTrade(trade);
    }

    public void addOngoingTrade(Trade trade) {
        // add the trade to the listCurrentTrade
        listCurrentTrade.add(trade);
    }

    public void createTrade(Trade trade) {
        /*
         Trade(Person tradeUser, Person tradePartner, Integer tradeStatus, Integer tradeType)
         */
        // create a new trade and add it to the listCurrentTrade
        listCurrentTrade.add(trade);
    }

    public void acceptTrade(Trade trade) {
        // search for that trade and change the tradeStatus to 1 and add the trade to listPastTrade
        // update both user and partner's inventory
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
        // TO DO send notification to the other user
    }

    public void declineTrade(Trade trade) {
        // search for that trade and change the tradeStatus to 2
        int i;
        int n = listCurrentTrade.size();
        for (i = 0; i < n;i++) {
            if ((listCurrentTrade.get(i).getTradeId() == trade.getTradeId()) && (listCurrentTrade.get(i).getTradeUser().getID() == trade.getTradeUser().getID())) {
                // remove the trade in listCurrentTrade
                listCurrentTrade.get(i).setTradeStatus(2);
                break;
            }
        }
        // ask user if they want to make a counter trade
        //   -> swap the User and Borrower and their inventories
    }

    public void counterTrade(Trade trade) {
        // swap the role of owner and borrower, edit the trade
        // construct a new trade, and ask user to edit the trade
        Trade tradetemp = new Trade(trade.getTradePartner(), trade.getTradeUser(), 3, 0, trade.getListBookPartner(), trade.getListBookUser(), trade.getTradeId());
        editTrade(tradetemp);
        // update the trade details from listCurrentTrade
        //   to do so, remove the trade and add the new trade in it
        deleteOngoingTrade(trade);
        createTrade(tradetemp);
    }

    public void editTrade(Trade trade) {}

    public ArrayList<Trade> completeTradeList() {
        int i;
        // TO DO (???)
        // return both listCurrentTrade and listPastTrade (???)
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

    public ArrayList<Trade> getListPastTrade() { return listPastTrade; }

    public ArrayList<Trade> getListCurrentTrade() { return listCurrentTrade; }

    public void changePartner(Trade trade, Person newPartner) {
        // change the trade Partner
        // update the listCurrentTrade
        deleteOngoingTrade(trade);
        trade.setTradePartner(newPartner);
        addOngoingTrade(trade); // make sure we have the right trade partner
    }

    public void sendNotification() {}

    public void deleteOngoingTrade(Trade trade) {
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
}
