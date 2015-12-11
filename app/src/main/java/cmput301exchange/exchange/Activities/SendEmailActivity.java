package cmput301exchange.exchange.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Trade;


// http://www.mkyong.com/android/how-to-send-email-in-android
// By: mkyong
public class SendEmailActivity extends Activity {

    public String sendTo, subject, message;
    Button sendButton;
    EditText comment;
    public TextView text;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendemail);

        message = "Trade Owner -> A\n Trade Partner -> B \n";
        //getTradeDetails(trade);
        text = (TextView) findViewById(R.id.messageText);
        text.setMovementMethod(new ScrollingMovementMethod());
        text.setText(message);


        sendButton = (Button) findViewById(R.id.sendButton);
        comment = (EditText) findViewById(R.id.commentEditText);

        sendButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    public void getTradeDetails(Trade trade) {
        if (trade == null) {
            message = "No Trade Loaded";
        }
        String message = "";
        message = message + "Trade Owner -> " + trade.getTradeUser().toString() + "\n";
        message = message + "Trade Partner -> " + trade.getTradePartner().toString() + "\n";
        message = message + "Trade id -> " + trade.getTradeId().toString() + "\n";
    }

    private void sendEmail() {
        //message = message + "\n\n" + comment.getText().toString();
        message = text.getText().toString() + "\n\n" + comment.getText().toString();
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, sendTo);
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client:"));
    }
}