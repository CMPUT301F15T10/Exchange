package cmput301f15t10.exchange;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by Yishuo Wang on 2015/10/9.
 */
public class TradeManager {
    private ArrayList<Trade> myTrades;
    private Integer tradeRequests_no;
    private ArrayList<Trade> tradeRequests;

    public TradeManager(){
    }

    public Trade createTrade(){
        Trade trade= new Trade();
        myTrades.add(trade);
        return trade;
    }

    public Integer getTradeRequests_no(){
        return tradeRequests_no;
    }
}
