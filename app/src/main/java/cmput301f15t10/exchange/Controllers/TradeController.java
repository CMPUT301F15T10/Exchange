package cmput301f15t10.exchange.Controllers;

import cmput301f15t10.exchange.Trade;
import cmput301f15t10.exchange.TradeManager;

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
        int status= myTrade.getStatus();
        if (status==0){
            return "In Progress";
        }

        //there will be other conditions too!
        return "";
    }

    public String getTradeID(){
        return myTrade.getTradeID().toString();
    }

    public void confirmTrade(){
        myTradeManager.acceptTrade(myTrade);
    }

    public void cancelTrade(){
        myTradeManager.declineTrade(myTrade);
    }


}
