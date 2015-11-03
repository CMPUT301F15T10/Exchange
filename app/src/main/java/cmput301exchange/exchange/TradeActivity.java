package cmput301exchange.exchange;

import java.util.ArrayList;

/**
 * Created by Yishuo Wang on 2015/10/8.
 */
public class TradeActivity {
    private Person tradeUser;
    private Person tradePartner;
    private Integer tradeStatus;
    private ArrayList<Item> listItemUser;
    private ArrayList<Item> listItemPartner;
    private Integer tradeType;

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

    // tradeType
    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }
}
