package cmput301f15t10.exchange;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by Charles on 10/8/2015.
 */

public class InventoryTest{
    public void testInstantiate(){
        Inventory inventory = new Inventory();
        assert (!(inventory == null)); //Find a better way of doing this
    }
    public void testAdd(){
        Inventory inventory = new Inventory();
        Book book = new Book();
        //Replace with book setters and getters
        book.updateTitle("TestyTester");
        book.updateAuthor("Tesla Tester");
        inventory.add(book);
        Book book2 = inventory.getBook("TestyTester");

        assert (book.compareTo(book2) == 1);
    }
    public void testRm(){
        Inventory inventory = new Inventory();
        Book book = new Book();
        book.updateTitle("Testy Tester");
        inventory.add(book);
        inventory.getBook(book);
        inventory.rmItem(book);

        assert (inventory.contains(book));
    }
}
