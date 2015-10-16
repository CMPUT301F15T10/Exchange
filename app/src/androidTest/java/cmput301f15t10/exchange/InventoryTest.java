package cmput301f15t10.exchange;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by Charles on 10/8/2015.
 */

public class InventoryTest extends ApplicationTestCase<Application> {
    public void testInstantiate(){
        try {
            Inventory inventory = new Inventory();
        }catch (Exception e){
            throw new RuntimeException;
        }
    }
    public void testAdd(){
        Inventory inventory = new Inventory();
        Book book = new Book();
        //Replace with book setters and getters
        book.updateTitle("TestyTester");
        book.updateAuthor("Tesla Tester");
        inventory.add(book);
        Book book2 = inventory.getBook("TestyTester");

        if (!book.compare(book2)){
            throw new RuntimeException();
        }
    }
    public void testRm(){
        Inventory inventory = new Inventory();
        Book book = new Book();
        book.updateTitle("Testy Tester");
        inventory.add(book);
        inventory.getBook(book);
        inventory.rmBook(book);

        if(!inventory.hasBook(book)){
            throw new RuntimeException();
        }
    }
}
