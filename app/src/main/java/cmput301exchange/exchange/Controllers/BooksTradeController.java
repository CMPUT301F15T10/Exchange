package cmput301exchange.exchange.Controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.PersonList;
import cmput301exchange.exchange.Serializers.deepClone;
import cmput301exchange.exchange.Trade;
import cmput301exchange.exchange.User;

/**
 * Created by touqir on 04/11/15.
 */
public class BooksTradeController {
    Trade myTrade;
    Integer type;
    Map<Long, Integer> quantityDictionaryUser=null;
    Map<Long, Integer> quantityDictionaryFriend=null;
    Context activity;
    Person lastTradePartner=null;
    User user;
    int status;

    public BooksTradeController(Trade trade, Integer type, Context activity, User user){
        myTrade=trade;
//        this.type=type;
        this.activity=activity;
        this.user=user;
        if (user.getID().equals(myTrade.getUserID())){
            status=1;
            this.type=1;
        } else {
            status=0;
            this.type=2;
        }
        initQuantityDictionary();
    }

    public int getStatus(){
        return status;
    }

    public void initQuantityDictionary(){
        if (type==1){
            createQuantityDictionaryUser();
        } else if (type==2){
            createQuantityDictionaryFriend();
        }
    }

    public void createQuantityDictionaryUser(){

        if (quantityDictionaryUser==null || user.getMyInventory().isUpdated()) {
            quantityDictionaryUser = new HashMap<Long, Integer>();
            for (Book book : user.getMyInventory().getInventoryList()) {
                quantityDictionaryUser.put(book.getID(), book.getQuantity());
            }
            user.getMyInventory().setIsUpdated(false);
        }
    }

    public Integer getTradeBookQuantity(Long ID){
        if (type==1){
            for (Book book:myTrade.getListBookUser()){
                if (book.getID().longValue()==ID.longValue()){
                    return book.getQuantity();
                }
            }
        } else if (type==2){
            for (Book book:myTrade.getListBookPartner()){
                if (book.getID().longValue()==ID.longValue()){
                    return book.getQuantity();
                }
            }
        }
        return null;
    }

    public boolean hasTradePartner(){
        if (status==1){
            return myTrade.hasTradePartner();
        }
        else {
            if (myTrade.getTradeUser()!=null){
                return true;
            }
        }
        return false;
    }

    public Integer setTradeBookQuantity(Long ID,int quantity){
        int tempQuantity=getOriginalBookQuantity(ID);
        if (tempQuantity>getOriginalBookQuantity(ID)){
            quantity=tempQuantity;
        }
        if (type==1){
            for (Book book:myTrade.getListBookUser()){
                if (book.getID().longValue()==ID.longValue()){
                    book.updateQuantity(quantity);
                }
            }
        } else if (type==2){
            for (Book book:myTrade.getListBookPartner()){
                if (book.getID().longValue()==ID.longValue()){
                    book.updateQuantity(quantity);
                }
            }
        }
        return null;
    }

    public void createQuantityDictionaryFriend(){
        if (myTrade.getTradePartner()==null){
            return;
        }
        if (lastTradePartner==null){
            lastTradePartner=myTrade.getTradePartner();
            myTrade.getTradePartner().getMyInventory().setIsUpdated(true);// this is done so that below "if" clause gets true
        }

        if (lastTradePartner.getID()!=myTrade.getTradePartner().getID() || myTrade.getTradePartner().getMyInventory().isUpdated()) {
            quantityDictionaryFriend = new HashMap<Long, Integer>();
            for (Book book : myTrade.getTradePartner().getMyInventory().getInventoryList()) {
                quantityDictionaryFriend.put(book.getID(), book.getQuantity());
            }
            lastTradePartner=myTrade.getTradePartner();
            myTrade.getTradePartner().getMyInventory().setIsUpdated(false);
        }
    }

    public Integer getOriginalBookQuantity( Long ID ){
        if (type==1)
            return quantityDictionaryUser.get(ID);
        else if(type==2)
            return quantityDictionaryFriend.get(ID);
        return null;
    }

    public ArrayList<Book> getMyBookList(){

        return myTrade.getListBookUser();
    }

    public ArrayList<Book> getFriendBookList() {

        if (myTrade.getTradePartner() != null) {
            return myTrade.getListBookPartner();
        }
        return null;
    }

    public void setMyBookList(ArrayList<Book> list){
        myTrade.setListBookUser(list);
    }

    public void setFriendBookList(ArrayList<Book> list){
        myTrade.setListBookPartner(list);
    }

    public void checkAvailability(){

    }

    public ArrayList<Integer> getTradeItemPosition(){
        ArrayList<Integer> Position= new ArrayList<>();
        Integer index;
        if (type==1){
            for (Book tradeBook:myTrade.getListBookUser()){
                index=0;
                for (Book inventoryBook:myTrade.getTradeUser().getMyInventory().getInventoryList()){
                    if (tradeBook.getID().longValue()==inventoryBook.getID().longValue()){
                        Position.add(index);
                    }
                    index=index+1;
                }
            }
        } else if (type==2){
            for (Book tradeBook:myTrade.getListBookPartner()){
                index=0;
                for (Book inventoryBook:myTrade.getTradePartner().getMyInventory().getInventoryList()){
                    if (tradeBook.getID().longValue()==inventoryBook.getID().longValue()){
                        Position.add(index);
                    }
                    index=index+1;
                }
            }
        }
        return Position;
    }

    public Inventory getTradeUserInventory(){
        return myTrade.getTradeUser().getMyInventory();
    }

    public Inventory getTradePartnerInventory(){
        return myTrade.getTradePartner().getMyInventory();
    }

    public void setInventoryType(Integer type){
        this.type=type;
    }

    public int getInventoryType(){
        return this.type;
    }

    public void removeBook(Book book){
        int index=0;
        if (type==1) {
            for (Book book1 : myTrade.getListBookUser()) {
                if (book1.getID().equals(book.getID())) {
                    myTrade.getListBookUser().remove(index);
                }
                index = index + 1;
            }
        } else if (type==2){
            for (Book book1:myTrade.getListBookPartner()){
                if (book1.getID().equals(book.getID())){
                    myTrade.getListBookPartner().remove(index);
                }
                index=index+1;
            }
        }
    }

    public void addFromInventory(Inventory inventory){
        boolean foundBook=false;
        if (type==1){
            for (Book inventoryBook:inventory.getInventoryList()){
                Log.e("Book name: ",inventoryBook.getName());
                for (Book tradeBook:myTrade.getListBookUser()){
                    if (inventoryBook.getID().longValue()==tradeBook.getID().longValue()){
                        foundBook=true;
                        Log.e("yes ","matched");
                        break;
                    }
                }
                if (foundBook==false){
                    myTrade.getListBookUser().add(inventoryBook);
                    Log.e("I added the book!","");
                }
                foundBook=false;
            }
        } else if (type == 2){
            for (Book inventoryBook:inventory.getInventoryList()){
                for (Book tradeBook:myTrade.getListBookPartner()){
                    if (inventoryBook.getID().longValue()==tradeBook.getID().longValue()){
                        foundBook=true;
                        break;
                    }
                }
                if (foundBook==false){
                    myTrade.getListBookPartner().add(inventoryBook);
                }
                foundBook=false;
            }
        }
    }
}
