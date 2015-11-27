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

    public ArrayList<Book> getMyBookList(){
//        ArrayList<String> stringList= new ArrayList<String>();
//        for (Book b:myTrade.getListBookUser()){
//            stringList.add(b.getName());
//        }
        return myTrade.getListBookUser();
    }

    public ArrayList<Book> getFriendBookList(){
//        ArrayList<String> stringList= new ArrayList<String>();
//        for (Book b:myTrade.getListBookPartner()){
//            stringList.add(b.getName());
//        }
        return myTrade.getListBookPartner();
    }

    public void setMyBookList(ArrayList<Book> list){
        myTrade.setListBookUser(list);
    }

    public void setFriendBookList(ArrayList<Book> list){
        myTrade.setListBookPartner(list);
    }

    public void checkAvailability(){
        
    }
}
