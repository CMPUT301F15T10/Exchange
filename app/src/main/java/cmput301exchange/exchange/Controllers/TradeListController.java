package cmput301exchange.exchange.Controllers;

import android.content.Context;

import java.util.ArrayList;

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
    private Context myContext;
    private ModelEnvironment GlobalENV;
    private Person owner;

    public TradeListController(TradeManager tradeManager, int mode,Context context){
        myTradeManager=tradeManager;
        this.mode=mode;
        this.myContext=context;
        initEnv();
    }

    public void initEnv(){
        DataIO io = new DataIO(myContext,ModelEnvironment.class);
        GlobalENV = io.loadEnvironment("GlobalENV");
        owner=GlobalENV.getOwner();
    }

    public ArrayList<Trade> getTradeRequestList(){
        return myTradeManager.getListTradeRequest(owner);
    }

    //Todo
    public ArrayList<Trade> searchTrade(String query, int flag){
        if (query==""){
            return null;
        }
        ArrayList<Trade> result= new ArrayList<>();
        if (flag==1){
            int ID=Integer.parseInt(query);
            // Add searching logic for int ID
        }
        //Add searching based on other kind of values for different flags
        return result;
    }
}
