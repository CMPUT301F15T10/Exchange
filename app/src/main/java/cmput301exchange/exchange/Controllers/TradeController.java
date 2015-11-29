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
    private int status;
//    private ArrayList<Book> userBookList=new ArrayList<>(), partnerBookList=new ArrayList<>();

    public TradeController(Context context,Trade trade, TradeManager tradeManager, User user){
        myTradeManager=tradeManager;
        if (trade==null){
            createTrade(context, user);
        } else {
            myTrade = trade;
        }

    }

    public void setStatus(int status){
        this.status=status;
    }

    public void createTrade(Context context, User user){

        myTrade=new Trade();
        myTrade.setTradeUser(user);
        myTrade.setTradePartner(null);
        myTrade.setTradeStatus(0);
        myTrade.setListBookUser(new ArrayList<Book>());
        myTrade.setListBookPartner(new ArrayList<Book>());
    }

    public void addToCurrentList(){
        myTrade.setTimeStamp();
        myTrade.setDate();
        myTradeManager.addTrade(myTrade);
        Log.e("size current list TradeController: ", String.valueOf(myTradeManager.getListCurrentTrade().size()));
    }

    public void deleteCompleteTrade(){
        myTradeManager.deleteCompleteTrade(myTrade);
    }

    public boolean hasTradePartner(){
        return myTrade.hasTradePartner();
    }

    public String getTradeType(){
        int status= myTrade.getTradeStatus();
        switch (status){
            case 0:
                return "In Progress";
            case 1:
                return "Completed";
        }

        //there will be other conditions too!
        return "";
    }

    public void setCompleteTrade(){
        myTradeManager.setCompleteTrade(myTrade);
    }

    public void setTradeManager(TradeManager tradeManager){
        myTradeManager=tradeManager;
    }
    public String getTradeID(){
        return myTrade.getTradeId().toString();
    }

    public void confirmTrade(){
        myTradeManager.acceptTrade(myTrade);
    }

    public void cancelTrade(){
        myTradeManager.declineTrade(myTrade, 1);
    }

    public void setTradePartner(Person partner){
        if (partner==null){
            return;
        } else {
            myTrade.setTradePartner(partner);
        }
    }

    public Person getTradePartner(){
        return myTrade.getTradePartner();
    }

    public void setTrade(Trade trade){
        myTrade=trade;
    }

    public Trade getTrade(){
        return myTrade;
    }

    public void saveTrade(){
        myTradeManager.lightenTrade(myTrade);
        myTrade=null;
    }
}
