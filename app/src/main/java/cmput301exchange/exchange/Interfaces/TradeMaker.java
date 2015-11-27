package cmput301exchange.exchange.Interfaces;

import cmput301exchange.exchange.Controllers.TradeController;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.Trade;
import cmput301exchange.exchange.TradeManager;

/**
 * Created by touqir on 11/6/15.
 */
public interface TradeMaker extends FragmentContainer {
    void displayItemsToTrade(Trade trade);
    void displayTrade();
//    Trade getTrade();
//    void setTrade(Trade trade);
    void setTradeController(TradeController tradeController);
    TradeController getTradeController();
    TradeManager getTradeManager();
//    boolean IsNewTrade();
    Person getTradePartner();
    void selectPerson();
    void selectItems();

}
