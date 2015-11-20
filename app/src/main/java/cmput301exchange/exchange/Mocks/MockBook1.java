package cmput301exchange.exchange.Mocks;

import cmput301exchange.exchange.Book;

/**
 * Created by nobody on 11/20/15.
 */
public class MockBook1 extends Book {
    public MockBook1() {
        bookName = "I Hate Reading";
        author = "Dr. Seuss";
        category = "Category1";
        quality = 0;
        quantity = 1;
        comment = "\nHi\n";
        sharable = true;
    }

}
