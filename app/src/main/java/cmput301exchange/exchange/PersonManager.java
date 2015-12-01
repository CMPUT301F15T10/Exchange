package cmput301exchange.exchange;

import android.app.Activity;
import android.content.Context;
import android.graphics.AvoidXfermode;
import android.util.Log;

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

    public boolean saveIntoPersonList(Person person1){
        Gson gson= new Gson();
//        Log.e("size of person1 PM",String.valueOf(gson.toJson(person1).length()));
        globalEnv.loadInstance(activity);
        PersonList pl=globalEnv.getPersonList();
        Log.e("5 ","PM");
        Log.e(String.valueOf(pl.getPersonList().size())," PM");
        boolean success=false;
        int i=0;
        for (Person person:pl.getPersonList()){
            if (person.getID().equals(person1.getID())){
//                pl.getPersonList().add(i,person1);
                success=true;
                break;
//                success=true;
            }
            i=i+1;
        }
        Log.e("1 ","PM");
        if (success==true){
            Log.e("2 ","PM");
            pl.setPerson(i, person1);
            Log.e("3 ", "PM");
        }
        globalEnv.saveInstance(activity);
        Log.e("4 ", "PM");
        return success;
    }

    public void updatePersonList(){
        ES=new ElasticSearch(activity);
        ES.addObserver(this);
        ES.fetchAllUsersFromServer("*", defaultPage.toString());
    }

    @Override
    public void update() {
        PersonList list=ES.getPersonList();
        globalEnv.setPersonList(list);
        globalEnv.saveInstance(this.activity);
    }

    public void pushOnline(){
        ES=new ElasticSearch(activity);
        globalEnv.loadInstance(activity);
        PersonList pl=globalEnv.getPersonList();
        for (Person person: pl.getPersonList()){
            ModelEnvironment env= new ModelEnvironment(activity);
            env.setOwner(person.toUser());
            ES.sendToServer(env);
        }
        ES.sendToServer(globalEnv);
    }
}
