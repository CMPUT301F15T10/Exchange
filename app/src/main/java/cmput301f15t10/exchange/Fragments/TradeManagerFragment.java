package cmput301f15t10.exchange.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cmput301f15t10.exchange.Activities.Main;
import cmput301f15t10.exchange.Activities.TradeManagerActivity;
import cmput301f15t10.exchange.Controllers.TradeManagerController;
import cmput301f15t10.exchange.Others.CharSequenceWrapper;
import cmput301f15t10.exchange.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TradeManagerFragment extends Fragment {

    private TradeManagerActivity myActivity;
    private View myView;
    private Button makeTrade,viewCurrentTrade,viewPastTrade,checkTradeRequest;
    private TextView tradeRequestsView;
    private CharSequenceWrapper tradeRequests_no=null;
    private TradeManagerController myTradeManagerController;

    public TradeManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myTradeManagerController= new TradeManagerController(myActivity.getTradeManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.activity_edit_item, container, false);
        initButtons();
        initTextView();
        return myView;
    }


    public void initButtons(){
        makeTrade= (Button) myView.findViewById(R.id.TM_makeTrade);
        viewCurrentTrade= (Button) myView.findViewById(R.id.TM_currentTrade);
        viewPastTrade= (Button) myView.findViewById(R.id.TM_pastTrade);
        checkTradeRequest= (Button) myView.findViewById(R.id.TM_checkRequest);

        makeTrade.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                MakeTrade_Handler();
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

    public void MakeTrade_Handler(){
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
}
