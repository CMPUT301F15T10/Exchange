package cmput301exchange.exchange;

import android.graphics.Picture;

import java.util.ArrayList;

/**
 * Created by Yishuo Wang on 2015/10/5.
 */
public class Book implements Shareable, Comparable<Book> {
    private String bookName; // Required
    private String genre;
    private int genreID; // Required
    private String author;
    private String publisher;
    private ArrayList <Picture> covers;
    private String ISBN;


    private int quality; // Required
    private int quantity;
    private String category;
    private String comment;
    private boolean sharable = false;

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
    }

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
    }

    public int getQuality(){
        return this.quality;
    }

    public void updateCategory(String updatedcat){
        this.category = updatedcat;
    }
    public String getCategory(){
        return this.category;
    }

    public void updateQuantity(int updateint){
        this.quantity = updateint;
    }

    public int getQuantity(){
        return quantity;

    }

    public String getComment() {
        return comment;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}
