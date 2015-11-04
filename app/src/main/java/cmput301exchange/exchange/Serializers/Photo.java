package cmput301exchange.exchange.Serializers;


import java.util.ArrayList;
import java.util.Observable;


import cmput301exchange.exchange.Interfaces.Observer;


/**
 * code written by joshua2ua on github:
 * https://github.com/kstem/AndroidElasticSearch/blob/master/app/src/main/java/ca/ualberta/ssrg/movies/Movies.java
 * Modified by kstember on 11/4/15.
 */
public class Photo extends Observable {
    private volatile ArrayList<Observer> observers = new ArrayList<Observer>();
    private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/testing/";
    //private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/testing/movie/_search";

    public int photoSize(Photo photo){
        int size = 0;

        return size;
    }
    //@Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    // @Override
    public void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(/*this*/);
        }
    }

    public String getResourceUrl() {
        return RESOURCE_URL;
    }

   /* public String getSearchUrl() {
        return SEARCH_URL;
    }
*/
    /**
     * Java wants this, we don't need it for Gson/Json
     */
    private static final long serialVersionUID = 3199561696102797345L;

}