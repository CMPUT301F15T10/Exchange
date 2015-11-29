package cmput301exchange.exchange;

import android.content.Context;
import android.graphics.AvoidXfermode;

import com.google.gson.Gson;

import java.util.ArrayList;

import cmput301exchange.exchange.Serializers.ElasticSearch;

/**
 * Created by touqir on 28/11/15.
 */
public class PersonManager {
    ModelEnvironment globalEnv;
    ElasticSearch ES;
    Context activity;

    public PersonManager(Context activity){
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

}
