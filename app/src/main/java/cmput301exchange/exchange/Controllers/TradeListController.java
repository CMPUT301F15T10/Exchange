package cmput301exchange.exchange.Controllers;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import cmput301exchange.exchange.Interfaces.Searchable;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.Serializers.DataIO;
import cmput301exchange.exchange.Trade;
import cmput301exchange.exchange.TradeManager;

/**
 * Created by touqir on 04/11/15.
 */
public class TradeListController {

    private TradeManager myTradeManager;
    private Integer mode;
//    private Context myContext;
//    private ModelEnvironment GlobalENV;
    private Person owner;
    private ArrayList<Trade> myTradeList;
    private Searchable searcher;

    public TradeListController(TradeManager tradeManager, int mode,Context context){
        myTradeManager=tradeManager;
        this.mode=mode;
        ModelEnvironment globalEnv= new ModelEnvironment(context,null);
        owner=globalEnv.getOwner();
        initTradeList();
        Log.e("list mode: ", String.valueOf(mode));
    }

    public void setMyTradeManager(TradeManager tradeManager){
        this.myTradeManager=tradeManager;
    }

    public void initTradeList(){
        if (mode==1) {
            myTradeList=myTradeManager.getListCompleteTrade(); //past trade
        }
        if (mode==2){
            myTradeList=myTradeManager.getListCurrentTrade(); //current trade
        }
        if (mode==3){
            myTradeList=myTradeManager.getListTradeRequest(); //trade requests
        }
        if (mode==4){
            myTradeList=myTradeManager.getListTransactionedTrade(); // List of trades that were either accepted or declined
        }
    }

    public ArrayList<Trade> getTradeList(){
        return myTradeList;
    }

    public void getTradeListAsBurrower(){

    }

    public void getTradeListAsOwner(){

    }
    //Todo
    //Figure out how to use searcher for searching through tradelist.
    public ArrayList<Trade> searchTrade(String query){
        Log.e("search query: ",query);
        if (query==""){
            return null;
        }
        //For now!!!!
//        ArrayList<Trade> result= (ArrayList<Trade>) searcher.Search(query);
        ArrayList<Trade> result=new ArrayList<>();
        //Add searching based on other kind of values for different flags
        return result;
    }
}
