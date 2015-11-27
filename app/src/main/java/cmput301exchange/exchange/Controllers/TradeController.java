package cmput301exchange.exchange.Controllers;

import android.content.Context;

import java.util.ArrayList;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.Trade;
import cmput301exchange.exchange.TradeManager;

/**
 * Created by touqir on 04/11/15.
 */
public class TradeController {
    private Trade myTrade;
    private TradeManager myTradeManager;
//    private ArrayList<Book> userBookList=new ArrayList<>(), partnerBookList=new ArrayList<>();

    public TradeController(Context context,Trade trade, TradeManager tradeManager){
        if (trade==null){
            createTrade(context);
        } else {
            myTrade = trade;
        }
        myTradeManager=tradeManager;
    }

    public void createTrade(Context context){

        ModelEnvironment globalEnv=new ModelEnvironment(context,null);
        myTrade=new Trade();
        myTrade.setTradeUser(globalEnv.getOwner());
        myTrade.setTradePartner(null);
        myTrade.setTradeType(0); // By default
        myTrade.setTradeStatus(0);
        myTrade.setListBookUser(new ArrayList<Book>());
        myTrade.setListBookPartner(new ArrayList<Book>());
    }

    public String getTradeType(){
        int status= myTrade.getTradeType();
        switch (status){
            case 0:
                return "In Progress";
            case 1:
                return "Completed";
        }

        //there will be other conditions too!
        return "";
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
}
