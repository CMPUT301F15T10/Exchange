package cmput301exchange.exchange.Mocks;

import java.util.ArrayList;

import cmput301exchange.exchange.Trade;

/**
 * Created by touqir on 21/11/15.
 */
public class MockTradeList {

    public ArrayList<Trade> currentTradeList1, currentTradeList2, completedTradeList1, completedTradeList2, requestTradeList1, requestTradeList2;

    public MockTradeList(){
        MockTrade mockTrade1=new MockTrade();
        MockTrade mockTrade2=new MockTrade();
        MockTrade mockTrade3=new MockTrade();

        currentTradeList1= new ArrayList<>();
        currentTradeList1.add(mockTrade1.sendTrade1);

        currentTradeList2= new ArrayList<>();
        currentTradeList2.add(mockTrade1.sendTrade1);
        currentTradeList2.add(mockTrade1.sendTrade2);

        completedTradeList1= new ArrayList<>();
        completedTradeList1.add(mockTrade1.acceptedTrade);

        completedTradeList2= new ArrayList<>();
        completedTradeList2.add(mockTrade1.acceptedTrade);
        completedTradeList2.add(mockTrade1.declinedTrade);

        requestTradeList1= new ArrayList<>();
        requestTradeList1.add(mockTrade1.sendTrade1);

        requestTradeList2= new ArrayList<>();
        requestTradeList2.add(mockTrade1.sendTrade1);
        requestTradeList2.add(mockTrade1.sendTrade2);
    }
}
