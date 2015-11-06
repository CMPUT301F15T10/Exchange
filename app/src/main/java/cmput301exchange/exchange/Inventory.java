package cmput301exchange.exchange;


import java.util.ArrayList;



/**
 * Created by Charles on 11/2/2015.
 */
public class Inventory{
    /**
     * Stores an inventory for a person
     * Allows storing books and attributing them to users.
     */
    private User inventoryOwner;
    private ArrayList<Book> inventoryList = new ArrayList<>();

    private boolean shareable;
    public void setShareable(boolean bool){shareable = bool;}
    public boolean isShareable(){return shareable;}

    public Book Search(String searchString){
        /**
         * @deprecated
         * allows searching of the inventory for a query string
         * returns a book object if the item is found. Return null if the item is not.
         * @param searchString the string that you wish to search for.
         */
        //If search returns a Null, then the object is not in the list.
        Book foundBook = null;
        for (Book iterator : inventoryList){
            if (searchString.equalsIgnoreCase(iterator.getName())){
                foundBook =iterator;
            }
        }
        return foundBook;

    }
    public User getInventoryOwner() {
        return inventoryOwner;
    }

    public void setInventoryOwner(User inventoryOwner) {
        this.inventoryOwner = inventoryOwner;
    }

    public ArrayList<Book> getInventoryList() {
        /**
         * Getter for the inventory
         */
        return inventoryList;
    }

    public void add(Book Book) {this.inventoryList.add(Book);}

    public void rmItem(Book book){this.inventoryList.remove(book);}

    public boolean contains(Book book){return this.inventoryList.contains(book);}

    public Inventory searchByCategory(String cat){
        /**
         * Searches inventory by category
         * returns subinventory of items in that category.
         * @param cat category you are searching for
         */
        Inventory result = new Inventory();
        int n = inventoryList.size();
        for (int i = 0; i < n; i++) {
            if (inventoryList.get(i).getCategory() == cat) {
                result.getInventoryList().add(inventoryList.get(i));
            }
        }
        return result;
    }
    public Inventory searchByText(String query){
        /**
         * Second method for searching for a string. this is a better version than above. It searches
         * for partial strings as well as in ALL fields.
         *
         * @param query The string you wish to search all fields for.
         */
        Inventory result = new Inventory();
        int n = inventoryList.size();
        for (int i = 0; i < n; i++) {
            if (inventoryList.get(i).getBookName().toLowerCase().contains(query.toLowerCase())) {
                result.getInventoryList().add(inventoryList.get(i));
                continue;
            }

            if (inventoryList.get(i).getAuthor().toLowerCase().contains(query.toLowerCase())) {
                result.getInventoryList().add(inventoryList.get(i));
                continue;
            }

            if (inventoryList.get(i).getPublisher().toLowerCase().contains(query.toLowerCase())) {
                result.getInventoryList().add(inventoryList.get(i));
                continue;
            }

            if (inventoryList.get(i).getComment().toLowerCase().contains(query.toLowerCase())) {
                result.getInventoryList().add(inventoryList.get(i));
                continue;
            }

        }
        return result;
    }
}
