package cmput301exchange.exchange;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Yishuo Wang on 2015/10/8.
 */
public class Trade {
    private Person tradeUser;
    private Person tradePartner;
    private Integer tradeStatus; // 0 -> not accepted, 1 -> accepted, 2 -> declined, 3 -> counterTrade
    private Integer tradeType;
    //private ArrayList<Item> listItemUser;
    //private ArrayList<Item> listItemPartner;
    private ArrayList<Book> listBookUser; // list of books the user wants to trade
    private ArrayList<Book> listBookPartner; // list of books the partner wants to trade

    private Integer tradeId; // generate a trade id

    // call this constructor
    public Trade(Person tradeUser, Person tradePartner, ArrayList<Book> listBookUser, ArrayList<Book> listBookPartner) {
        this.tradeUser = tradeUser;
        this.tradePartner = tradePartner;
        this.listBookUser = listBookUser;
        this.listBookPartner = listBookPartner;
        this.tradeStatus = 0; // default
        this.tradeType = 0; // default
        Random rand = new Random();
        this.tradeId = rand.nextInt();
    }

    public Trade(Person tradeUser, Person tradePartner, Integer tradeStatus, Integer tradeType, ArrayList<Book> listBookUser, ArrayList<Book> listBookPartner) {
        this.tradeUser = tradeUser;
        this.tradePartner = tradePartner;
        this.tradeStatus = tradeStatus;
        this.tradeType = tradeType;
        this.listBookUser = listBookUser;
        this.listBookPartner = listBookPartner;
        Random rand = new Random();
        this.tradeId = rand.nextInt();
    }

    public Trade(Person tradeUser, Person tradePartner, Integer tradeStatus, Integer tradeType, ArrayList<Book> listBookUser, ArrayList<Book> listBookPartner, Integer tradeId) {
        this.tradeUser = tradeUser;
        this.tradePartner = tradePartner;
        this.tradeStatus = tradeStatus;
        this.tradeType = tradeType;
        this.listBookUser = listBookUser;
        this.listBookPartner = listBookPartner;
        this.tradeId = tradeId;
    }

    /*
    public Trade(Person tradeUser, Person tradePartner, Integer tradeStatus, ArrayList<Item> listItemUser, ArrayList<Item> listItemPartner, Integer tradeType) {
        this.tradeUser = tradeUser;
        this.tradePartner = tradePartner;
        this.tradeStatus = tradeStatus;
        this.listItemUser = listItemUser;
        this.listItemPartner = listItemPartner;
        this.tradeType = tradeType;
        Random rand = new Random();
        this.tradeId = rand.nextInt();
    }
    */

    // tradeUser
    public Person getTradeUser() {
        return tradeUser;
    }

    public void setTradeUser(Person tradeUser) {
        this.tradeUser = tradeUser;
    }

    // tradePartner
    public Person getTradePartner() {
        return tradePartner;
    }

    public void setTradePartner(Person tradePartner) {
        this.tradePartner = tradePartner;
    }

    // tradeStatus
    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    /*
    // listItemUser
    public ArrayList<Item> getListItemUser() {
        return listItemUser;
    }

    public void setListItemUser(ArrayList<Item> listItemUser) {
        this.listItemUser = listItemUser;
    }

    // listItemPartner
    public ArrayList<Item> getListItemPartner() {
        return listItemPartner;
    }

    public void setListItemPartner(ArrayList<Item> listItemPartner) {
        this.listItemPartner = listItemPartner;
    }
    */

    // tradeType
    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    // tradeId
    public Integer getTradeId() {
        return tradeId;
    }

    // listBookUser
    public ArrayList<Book> getListBookUser() {
        return listBookUser;
    }

    public void setListBookUser(ArrayList<Book> listBookUser) {
        this.listBookUser = listBookUser;
    }

    // listBookPartner
    public ArrayList<Book> getListBookPartner() {
        return listBookPartner;
    }

    public void setListBookPartner(ArrayList<Book> listBookPartner) {
        this.listBookPartner = listBookPartner;
    }
}
