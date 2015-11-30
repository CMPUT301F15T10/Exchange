package cmput301exchange.exchange.Controllers;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.Trade;
import cmput301exchange.exchange.TradeManager;
import cmput301exchange.exchange.User;

/**
 * Created by touqir on 04/11/15.
 */
public class TradeController {
    private Trade myTrade;
    private TradeManager myTradeManager;
    private int[] status = new int[]{0, 2, 3, 3, 0, 1, 4, 3};//magic on the fisrt sight, but make sense since it will be reset later
    private User user;
    private Context context;
//    private ArrayList<Book> userBookList=new ArrayList<>(), partnerBookList=new ArrayList<>();

    public TradeController(Context context, Trade trade, TradeManager tradeManager, User user) {
        this.context = context;
        myTradeManager = tradeManager;
        if (trade == null) {
            createTrade(context, user);
        } else {
            myTrade = trade;
        }
        this.user = user;

    }

//    public void setStatus(int status){
//        this.status=status;
//    }

    public int getControllerStatus() {
        return status[myTrade.getTradeStatus().intValue()];
    }

    public int getTradeStatus() {
        return myTrade.getTradeStatus();
    }

    public void createTrade(Context context, User user) {

        Log.e("User name", String.valueOf(user.getID()));
        myTrade = new Trade();
        myTrade.setTradeUser(user);
        myTrade.setTradePartner(null, false);
        myTrade.setTradeStatus(0);
        myTrade.setListBookUser(new ArrayList<Book>());
        myTrade.setListBookPartner(new ArrayList<Book>());
    }

    public boolean sendTradeOffer() {
        if (myTradeManager.sendTradeOffer(myTrade) == false) {
            return false;
        }
        myTradeManager.pushChanges(myTrade);
        return true;
        //push the offer to server as the user wish
    }

    public void deleteTrade() {
        // This method deletes trades that have not been functional and were under construction
        myTradeManager.deleteUnInitiatedTrade(myTrade);
    }

    public void setTime() {
        myTrade.setTimeStamp();
        myTrade.setDate();
        Log.e("size current list TradeController: ", String.valueOf(myTradeManager.getListCurrentTrade().size()));
    }

    public void deleteCompleteTrade() {
        myTradeManager.deleteCompleteTrade(myTrade);
        myTradeManager.pushChanges(null); // Only its user side's trademanager that has changed
    }

    public boolean hasTradePartner() {
        return myTrade.hasTradePartner();
    }

    public String getTradeType() {
        int status = myTrade.getTradeStatus();
        switch (status) {
            case 0:
                return "Trade initialization";
            case 1:
                return "Trade offer sent!";
            case 2:
                return "Accepted!";
            case 3:
                return "Declined";
            case 4:
                return "Counter Trade!";
            case 5:
                return "Trade Invitation";
            case 6:
                return "Trade Completed";
            case 7:
                return "Books returned!";
        }

        //there will be other conditions too!
        return "";
    }

    public void setCompleteTrade() {
        myTradeManager.setTradeComplete(myTrade);
        myTradeManager.pushChanges(null); // The partner object's tradeManager wont be pushed as only its the user side's trademanager that is changing
    }

    public void acceptTrade() {
        myTradeManager.acceptTradeRequest(myTrade);
        myTradeManager.pushChanges(myTrade);
    }

    public void setTradeManager(TradeManager tradeManager) {
        myTradeManager = tradeManager;
    }

    public String getTradeID_Text() {
        return myTrade.getTradeId().toString();
    }


    public void setTradePartner(Person partner) {
        if (myTrade.getTradeStatus().intValue() == 5) {
            // In case of trade offer request being made to user
            myTradeManager.counterTrade(myTrade);
        }

        if (user.getID() != myTrade.getUserID()) {
            throw new RuntimeException("Not allowed to change partner as you yourself are the owner");
        }
        if (partner == null) {
            return;
        } else {
            myTrade.setTradePartner(partner, false);
            Log.e("Got to Trade Controller", String.valueOf(myTrade.hasTradePartner()));
        }
    }

    public void declineTrade() {
        myTradeManager.declineTradeRequest(myTrade);
        myTradeManager.pushChanges(myTrade);
    }

    public Person getTradePartner() {
        if (user.getID().equals(myTrade.getUserID())) {
            if (myTrade.hasTradePartner()) {
                return myTrade.getTradePartner();
            }
        } else {
            return myTrade.getTradeUser();
        }
        return null;
    }


    public void setTrade(Trade trade){
        if (trade.isLoaded()==false){
            myTradeManager.loadPersons(trade,context);
        }
        myTrade=trade;
//        Log.e("User name",trade.getTradeUser().getName());
    }

    public Trade getTrade(){
        return myTrade;
    }

    public void saveTradeUnInitiated(){

        myTradeManager.addUnInitiatedTrade(myTrade);
        myTrade=null;
    }
}
