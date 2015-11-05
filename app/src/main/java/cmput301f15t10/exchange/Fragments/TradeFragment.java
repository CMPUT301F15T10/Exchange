package cmput301f15t10.exchange.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import cmput301f15t10.exchange.Activities.TradeManagerActivity;
import cmput301f15t10.exchange.Controllers.TradeController;
import cmput301f15t10.exchange.Interfaces.ListItemRunnable;
import cmput301f15t10.exchange.Others.CharSequenceWrapper;
import cmput301f15t10.exchange.R;
import cmput301f15t10.exchange.Trade;
import cmput301f15t10.exchange.TradeManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class TradeFragment extends Fragment {
    private int FragmentID=2;
    private TradeManagerActivity myActivity;
    private View myView;
    private Button confirm,abort,tradeItems,checkTradeRequest;
    private TextView tradeStatusView,tradeIDView;
    private CharSequenceWrapper tradeStatus=null, tradeID=null;
    private TradeController myTradeController;
    private ArrayAdapter<ListItemRunnable> spinnerAdapter;
    private ArrayList<ListItemRunnable> spinnerItems= new ArrayList<>();
    private Spinner traderSelection;
    private String traderName=null;
    private Trade myTrade;
    private TradeManager myTradeManager;

    public TradeFragment() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTrade();
        myTradeManager=myActivity.getTradeManager();
        myTradeController= new TradeController(myTrade,myTradeManager);
    }

    public void initTrade(){
        myTrade=myActivity.getTrade();
        if (myTrade==null){
            myTrade=new Trade();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.activity_trade, container, false);
        initButtons();
        initTextView();
        initAdapter();
        initSpinner();
        return myView;
    }


    public void initButtons(){
        confirm= (Button) myView.findViewById(R.id.Trade_confirm);
        abort= (Button) myView.findViewById(R.id.Trade_abort);
        tradeItems= (Button) myView.findViewById(R.id.Trade_viewItems);
//        checkTradeRequest= (Button) myView.findViewById(R.id.TM_checkRequest);

        confirm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                confirm_Handler();
            }
        });

        abort.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                abort_Handler();
            }
        });

        tradeItems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tradeItems_Handler();
            }
        });

//        checkTradeRequest.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                CheckRequest_Handler();
//            }
//        });
    }
    
    public void confirm_Handler(){
        myTradeController.confirmTrade();
        //May add a dialog box if necessary
        exit();
    }
    
    public void abort_Handler(){
        myTradeController.cancelTrade();
        //May add a dialog box if necessary
        exit();
    }
    
    public void tradeItems_Handler(){
        //Call TradeListFragment for displaying the items of the trade.
        myActivity.displayItemsToTrade(myTrade);
    }

    public void initAdapter() {
        spinnerAdapter =
                new ArrayAdapter<>(myActivity, android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    public void initSpinner(){
        traderSelection= (Spinner) myView.findViewById(R.id.Trade_spinner);
        traderSelection.setAdapter(spinnerAdapter);
        traderSelection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((ListItemRunnable) traderSelection.getItemAtPosition(i)).run(null);
            }
        });
        initSpinnerItems();
        spinnerAdapter.notifyDataSetChanged();
    }

    public void initSpinnerItems(){
        updateTraderSpinnerItem();
        spinnerItems.add(new ListItemRunnable() {
            String text = "Choose new trade partner";

            @Override
            public void run(Object obj) {
                selectTrader();
            }

            public String toString() {
                return text;
            }
        });
    }

    public void updateTraderSpinnerItem(){
        spinnerItems.add(0, new ListItemRunnable() {
            String text=traderName;
            @Override
            public void run(Object obj) {

            }

            public String toString(){
                return text;
            }
        });
    }

    public void initTextView(){
        tradeStatusView= (TextView) myView.findViewById(R.id.TM_showRequest);
        tradeIDView= (TextView) myView.findViewById(R.id.Trade_ID);
//        updateTextView();
    }

    //TODO
    public void selectTrader(){
        // Should call person search/browse fragment
        // Then should set traderName
        updateTraderSpinnerItem();
    }

    public void updateTextView(){
        if (tradeStatus==null) {
            tradeStatus = new CharSequenceWrapper(myTradeController.getTradeStatus());
        }
        else {
            tradeStatus.setText(myTradeController.getTradeStatus());
        }

        if (tradeID==null){
            tradeID= new CharSequenceWrapper(myTradeController.getTradeID());
        }
        else {
            tradeID.setText(myTradeController.getTradeID());
        }

        tradeStatusView.setText(tradeStatus);
        tradeID.setText("Trade "+tradeID.toString());
        tradeIDView.setText(tradeID);
    }

    public void onResume(){
        super.onResume();
        updateTextView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = (TradeManagerActivity) activity;
    }

    public void exit(){
        myActivity.closeFragment(FragmentID);
    }

}
