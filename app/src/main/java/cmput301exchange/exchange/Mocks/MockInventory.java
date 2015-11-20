package cmput301exchange.exchange.Mocks;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Inventory;

/**
 * Created by nobody on 11/20/15.
 */
public class MockInventory extends Inventory {
    public MockInventory() {
        inventoryOwner = new MockUser("Bob");

        Book book1 = new MockBook1();
        Book book2 = new MockBook2();
        inventoryList.add(book1);
        inventoryList.add(book2);
    }
}
