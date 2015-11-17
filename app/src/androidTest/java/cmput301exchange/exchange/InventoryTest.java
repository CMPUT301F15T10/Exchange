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
        inventory.removeItem(book);

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
        Inventory result=new Inventory();
        Book book=new Book();
        Book book1=new Book();
        book.updateCategory("cat");
        book1.updateCategory("dog");
        inventory.add(book);
        inventory.add(book1);
        result=inventory.searchByCategory("cat");
        assertEquals(result.getInventoryList().size(),1);
    }
    public void testTx(){
        Inventory result=new Inventory();
        Book book1=new Book();
        book1.updateTitle("abc");
        Book book2=new Book();
        book2.updateTitle("def");
        Book book3=new Book();
        book3.updateAuthor("abc");
        Book book4=new Book();
        book4.updateAuthor("def");
        Book book5=new Book();
        book5.updateComment("abc");
        Book book6=new Book();
        book6.updateComment("def");
        inventory.add(book1);
        inventory.add(book2);
        inventory.add(book3);
        inventory.add(book4);
        inventory.add(book5);
        inventory.add(book6);
        result=inventory.searchByText("b");
        assertEquals(result.getInventoryList().size(),3);




    }

}
