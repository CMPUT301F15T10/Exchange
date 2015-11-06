package cmput301exchange.exchange;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

import java.util.ArrayList;

/**
 * Created by Charles on 10/8/2015.
 */

public class InventoryTest extends ApplicationTestCase<Application>{
    public InventoryTest() {
        super(Application.class);
    }

    public Inventory inventory = new Inventory();

    public void testAdd(){
        Book book = new Book();
        //Replace with book setters and getters
        book.updateTitle("TestyTester");
        book.updateAuthor("Tesla Tester");
        inventory.add(book);
        Book book2 = inventory.Search("TestyTester");

        assert (book.compareTo(book2) == 1);
    }
    public void testRm(){
        Book book = new Book();
        book.updateTitle("Testy Tester");
        inventory.add(book);
        inventory.Search("Testy Tester");
        inventory.rmItem(book);

        assert (inventory.contains(book));
    }

    public void testView(){
        Book book = new Book();
        inventory.add(book);
        ArrayList<Book> testlist = new ArrayList<Book>();
        testlist.add(book);
        assertEquals(inventory.getInventoryList(), testlist);
    }
    public void testCt(){
        Book book=new Book();
        book.updateCategory("1");
        inventory.add(book);
        inventory.searchByCategory("1");
        assertEquals(inventory.getInventoryList().size(),1);
    }

}
