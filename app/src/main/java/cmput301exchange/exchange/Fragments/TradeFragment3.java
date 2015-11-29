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
public class TradeFragment3 extends Fragment implements BackButtonListener {
    private int FragmentID=2;
    private TradeMaker myActivity;
    private View myView;
    private Button tradeItems,back;
    private TextView TradeTypeView,tradeIDView,tradePartnerView;
    private CharSequenceWrapper TradeType=null, tradeID=null;
    private TradeController myTradeController;
    private ArrayAdapter<ListItemRunnable> spinnerAdapter;
    private ArrayList<ListItemRunnable> spinnerItems;
    private String traderName=null;
//    private TradeManager myTradeManager;
//    private Person tradePartner=null;

    public TradeFragment3() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spinnerItems= new ArrayList<>();
        initTradeController();
        initTradePartner();
    }

    public void initTradeController(){
//        myTradeManager=myActivity.getTradeManager();
        myTradeController= myActivity.getTradeController();

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

        myView=inflater.inflate(R.layout.activity_trade3, container, false);
        initButtons();
        initTextView();
        return myView;
    }


    public void initButtons(){
        tradeItems= (Button) myView.findViewById(R.id.Trade3_viewItems);
        back=(Button) myView.findViewById(R.id.Trade3_back);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBack_Handler();
            }
        });

        tradeItems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tradeItems_Handler();
            }
        });

    }

    public void onBack_Handler(){
        exit();
    }

    //TODO
    public void tradeItems_Handler(){
        //Call TradeListFragment for displaying the items of the trade.
        myActivity.displayItemsToTrade(null);
    }

    public void initTextView(){
        TradeTypeView= (TextView) myView.findViewById(R.id.Trade3_type);
        tradeIDView= (TextView) myView.findViewById(R.id.Trade3_ID);
        tradePartnerView=(TextView) myView.findViewById(R.id.Trade3_Partner);
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
            tradeID= new CharSequenceWrapper(myTradeController.getTradeID());
        }
        else {
            tradeID.setText(myTradeController.getTradeID());
        }

        TradeTypeView.setText(TradeType);
        tradeID.setText("Trade ID "+tradeID.toString());
        tradeIDView.setText(tradeID);
    }

    public void onResume(){
        super.onResume();
//        initTradeController();
//        initTradePartner();
        updateTextView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = (TradeMaker) activity;
    }

    public void exit(){
        myActivity.setTradeController(myTradeController);
        myTradeController.saveTrade();
        myActivity.switchFragment(1); //switches back to tradeManager.
    }

    @Override
    public void onBackPress() {
        exit();
    }
}
