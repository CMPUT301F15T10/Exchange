package cmput301exchange.exchange.Mocks;

import java.util.ArrayList;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.Trade;

/**
 * Created by touqir on 20/11/15.
 */
public class MockTrade {
    public Trade onGoingTrade1, onGoingTrade2, completedTrade;
    private ArrayList<Book> userBooks= new ArrayList<>(), friendBooks= new ArrayList<>();

    //TODO
    public MockTrade(){

        MockBooks mockBooks= new MockBooks();
        ArrayList<Book> sharableBooks=mockBooks.getSharableBooks();
        ArrayList<Book> nonSharableBooks=mockBooks.getNonSharableBooks(); // Might need for later use

        onGoingTrade1= new Trade();
        onGoingTrade1.setTradeUser(new Person("USER")); // Instead of creating a new person, you should put the user's object
        onGoingTrade1.setTradePartner(new Person("friend1"));
        userBooks.add(sharableBooks.get(0));
        userBooks.add(sharableBooks.get(1));
        friendBooks.add(sharableBooks.get(2));
        friendBooks.add(sharableBooks.get(3));
        onGoingTrade1.setListBookPartner(friendBooks);
        onGoingTrade1.setListBookUser(userBooks);
        onGoingTrade1.setTradeStatus(0); // Ongoing

        userBooks= new ArrayList<>();
        friendBooks= new ArrayList<>();
        onGoingTrade2= new Trade();

        onGoingTrade2.setTradeUser(new Person("USER")); // Instead of creating a new person, you should put the user's object
        onGoingTrade2.setTradePartner(new Person("friend2"));
        userBooks.add(sharableBooks.get(0));
        friendBooks.add(sharableBooks.get(1));
        onGoingTrade2.setListBookPartner(friendBooks);
        onGoingTrade2.setListBookUser(userBooks);
        onGoingTrade2.setTradeStatus(0); // Ongoing

        userBooks= new ArrayList<>();
        friendBooks= new ArrayList<>();
        completedTrade= new Trade();

        completedTrade.setTradeUser(new Person("USER")); // Instead of creating a new person, you should put the user's object
        completedTrade.setTradePartner(new Person("friend3"));
        userBooks.add(sharableBooks.get(2));
        friendBooks.add(sharableBooks.get(3));
        completedTrade.setListBookPartner(friendBooks);
        completedTrade.setListBookUser(userBooks);
        completedTrade.setTradeStatus(1); // Accepted trade

    }


}
