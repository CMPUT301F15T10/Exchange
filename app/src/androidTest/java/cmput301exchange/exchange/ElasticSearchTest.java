package cmput301exchange.exchange;
import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import cmput301exchange.exchange.Serializers.ElasticSearch;


/**
 * Created by Charles on 11/27/2015.
 */
public class ElasticSearchTest extends ApplicationTestCase {
    private Context context;
    private ElasticSearch elasticSearch = new ElasticSearch();
    public ElasticSearchTest(){
       super (Application.class);
    }

    public void testStart(){
        elasticSearch = new ElasticSearch();
    }

    public void testLoad(){
        User user;
        String username = "arbitraryLongString";
        synchronized (elasticSearch){
            user = elasticSearch.fetchUser(username);
        }
        assertEquals(user.getName(),username);
    }
    public void testSave(){
        User user = new User("BIllyTimBuckets");
        Book book = new Book();
        book.updateTitle("Tester");
        book.updateAuthor("BillyTimBuckers");
        user.getMyInventory().add(book);
        elasticSearch.sendUser(user);

    }
}
