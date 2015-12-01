package cmput301exchange.exchange;

import android.app.Activity;
import android.content.Context;
import android.graphics.AvoidXfermode;

import com.google.gson.Gson;

import java.util.ArrayList;

import cmput301exchange.exchange.Interfaces.Observer;
import cmput301exchange.exchange.Serializers.ElasticSearch;

/**
 * Created by touqir on 28/11/15.
 */
public class PersonManager implements Observer {
    ModelEnvironment globalEnv;
    ElasticSearch ES;
    Activity activity;
    Integer defaultPage=0;

    public PersonManager(Activity activity){
        this.activity=activity;
        globalEnv= new ModelEnvironment(activity,null);
    }

    public PersonList extractPersonList(ArrayList<Long> ID_list){
        PersonList extractedPersons= new PersonList();
        PersonList persons=globalEnv.getPersonList();
        for (Long ID: ID_list){
            extractedPersons.addPerson(persons.getPersonByID(ID));
        }
        return extractedPersons;
    }

    public void updatePersonList(Activity activity){
        ES=new ElasticSearch(activity);
        ES.addObserver(this);
        ES.fetchAllUsersFromServer("*", defaultPage.toString());
    }
//    public void initPersonList(Integer integer){
//        String page = integer.toString();
//        ES.addObserver(this);
//        ES.fetchAllUsersFromServer("*", page);
//    }

    @Override
    public void update() {
        PersonList list=ES.getPersonList();
        globalEnv.setPersonList(list);
        globalEnv.saveInstance(this.activity);
    }
}
