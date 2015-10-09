package cmput301f15t10.exchange;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by Charles on 10/9/2015.
 */
public class ItemTest extends ApplicationTestCase<Application> {
    public ItemTest() {
        super(Application.class);
    }

    public testInstatiate(){
        try{
        Book book  = new Book();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public testUpdateTitle(){
        Book book = new Book();
        try{
            book.updateTitle("Testy Tester");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public testUpdateAuthor(){
        book book = new Book();
        try{
            book.updateAuthor("Tesla the Tester");

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public 
}
