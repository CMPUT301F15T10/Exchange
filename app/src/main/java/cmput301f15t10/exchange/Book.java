package cmput301f15t10.exchange;

import android.graphics.Picture;

import java.util.ArrayList;

/**
 * Created by Qinghan Yu on 2015/10/5.
 */
public class Book {
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
}
