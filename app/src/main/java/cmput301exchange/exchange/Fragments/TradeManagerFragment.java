package cmput301exchange.exchange.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cmput301exchange.exchange.Activities.TradeManagerActivity;
import cmput301exchange.exchange.Controllers.TradeManagerController;
import cmput301exchange.exchange.Interfaces.BackButtonListener;
import cmput301exchange.exchange.Others.CharSequenceWrapper;
import cmput301exchange.exchange.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TradeManagerFragment extends Fragment implements BackButtonListener {

    private TradeManagerActivity myActivity;
    private View myView;
    private Button displayTrade,viewCurrentTrade,viewPastTrade,checkTradeRequest;
    private TextView tradeRequestsView;
    private CharSequenceWrapper tradeRequests_no=null;
    private TradeManagerController myTradeManagerController;

    public TradeManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myTradeManagerController= new TradeManagerController(myActivity.getTradeManager(), myActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.activity_trade_manager, container, false);
        initButtons();
        initTextView();
        return myView;
    }


    public void initButtons(){
        displayTrade= (Button) myView.findViewById(R.id.TM_displayTrade);
        viewCurrentTrade= (Button) myView.findViewById(R.id.TM_currentTrade);
        viewPastTrade= (Button) myView.findViewById(R.id.TM_pastTrade);
        checkTradeRequest= (Button) myView.findViewById(R.id.TM_checkRequest);

        displayTrade.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayTrade_Handler();
            }
        });

        viewCurrentTrade.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CurrentTrade_Handler();
            }
        });

        viewPastTrade.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                PastTrade_Handler();
            }
        });

        checkTradeRequest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                CheckRequest_Handler();
            }
        });
    }

    public void initTextView(){
        tradeRequestsView= (TextView) myView.findViewById(R.id.TM_showRequest);
//        updateTextView();
    }
    
    public void updateTextView(){
        if (tradeRequests_no==null) {
            tradeRequests_no = new CharSequenceWrapper(myTradeManagerController.getTradeRequests_no());
        }
        else {
            tradeRequests_no.setText(myTradeManagerController.getTradeRequests_no());
        }
        tradeRequestsView.setText(tradeRequests_no);
    }

    public void CheckRequest_Handler(){
        myActivity.displayTradeRequests();
    }

    public void displayTrade_Handler(){
        myActivity.makeTrade();
    }

    public void CurrentTrade_Handler(){
        myActivity.displayCurrentTrades();
    }

    public void PastTrade_Handler(){
        myActivity.displayPastTrades();
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

    @Override
    public void onBackPress() {
        myActivity.setCurrentFragment(null);
        myActivity.onBackPressed();
    }
}
