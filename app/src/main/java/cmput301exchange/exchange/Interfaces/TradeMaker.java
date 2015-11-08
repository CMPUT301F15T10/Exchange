package cmput301exchange.exchange.Interfaces;

import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.Trade;
import cmput301exchange.exchange.TradeManager;

/**
 * Created by touqir on 11/6/15.
 */
public interface TradeMaker extends FragmentContainer {
    void displayItemsToTrade(Trade trade);
    Trade getTrade();
    void setTrade(Trade trade);
    TradeManager getTradeManager();
    boolean IsNewTrade();
    Person getTradePartner();
}
