package cmput301exchange.exchange;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Yishuo Wang on 12/11/15.
 */
public class SendEmailUiTest extends ActivityInstrumentationTestCase2 {
    private Activity activity;
    private EditText editText;
    private TextView textView;
    private Button button;
    private Person person0;
    private Person person1;
    private Book book00;
    private Book book01;
    private Book book10;
    private Book book11;
    private ArrayList<Book> alist = new ArrayList<>();
    private ArrayList<Book> blist = new ArrayList<>();
    private Trade trade0;

    public SendEmailUiTest() {
        super(cmput301exchange.exchange.Activities.SendEmailActivity.class);
    }

    public void testStart() throws Exception {
        activity = getActivity();
    }

    // setup
    public void testSetup() {
        person0 = new Person("A");
        book00 = new Book();
        book00.updateTitle("Harry Potter and the Philosopher's Stone");
        book01 = new Book();
        book01.updateTitle("Tang");
        // add book0 to person0's inventory and his/her trading list
        person0.getMyInventory().add(book00);
        person0.getMyInventory().add(book01);
        alist.add(book00);
        alist.add(book01);

        person1 = new Person("B");
        book10 = new Book();
        book10.updateTitle("Compsci Sucks");
        book11 = new Book();
        book11.updateTitle("Math Sucks Too");
        // add book1 to person1's inventory and his/her trading list
        person1.getMyInventory().add(book10);
        person1.getMyInventory().add(book11);
        blist.add(book10);
        blist.add(book11);

        activity = getActivity();
        editText = (EditText)activity.findViewById(R.id.commentEditText);
        textView = (TextView)activity.findViewById(R.id.messageText);
        button = (Button)activity.findViewById(R.id.sendButton);
    }

    public String getTradeDetails(Trade trade) {
        String message = "";
        if (trade == null) {
            message = "No Trade Loaded";
        }
        message = message + "Trade Owner -> " + trade.getTradeUser().toString() + "\n";
        message = message + "Trade Partner -> " + trade.getTradePartner().toString() + "\n";
        message = message + "Trade id -> " + trade.getTradeId().toString() + "\n";
        return message;
    }

    /*
     * US04.07.01
     * As an owner, if I accept a trade both parties will be emailed all trade and item information
     * relevant to the trade, as well owner comments for how to continue on with the trade.
     */
    public void testSendEmail() {
        testSetup();
        trade0 = new Trade(person0, person1, alist, blist);

        activity.runOnUiThread(new Runnable() {
            public void run() {
                textView.setText(getTradeDetails(trade0));
                editText.setText("This is comment");
            }
        });

        getInstrumentation().waitForIdleSync();

        activity.runOnUiThread(new Runnable() {
            public void run() {
                button.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();
    }
}
