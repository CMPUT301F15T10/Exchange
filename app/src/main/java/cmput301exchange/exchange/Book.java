package cmput301exchange.exchange;

import android.graphics.Picture;

import java.util.ArrayList;

import cmput301exchange.exchange.Interfaces.Shareable;
/**

 * Book is the object that makes up the inventory class
 * Book book = new Book(){
 *     book.updateTitle("Harry Potter");
 *     book.updateAuthor("J.K Rowling");
 *     ...
 *
 * }
 * @Author: Charles Laing
 * @version: 1.0.0
 */
public class Book implements Shareable, Comparable<Book> {

    private String bookName; // Required
    private String genre; //String to Contain Genre. A spinner is used to set this, so don't worry about the 10 requirement.
    private int genreID; // Required
    private String author; //String to contain the Author
    private String publisher;
    private ArrayList <Picture> covers; //List of the pictures. Be sure to use the photo's add method to ensure that the photos are under the size req.
    private String ISBN;
    private int quality; // value between 1 and 5
    private int quantity;
    private String category; //Some ~10 values
    private String comment; //Arbitrary string
    private boolean sharable = false; //Default items to false.

    public boolean isShareable(){
        /**
         * Returns a Boolean representing if the book is shareable or not.
         *
         */
        return this.sharable;
    }

    public void setShareable(boolean setValue){
        /**
         * Used to set the value of whether the book is shareable or not.
         * @param setValue the value of shareable you want to set.
         */
        this.sharable = setValue;
    }

    public void updateTitle(String title){
        /**
         * Updates the title of the Book. Accepts a String Object.
         * @param title The title you want to update
         */
        this.bookName = title;

    }

    public void updateAuthor(String author){
        /**
         * Updates the stored Author. Accepts a String Object.
         * @param author The author you want to append to the book.
         */
        this.author = author;
    }

    public String getName(){
        /**
         * Updates the Name of the Book. returns a String Object.
         *
         */
        return this.bookName;
    }

    public int compareTo(Book book){
        /**
         * Implementation of CompareTo, used for Shareable. This method compares two books together by Title.
         * @param book the book you need to compare this to.
         */
        return bookName.compareTo(book.getName());
    } //Method is used for searching, and searchable, and comparable.

    public void setQuality(int setCondition){
        /**
         * Sets the Quality of the Book on a scale of 1-5. Accepts an int.
         * @param setCondition an integer value representing the quality of the item
         */
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
        /**
         * Returns the Stored Quality value as an int
         *
         */
        return this.quality;
    } //Returns the Quality Integer

    public void updateCategory(String updatedcat){
        /**
         * Updates the Category of the book to an Arbitrary String.
         * The 10 Categories rule is imposed by the design of the Sorting, adding, and Browsing pages.
         * @param updatedcat the new category to assign to the item
         */
        this.category = updatedcat;
    }

    public String getCategory(){
        /**
         * Returns a String of the Book's Stored Category.
         */
        return this.category;
    }

    public void updateQuantity(int updateint){
        /**
         * Updates the Quantity. Accepts and int.
         * @param updateint Quantity that you want to append to the book
         */
        this.quantity = updateint;
    }

    public int getQuantity(){
        /**
         * Returns an int representing a quantity of books.
         */
        return quantity;
    }

    public String getComment() {
        /**
         * Returns a string representing the User comment on the Book.
         */
        return comment;
    }

    public void updateComment(String comment) {
        /**
         * Accepts a string and updates the comment.
         * @param comment the comment that you want to append to the book.
         */
        this.comment = comment;
    }

    public int getGenreID() {
        /**
         * Returns an int of the GenreID
         */
        return genreID;
    }

    public String getBookName() {
        /**
         * Returns a Book Name String.
         */
        return bookName;
    }

    public String getAuthor() {
        /**
         * Returns a string representing the Author.
         */
        return author;
    }

    public String getPublisher() {
        /**
         * Returns a String representing the Publisher.
         */
        return publisher;
    }

    public String toString(){
        /**
         * Used to accomodate the use of an ArrayAdapter. Returns a condensed, two line string
         * With the Author and the Title laid out like so
         *
         * Title: Harry Potter and the Philosopher's Stone
         * Author: J. K. Rowling
         */
        return "Title: "+this.getName()+"\n"+"Author: "+this.getAuthor();
    }
}
