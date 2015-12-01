package cmput301exchange.exchange.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
public class TradeFragment2 extends Fragment implements BackButtonListener {
    private int FragmentID=2;
    private TradeMaker myActivity;
    private View myView;
    private Button accept,decline,tradeItems,save;
    private TextView TradeTypeView,tradeIDView;
    private CharSequenceWrapper TradeType=null, tradeID=null;
    private TradeController myTradeController;
    private ArrayAdapter<ListItemRunnable> spinnerAdapter;
    private ArrayList<ListItemRunnable> spinnerItems;
    private Spinner traderSelection;
    private String traderName=null;
//    private TradeManager myTradeManager;
//    private Person tradePartner=null;

    public TradeFragment2() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spinnerItems= new ArrayList<>();
        initTradeController();
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

        myView=inflater.inflate(R.layout.activity_trade2, container, false);
        initButtons();
        initTextView();
        initAdapter();
        initSpinner();
        return myView;
    }


    public void initButtons(){
        accept= (Button) myView.findViewById(R.id.Trade2_accept);
        decline= (Button) myView.findViewById(R.id.Trade2_decline);
        tradeItems= (Button) myView.findViewById(R.id.Trade2_viewItems);
        save=(Button) myView.findViewById(R.id.Trade2_save);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSave_Handler();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                accept_Handler();
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTradeDeclineDialog();
            }
        });
        tradeItems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tradeItems_Handler();
            }
        });

    }

    public void onSave_Handler(){
        exit();
    }

    public void accept_Handler(){
        if (myTradeController.acceptTrade()==true){
            showTradeAcceptDialog();
        };
        //May add a dialog box if necessary
        exit();
    }

    //TODO may have to implement the email sending!
    public void showTradeAcceptDialog(){
        new AlertDialog.Builder((Context) myActivity)
                .setTitle("Accepted!")
                .setMessage("You have accepted a trade offer from "+myTradeController.getTrade().getTradeUser().getName())
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        exit();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    public void showTradeDeclineDialog(){
        new AlertDialog.Builder((Context) myActivity)
                .setTitle("Declined!")
                .setMessage("You have declined a trade offer from " + myTradeController.getTrade().getTradeUser().getName()
                        + "\n" + "Would you instead perform a counter Trade with another user? Press Yes to select a user or No to decline")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        performCounterTrade();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        decline_Handler();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    public void performCounterTrade(){
        myActivity.selectPerson();
    }

    public void decline_Handler(){
        myTradeController.declineTrade();
        //May add a dialog box if necessary
        exit();
    }

    //TODO
    public void tradeItems_Handler(){
        //Call TradeListFragment for displaying the items of the trade.
        myActivity.displayItemsToTrade(null);
    }

    public void initAdapter() {
        spinnerAdapter =
                new ArrayAdapter<>((Context) myActivity, android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }


    public void initSpinner(){
        traderSelection= (Spinner) myView.findViewById(R.id.Trade2_spinner);
        traderSelection.setAdapter(spinnerAdapter);
        traderSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((ListItemRunnable) traderSelection.getItemAtPosition(i)).run(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        initSpinnerItems();
        spinnerAdapter.notifyDataSetChanged();
    }

    public void initSpinnerItems(){
        spinnerItems.add(null);
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
        spinnerItems.set(0, new ListItemRunnable() {
            String text = traderName;

            @Override
            public void run(Object obj) {

            }

            public String toString() {
                return text;
            }
        });
    }

    public void initTextView(){
        TradeTypeView= (TextView) myView.findViewById(R.id.Trade2_type);
        tradeIDView= (TextView) myView.findViewById(R.id.Trade2_ID);
//        updateTextView();
    }

    //TODO
    public void selectTrader(){
        // Should call person search/browse fragment
        // Then should set traderName
        myActivity.selectPerson();
//        updateTraderSpinnerItem();
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
        updateTraderSpinnerItem();
        traderSelection.setSelection(0);
        updateTextView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = (TradeMaker) activity;
    }

    public void exit(){
        myActivity.setTradeController(myTradeController);
//        myTradeController.saveTradeUnInitiated();
        myActivity.displayTradeRequests(); //switches back to tradeRequestList fragment.
    }

    @Override
    public void onBackPress() {
        exit();
    }
}
