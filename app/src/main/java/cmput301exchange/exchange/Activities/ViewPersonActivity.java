package cmput301exchange.exchange.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cmput301exchange.exchange.Controllers.ViewPersonController;
import cmput301exchange.exchange.Interfaces.Observer;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.PersonList;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.ElasticSearch;
import cmput301exchange.exchange.Serializers.SearchHit;
import cmput301exchange.exchange.User;

public class ViewPersonActivity extends AppCompatActivity {
    private ViewPersonController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_person);
        controller = new ViewPersonController(this, this);
        controller.init();
        controller.initPersonList(10);
    }

    @Override
    public void onPause(){
        controller.saveUser();
        controller.updateOnline();
        super.onPause();
    }

    @Override
    public void onResume(){
        controller.loadUser();
//        downloadServer();
        super.onResume();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        controller.prepareOptionsMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        controller.createOptionsMenu(menu);
        controller.initSearchView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        controller.optionsItemSelected(item);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        this.finish();
    }

    @Override
    public void finish(){
        if (controller.getState() == 2) {
            controller.sendBackTradePartner();
        }else{
            setResult(RESULT_OK, new Intent());

        }
        super.finish();
    }


}
