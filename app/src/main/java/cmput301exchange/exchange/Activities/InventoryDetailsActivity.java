package cmput301exchange.exchange.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.R;

public class InventoryDetailsActivity extends ActionBarActivity {
    // this is a class of show details of a inventory
    //it use gson to get the data about a specific inventory
    //and change edit text of the view shown on the screen
    private TextView totalBooks,sharedBooks,nonSharedBooks;
    private Integer totalBooks_No, sharedBooks_No, nonSharedBooks_No;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_details);
        Intent intent =getIntent();
        String json=intent.getStringExtra("Inventory");
        Gson gson= new Gson();
        Inventory inventory=gson.fromJson(json, Inventory.class);
        totalBooks_No=inventory.getInventoryList().size();
        sharedBooks_No=inventory.getSharedItems().size();
        nonSharedBooks_No=inventory.getNonSharedItems().size();
        initTextViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inventory_details, menu);
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

    public void initTextViews(){
        totalBooks=(TextView) findViewById(R.id.ID_TotalItems);
        totalBooks.setText(totalBooks_No.toString());
        sharedBooks=(TextView) findViewById(R.id.ID_SharedItems);
        sharedBooks.setText(sharedBooks_No.toString());
        nonSharedBooks=(TextView) findViewById(R.id.ID_NonSharedItems);
        nonSharedBooks.setText(nonSharedBooks_No.toString());
    }

    public void GoBack(View view) {
        Intent goBack = new Intent();
        setResult(RESULT_OK, goBack);
        this.finish();
    }
}
