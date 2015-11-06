package cmput301exchange.exchange.Controllers;

import android.content.Context;

import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.Serializers.DataIO;
import cmput301exchange.exchange.Trade;
import cmput301exchange.exchange.TradeManager;

/**
 * Created by touqir on 03/11/15.
 */
public class TradeManagerController {

    private TradeManager myTradeManager;
    private Context myContext;
    private ModelEnvironment GlobalENV;
    private Person owner;


    public TradeManagerController(TradeManager tradeManager, Context context){
        myTradeManager=tradeManager;
        this.myContext=context;
        initEnv();
    }

    public Trade createTrade(){
        return myTradeManager.createTrade();
    }

    public String getTradeRequests_no(){
        Integer numberRequests=myTradeManager.getTradeRequest_no(owner);
        return numberRequests.toString();
    }

    public void initEnv(){
        DataIO io = new DataIO(myContext,ModelEnvironment.class);
        GlobalENV = io.loadEnvironment("GlobalENV");
        owner=GlobalENV.getOwner();
    }
}
