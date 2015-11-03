package cmput301exchange.exchange.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cmput301f15t10.exchange.R;
public class TradeManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_manager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trade_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void CheckRequest_Handler(){

    }

    public void MakeTrade_Handler(){

    }

    public void CurrentTrade_Handler(){

    }

    public void PastTrade_Handler(){

    }

    public void onCheckRequest(View view) {
        CheckRequest_Handler();
    }

    public void onMakeTrade(View view) {
        MakeTrade_Handler();
    }

    public void onCurrentTrade(View view) {
        CurrentTrade_Handler();
    }

    public void onPastTrade(View view) {
        PastTrade_Handler();
    }
}
