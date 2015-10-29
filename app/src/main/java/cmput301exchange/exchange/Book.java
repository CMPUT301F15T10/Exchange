package cmput301f15t10.exchange;

import android.content.SharedPreferences;
import android.graphics.Picture;

import java.util.ArrayList;

/**
 * Created by Yishuo Wang on 2015/10/5.
 */
public class Book implements Shareable, Searchable, Comparable<Book> {
    public String bookName; // Required
    public String genre;
    public int genreID; // Required
    public String author;
    public String publisher;
    public ArrayList <Picture> covers;
    public String ISBN;
    public int condition; // Required
    public String description;
    public Boolean isListed; // Required, may set default to True

    private boolean sharable = false;

    public boolean isShareable(){return sharable;}

    public void setShareable(boolean setValue){sharable = setValue;}

    public Book(String bookName, int genreID, int condition, Boolean isListed) {
        this.bookName = bookName;
        this.genreID = genreID;
        this.condition = condition;
        this.isListed = isListed;
    }

    public void updateTitle(String title){
        bookName = title;

    }
    public void updateAuthor(String author){
        this.author = author;
    }
    public String getName(){
        return bookName;
    }

    public int compareTo(Book book){
        return bookName.compareTo(book.getName());
    }

}
