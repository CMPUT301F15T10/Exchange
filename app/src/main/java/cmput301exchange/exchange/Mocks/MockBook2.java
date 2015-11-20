package cmput301exchange.exchange.Mocks;

import cmput301exchange.exchange.Book;

/**
 * Created by nobody on 11/20/15.
 */
public class MockBook2 extends Book {
    public MockBook2() {
        bookName = "Why do books exist?";
        author = "John Locke";
        category = "Category2";
        quality = 5;
        quantity = 2;
        comment = "\nDNE\n";
        sharable = false;
    }

}
