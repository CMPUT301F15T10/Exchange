package cmput301exchange.exchange;

import android.graphics.Picture;

import java.util.ArrayList;

/**
 * Created by Yishuo Wang on 2015/10/5.
 */
public class Book implements Shareable, Comparable<Book> {
    private String bookName; // Required
    private String genre; //String to Contain Genre. A spinner is used to set this, so don't worry about the 10 requirement.
    private int genreID; // Required
    private String author; //String to contain the Author
    private String publisher;
    private ArrayList <Picture> covers; //List of the pictures. Be sure to use the photo's add method to ensure that the photos are under the size req.
    private String ISBN;
    private int quality; // Required
    private int quantity; //Value Between 1 and 5
    private String category; //Some ~10 values
    private String comment; //Arbitrary string
    private boolean sharable = false; //Default items to false.

    public boolean isShareable(){return this.sharable;}

    public void setShareable(boolean setValue){this.sharable = setValue;}

    public void updateTitle(String title){
        this.bookName = title;

    }

    public void updateAuthor(String author){
        this.author = author;
    }

    public String getName(){
        return this.bookName;
    }

    public int compareTo(Book book){
        return bookName.compareTo(book.getName());
    } //Method is used for searching, and searchable, and comparable.

    public void setQuality(int setCondition){
        //We have to ensure that the value of the condition never exceeds a certain value
        if (setCondition > 5){
            this.quality = 5;
        }
        else if (setCondition < 0){
            this.quality = 0;
        }
        else {
            this.quality = setCondition;
        }
    } //Sets to a value between 1 and 5

    public int getQuality(){
        return this.quality;
    } //Returns the Quality Integer

    public void updateCategory(String updatedcat){
        this.category = updatedcat;
    }

    public String getCategory(){
        return this.category;
    }

    public void updateQuantity(int updateint){
        this.quantity = updateint;
    }

    public int getQuantity(){return quantity;}

    public String getComment() {
        return comment;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }

    public int getGenreID() {
        return genreID;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String toString(){
        return "Title: "+this.getName()+"\n"+"Author: "+this.getAuthor();
    }
}
