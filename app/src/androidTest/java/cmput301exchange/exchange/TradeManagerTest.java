package cmput301exchange.exchange;

import android.graphics.Picture;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by yishuo on 11/3/15.
 */
public class TradeManagerTest extends ActivityInstrumentationTestCase2 {

    private Person person0;
    private Person person1;
    private Book book00;
    private Book book01;
    private Book book10;
    private Book book11;
    private ArrayList<Book> alist = new ArrayList<>();
    private ArrayList<Book> blist = new ArrayList<>();
    private Trade trade0;
    private TradeManager tradeManager0;

    public TradeManagerTest() {super(cmput301exchange.exchange.Activities.TradeManagerActivity.class);}

    // setup
    public void testSetup() {
        person0 = new Person("A");
        book00 = new Book();
        book00.updateTitle("Harry Potter and the Philosopher's Stone");
        book01 = new Book();
        book01.updateTitle("Tang");
        // add book0 to person0's inventory and his/her trading list
        person0.getMyInventory().add(book00);
        person0.getMyInventory().add(book01);
        alist.add(book00);
        alist.add(book01);

        person1 = new Person("B");
        book10 = new Book();
        book10.updateTitle("Compsci Sucks");
        book11 = new Book();
        book11.updateTitle("Math Sucks Too");
        // add book1 to person1's inventory and his/her trading list
        person1.getMyInventory().add(book10);
        person1.getMyInventory().add(book11);
        blist.add(book10);
        blist.add(book11);
    }

    /*
     * US04.01.01
     * As an borrower, I want to offer a trade with a friend. A trade will consist of an item
     * from the owner's inventory and 0 or more items from the borrower's inventory.
     */
    public void testOfferTrade() {
        // setup
        testSetup();
        // offer a trade
        trade0 = new Trade(person0, person1, alist, blist);
        tradeManager0 = new TradeManager();
        tradeManager0.addUnInitiatedTrade(trade0);
        // person0 should have book "Harry Potter and the Philosopher's Stone"
        // person1 should have book "Compsci Sucks"
        assertEquals(tradeManager0.getListCurrentTrade().get(0).getListBookUser().get(0).getBookName(), "Harry Potter and the Philosopher's Stone");
        assertEquals(tradeManager0.getListCurrentTrade().get(0).getListBookPartner().get(0).getBookName(), "Compsci Sucks");
    }

    /*
     * US04.03.01
     * As an owner, I can accept or decline a trade.
     */
    // i. accept offer
    public void testAcceptOffer() {
        // setup
        testSetup();
        // offer a trade
        testOfferTrade();
        // accept trade
        tradeManager0.tradeGotAccepted(trade0);
        // test trade accepted
        int testTradeStatus = tradeManager0.getListTransactionedTrade().get(0).getTradeStatus();
        assertEquals(testTradeStatus, 2);
    }

    // ii. decline offer
    public void testDeclineOffer() {
        // setup
        testSetup();
        // offer a trade
        testOfferTrade();
        // decline trade and not offering a counter trade
        tradeManager0.tradeGotDeclined(trade0);
        // now the tradeStatus should be 2: trade declined
        int testTradeStatus = tradeManager0.getListTransactionedTrade().get(0).getTradeStatus();
        assertEquals(testTradeStatus, 3);
    }

    /*
     * US04.04.01
     * As an owner, upon declining a trade I can offer a counter trade initialized with the items of the declined trade.
     */
/*
    public void testCounterTrade() {
        // setup
        testSetup();
        // offer a trade
        testOfferTrade();
        // decline trade and offering a counter trade
        tradeManager0.counterTrade(trade0);
        // now the tradeStatus should be 3: counter trade
        int testTradeStatus = tradeManager0.getListCurrentTrade().get(0).getTradeStatus();
        assertEquals(testTradeStatus, 3);
    }
*/
    /*
     * US04.05.01
     * As a borrower or owner, when composing a trade or counter-trade I can edit the trade.
     */

    public void testEditTrade() {
        // setup
        testSetup();
        // offer a trade
        testOfferTrade();
        // change trade details
        Trade tmptrade = new Trade();
        Book book02 = new Book();
        book02.updateTitle("No Name");
        ArrayList<Book> clist = new ArrayList<>();
        clist.add(book02);
        tmptrade.setListBookUser(clist);
        tradeManager0.deleteUnInitiatedTrade(trade0);
        tradeManager0.addUnInitiatedTrade(tmptrade);
        String tmpname = tradeManager0.getListCurrentTrade().get(0).getListBookUser().get(0).getBookName();
        assertEquals(tmpname, "No Name");
    }

    /*
     * US04.06.01
     * As a borrower, when composing a trade or counter-trade I can delete the trade
     */

    public void testDeleteTrade() {
        // setup
        testSetup();
        // offer a trade
        testOfferTrade();
        // delete a trade, now ongoing trade list size is 0
        tradeManager0.deleteUnInitiatedTrade(trade0);
        int tmpsize = tradeManager0.getListCurrentTrade().size();
        assertEquals(tmpsize, 0);
    }

//    /*
//     * US04.07.01
//     * As an owner, if I accept a trade both parties will be emailed all trade and item information
//     * relevant to the trade, as well owner comments for how to continue on with the trade.
//     */
//    // Sending emails can be done by starting a new activity
//
//    public void testSentEmail() {
//        // setup
//        testSetup();
//        // offer a trade
//        testOfferTrade();
//        // accept a trade
//        tradeManager0.tradeGotAccepted(trade0);
//        String comments = "TEST";
//        int i = tradeManager0.sendEmail(trade0, comments);
//        // 0 indicates that successfully sending email
//        assertEquals(i, 0);
//    }

    /*
     * US04.08.01
     * As an owner or borrower, I can browse all past and current trades involving me.
     */

//    public void testBrowseTrade0() {
//        // setup
//        testSetup();
//        // offer a trade
//        testOfferTrade();
//        // browse current trades
//        ArrayList<Trade> tmpList = new ArrayList<>();
//        //tradeManager0.tradeGotAccepted(trade0);
//        tmpList = tradeManager0.browseCurrentTrade(0, person0);
//        Long tmpName = tmpList.get(0).getTradeId();
//        assertEquals(tmpName, "A");
////        // browse past trades
////        tradeManager0.tradeGotAccepted(trade0);
////        tmpList = tradeManager0.browseCompletedTrade(0, person0);
////        tmpName = tmpList.get(0).getTradeUser().getName();
////        assertEquals(tmpName, "A");
//    }


    /*
     * US04.09.01
     * As an owner or borrower, I can browse all past and current trades involving me as either
     * borrower or owner. I should look at my trades and trades offered to me.
     */
/*
    public void testBrowseTrade1() {
        // Note that this is similar as the above user case while this one is more advanced
        // setup
        testSetup();
        // offer a trade
        testOfferTrade();
        // browse current trades
        ArrayList<Trade> tmpList = new ArrayList<>();
        tmpList = tradeManager0.browseCurrentTrade(0, person0);
        String tmpName = tmpList.get(0).getTradeUser().getName();
        assertEquals(tmpName, "A");
        // browse past trades
        tradeManager0.acceptTrade(trade0);
        tmpList = tradeManager0.browsePastTrade(0, person0);
        tmpName = tmpList.get(0).getTradeUser().getName();
        assertEquals(tmpName, "A");
    }
*/

    // -----------------------------------------------------
    // Below is the new requirements
    // -----------------------------------------------------

    /*
     * US04.10.01 [must]
     * As an owner, I can set a trade to complete when the borrower returns the item and the item is now available again.
     */
/*
    public void testSetTradeComplete() {
        // setup
        testSetup();
        // offer a trade
        testOfferTrade();
        // accecp trade
        tradeManager0.acceptTrade(trade0);
        tradeManager0.getListPastTrade().get(0).setTradeType(1);
        int tmp = tradeManager0.getListPastTrade().get(0).getTradeType();
        assertEquals(tmp, 1);
    }
*/

    /*
     * US04.11.01 [must]
     * As an owner or borrower, a trade is considered current and in-progress when the item is borrowed.
     */
/*
    public void testInProgress() {
        // setup
        testSetup();
        // offer a trade
        testOfferTrade();
        // accecp trade
        tradeManager0.acceptTrade(trade0);
        // trade is considered current and in-progress by default
        //   where tradeType = 0
        int tmp = tradeManager0.getListPastTrade().get(0).getTradeType();
        assertEquals(tmp, 0);
    }
*/

    /*
     * US04.12.01 [must]
     * As an owner or borrower, I should be able to browse in-progress trades, and complete trades.
     */

}
