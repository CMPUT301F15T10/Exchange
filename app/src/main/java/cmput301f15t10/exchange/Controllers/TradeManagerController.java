package cmput301f15t10.exchange.Controllers;

import cmput301f15t10.exchange.Trade;
import cmput301f15t10.exchange.TradeManager;

/**
 * Created by touqir on 03/11/15.
 */
public class TradeManagerController {

    private TradeManager myTradeManager;

    public TradeManagerController(TradeManager tradeManager){
        myTradeManager=tradeManager;
    }

    public Trade createTrade(){
        return myTradeManager.createTrade();
    }

    public String getTradeRequests_no(){
        return myTradeManager.getTradeRequests_no().toString();
    }
}
