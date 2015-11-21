package cmput301exchange.exchange;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by Charles on 11/2/2015.
 */
public class BookTest extends ApplicationTestCase<Application> {
    public BookTest() {
        super(Application.class);
    }

    public void testQualityUpdate(){
        Book book = new Book();
        book.updateQuality(100); // make it a huge value
        assertEquals(5,book.getQuality());
        book.updateQuality(-100); // make it a small value
        assertEquals(0,book.getQuality());
        book.updateQuality(3); //make it an intermediate
        assertEquals(3, book.getQuality());
    }

    public void testNameUpdate(){
        Book book = new Book();
        book.updateTitle("TestyTesty");
        assertEquals(book.getName(), "TestyTesty");
        book.updateTitle("Filthy Frank's Adventure Book (){};'123"); // TEST EVERY POSSIBLE CHAR
        assertEquals("Filthy Frank's Adventure Book (){};'123", book.getName());
    }

    public void testEditCategory(){
        Book book = new Book();
        book.updateCategory("Horror");
        assertEquals("Horror", book.getCategory());
    }

    public void testUpdateQuantity(){
        Book book = new Book();
        book.updateQuantity(3);
        assertEquals(3, book.getQuantity());

    }

    public void testShareable(){
        Book book = new Book();
        book.setShareable(true);
        assertEquals(true,book.isShareable());
        book.setShareable(false);
        assertEquals(false,book.isShareable());
    }

    public void testComments(){
        Book book = new Book();
        book.updateComment("This book is so bad it made me want to read Ayn Rand");
        assertEquals("This book is so bad it made me want to read Ayn Rand",book.getComment());

    }

}
