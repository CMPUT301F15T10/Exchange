package cmput301exchange.exchange;


import java.util.ArrayList;

/**
 * Created by Charles on 11/2/2015.
 */
public class Inventory implements Searchable, Shareable {
    private User inventoryOwner;
    private ArrayList<Book> inventoryList = new ArrayList<>();

    private boolean shareable;
    public void setShareable(boolean bool){shareable = bool;}
    public boolean isShareable(){return shareable;}

    public Book Search(String searchString){
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
        return inventoryList;
    }

    public void add(Book Book) {this.inventoryList.add(Book);}

    public void rmItem(Book book){this.inventoryList.remove(book);}

    public boolean contains(Book book){return this.inventoryList.contains(book);}



}
