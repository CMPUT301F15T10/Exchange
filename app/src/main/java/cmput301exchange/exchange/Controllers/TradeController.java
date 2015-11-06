package cmput301exchange.exchange.Controllers;

import cmput301exchange.exchange.Trade;
import cmput301exchange.exchange.TradeManager;

/**
 * Created by touqir on 04/11/15.
 */
public class TradeController {
    Trade myTrade;
    TradeManager myTradeManager;

    public TradeController(Trade trade, TradeManager tradeManager){
        myTrade=trade;
        myTradeManager=tradeManager;
    }

    public String getTradeStatus(){
        int status= myTrade.getTradeStatus();
        if (status==0){
            return "In Progress";
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
        myTradeManager.declineTrade(myTrade);
    }


}
