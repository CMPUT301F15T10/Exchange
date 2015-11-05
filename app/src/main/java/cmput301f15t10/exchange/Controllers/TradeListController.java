package cmput301f15t10.exchange.Controllers;

import java.util.ArrayList;

import cmput301f15t10.exchange.Trade;
import cmput301f15t10.exchange.TradeManager;

/**
 * Created by touqir on 04/11/15.
 */
public class TradeListController {

    private TradeManager myTradeManager;
    private Integer mode;

    public TradeListController(TradeManager tradeManager, int mode){
        myTradeManager=tradeManager;
        this.mode=mode;
    }

    public ArrayList<Trade> getTradeRequestList(){
        return myTradeManager.getTradeRequests();
    }

    //Todo
    public ArrayList<Trade> searchTrade(String query, int flag){
        if (query==""){
            return null;
        }
        ArrayList<Trade> result= new ArrayList<>();
        if (flag==1){
            int ID=Integer.parseInt(query);
            // Add searching logic for int ID
        }
        //Add searching based on other kind of values for different flags
        return result;
    }
}
