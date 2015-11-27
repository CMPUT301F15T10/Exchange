package cmput301exchange.exchange;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

import cmput301exchange.exchange.Activities.Login;
import cmput301exchange.exchange.Interfaces.Observer;
import cmput301exchange.exchange.Serializers.ElasticSearch;


/**
 * Created by Charles on 11/27/2015.
 */
public class ElasticSearchTest extends ActivityInstrumentationTestCase2 implements Observer {
    private Activity activity;
    private ElasticSearch elasticSearch;
    public ElasticSearchTest(){
       super (Login.class);
    }
    private User user;
    private Book book;

    @Override
    public void update() {

        user = elasticSearch.getUser();
    }

    public void setUp(){
        user = new User("frank");
        activity = getActivity();
        elasticSearch = new ElasticSearch(activity);
        elasticSearch.addObserver(this);
    }

    public void testStart(){
        elasticSearch = new ElasticSearch(activity);
    }

    public void testLoad(){
        String username = "arbitraryLongString";
        user = elasticSearch.fetchUser(username);
    }

    public void testSave(){
        user = new User("BIllyTimBuckets");
        book = new Book();
        book.updateTitle("Tester");
        book.updateAuthor("BillyTimBuckers");
        user.getMyInventory().add(book);
        elasticSearch.sendUser(user);

    }
}
