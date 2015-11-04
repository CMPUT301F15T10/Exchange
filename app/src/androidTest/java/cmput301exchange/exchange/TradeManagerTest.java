package cmput301exchange.exchange;

import android.graphics.Picture;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by yishuo on 11/3/15.
 */
public class TradeManagerTest extends ActivityInstrumentationTestCase2 {

    private Friend person0;
    private Friend person1;
    private Book book0;
    private Book book1;
    private Trade trade0;
    private TradeManager tradeManager0;

    public TradeManagerTest() {super(cmput301exchange.exchange.Activities.TradeManagerActivity.class);}

    public void testSetup() {
        person0 = new Friend("A");
        assertSame("A", person0.getUserName());
        person1 = new Friend("B");
        book0 = new Book();
        book0.updateTitle("Harry Potter and the Philosopher's Stone");

        book1 = new Book();
        book1.updateTitle("Compsci Sucks");

        ArrayList<Book> alist = new ArrayList<>();
        // add book0 to person0's inventory and his/her trading list
        person0.getMyInventory().add(book0);
        alist.add(book0);
        ArrayList<Book> blist = new ArrayList<>();
        // add book1 to person1's inventory and his/her trading list
        person1.getMyInventory().add(book1);
        blist.add(book1);

        trade0 = new Trade(person0, person1, alist, blist);
        tradeManager0 = new TradeManager();
        tradeManager0.createTrade(trade0);
        int i;
        int n;
        n = tradeManager0.getListCurrentTrade().size();
        for (i = 0; i < n; i++) {
            assertEquals(tradeManager0.getListCurrentTrade().get(i).getListBookUser().get(0).getBookName(), "Harry Potter and the Philosopher's Stone");
        }

        tradeManager0.addOngoingTrade(trade0);
        // person0 should have book "Harry Potter and the Philosopher's Stone"
        // person1 should have book "Compsci Sucks"
        assertEquals(tradeManager0.getListCurrentTrade().get(0).getListBookUser().get(0).getBookName(), "Harry Potter and the Philosopher's Stone");
        assertEquals(tradeManager0.getListCurrentTrade().get(0).getListBookPartner().get(0).getBookName(), "Compsci Sucks");

        // accept the trade
        // person0 should have book "Compsci Sucks"
        // person1 should have book "Harry Potter and the Philosopher's Stone"
        // since TradeManager cannot overwrite the one person's inventory, check the currentTradeList
        tradeManager0.acceptTrade(trade0);
        assertEquals(tradeManager0.getListCurrentTrade().get(0).getListBookUser().get(0).getBookName(), "Compsci Sucks");
        assertEquals(tradeManager0.getListCurrentTrade().get(0).getListBookPartner().get(0).getBookName(), "Harry Potter and the Philosopher's Stone");
    }
}
