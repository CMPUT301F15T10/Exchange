package cmput301exchange.exchange.Fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301exchange.exchange.Activities.TradeManagerActivity;
import cmput301exchange.exchange.Controllers.TradeController;
import cmput301exchange.exchange.Interfaces.BackButtonListener;
import cmput301exchange.exchange.Interfaces.ListItemRunnable;
import cmput301exchange.exchange.Interfaces.TradeMaker;
import cmput301exchange.exchange.Others.CharSequenceWrapper;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Trade;
import cmput301exchange.exchange.TradeManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class TradeFragment4 extends Fragment implements BackButtonListener {
    private int FragmentID=2;
    private TradeMaker myActivity;
    private View myView;
    private Button setComplete,back,tradeItems;
    private TextView TradeTypeView,tradeIDView, tradePartnerView;
    private CharSequenceWrapper TradeType=null, tradeID=null;
    private TradeController myTradeController;
    private ArrayAdapter<ListItemRunnable> spinnerAdapter;
    private ArrayList<ListItemRunnable> spinnerItems;
    private Spinner traderSelection;
    private String traderName=null;
    private int state; // state=1 refers that you can return item, state=2 refers that you can set it to complete
//    private TradeManager myTradeManager;
//    private Person tradePartner=null;

    public TradeFragment4() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spinnerItems= new ArrayList<>();
        initTradeController();
//        initTradePartner();
    }

    public void initTradeController(){
//        myTradeManager=myActivity.getTradeManager();
        myTradeController= myActivity.getTradeController();
        state=myTradeController.getTransactionedState();
    }

    void initTradePartner(){
        if(myTradeController.hasTradePartner()){
//            myTradeController.setTradePartner(myActivity.getTradePartner());
            traderName=myTradeController.getTradePartner().getName();
            Log.e("found trade Partner","tradeName");
        }
        else{
            traderName="No Trade Partner Selected!";
            Log.e("Came here!","initTradePartner");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.activity_trade4, container, false);
        initButtons();
        initTextView();
        return myView;
    }


    public void initButtons(){
        setComplete= (Button) myView.findViewById(R.id.Trade4_setComplete);
        back= (Button) myView.findViewById(R.id.Trade4_back);
        tradeItems= (Button) myView.findViewById(R.id.Trade4_viewItems);

        if (state==1) {
            setComplete.setText("Revert Trade");
            setComplete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    returnItems();
                }
            });
        } else if (state==2){
            setComplete.setText("Set as Complete");
            setComplete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setToComplete();
                }
            });
        } else if (state==3){
            setComplete.setText("Delete trade");
            setComplete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    deleteTrade();
                }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                back_Handler();
            }
        });
        tradeItems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tradeItems_Handler();
            }
        });

    }

    public void deleteTrade(){
        myTradeController.deleteDeclinedTrade();
        exit();
    }

    public void setToComplete(){
        myTradeController.setCompleteTrade();
        exit();
    }

    public void returnItems(){
        myTradeController.returnItems();
        exit();
    }

    public void back_Handler(){
        exit();
    }

    //TODO
    public void tradeItems_Handler(){
        //Call TradeListFragment for displaying the items of the trade.
        myActivity.displayItemsToTrade(null);
    }


    public void initTextView(){
        TradeTypeView= (TextView) myView.findViewById(R.id.Trade4_type);
        tradeIDView= (TextView) myView.findViewById(R.id.Trade4_ID);
        tradePartnerView=(TextView) myView.findViewById(R.id.Trade4_Partner);
        tradePartnerView.setText(traderName);
//        updateTextView();
    }

    public void updateTextView(){
        if (TradeType==null) {
            TradeType = new CharSequenceWrapper(myTradeController.getTradeType());
        }
        else {
            TradeType.setText(myTradeController.getTradeType());
        }

        if (tradeID==null){
            tradeID= new CharSequenceWrapper(myTradeController.getTradeID_Text());
        }
        else {
            tradeID.setText(myTradeController.getTradeID_Text());
        }

        TradeTypeView.setText(TradeType);
//        tradeID.setText("Trade ID "+tradeID.toString());
        tradeID.setText("Trade");
        tradeIDView.setText(tradeID);
    }

    public void onResume(){
        super.onResume();
//        initTradeController();
        initTradePartner();
        updateTextView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = (TradeMaker) activity;
    }

    public void exit(){
        myActivity.setTradeController(myTradeController);
//        myActivity.switchFragment(1); //switches back to tradeManager.
        myActivity.displayTransactionedTrades();
    }

    @Override
    public void onBackPress() {
        exit();
    }
}
