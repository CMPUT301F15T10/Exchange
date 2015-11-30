package cmput301exchange.exchange.Mocks;

import java.util.ArrayList;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.Trade;

/**
 * Created by touqir on 20/11/15.
 */
public class MockTrade {
    public Trade sendTrade1, sendTrade2, acceptedTrade, declinedTrade, counterTrade;
    private ArrayList<Book> userBooks= new ArrayList<>(), friendBooks= new ArrayList<>();

    //TODO
    public MockTrade(){

        MockBooks mockBooks= new MockBooks();
        ArrayList<Book> sharableBooks=mockBooks.getSharableBooks();
        ArrayList<Book> nonSharableBooks=mockBooks.getNonSharableBooks(); // Might need for later use

        sendTrade1= new Trade();
        sendTrade1.setTradeUser(new Person("USER")); // Instead of creating a new person, you should put the user's object
        sendTrade1.setTradePartner(new Person("friend1"),false);
        userBooks.add(sharableBooks.get(0));
        userBooks.add(sharableBooks.get(1));
        friendBooks.add(sharableBooks.get(2));
        friendBooks.add(sharableBooks.get(3));
        sendTrade1.setListBookPartner(friendBooks);
        sendTrade1.setListBookUser(userBooks);
        sendTrade1.setTradeStatus(0); // User has sent the trade request
//        sendTrade1.setTradeType(0); // In progress

        userBooks= new ArrayList<>();
        friendBooks= new ArrayList<>();
        sendTrade2= new Trade();

        sendTrade2.setTradeUser(new Person("USER")); // Instead of creating a new person, you should put the user's object
        sendTrade2.setTradePartner(new Person("friend2"),false);
        userBooks.add(sharableBooks.get(0));
        friendBooks.add(sharableBooks.get(1));
        sendTrade2.setListBookPartner(friendBooks);
        sendTrade2.setListBookUser(userBooks);
        sendTrade2.setTradeStatus(0); // User has sent the trade request
//        sendTrade2.setTradeType(0); // In progress

        userBooks= new ArrayList<>();
        friendBooks= new ArrayList<>();
        acceptedTrade= new Trade();

        acceptedTrade.setTradeUser(new Person("USER")); // Instead of creating a new person, you should put the user's object
        acceptedTrade.setTradePartner(new Person("friend3"),false);
        userBooks.add(sharableBooks.get(2));
        friendBooks.add(sharableBooks.get(3));
        acceptedTrade.setListBookPartner(friendBooks);
        acceptedTrade.setListBookUser(userBooks);
        acceptedTrade.setTradeStatus(1); // Accepted trade
//        acceptedTrade.setTradeType(1); //Accepted and completed

        userBooks= new ArrayList<>();
        friendBooks= new ArrayList<>();
        declinedTrade= new Trade();

        declinedTrade.setTradeUser(new Person("USER")); // Instead of creating a new person, you should put the user's object
        declinedTrade.setTradePartner(new Person("friend4"),false);
        userBooks.add(sharableBooks.get(2));
        friendBooks.add(sharableBooks.get(3));
        declinedTrade.setListBookPartner(friendBooks);
        declinedTrade.setListBookUser(userBooks);
        declinedTrade.setTradeStatus(2); // Declined trade
//        counterTrade.setTradeType(1); // Completed and declined

        userBooks= new ArrayList<>();
        friendBooks= new ArrayList<>();
        counterTrade= new Trade();

        counterTrade.setTradeUser(new Person("USER")); // Instead of creating a new person, you should put the user's object
        counterTrade.setTradePartner(new Person("friend4"),false);
        userBooks.add(sharableBooks.get(1));
        friendBooks.add(sharableBooks.get(3));
        counterTrade.setListBookPartner(friendBooks);
        counterTrade.setListBookUser(userBooks);
        counterTrade.setTradeStatus(3); // Declined trade
//        counterTrade.setTradeType(0); // In progress

    }


}
