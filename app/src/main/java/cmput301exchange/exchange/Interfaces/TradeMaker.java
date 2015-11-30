package cmput301exchange.exchange.Interfaces;

import java.util.ArrayList;

import cmput301exchange.exchange.Controllers.BooksTradeController;
import cmput301exchange.exchange.Controllers.TradeController;
import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.Trade;
import cmput301exchange.exchange.TradeManager;

/**
 * Created by touqir on 11/6/15.
 */
public interface TradeMaker extends FragmentContainer {
    void displayItemsToTrade(Trade trade);
    void displayTrade(Trade trade);
//    Trade getTrade();
//    void setTrade(Trade trade);
    void setTradeController(TradeController tradeController);
    TradeController getTradeController();
    TradeManager getTradeManager();
//    boolean IsNewTrade();
    Person getTradePartner();
    void selectPerson();
    void selectItems(int type, Inventory inventory, ArrayList<Integer> position_array);
    Inventory assignBooks();
    BooksTradeController getBookTradeController();
    public void displayTransactionedTrades();
    void displayTradeRequests();
}
