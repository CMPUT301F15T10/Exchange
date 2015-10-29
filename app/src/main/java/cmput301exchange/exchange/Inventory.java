package cmput301f15t10.exchange;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Yishuo Wang on 2015/10/5.
 */
public class Inventory {
    private ArrayList <Book> myBooks;


    public ArrayList<Book> getInventory(){
        return myBooks;
    }

    public void add(Book book){
        myBooks.add(book);

    }

    public void rmItem(Book book){
        myBooks.remove(book);
    }

    public Boolean contains(Book book){
        return myBooks.contains(book);

    }
    public Book getBook(String title){
        Book returnItem = null;
        for (Book iterator : myBooks){
            if (iterator.getName().equalsIgnoreCase(title)){ //Apparently Android needs to have special comparison methods.
                returnItem = iterator;
            }
        }
        return returnItem;
    }

}
