package cmput301exchange.exchange.Controllers;

import java.util.ArrayList;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Trade;

/**
 * Created by touqir on 04/11/15.
 */
public class BooksTradeController {
    Trade myTrade;
    public BooksTradeController(Trade trade){
        myTrade=trade;
    }

    public ArrayList<String> getMyBookList(){
        ArrayList<String> stringList= new ArrayList<String>();
        for (Book b:myTrade.getListBookUser()){
            stringList.add(b.getName());
        }
        return stringList;
    }

    public ArrayList<String> getFriendBookList(){
        ArrayList<String> stringList= new ArrayList<String>();
        for (Book b:myTrade.getListBookPartner()){
            stringList.add(b.getName());
        }
        return stringList;
    }
}
