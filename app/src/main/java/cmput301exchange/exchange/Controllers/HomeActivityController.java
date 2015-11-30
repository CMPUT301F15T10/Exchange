package cmput301exchange.exchange.Controllers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import cmput301exchange.exchange.Interfaces.Observer;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.PersonList;
import cmput301exchange.exchange.Serializers.ElasticSearch;

/**
 * Created by touqir on 29/11/15.
 */
public class HomeActivityController implements Observer {

    ElasticSearch elasticSearch;
    Activity activity;
    ArrayAdapter personListAdapter;
    ModelEnvironment globalEnv;

    public HomeActivityController(Activity activity,ModelEnvironment globalEnv){
        //constructor, basic setups
        this.activity=activity;
        elasticSearch= new ElasticSearch(activity);
        this.globalEnv=globalEnv;
    }

    public void fetchPersons(Integer integer){
        String page = integer.toString();
        elasticSearch.addObserver(this);
        elasticSearch.fetchAllUsersFromServer("*", page);
    }

    @Override
    public void update() {
        PersonList personList = elasticSearch.getPersonList();
        globalEnv.setPersonList(personList);
        globalEnv.saveInstance(activity);
    }
}
